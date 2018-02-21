package collection.map;

import com.alibaba.dubbo.common.utils.LRUCache;

import java.util.Map;

/**
 * 通过继承LinkedHashMap并重写实现LRu （Least Recnetly used）算法 按照使用时间，最久没用的先被淘汰
 *
 * LFU（Least Frequently Used） 按照使用次数，使用最少的淘汰
 * FIFO first in first out  先进先出   如果一个数据最先进入缓存中，则应该最早淘汰掉
 */
public class LRULinkedHashMapTest {

    public static void main(String[] args) {

        LRUCache<String, String> cache = new LRUCache<>(5);

        cache.put("1","1");
        cache.put("2","2");
        cache.put("3","3");
        cache.put("4","4");
        cache.put("5","5");
        //1,2,3,4,5

        cache.get("2"); //1,3,4,5,2 //由于2被访问了，被移到最后队尾
        for (Map.Entry<String, String> entry : cache.entrySet()) {
            System.out.print(entry.getValue() + ", ");
        }
        System.out.println();

        cache.put("6", "6"); //3,4,5,2,6  插入6后，大于最大数，移除队首的元素
        for (Map.Entry<String, String> entry : cache.entrySet()) {
            System.out.print(entry.getValue() + ", ");
        }
        System.out.println();
    }
}
