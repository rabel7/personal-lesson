package proxy.primitive;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 与NoneProxy的差别在于，本例子缺少了B类，直接由方法newProxyInstance生成一个实现了代理接口的类
 * 如果要在方法A2实现的方法foo调用前执行先操作，就要生成一个代理类来执行
 * 缺陷：只能是代理接口,所以出现了cglib等动态代理的组件
 * <p>
 * 代理有两种：动态代理和静态代理
 */
public class TestDynamicProxy {

    public static void main(String[] args) {
        A2 a2 = new A2();
        new DynamicProxyHandler<Interface2>().createProxy(a2).foo();
    }
}


interface Interface2 {
    void foo();
}

class A2 implements Interface2 {
    public void foo() {
        System.out.println("Method a of class A!");
    }
}

class DynamicProxyHandler<T> implements InvocationHandler {
    private Object proxied;

    public T createProxy(Object proxied) {
        this.proxied = proxied;

        return (T) Proxy.newProxyInstance(
                proxied.getClass().getClassLoader(),
                proxied.getClass().getInterfaces(),
                this
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("------before invoke");
        Object result = method.invoke(proxied, args);
        System.out.println("------after invoke");
        return result;
    }
}