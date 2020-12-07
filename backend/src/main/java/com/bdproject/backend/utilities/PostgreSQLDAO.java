package com.bdproject.backend.utilities;

import com.bdproject.backend.annotations.Attribute;
import com.bdproject.backend.annotations.PrimaryKey;
import com.bdproject.backend.configuration.Config;
import com.bdproject.backend.models.Division;
import com.bdproject.backend.models.MilitaryChief;
import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.models.PoliticalLeader;
import com.bdproject.backend.models.Table;
import com.bdproject.backend.models.WarConflict;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.rmi.UnexpectedException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostgreSQLDAO extends AbstractDAO {
    private static final String FIELD_EXPERSSION_TEMPLATE = "%s='%s' and ";
    private static final String SELECT_QUERY_BASE = "select * from %s";
    private static final String FILTER_BASE = " where ";
    private static final String INSERT_QUERY_BASE = "INSERT INTO %s (%s) VALUES(%s);";
    private static final String UPDATE_QUERY_BASE = "UPDATE %s set %s where %s";
    private static final String SEPARATOR = ", ";
    private static final String EXPRESSION_ENDING_CHAR = ";";

    private static final String COULD_NOT_FIND_ERROR_MESSAGE = "Could not find an Object with the given key. Please check your input and try again";
    private static final String MULTIPLE_OBJECTS_ERROR_MESSAGE = "Somehow a key returned more than one item. Please fix your database and try again";

    private Connection connection;

    private Statement statement;

    private void init() throws SQLException {
        connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
        statement = connection.createStatement();
    }

    public PostgreSQLDAO() throws SQLException {
        init();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Division> retrieveDivision(Division division) throws IllegalAccessException, SQLException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        String sqlQuery = mapObjectToSelectQuery(division);
        return (List<Division>) retrieve(sqlQuery, Division.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MilitaryGroup> retrieveMilitaryGroup(MilitaryGroup militaryGroup) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException {
        String sqlQuery = mapObjectToSelectQuery(militaryGroup);
        return (List<MilitaryGroup>) retrieve(sqlQuery, MilitaryGroup.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WarConflict> retrieveWarConflict(WarConflict warConflict) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException {
        String sqlQuery = mapObjectToSelectQuery(warConflict);
        return (List<WarConflict>) retrieve(sqlQuery, WarConflict.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PoliticalLeader> retrievePoliticalLeader(PoliticalLeader politicalLeader) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException {
        String sqlQuery = mapObjectToSelectQuery(politicalLeader);
        return (List<PoliticalLeader>) retrieve(sqlQuery, PoliticalLeader.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MilitaryChief> retrieveMilitaryChief(MilitaryChief militaryChief) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException, NoSuchMethodException {
        String sqlQuery = mapObjectToSelectQuery(militaryChief);
        return (List<MilitaryChief>) retrieve(sqlQuery, MilitaryChief.class);
    }

    @Override
    public boolean saveDivision(Division division) throws IllegalAccessException {
        String sqlQuery = mapObjectToInsertQuery(division);
        return save(sqlQuery);
    }

    @Override
    public boolean saveMilitaryGroup(MilitaryGroup militaryGroup) throws IllegalAccessException {
        String sqlQuery = mapObjectToInsertQuery(militaryGroup);
        return save(sqlQuery);
    }

    @Override
    public boolean saveDivisionIntoMilitaryGroup(Division division, MilitaryGroup militaryGroup) throws IllegalAccessException, SQLException, InstantiationException, NoSuchMethodException, InvocationTargetException, UnexpectedException {
        List<MilitaryGroup> groupList = retrieveMilitaryGroup(militaryGroup);
        List<Division> divisionList = retrieveDivision(division);

        if (groupList.size() <= 0 || divisionList.size() <= 0) {
            throw new IllegalArgumentException(COULD_NOT_FIND_ERROR_MESSAGE);
        }

        if (groupList.size() > 1 || divisionList.size() > 1) {
            throw new UnexpectedException(MULTIPLE_OBJECTS_ERROR_MESSAGE);
        }

        MilitaryGroup group = groupList.get(0);
        Division div = divisionList.get(0);

        div.setCodigoG(group.getCodigoG());

        return updateDivision(div);
    }

    @Override
    public boolean saveWarConflict(WarConflict warConflict) throws IllegalAccessException {
        String sqlQuery = mapObjectToInsertQuery(warConflict);
        return save(sqlQuery);
    }

    @Override
    public boolean savePoliticalLeader(PoliticalLeader politicalLeader) throws IllegalAccessException {
        String sqlQuery = mapObjectToInsertQuery(politicalLeader);
        return save(sqlQuery);
    }

    @Override
    public boolean saveMilitaryChief(MilitaryChief militaryChief) throws IllegalAccessException {
        String sqlQuery = mapObjectToInsertQuery(militaryChief);
        return save(sqlQuery);
    }

    @Override
    public boolean updateDivision(Division division) throws IllegalAccessException {
        String sqlQuery = mapObjectToUpdateQuery(division);
        return save(sqlQuery);
    }

    private Object retrieve(String sqlQuery, Class<?> clazz) throws SQLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        List<Object> returnList = new ArrayList<>();

        ResultSet result = statement.executeQuery(sqlQuery);

        while (result.next()) {
            Object obj = mapResultToObject(result, clazz);
            returnList.add(obj);
        }

        return returnList;
    }

    private boolean save(String insertQuery) {
        try {
            statement.executeUpdate(insertQuery);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    private String mapObjectToInsertQuery(Table instance) throws IllegalAccessException {
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
    private String mapObjectToUpdateQuery(Division instance) throws IllegalAccessException { // This function sucks, but I'm too fucking tired to write it any better
        return String.format(UPDATE_QUERY_BASE,
                instance.getTableName(),
                String.format("codigog = %s", instance.getCodigoG()),
                String.format("nrodivisao = %s", instance.getNroDivisao())
        );
    }

    @SuppressWarnings("unchecked")
    private String mapObjectToSelectQuery(Table instance) throws IllegalAccessException {
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
    private <T> T mapResultToObject(ResultSet result, Class<T> objectClazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException, NoSuchMethodException {
        T returnObject = (T) objectClazz.getConstructor(new Class[]{}).newInstance();
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
            if (String.class.equals(field.getType())) {
                fieldValue = result.getString(fieldName);
            } else if (Integer.class.equals(field.getType())) {
                fieldValue = result.getInt(fieldName);
            } else if (Boolean.class.equals(field.getType())) {
                fieldValue = result.getBoolean(fieldName);
            } else {
                throw new IllegalStateException("Unexpected value: " + field.getType());
            }

            field.set(returnObject, fieldValue);

            field.setAccessible(false);
        }

        return returnObject;
    }
}
