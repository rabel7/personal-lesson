package netty.object;

import java.io.Serializable;

/**
 * rpc相应对象
 */
public class RpcResponse implements Serializable{

    /**
     * 请求id
     */
    private String messageId;
    /**
     * 错误信息
     */
    private String error;
    /**
     * 请求结果
     */
    private Object resultDesc;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(Object resultDesc) {
        this.resultDesc = resultDesc;
    }
}
