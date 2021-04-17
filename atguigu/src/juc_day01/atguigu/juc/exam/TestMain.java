package juc_day01.atguigu.juc.exam;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @date 2020年2月10日
 */
public class TestMain {

    // “北京 杭州 杭州 北京”， 要求输入一个匹配模式（简单的以字符来写）， 比如 aabb, 来判断该字符串是否符合该模式， 举个例子：
    // * 1. pattern = "abba", str="北京 杭州 杭州 北京" 返回 ture
    // * 2. pattern = "aabb", str="北京 杭州 杭州 北京" 返回 false
    // * 3. pattern = "abc", str="北京 杭州 杭州 南京" 返回 false
    // * 4. pattern = "acac", str="北京 杭州 北京 广州" 返回 false
    //
    public static boolean wordPattern(String pattern, String str) {
        int num = 97;
        String[] arrayStr = str.split(" ");
        Set<String> hashSet = new LinkedHashSet<String>(10);
        for (int i = 0; i < arrayStr.length; i++) {
            hashSet.add(arrayStr[i]);
        }
        HashMap<String, String> hashMap = new HashMap<String, String>(10);
        for (String hashStr : hashSet) {
            Character ch = new Character((char) num);
            hashMap.put(hashStr, String.valueOf(ch));
            num++;
        }
        StringBuffer patternResult = new StringBuffer();
        for (int i = 0; i < arrayStr.length; i++) {
            String result = hashMap.get(arrayStr[i]);
            patternResult.append(result);
        }
        System.out.println("解析结果为patternResult=" + patternResult.toString());
        if (patternResult.toString().equals(pattern)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        //	        abba", str="北京 杭州 杭州 北京" 返回 ture
        String pattern = "abba";
        String str = "北京 杭州 杭州 北京";
        Boolean flag = wordPattern(pattern, str);
        System.out.println("【北京 杭州 杭州 北京】与abba 获取对比的结果：" + flag);
        // * 2. pattern = "aabb", str="北京 杭州 杭州 北京" 返回 false

        String pattern2 = "aabb";
        String str2 = "北京 杭州 杭州 北京";
        Boolean flag2 = wordPattern(pattern2, str2);
        System.out.println("【北京 杭州 杭州 北京】与aabb 获取对比的结果：" + flag2);
        // * 3. pattern = "abc", str="北京 杭州 杭州 南京" 返回 false
        String pattern3 = "abc";
        String str3 = "北京 杭州 杭州 南京";
        Boolean flag3 = wordPattern(pattern3, str3);
        System.out.println("【北京 杭州 杭州 南京】与abc 获取对比的结果：" + flag3);
        // * 4. pattern = "acac", str="北京 杭州 北京 广州" 返回 false
        String pattern4 = "acac";
        String str4 = "北京 杭州 北京 广州";
        Boolean flag4 = wordPattern(pattern4, str4);
        System.out.println("【北京 杭州 北京 广州】与acac 获取对比的结果：" + flag4);

    }
}
// 1/ hashSet,确定字符串String有几个参数，设定产生几个字母，对应字母
// 2/ HashSet，--》HashMap然后通过给出的字母来获取字符串，匹配上则返回true

/****
 * 运行结果  ：
 解析结果为patternResult=abba
 【北京 杭州 杭州 北京】与abba 获取对比的结果：true
 解析结果为patternResult=abba
 【北京 杭州 杭州 北京】与aabb 获取对比的结果：false
 解析结果为patternResult=abbc
 【北京 杭州 杭州 南京】与abc 获取对比的结果：false
 解析结果为patternResult=abac
 【北京 杭州 北京 广州】与acac 获取对比的结果：false
 */
 