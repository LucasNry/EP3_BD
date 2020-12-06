package com.bdproject.backend.models;

import lombok.Getter;

@Getter
public class Table {
    private String tableName;

    public Table(String tableName) {
        this.tableName = tableName;
    }
}
