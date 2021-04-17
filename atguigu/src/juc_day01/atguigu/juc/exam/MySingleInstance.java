package juc_day01.atguigu.juc.exam;

public class MySingleInstance {

    private static SigleInstance instance;

    static {
        getInstanceA();
    }

    /**
     * 私有化，别人无法自己创建
     */
    private MySingleInstance() {

    }

    /**
     * 方式一：启动就创建，不好
     *
     * @return
     */
    public static SigleInstance getInstanceA() {
        if (instance == null) {
            instance = new SigleInstance();
        }
        return instance;
    }

    /**
     * 方式二：单线程创建，线程不安全，不推荐
     *
     * @return
     */
    public static SigleInstance getInstanceB() {
        if (instance == null) {
            instance = new SigleInstance();
        }
        return instance;
    }

    /**
     * 方式三： 多线程创建，但是效率不高
     *
     * @return
     */
    public static synchronized SigleInstance getInstanceC() {
        if (instance == null) {
            instance = new SigleInstance();
        }
        return instance;
    }

    /**
     * 方式四：、双重检查加锁（推荐）
     */
    public static SigleInstance getInstanceD() {
        // 先判断实例是否存在，若不存在再对类对象进行加锁处理
        if (instance == null) {
            synchronized (SigleInstance.class) {
                if (instance == null) {
                    instance = new SigleInstance();
                }
            }
        }
        return instance;
    }

    /**
     * 方式五：
     * 加载一个类时，其内部类不会同时被加载。一个类被加载，当且仅当其某个静态成员（静态域、构造器、静态方法等）被调用时发生。
     * 由于在调用 StaticSingleton.getInstance() 的时候，才会对单例进行初始化，而且通过反射，是不能从外部类获取内部类的属性的；
     * 由于静态内部类的特性，只有在其被第一次引用的时候才会被加载，所以可以保证其线程安全性。
     * 总结：
     * 优势：兼顾了懒汉模式的内存优化（使用时才初始化）以及饿汉模式的安全性（不会被反射入侵）。
     * 劣势：需要两个类去做到这一点，虽然不会创建静态内部类的对象，但是其 Class 对象还是会被创建，而且是属于永久带的对象。
     */
    public static SigleInstance getInstance() {
        return StaticSingletonHolder.instance;
    }

    /**
     * 饿汉式单例类:在类初始化时，已经自行实例化。
     */
    private static class StaticSingletonHolder {
        private static final SigleInstance instance = new SigleInstance();
    }

    private static final SigleInstance instance1 = new SigleInstance();

    public static SigleInstance getInstanceE() {
        return instance1;
    }
}
