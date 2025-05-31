package com.geekhalo.tinyurl.api;

public interface TinyUrlQueryApi {
    String PATH = "tinyurl/query";

    String queryByCode(String code);

    String accessByCode(String code);
}
