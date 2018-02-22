package netty.object;

import java.io.Serializable;

/**
 * rpc请求对象
 * todo 少了调用方的基本信息（ip、应用名）
 */
public class RpcRequest implements Serializable{

    /**
     * 每次请求的id
     */
    private String messageId;
    /**
     * 请求的类名
     */
    private String className;
    /**
     * 请求的方法名
     */
    private String methodName;
    /**
     * 方法的参数类型
     */
    private Class<?>[] typeParameters;
    /**
     * 对应的参数值
     */
    private Object[] parametersVal;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(Class<?>[] typeParameters) {
        this.typeParameters = typeParameters;
    }

    public Object[] getParametersVal() {
        return parametersVal;
    }

    public void setParametersVal(Object[] parametersVal) {
        this.parametersVal = parametersVal;
    }
}
