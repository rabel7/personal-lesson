package proxy.primitive;

/**
 * 代理最简单的例子
 * 没有用到动态代理，但是体现了代理的思想，B代理了A调用了A类的foo方法
 * http://www.zhihu.com/question/49337471/answer/115462314
 */

public class NoneProxy {
    public static void main(String[] args) {
        Interface i = new B();
        Consumer.consum(i);
    }
}

interface Interface {
    void foo();
}

class A implements Interface {
    public void foo() {
        System.out.println("Method a of class A!");
    }
}

class B implements Interface {
    public A a = new A();

    public void foo() {
        a.foo();
    }
}

class Consumer {
    public static void consum(Interface i) {
        i.foo();
    }
}

