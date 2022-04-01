package com.github.sigureruri.fallingprojectile.registry;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import org.jetbrains.annotations.Nullable;

public class FPRegistry<K, V> {
    private final BiMap<K, V> map = HashBiMap.create();

    public void register(K key, V value) {
        map.put(key, value);
    }

    @Nullable
    public V getFromKey(K key) {
        return map.get(key);
    }

    @Nullable
    public K getFromValue(V value) {
        return map.inverse().get(value);
    }
}
