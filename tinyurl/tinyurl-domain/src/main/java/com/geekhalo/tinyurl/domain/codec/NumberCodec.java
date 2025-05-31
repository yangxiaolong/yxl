package com.geekhalo.tinyurl.domain.codec;

public interface NumberCodec {
    String encode(Long number);

    Long decode(String code);
}
