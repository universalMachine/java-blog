package com.wang.blog.helper.enums;

public enum USER_LOCK {
    LOCK(true),
    UNLOCK(false);

    private boolean isLocked;

    USER_LOCK(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
}
