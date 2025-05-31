package com.geekhalo.tinyurl.domain;

import com.google.common.base.Preconditions;

public enum TinyUrlType {
    COMMON,
    EXPIRE_TIME{
        @Override
        public boolean canAccess(TinyUrl tinyUrl){
            return tinyUrl.checkStatus() && tinyUrl.checkTime();
        }

        @Override
        public void validate(TinyUrl tinyUrl) {
            Preconditions.checkArgument(tinyUrl.getExpireTime() != null, "type EXPIRE_TIME, ExpireTime can not be null");
        }
    },
    LIMIT_TIME{
        @Override
        public boolean canAccess(TinyUrl tinyUrl){
            return tinyUrl.checkStatus() && tinyUrl.checkCount();
        }

        @Override
        public boolean needUpdateAccessCount() {
            return true;
        }

        @Override
        public void validate(TinyUrl tinyUrl) {
            Preconditions.checkArgument(tinyUrl.getMaxCount() != null, "type LIMIT_TIME, MaxCount can not be null");
            Preconditions.checkArgument(tinyUrl.getAccessCount() != null, "type LIMIT_TIME, AccessCount can not be null");
        }
    };

    public boolean canAccess(TinyUrl tinyUrl) {
        return tinyUrl.checkStatus();
    }

    public boolean needUpdateAccessCount() {
        return false;
    }

    public void validate(TinyUrl tinyUrl) {

    }
}
