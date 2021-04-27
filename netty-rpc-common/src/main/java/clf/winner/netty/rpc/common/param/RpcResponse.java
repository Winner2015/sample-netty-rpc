package clf.winner.netty.rpc.common.param;

/**
 * @author chenlongfei
 */
public class RpcResponse {
    private String requestId;
    private int code;
    private String msg;
    private Object data;

    public String getRequestId () {
        return requestId;
    }

    public void setRequestId (String requestId) {
        this.requestId = requestId;
    }

    public int getCode () {
        return code;
    }

    public void setCode (int code) {
        this.code = code;
    }

    public String getMsg () {
        return msg;
    }

    public void setMsg (String msg) {
        this.msg = msg;
    }

    public Object getData () {
        return data;
    }

    public void setData (Object data) {
        this.data = data;
    }
}
