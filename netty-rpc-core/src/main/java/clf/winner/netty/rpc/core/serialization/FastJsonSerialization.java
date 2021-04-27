package clf.winner.netty.rpc.core.serialization;

import com.alibaba.fastjson.JSON;

/**
 * @author chenlongfei
 */
public class FastJsonSerialization implements Serialization {
    @Override
    public <T> byte[] serialize (T obj) {
        return JSON.toJSONBytes(obj);
    }

    @Override
    public <T> T deSerialize (byte[] data, Class<T> clz) {
        return JSON.parseObject(data, clz);
    }
}
