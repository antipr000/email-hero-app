package com.example.emailhero.db;

import com.example.emailhero.exceptions.DataNotFoundException;

public interface Database<T> {
    public void put(String key, T value);
    public boolean containsKey(String key);
    public T get(String key) throws DataNotFoundException;
    public void remove(String key) throws DataNotFoundException;
}
