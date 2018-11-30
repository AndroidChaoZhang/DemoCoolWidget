package com.zc.democoolwidget;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test1 () {
        BigDecimal singleMoneyBig = new BigDecimal(".");
        System.out.print(singleMoneyBig);
    }

//    public static boolean isNumeric(String str) {
//        Pattern pattern = Pattern.compile(".*[1-9]+.*");
//        return pattern.matcher(str).matches();
//    }
//
//    public static String addSpaceByCredit(String content) {
//        content = content.replaceAll(" ", "");
//        StringBuilder newString = new StringBuilder();
//        for (int i = 1; i <= content.length(); i++) {
//            if (i % 4 == 0 && i != content.length()) {
//                newString.append(content.charAt(i - 1) + " ");
//            } else {
//                newString.append(content.charAt(i - 1));
//            }
//        }
//        return newString.toString();
//    }
//
//    @Test
//    public void test () {
//        List<String> lists = new ArrayList<>();
//        lists.add("1");
//        lists.add("2");
//        lists.add("3");
//        lists.add("4");
//        lists.add("5");
//        lists.add("6");
////        lists.forEach((string) -> System.out.print(string + "; "));
////        // 1.1使用匿名内部类
////        new Thread(new Runnable() {
////            @Override
////            public void run() {
////                System.out.println("Hello world !");
////            }
////        }).start();
//        // 2.1使用匿名内部类
////        Runnable race1 = new Runnable() {
////            @Override
////            public void run() {
////                System.out.println("Hello world !");
////            }
////        };
//
//        String[] players = {"Rafael Nadal", "Novak Djokovic",
//                "Stanislas Wawrinka", "David Ferrer",
//                "Roger Federer", "Andy Murray",
//                "Tomas Berdych", "Juan Martin Del Potro",
//                "Richard Gasquet", "John Isner"};
//// 1.1 使用匿名内部类根据 name 排序 players
////        Arrays.sort(players, new Comparator<String>() {
////            @Override
////            public int compare(String s1, String s2) {
////                return (s1.compareTo(s2));
////            }
////        });
//
//        // lamabda
//        lists.forEach(s -> System.out.print(s+"-"));
//// 1.1使用匿名内部类
//        new Thread(() -> System.out.println("Hello world !")).start();
//// 2.1使用匿名内部类
//        Runnable runnable = () -> System.out.println("Hello world !runnable");
//        runnable.run();
//
//        // 1.1 使用匿名内部类根据 name 排序 players
//        System.out.print(players[0]);
//        Arrays.sort(players,(o1, o2) -> o1.compareTo(o2) );
//        System.out.print(players[0]);
//    }


}