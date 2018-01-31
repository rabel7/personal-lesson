package serialize.hessian;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import javafx.beans.binding.ObjectExpression;
import org.junit.Assert;
import org.junit.Test;
import serialize.SerialTest;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * http://hessian.caucho.com/doc/hessian-overview.xtp#HessianSerialization
 */
public class HessianSerializeUtil {

    @Test
    public void test() throws IOException {
        SerialTest test = before();


        byte[] bytes = HessianSerializeUtil.serialize(test);

        SerialTest test2 = (SerialTest) HessianSerializeUtil.deserialize(bytes);

        Assert.assertEquals(test, test2);

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
        Hessian2Output out = new Hessian2Output(os);
        out.writeObject(obj);
        out.flush();
        byte[] bytes = os.toByteArray();
        os.close();
        return bytes;
    }

    public static Object deserialize(byte[] bytes) throws IOException {
       return deserialize(bytes, null);
    }

    public static Object deserialize(byte[] bytes, Class cls) throws IOException {
        ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        Hessian2Input in = new Hessian2Input(is);
        Object obj = in.readObject(cls);
        is.close();
        return obj;
    }
}
