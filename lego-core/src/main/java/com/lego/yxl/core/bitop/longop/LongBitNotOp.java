package com.lego.yxl.core.bitop.longop;


public class LongBitNotOp implements LongBitOp {
    private final LongBitOp longBitOp;

    LongBitNotOp(LongBitOp longBitOp) {
        this.longBitOp = longBitOp;
    }

    @Override
    public boolean match(long value) {
        return !this.longBitOp.match(value);
    }

    @Override
    public String toSqlFilter(String fieldName) {

        LongMaskOp longMaskOp = (LongMaskOp) longBitOp;
        return new StringBuilder()
                .append("(")
                .append(fieldName)
                .append(" & ")
                .append(longMaskOp.getMask())
                .append(")")
                .append("<>")
                .append(longMaskOp.getMask())
                .toString();
    }
}