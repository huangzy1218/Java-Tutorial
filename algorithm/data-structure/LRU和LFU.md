# LRU和LFU

## LRU

LRU是Least Recently Used的缩写，即最近最少使用，是一种常用的页面置换算法，选择最近最久未使用的页面予以淘汰。

```java
public class LRUCache {
    int cap;
    LinkedHashMap<Integer, Integer> cache;

    public LRUCache(int cap) {
        this.cap = cap;
        cache = new LinkedHashMap<>();
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        makeRecently(key);
        return cache.get(key);
    }

    public void put(int key, int val) {
        if (cache.containsKey(key)) {
            cache.put(key, val);
            makeRecently(key);
            return;
        }
        if (cache.size() >= cap) {
            // 利用迭代器获取第一个键
            int oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        cache.put(key, val);
    }
    
    private void makeRecently(int key) {
        int val = cache.get(key);
        cache.remove(key);
        cache.put(key, val);
    }
}
```

## LFU

*LFU*（least frequently used (*LFU*) page-replacement algorithm）。即最不经常使用页置换算法，要求在页置换时置换引用计数最小的页，因为经常使用的页应该有一个较大的引用次数。

```java
class LFUCache {
    private HashMap<Integer, Integer> keyToVal;
    private HashMap<Integer, Integer> keyToFreq;
    private HashMap<Integer, LinkedHashSet<Integer>> freqToKey;
    private int capacity;
    private int minFreq;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        keyToVal = new HashMap<>();
        keyToFreq = new HashMap<>();
        freqToKey = new HashMap<>();
    }
    
    public int get(int key) {
        if (!keyToVal.containsKey(key)) {
            return -1;
        }
        increaseFreKey(key);
        return keyToVal.get(key);
    }
    
    public void put(int key, int val) {
        if (capacity <= 0) {
            return;
        }
        // 原先存在，直接更改
        if (keyToVal.containsKey(key)) {
            keyToVal.put(key, val);
            increaseFreKey(key);
            return;
        }
        // 淘汰一个freq最小的key
        if (keyToVal.size() >= capacity) {
            removeMinFreqKey();
        }
        keyToVal.put(key, val);
        keyToFreq.put(key, 1);
        freqToKey.putIfAbsent(1, new LinkedHashSet());
        freqToKey.get(1).add(key);
        this.minFreq = 1;
    }

    private void removeMinFreqKey() {
        LinkedHashSet<Integer> minUsed = freqToKey.get(minFreq);
        int oldestKey = minUsed.iterator().next();
        keyToVal.remove(oldestKey);
        minUsed.remove(oldestKey);
        if (minUsed.isEmpty()) {
            // 不需要更新minFreq。在put时自动更新minFreq
            freqToKey.remove(minFreq);
        }
        keyToFreq.remove(oldestKey);
    }

    private void increaseFreKey(int key) {
        int freq = keyToFreq.get(key);
        keyToFreq.put(key, freq + 1);
        freqToKey.get(freq).remove(key);
        freqToKey.putIfAbsent(freq + 1, new LinkedHashSet<>());
        freqToKey.get(freq + 1).add(key);
        // 删除后freq为0
        if (freqToKey.get(freq).isEmpty()) {
            // 删除freq对应的键值对
            freqToKey.remove(freq);
            if (freq == minFreq) {
                // 如果这个freq恰好是minFreq，更新minFreq
                this.minFreq++;
            }
        }
    }
}
```