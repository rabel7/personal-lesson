package proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * cgLib代理 不需要接口
 */
public class TestCGlibProxy {

    public static void main(String[] args) throws Exception {
        A a = new A();
        new CGlibProxyHandler<A>().createProxy(a).foo();
    }
}

class A {
    public void foo() {
        System.out.println("Method a of class A!");
    }
}

class CGlibProxyHandler<T> implements MethodInterceptor {
    private Object proxied;

    public T createProxy(Object proxied) {
        this.proxied = proxied;

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(proxied.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 创建代理对象
        return (T) enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("------before invoke");
        Object result = method.invoke(proxied, args);
        System.out.println("------after invoke");
        return result;
    }
}

