package collection.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 1.8之前的实现和之后实现略有不同
 * 1.8的实现优化了hash冲突严重的情况下，链表的结构会转换为红黑树的结构
 */
public class HashMapTest {

    public static void main(String[] args) {
        Map<String, String> keySetMap = new HashMap<>();
        Map<String, String> entrySetMap = new HashMap<>();

        for (int i = 0; i < 100000; i++) {
            keySetMap.put("" + i, "keySet");
        }
        for (int i = 0; i < 100000; i++) {
            entrySetMap.put("" + i, "entrySet");
        }

        long startTimeOne = System.currentTimeMillis();
        Iterator<String> keySetIterator = keySetMap.keySet().iterator();
        while (keySetIterator.hasNext()) {
            String key = keySetIterator.next();
            String value = keySetMap.get(key);
//            System.out.println(value);
        }

        System.out.println("keyset spent times:"
                + (System.currentTimeMillis() - startTimeOne));

        long startTimeTwo = System.currentTimeMillis();

        Iterator<Map.Entry<String, String>> entryKeyIterator = entrySetMap
                .entrySet().iterator();
        while (entryKeyIterator.hasNext()) {
            Map.Entry<String, String> e = entryKeyIterator.next();
//            System.out.println(e.getValue());
        }
        System.out.println("entrySet spent times:"
                + (System.currentTimeMillis() - startTimeTwo));


        Map<String, Object> m = new HashMap<>();
//        int h = 0;
//        System.out.println((h = "1".hashCode()) ^ (h >>> 16));
        m.put("a", "rrr1");
        m.put("b", "tt9");
        m.put("c", "tt8");
        m.put("d", "g7");
        m.put("e", "d6");
        m.put("f", "d4");
        m.put("g", "d4");
        m.put("h", "d3");
        m.put("i", "d2");
        m.put("j", "d1");
        m.put("k", "1");
        m.put("o", "2");
        m.put("p", "3");
        m.put("q", "4");
        m.put("r", "5");
        m.put("s", "6");
        m.put("t", "7");
        m.put("u", "8");
        m.put("v", "9");
        m.put("5", "9");
        m.put("4", "9");
        m.put("3", "9");
        m.put("2", "9");
        m.put("1", "9");


    }
}
