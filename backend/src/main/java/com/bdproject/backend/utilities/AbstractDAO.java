package com.bdproject.backend.utilities;

import com.bdproject.backend.annotations.Attribute;
import com.bdproject.backend.annotations.PrimaryKey;
import com.bdproject.backend.models.Division;
import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.models.Table;

import javax.activation.UnsupportedDataTypeException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.rmi.UnexpectedException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractDAO {

    private static final String FIELD_EXPERSSION_TEMPLATE = "%s='%s' and ";
    private static final String SELECT_QUERY_BASE = "select * from %s";
    private static final String FILTER_BASE = " where ";
    private static final String INSERT_QUERY_BASE = "INSERT INTO %s (%s) VALUES(%s);";
    private static final String UPDATE_QUERY_BASE = "UPDATE %s set %s where %s";
    private static final String SEPARATOR = ", ";
    private static final String EXPRESSION_ENDING_CHAR = ";";

    public abstract <T extends Table> List<T> retrieveFromModel(T object) throws IllegalAccessException, SQLException, InvocationTargetException, InstantiationException, NoSuchMethodException, UnsupportedDataTypeException;

    public abstract <T extends Table> void saveFromModel(T object) throws IllegalAccessException, SQLException;

    public abstract void saveDivisionIntoMilitaryGroup(Division division, MilitaryGroup militaryGroup) throws IllegalAccessException, SQLException, InstantiationException, NoSuchMethodException, InvocationTargetException, UnexpectedException, UnsupportedDataTypeException;

    public abstract void updateDivision(Division division) throws IllegalAccessException, SQLException;

    @SuppressWarnings("unchecked")
    protected String mapObjectToInsertQuery(Table instance) throws IllegalAccessException {
        StringBuilder propertyString = new StringBuilder();
        StringBuilder valueString = new StringBuilder();

        Class<Table> objectClazz = (Class<Table>) instance.getClass();
        Field[] fields = objectClazz.getDeclaredFields();

        for (Field field : fields) {
            String fieldName;

            if (field.isAnnotationPresent(PrimaryKey.class)) {
                field.setAccessible(true);
                fieldName = field.getAnnotation(PrimaryKey.class).name();
            } else if (field.isAnnotationPresent(Attribute.class)) {
                field.setAccessible(true);
                fieldName = field.getAnnotation(Attribute.class).name();
            } else {
                continue;
            }

            Object fieldValue = field.get(instance);

            if (String.class.equals(field.getType())) {
                fieldValue = "'" + fieldValue + "'";
            }

            if (fieldValue != null) {
                propertyString.append(fieldName).append(SEPARATOR);
                valueString.append(fieldValue).append(SEPARATOR);
            }
        }

        propertyString.delete(propertyString.length() - 2, propertyString.length());

        valueString.delete(valueString.length() - 2, valueString.length());

        return String.format(INSERT_QUERY_BASE, instance.getTableName(), propertyString.toString(), valueString.toString());
    }

    @SuppressWarnings("unchecked")
    protected String mapObjectToUpdateQuery(Division instance) throws IllegalAccessException { // This function sucks, but I'm too fucking tired to write it any better
        return String.format(UPDATE_QUERY_BASE,
                instance.getTableName(),
                String.format("codigog = %s", instance.getCodigoG()),
                String.format("nrodivisao = %s", instance.getNroDivisao())
        );
    }

    @SuppressWarnings("unchecked")
    protected String mapObjectToSelectQuery(Table instance) throws IllegalAccessException {
        StringBuilder finalQuery = new StringBuilder();

        Class<Table> objectClazz = (Class<Table>) instance.getClass();
        Field[] fields = objectClazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                field.setAccessible(true);
                String fieldName = field.getAnnotation(PrimaryKey.class).name();
                Object fieldValue = field.get(instance);

                if (fieldValue != null) {
                    String fieldExpression = String.format(FIELD_EXPERSSION_TEMPLATE, fieldName, fieldValue);
                    finalQuery.append(fieldExpression);
                    field.setAccessible(false);
                }
            }
        }

        String selectBase = String.format(SELECT_QUERY_BASE, instance.getTableName());

        if (finalQuery.length() > 0) {
            finalQuery.delete(finalQuery.length() - 5, finalQuery.length());
            finalQuery.insert(0, selectBase + FILTER_BASE);
            finalQuery.append(EXPRESSION_ENDING_CHAR);
        } else {
            finalQuery.append(selectBase).append(EXPRESSION_ENDING_CHAR);
        }


        return finalQuery.toString();
    }

    @SuppressWarnings("unchecked")
    protected <T extends Table> T mapResultToObject(ResultSet result, Class<T> objectClazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException, NoSuchMethodException, UnsupportedDataTypeException {
        T returnObject = objectClazz.getConstructor(new Class[]{}).newInstance();
        Field[] fields = objectClazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);

            String fieldName;
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                fieldName = field.getAnnotation(PrimaryKey.class).name();
            } else if (field.isAnnotationPresent(Attribute.class)) {
                fieldName = field.getAnnotation(Attribute.class).name();
            } else {
                continue;
            }

            Object fieldValue;
            try {
                if (String.class.equals(field.getType())) {
                    try {
                        fieldValue = result.getString(fieldName);
                    } catch(Exception e) {
                        fieldValue = result.getString("max");
                    }
                } else if (Integer.class.equals(field.getType())) {
                    fieldValue = result.getInt(fieldName);
                } else if (Boolean.class.equals(field.getType())) {
                    fieldValue = result.getBoolean(fieldName);
                } else {
                    throw new UnsupportedDataTypeException("Unexpected value: " + field.getType());
                }

                field.set(returnObject, fieldValue);
            } catch (Exception e) {
                field.setAccessible(false);
            }
        }

        return returnObject;
    }
}
