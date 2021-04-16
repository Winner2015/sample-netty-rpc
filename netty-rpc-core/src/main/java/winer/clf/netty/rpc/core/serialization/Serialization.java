package winer.clf.netty.rpc.core.serialization;

/**
 * @author chenlongfei
 */
public interface Serialization {
    <T> byte[] serialize (T obj);
    <T> T deSerialize (byte[] data, Class<T> clz);
}
