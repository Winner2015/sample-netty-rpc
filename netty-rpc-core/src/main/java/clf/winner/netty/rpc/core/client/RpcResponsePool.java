package clf.winner.netty.rpc.core.client;

import clf.winner.netty.rpc.common.param.RpcResponse;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

/**
 * @author chenlongfei
 */
public class RpcResponsePool {

    //requestID ——》 调用结果
    private ConcurrentHashMap<String, SynchronousQueue<RpcResponse>> queueMap = new ConcurrentHashMap<>();

    public void putRequest (String requestId) {
        //建立request的同步队列
        SynchronousQueue<RpcResponse> queue = new SynchronousQueue<>();
        queueMap.put(requestId, queue);
    }

    public void putResponse (RpcResponse response) {
        queueMap.get(response.getRequestId()).offer(response);
    }

    public RpcResponse takeResponse (String requestId) {
        RpcResponse response = null;
        try {
            //在requestId对应的response放入之前，会阻塞在这里
            response =  queueMap.get(requestId).take();
            queueMap.remove(requestId);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response;
    }
}
