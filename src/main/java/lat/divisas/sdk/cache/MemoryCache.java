package lat.divisas.sdk.cache;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryCache {
    private final ConcurrentHashMap<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private final long defaultTtlMillis;

    public MemoryCache(long defaultTtlSeconds) {
        this.defaultTtlMillis = defaultTtlSeconds * 1000;
    }

    public <T> void set(String key, T item) {
        if (defaultTtlMillis <= 0) return;

        cache.put(key, new CacheEntry(item, System.currentTimeMillis() + defaultTtlMillis));

        // Simple cleanup to prevent OOM
        if (cache.size() > 1000) {
            int toRemove = cache.size() - 500;
            Iterator<Map.Entry<String, CacheEntry>> it = cache.entrySet().iterator();
            while (it.hasNext() && toRemove > 0) {
                it.next();
                it.remove();
                toRemove--;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        if (defaultTtlMillis <= 0) return null;

        CacheEntry entry = cache.get(key);
        if (entry != null) {
            if (entry.expiresAt > System.currentTimeMillis()) {
                return (T) entry.data;
            }
            cache.remove(key);
        }
        return null;
    }

    private static class CacheEntry {
        final Object data;
        final long expiresAt;

        CacheEntry(Object data, long expiresAt) {
            this.data = data;
            this.expiresAt = expiresAt;
        }
    }
}
