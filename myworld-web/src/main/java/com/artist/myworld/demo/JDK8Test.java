package com.artist.myworld.demo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by asiamastor on 2016/10/27.
 */
public class JDK8Test {
    public static void main1(String[] args) throws Throwable {
        Optional<String> name = Optional.of("Sanaulla");
//        //传入参数为null，抛出NullPointerException.
//        Optional<String> someNull = Optional.of(null);
        //下面创建了一个不包含任何值的Optional实例
        //例如，值为'null'
        Optional empty = Optional.ofNullable(null);

        System.out.println(name.orElse("else value"));
        System.out.println(empty.orElseGet(()->"else value"));
        System.out.println(empty.orElseThrow(RuntimeException::new));

        //map方法执行传入的lambda表达式参数对Optional实例的值进行修改。
        //为lambda表达式的返回值创建新的Optional实例作为map方法的返回值。
        Optional<String> upperName = name.map((value) -> value.toUpperCase());
        System.out.println(upperName.orElse(""));

        //flatMap与map（Function）非常类似，区别在于传入方法的lambda表达式的返回类型。
        //map方法中的lambda表达式返回值可以是任意类型，在map函数返回之前会包装为Optional。
        //但flatMap方法中的lambda表达式返回值必须是Optionl实例。
        upperName = name.flatMap((value) -> Optional.of(value.toUpperCase()));
        System.out.println(upperName.orElse("No value found"));//输出SANAULLA

        //filter方法检查给定的Option值是否满足某些条件。
        //如果满足则返回同一个Option实例，否则返回空Optional。
        Optional<String> longName = name.filter((value) -> value.length() > 6);
        System.out.println(longName.orElse("The name is less than 6 characters"));//输出Sanaulla

        //另一个例子是Optional值不满足filter指定的条件。
        Optional<String> anotherName = Optional.of("Sana");
        Optional<String> shortName = anotherName.filter((value) -> value.length() > 6);
        //输出：name长度不足6字符
        System.out.println(shortName.orElse("The name is less than 6 characters"));

        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }
        map.computeIfPresent(3,
                (num, val) ->
                val + num);
        System.out.println(map.get(3));;

//        map.remove(3, "val3");
//        System.out.println(map.get(3));             // val33
//        map.remove(3, "val33");
//        System.out.println(map.get(3));
        map.merge(9, "val9", (value, newValue) ->
                value.concat(newValue));
        map.get(9);             // val9
        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        map.get(9);             // val9concat
    }
}
