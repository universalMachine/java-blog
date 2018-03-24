package com.wang.blog.service;

import com.wang.blog.helper.enums.TIME_ZONE;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class DateTimeService {
    public LocalDate getNowBeiJingDate(){
        return LocalDate.now(TIME_ZONE.BEIJING.getZone());
    }

    public LocalDateTime getNowBeiJingDateTime(){
        return LocalDateTime.now(TIME_ZONE.BEIJING.getZone());
    }

    public Date convertLocalDateTimeToDate(LocalDateTime localDateTime){
        return Timestamp.valueOf(localDateTime);
    }
}
