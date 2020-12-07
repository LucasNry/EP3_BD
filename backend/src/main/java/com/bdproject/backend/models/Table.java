package com.bdproject.backend.models;

import lombok.Getter;

@Getter
public abstract class Table {
    private String tableName;

    public Table(String tableName) {
        this.tableName = tableName;
    }
}
