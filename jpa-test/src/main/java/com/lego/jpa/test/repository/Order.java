package com.lego.jpa.test.repository;

import com.lego.jpa.test.command.CreateOrderCommand;
import com.lego.jpa.test.command.PaySuccessCommand;
import com.lego.jpa.test.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
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

    //  收货地址
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_address_id")
    private OrderAddress address;

    //  订单项
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
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
