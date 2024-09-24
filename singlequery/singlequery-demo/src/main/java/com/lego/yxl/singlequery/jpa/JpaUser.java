package com.lego.yxl.singlequery.jpa;

import com.lego.yxl.singlequery.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "t_user")
public class JpaUser implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer status;

    private Date birthAt;

    private String mobile;
}
