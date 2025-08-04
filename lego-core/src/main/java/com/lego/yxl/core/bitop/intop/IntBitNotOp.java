package com.lego.yxl.core.bitop.intop;


public class IntBitNotOp implements IntBitOp {
    private final IntBitOp intBitOp;

    public IntBitNotOp(IntBitOp intBitOp) {
        this.intBitOp = intBitOp;
    }

    @Override
    public boolean match(int value) {
        return !this.intBitOp.match(value);
    }

    @Override
    public String toSqlFilter(String fieldName) {

        IntMaskOp intMaskOp = (IntMaskOp) intBitOp;
        return new StringBuilder()
                .append("(")
                .append(fieldName)
                .append(" & ")
                .append(intMaskOp.getMask())
                .append(")")
                .append("<>")
                .append(intMaskOp.getMask())
                .toString();
    }
}