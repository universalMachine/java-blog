package com.wang.blog.helper.config.ParamDataBInd;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JsonArg {
    /**
     * select json path
     * @return
     */
    String value() ;
}
