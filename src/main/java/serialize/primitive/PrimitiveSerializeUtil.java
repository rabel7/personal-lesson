package serialize.primitive;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.junit.Assert;
import org.junit.Test;
import serialize.SerialTest;
import serialize.hessian.HessianSerializeUtil;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * protobuf  protostuff kyro fst thrift
 */
public class PrimitiveSerializeUtil {

    @Test
    public void test() throws IOException, ClassNotFoundException {
        SerialTest test = before();


        byte[] bytes = PrimitiveSerializeUtil.serialize(test);

        SerialTest test2 = (SerialTest) PrimitiveSerializeUtil.deserialize(bytes);

        Assert.assertEquals(test, test2);

        test2.setName("测试");
        Assert.assertNotEquals(test, test2);

    }

    private SerialTest before() {
        SerialTest test = new SerialTest();
        test.setName("123");
        test.setNum(10D);
        Map<String,Object> params = new HashMap<>();
        params.put("test", 1);
        test.setParam(params);
        return test;
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(os);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = os.toByteArray();
        os.close();
        return bytes;
    }

    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        return deserialize(bytes, null);
    }

    public static Object deserialize(byte[] bytes, Class cls) throws IOException, ClassNotFoundException {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        ObjectInputStream in = new ObjectInputStream(is);

        Object obj = in.readObject();
        is.close();
        return obj;
    }
}
