package com.zc.democoolwidget.basic.annotation;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotation type {@code java.lang.annotation.Repeatable} is used to indicate that the annotation type whose declaration it (meta-)annotates is repeatable. The value of @Repeatable indicates the containing annotation type for the repeatable annotation type.
 * @Repeatable 注解是用于声明其它类型注解的元注解，来表示这个声明的注解是可重复的。
 *              @Repeatable的值是另一个注解，其可以通过这个另一个注解的值来包含这个可重复的注解。
 *
 * Roles注解里面的值必须是value
 * @Repeatable 括号内的就相当于用来保存该注解内容的容器。
 *
 * 注意事项：
 *      1。@Repeatable 所声明的注解，其元注解@Target的使用范围要比@Repeatable的值声明的注解中的@Target的范围要大或相同，否则编译器错误
 *          即@Target Person >= Roles
 *      2。@Repeatable 注解声明的注解的元注解@Retention的周期要比@Repeatable的值指向的注解的@Retention得周期要小或相同。
 *          即@Retention Person <= Roles
 * */
public class AnnotationRepeatable {


    @Repeatable(Roles.class)
    public @interface Person {
        String role() default "";
    }


    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Roles {
        Person[] value();
    }


    @Person(role="CEO")
    @Person(role="husband")
    @Person(role="father")
    static class Man {
    }

    @Test
    public void test () {
        //1
//        Annotation[] annotations = Man.class.getAnnotations();
//        Roles roles = (Roles) annotations[0];
//        for (Person person : roles.value()) {
//            System.out.println(person.role());
//        }

        //  2  严谨
        if (Man.class.isAnnotationPresent(Roles.class)) {
            Roles annotation = Man.class.getAnnotation(Roles.class);
            for (Person person : annotation.value()) {
                System.out.println(person.role());
            }
        }
    }
}
