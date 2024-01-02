package com.veda.hrentgelt.dlstools.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class IndexFileData {
    private final File file;
    private ArrayList<Table> tables = new ArrayList<Table>();

    public IndexFileData(File indexFile) {
        this.file = indexFile;
    }

    public void add(Table t) {
        if (!tables.contains(t))
            tables.add(t);
    }

    public Table getTable(String name) {
        for (Table tab : tables)
            if (tab.NAME.equalsIgnoreCase(name))
                return tab;
        return null;
    }


    public File getFile() {
        return file;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }
}
