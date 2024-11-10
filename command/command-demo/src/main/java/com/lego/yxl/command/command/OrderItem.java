package com.lego.yxl.command.command;

import com.lego.yxl.repository.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
@Entity(name = "CommandOrderItem")
@Table(name = "command_order_item")
@Setter(AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "price")
    private int price;

    @Column(name = "amount")
    private int amount;

    public static OrderItem create(Product product, Integer amount) {
        OrderItem orderItem = new OrderItem();
        orderItem.setProductName(product.getName());
        orderItem.setPrice(product.getPrice());
        orderItem.setAmount(amount);
        return orderItem;
    }

    public int getRealPrice() {
        return price * amount;
    }
}
