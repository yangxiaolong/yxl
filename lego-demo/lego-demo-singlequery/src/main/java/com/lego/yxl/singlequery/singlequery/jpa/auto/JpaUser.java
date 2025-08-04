package com.lego.yxl.singlequery.singlequery.jpa.auto;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;


@Entity(name = "JpaUser3")
@Table(name = "t_user", schema = "lego", catalog = "")
public class JpaUser {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "birth_at", nullable = true)
    private Timestamp birthAt;
    @Basic
    @Column(name = "mobile", nullable = true, length = 255)
    private String mobile;
    @Basic
    @Column(name = "name", nullable = true, length = 255)
    private String name;
    @Basic
    @Column(name = "status", nullable = true)
    private Integer status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Timestamp getBirthAt() {
        return birthAt;
    }

    public void setBirthAt(Timestamp birthAt) {
        this.birthAt = birthAt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JpaUser jpaUser = (JpaUser) o;
        return id == jpaUser.id && Objects.equals(birthAt, jpaUser.birthAt) && Objects.equals(mobile, jpaUser.mobile) && Objects.equals(name, jpaUser.name) && Objects.equals(status, jpaUser.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, birthAt, mobile, name, status);
    }
}
