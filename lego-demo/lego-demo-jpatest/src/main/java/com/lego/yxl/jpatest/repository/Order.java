package com.lego.yxl.jpatest.repository;

import com.lego.yxl.jpatest.command.CreateOrderCommand;
import com.lego.yxl.jpatest.command.PaySuccessCommand;
import com.lego.yxl.jpatest.enums.OrderStatus;
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

//N+1问题, 2. 使用实体图（Entity Graph，JPA 2.1+，推荐，更灵活）
@NamedEntityGraph(
        name = "Order.withItems",  // 实体图名称
        attributeNodes = @NamedAttributeNode("items")  // 需要加载的关联属性
)

public class Order {

    //    @Id
//    // 改用H2支持的SEQUENCE策略
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq")
//    @SequenceGenerator(
//            name = "order_seq",          // 生成器名称
//            sequenceName = "tb_order_seq", // 数据库中序列的名称
//            allocationSize = 50          // 批量分配ID（与批处理大小匹配）
//    )
    @Id
    // 改用H2支持的SEQUENCE策略
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

    //解决N+1问题, 方式1 使用batch
    //先查询order表, 再使用in ()查询OrderItem表
    //  订单项
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @BatchSize(size = 20)
//    @Fetch(value = FetchMode.JOIN)  //只能根据id查询才有效
//    @JoinColumn(name = "order_id")
//    private List<OrderItem> items = new ArrayList<>();


    // 关键：mappedBy的值是“维护方（OrderItem）中关联当前实体（Order）的属性名”
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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
                .map(s -> OrderItem.create(s, order))
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
