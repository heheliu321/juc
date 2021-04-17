package juc_day01.atguigu.juc.exam;

public class SigleInstance {

    public static void main(String[] args) {
        SigleInstance instance = MySingleInstance.getInstanceD();
        System.out.println(instance);
    }
}
