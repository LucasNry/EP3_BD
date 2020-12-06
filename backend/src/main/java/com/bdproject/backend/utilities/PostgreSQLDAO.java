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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
public class PostgreSQLDAO extends AbstractDAO {
    private static final String FIELD_EXPERSSION_TEMPLATE = "%s=\"%s\" ";
    private static final String QUERY_BASE = "select * from %s where ";
    private static final String EXPRESSION_ENDING_CHAR = ";";
    private static final String FORMAT_ERROR_MESSAGE = "All table attributes must be annotated with either @PrimaryKey or @Attribute. Please check for unannotated attributes on model %s";

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
    public List<Division> retrieveDivision(Division division) throws IllegalAccessException, SQLException, InvocationTargetException, InstantiationException {
        String sqlQuery = mapObjectSelectToQuery(division);
        return (List<Division>) retrieve(sqlQuery, Division.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MilitaryGroup> retrieveMilitaryGroup(MilitaryGroup militaryGroup) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException {
        String sqlQuery = mapObjectSelectToQuery(militaryGroup);
        return (List<MilitaryGroup>) retrieve(sqlQuery, MilitaryGroup.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WarConflict> retrieveWarConflict(WarConflict warConflict) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException {
        String sqlQuery = mapObjectSelectToQuery(warConflict);
        return (List<WarConflict>) retrieve(sqlQuery, WarConflict.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PoliticalLeader> retrievePoliticalLeader(PoliticalLeader politicalLeader) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException {
        String sqlQuery = mapObjectSelectToQuery(politicalLeader);
        return (List<PoliticalLeader>) retrieve(sqlQuery, PoliticalLeader.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<MilitaryChief> retrieveMilitaryChief(MilitaryChief militaryChief) throws IllegalAccessException, InvocationTargetException, SQLException, InstantiationException {
        String sqlQuery = mapObjectSelectToQuery(militaryChief);
        return (List<MilitaryChief>) retrieve(sqlQuery, MilitaryChief.class);
    }

    @Override
    public void saveDivision(Division division) {

    }

    @Override
    public void saveMilitaryGroup(MilitaryGroup militaryGroup) {

    }

    @Override
    public void saveDivisionIntoMilitaryGroup(String militaryGroupId, Division division) {

    }

    @Override
    public void saveWarConflict(WarConflict warConflict) {

    }

    @Override
    public void savePoliticalLeader(PoliticalLeader politicalLeader) {

    }

    @Override
    public void saveMilitaryChief(MilitaryChief militaryChief) {

    }

    private Object retrieve(String sqlQuery, Class<?> clazz) throws SQLException, IllegalAccessException, InstantiationException, InvocationTargetException {
        List<Object> returnList = new ArrayList<>();

        ResultSet results = statement.executeQuery(sqlQuery);

        while (results.next()) {
            Object obj = mapResultToObject(results, clazz);
            returnList.add(obj);
        }

        return returnList;
    }

    @SuppressWarnings("unchecked")
    private String mapObjectSelectToQuery(Table instance) throws IllegalAccessException {
        StringBuilder finalQuery = new StringBuilder();
        finalQuery.append(String.format(QUERY_BASE, instance.getTableName()));

        Class<Table> objectClazz = (Class<Table>) instance.getClass();
        Field[] fields = objectClazz.getFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                String fieldName = field.getAnnotation(PrimaryKey.class).name();
                Object fieldValue = field.get(instance);

                String fieldExpression = String.format(FIELD_EXPERSSION_TEMPLATE, fieldName, fieldValue);
                finalQuery.append(fieldExpression);
            }
        }

        finalQuery.append(EXPRESSION_ENDING_CHAR);

        return finalQuery.toString();
    }

    @SuppressWarnings("unchecked")
    private <T> T mapResultToObject(ResultSet result, Class<T> objectClazz) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        T returnObject = (T) objectClazz.getConstructors()[0].newInstance();
        Field[] fields = objectClazz.getFields();

        for (Field field : fields) {
            String fieldName;
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                fieldName = field.getAnnotation(PrimaryKey.class).name();
            } else if (field.isAnnotationPresent(Attribute.class)) {
                fieldName = field.getAnnotation(Attribute.class).name();
            } else {
                throw new IllegalArgumentException(String.format(FORMAT_ERROR_MESSAGE, objectClazz.getName()));
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
        }

        return returnObject;
    }
}
