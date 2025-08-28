package com.lego.yxl.jpatest.repository;

import com.lego.yxl.jpatest.command.ProductForBuy;
import com.lego.yxl.jpatest.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity(name = "CommandOrderItem")
@Table(name = "tb_order_item")
@Setter(AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private int price;

    @Column(name = "amount")
    private int amount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // 维护方：通过@ManyToOne和@JoinColumn指定外键
    @ManyToOne
    @JoinColumn(name = "order_id") // 数据库中外键列名
    private Order order; // 这个属性名就是Order中mappedBy的值

    public int getRealPrice() {
        return price * amount;
    }

    public static OrderItem create(ProductForBuy product) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductId(product.getProductId());
        orderItem.setProductName(product.getProductName());
        orderItem.setPrice(product.getPrice());
        orderItem.setAmount(product.getAmount());

        orderItem.init();
        return orderItem;
    }

    private void init() {
        setStatus(OrderStatus.NONE);
    }

    public void paySuccess() {
        setStatus(OrderStatus.PAID);
    }

}
