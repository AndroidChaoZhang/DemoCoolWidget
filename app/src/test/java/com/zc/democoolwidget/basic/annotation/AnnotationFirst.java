package com.zc.democoolwidget.basic.annotation;

import android.support.annotation.StringDef;

import org.junit.Test;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.zc.democoolwidget.basic.annotation.AnnotationFirst.Sex.MAN;
import static com.zc.democoolwidget.basic.annotation.AnnotationFirst.Sex.WOMEN;

/**
 * 注解：Annotation,java标注，jdk1.5引入的解释机制   记得查看Annotation的源码
 *
 * 3个重要的组成部分：Annotation.java ElementType.java RetentionPolicy.java
 *
 * 作用：1。生成文档                           这是最常见的，也是java 最早提供的注解。常用的有@see @param @return 等；
 *      2。追踪代码依赖性，实现替代配置文件功能
 *      3。在编译时进行格式检查                 如@Override放在方法前，如果你这个方法并不是覆盖了超类方法，则编译时就能检查出；
 *
 * 作用在代码的注解
 *      @Override - 检查该方法是否是重写方法。如果发现其父类，或者是引用的接口中并没有该方法时，会报编译错误。
 *      @Deprecated - 标记过时方法。如果使用该方法，会报编译警告。
 *      @SuppressWarings - 指示编译器去忽略注解中声明的警告。
 *      @SafeVarargs - Java7 开始支持，忽略任何使用参数为泛型变量的方法或构造函数调用产生的警告。
 *      @FunctionalInterface - Java 8开始支持，标识一个匿名函数或函数式接口。
 *
 * 元注解
 *      @Documented - 标记这些注解是否包含在用户文档中。
 *      @Retention - 标识这个注解怎么保存，是只在代码中，还是编入class文件中，或者是在运行时可以通过反射访问。
 *      @Target - 标记这个注解应该是哪种 Java 成员。
 *      @Inherited - 标记这个注解是继承于哪个注解类(默认 注解并没有继承于任何子类)
 *      @Repeatable - Java 8开始支持，标识某注解可以在同一个声明上使用多次。
 *
 * 注解的使用场景
 * - 提供信息给编译器： 编译器可以利用注解来探测错误和警告信息
 * - 编译阶段时的处理： 软件工具可以用来利用注解信息来生成代码、Html文档或者做其它相应处理。
 * - 运行时的处理： 某些注解可以在程序运行的时候接受代码的提取
 *   值得注意的是，注解不是代码本身的一部分。
 *
 *
 *   使用枚举（Enum）的弊端：
 *          1。每个枚举值都是一个单例对象，会增加内存消耗
 *          2。较多的使用枚举会增加DEX文件的大小，增加运行的IO开销
 *   所以使用@IntDef/@StringDef + @interface 替代  枚举
 * */

public class AnnotationFirst {

    @Sex
    private String sex = WOMEN;

    /**
     * 只能使用 {@link #MAN} {@link #WOMEN}
     */
    @Documented // 表示开启Doc文档
    @StringDef({
        MAN,WOMEN
    })
    @Target({//表示注解作用范围: 参数注解，成员注解，方法注解
            ElementType.PARAMETER,
            ElementType.FIELD,
            ElementType.METHOD,
            ElementType.TYPE
    })
    @Retention(RetentionPolicy.SOURCE)//表示注解所存活的时间,在运行时,而不会存在 .class 文件中
    @interface Sex {
        String MAN = "M";//默认是 public static final
        String WOMEN = "F";
    }

    public String getSex() {
        return sex;
    }

    public void setSex(@Sex String sex) {
        this.sex = sex;
    }

    @Test
    public void test() {
        AnnotationFirst first = new AnnotationFirst();
//        first.setSex(MAN);
        System.out.println(first.getSex());
    }
}
