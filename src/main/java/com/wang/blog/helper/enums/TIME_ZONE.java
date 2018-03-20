package com.wang.blog.helper.enums;

import java.time.ZoneId;

public enum TIME_ZONE {
    BEIJING(ZoneId.of("GMT+08:00"));

    private ZoneId zone;

    TIME_ZONE(ZoneId zone) {
        this.zone = zone;
    }

    public ZoneId getZone() {
        return zone;
    }

    public void setZone(ZoneId zone) {
        this.zone = zone;
    }
}
