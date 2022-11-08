package com.sdjpa.custom_annotations;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface EncryptedString {
    public String key() default "";
}
