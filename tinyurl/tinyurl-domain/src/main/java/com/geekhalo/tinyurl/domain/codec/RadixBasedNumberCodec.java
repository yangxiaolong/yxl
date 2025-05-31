package com.geekhalo.tinyurl.domain.codec;

public class RadixBasedNumberCodec implements NumberCodec {
    private final int radix;
    public RadixBasedNumberCodec(int radix){
        this.radix = radix;
    }

    @Override
    public String encode(Long id) {
        return Long.toString(id, radix);
    }

    @Override
    public Long decode(String str) {
        return Long.valueOf(str, radix);
    }

}
