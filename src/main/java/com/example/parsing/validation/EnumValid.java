package com.example.parsing.validation;

import com.example.parsing.domain.ParsingType;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 해당 annotation이 실행 할 ConstraintValidator 구현체를 `EnumValidator`로 지정합니다
@Constraint(validatedBy = {EnumValidator.class})
// 해당 annotation은 메소드, 필드, 파라미터에 적용 가능
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValid {
    String message() default "Invalid Type.";
    Class<? extends java.lang.Enum<?>> enumClass();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}