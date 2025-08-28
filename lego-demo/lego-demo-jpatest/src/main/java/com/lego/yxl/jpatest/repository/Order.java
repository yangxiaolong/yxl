package com.lego.yxl.jpatest.repository;

import com.lego.yxl.jpatest.command.CreateOrderCommand;
import com.lego.yxl.jpatest.command.PaySuccessCommand;
import com.lego.yxl.jpatest.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "tb_order")
@Setter(AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "price")
    private int price;

    @Column(name = "vsn")
    @Version
    private int vsn;

    //  收货地址
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_address_id")
    private OrderAddress address;

    //  订单项
    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @BatchSize(size = 20)
//    @Fetch(value = FetchMode.JOIN)  //只能根据id查询才有效
    @JoinColumn(name = "order_id")
    private List<OrderItem> items = new ArrayList<>();*/


    // 关键：mappedBy的值是“维护方（OrderItem）中关联当前实体（Order）的属性名”
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> items = new ArrayList<>();

    public static Order create(CreateOrderCommand command) {
        // 创建内存对象
        Order order = new Order();
        order.setUserId(command.getUserId());

        String userAddress = command.getUserAddress();
        if (StringUtils.hasText(userAddress)) {
            // 设置收获地址
            OrderAddress orderAddress = new OrderAddress();
            orderAddress.setDetail(userAddress);
            order.setAddress(orderAddress);
        }

        // 添加订单项
        command.getProducts()
                .stream()
                .map(OrderItem::create)
                .forEach(order::addOrderItem);

        order.init();
        return order;
    }

    private void init() {
        setStatus(OrderStatus.NONE);
    }

    private void addOrderItem(OrderItem orderItem) {
        this.price = this.price + orderItem.getRealPrice();
        this.items.add(orderItem);
    }

    public void modifyAddress(String address) {
        if (this.address == null) {
            this.address = new OrderAddress();
        }
        this.address.modify(address);
    }

    public void paySuccess(PaySuccessCommand paySuccessCommand) {
        this.setStatus(OrderStatus.PAID);
        this.items.forEach(OrderItem::paySuccess);
    }

}
