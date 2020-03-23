package com.zc.democoolwidget.basic.annotation;

import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 元注解：一种基本注解，可以应用到其他注解上
 * 元注解有5种：
 * @Documented 将注解中的元素包含到javadoc文档中
 *
 * @Retention 保留期的意思，注解的存活时间 1。RetentionPolicy.SOURCE 只在源代码保留，编译器编译时丢弃
 *                                      2。RetentionPolicy.CLASS (默认行为)只在编译器编译时保留，不会加载到jvm中
 *                                      3。RetentionPolicy.RUNTIME 加载到jvm中，保留到程序运行，程序运行通过反射可以获取到它们
 *                                      「java  - source被丢弃 -> class - class被丢弃 > jvm （runtime）」
 *
 * @Target 注解运用（限定）到的场景    1. ElementType.ANNOTATION_TYPE 可以给一个注解进行注解
 *                                  2. ElementType.CONSTRUCTOR------构造器声明               可以给构造方法进行注解
 *                                  3. ElementType.FIELD------------域声明（包括 enum 实例）  可以给属性进行注解
 *                                  4. ElementType.LOCAL_VARIABLE---局部变量声明              可以给局部变量进行注解
 *                                  5. ElementType.METHOD-----------方法声明                 可以给方法进行注解
 *                                  6. ElementType.PACKAGE----------包声明                   可以给一个包进行注解
 *                                  7. ElementType.PARAMETER--------参数声明              可以给一个方法内的参数进行注解
 *                                  8. ElementType.TYPE-------------类，接口（包括注解类型）或enum声明
 *                                  9. ElementType.TYPE_PARAMETER---表示该注解能写在类型变量的声明语句中。 java8新增
 *                                  10.ElementType.TYPE_USE---------表示该注解能写在使用类型的任何语句中。 java8新增
 * @Inherited  允许子类继承父类的注解
 * @Repeatable 标记的注解可以多次应用于声明或类型中
 * */
public class AnnotationMeta {

    @Target({ElementType.METHOD})
    @interface Person {
        String name() default "";
    }

    class Man {

        @Person(name = "test")
        public void getName (String userName,int age) {
            System.out.println("userName = "+userName+", age = "+age);
        }
    }

    @Test
    public void test () throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Man man = new Man();
        Class<Man> manClass = Man.class;
        Method nameMethod = manClass.getMethod("getName", String.class, int.class);
        nameMethod.invoke(man,new Object[]{"Hello",20});


        if (nameMethod.isAnnotationPresent(Person.class)) {
            Person annotation = nameMethod.getAnnotation(Person.class);
            String name = annotation.name();
            System.out.printf("name="+name);
        }

    }
}
