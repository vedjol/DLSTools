package com.veda.hrentgelt.dlstools.util;

import java.util.ArrayList;
import java.util.Arrays;

public class Table {
    public final String NAME;
    public final String URL;
    public final ArrayList<String> keys;

    public Table(String name, String fullURL, String... keys) {
        this(name, fullURL, new ArrayList<>(Arrays.asList(keys)));
    }

    public Table(String name, String fullURL, ArrayList<String> keys) {
        this.NAME = name;
        this.URL = fullURL;
        this.keys = keys;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Table)
            return ((Table) obj).NAME.equals(NAME);
        return false;
    }

    public int getIndexOf(String key) {
        for(int i = 0; i < keys.size(); i++){
            String currentKey = keys.get(i);
            if(currentKey != null && currentKey.equalsIgnoreCase(key)){
                return i;
            }
        }
        return -1;
    }
}
