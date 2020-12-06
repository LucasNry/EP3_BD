package com.bdproject.backend.configuration;

public class Config {
    public static final String DB_PORT = "5432";
    public static final String DB_INSTANCE_NAME = "conflitos-belicos";
    public static final String DB_URL = String.format("jdbc:postgresql://conflitos-belicos.cxrn7b7s4ui9.us-east-1.rds.amazonaws.com:%s/%s", DB_PORT, DB_INSTANCE_NAME);
    public static final String DB_USER = "each2020bd";
    public static final String DB_PASSWORD = "each2020bd1";
}
