package com.lego.yxl.core;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Sort<FIELD extends Enum<FIELD> & OrderField> {

    private final List<Order<FIELD>> orders = new ArrayList<>();

    public static <FIELD extends Enum<FIELD> & OrderField> Sort of(Order<FIELD>... orders) {
        Sort sort = new Sort();
        for (Order<FIELD> order : orders) {
            sort.addOrder(order);
        }
        return sort;
    }

    public static <FIELD extends Enum<FIELD> & OrderField> Sort of(FIELD field, Direction direction) {
        Order<FIELD> order = Order.<FIELD>builder()
                .direction(direction)
                .orderField(field).build();
        return of(order);
    }

    public static <FIELD extends Enum<FIELD> & OrderField> Sort of(FIELD field) {
        return of(field, Direction.ASC);
    }

    public static <FIELD extends Enum<FIELD> & OrderField> Sort of() {
        return new Sort();
    }

    public void addOrder(Order<FIELD> order) {
        this.orders.add(order);
    }

    public void addOrder(FIELD field, Direction direction) {
        this.orders.add(
                Order.<FIELD>builder()
                        .direction(direction)
                        .orderField(field)
                        .build())
        ;
    }


    @Data
    @Builder
    public static class Order<FIELD extends Enum<FIELD> & OrderField> {
        private Direction direction;
        private FIELD orderField;

        public String toOrderByClause() {
            if (orderField == null) {
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(orderField.getColumnName());
            if (direction != null) {
                stringBuilder.append(' ')
                        .append(direction.name());
            }
            return stringBuilder.toString();
        }
    }

    public enum Direction {
        ASC, DESC;
    }

    public String toOrderByClause() {
        if (CollectionUtils.isEmpty(orders)) {
            return null;
        }
        return orders.stream()
                .map(Order::toOrderByClause)
                .filter(StringUtils::isNoneBlank)
                .collect(Collectors.joining(",", " ", " "));
    }
}
