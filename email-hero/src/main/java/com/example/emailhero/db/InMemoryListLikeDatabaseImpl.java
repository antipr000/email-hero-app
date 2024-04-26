package com.example.emailhero.db;

import com.example.emailhero.exceptions.DataNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@Component
public class InMemoryListLikeDatabaseImpl<T> implements Database<T> {
    private final Map<String, T> store;

    public InMemoryListLikeDatabaseImpl() {
        this.store = new TreeMap<>();
    }


    @Override
    public void put(String key, T value) {
        store.put(key, value);
    }

    @Override
    public boolean containsKey(String key) {
        return store.containsKey(key);
    }

    @Override
    public T get(String key) throws DataNotFoundException {
        if (!store.containsKey(key)) {
            throw new DataNotFoundException("No data with key exists!");
        }
        return store.get(key);
    }

    @Override
    public void remove(String key) throws DataNotFoundException {
        if (!store.containsKey(key)) {
            throw new DataNotFoundException("No data with key exists!");
        }
        store.remove(key);
    }
}
