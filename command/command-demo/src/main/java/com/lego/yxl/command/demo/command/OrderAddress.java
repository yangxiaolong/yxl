package com.lego.yxl.command.demo.command;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "command_order_address")
public class OrderAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String detail;
}
