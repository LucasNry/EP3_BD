package com.bdproject.backend.utilities;

import com.bdproject.backend.configuration.Config;
import com.bdproject.backend.models.Division;
import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.models.Table;
import org.springframework.stereotype.Component;

import javax.activation.UnsupportedDataTypeException;
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
    private static final String COULD_NOT_FIND_ERROR_MESSAGE = "Could not find an Object with the given key. Please check your input and try again";
    private static final String MULTIPLE_OBJECTS_ERROR_MESSAGE = "Somehow a key returned more than one item. Please fix your database and try again";

    private static final String AGGREGATE_REGEX = "[A-Z]\\((.*?)\\)";
    private static final String PARENTHESES_REGEX = "\\((.*?)\\)";

    private static final String MAX = "max";
    private static final String MIN = "min";
    private static final String AVG = "avg";
    private static final String SUM = "sum";

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
    public <T extends Table> List<T> retrieveFromModel(T object) throws IllegalAccessException, SQLException, UnsupportedDataTypeException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String sqlQuery = mapObjectToSelectQuery(object);
        return (List<T>) retrieve(sqlQuery, object.getClass());
    }

    @SuppressWarnings("unchecked")
    public <T extends Table> List<T> retrieveFromQuery(String sqlQuery, Class<T> clazz) throws IllegalAccessException, SQLException, UnsupportedDataTypeException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return (List<T>) retrieve(sqlQuery, clazz);
    }

    public <T extends Table> void saveFromModel(T object) throws IllegalAccessException, SQLException {
        String sqlQuery = mapObjectToInsertQuery(object);
        save(sqlQuery);
    }

    @Override
    public void saveDivisionIntoMilitaryGroup(Division division, MilitaryGroup militaryGroup) throws IllegalAccessException, SQLException, InstantiationException, NoSuchMethodException, InvocationTargetException, UnexpectedException, UnsupportedDataTypeException {
        List<MilitaryGroup> groupList = retrieveFromModel(militaryGroup);
        List<Division> divisionList = retrieveFromModel(division);

        if (groupList.size() <= 0 || divisionList.size() <= 0) {
            throw new IllegalArgumentException(COULD_NOT_FIND_ERROR_MESSAGE);
        }

        if (groupList.size() > 1 || divisionList.size() > 1) {
            throw new UnexpectedException(MULTIPLE_OBJECTS_ERROR_MESSAGE);
        }

        MilitaryGroup group = groupList.get(0);
        Division div = divisionList.get(0);

        div.setCodigoG(group.getCodigoG());

        updateDivision(div);
    }

    @Override
    public void updateDivision(Division division) throws IllegalAccessException, SQLException {
        String sqlQuery = mapObjectToUpdateQuery(division);
        save(sqlQuery);
    }

    private Object retrieve(String sqlQuery, Class<? extends Table> clazz) throws SQLException, IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException, UnsupportedDataTypeException {
        List<Object> returnList = new ArrayList<>();

        ResultSet result = statement.executeQuery(sqlQuery);

        while (result.next()) {
            Object obj = mapResultToObject(result, clazz);
            returnList.add(obj);
        }

        return returnList;
    }

    private void save(String insertQuery) throws SQLException {
        statement.executeUpdate(insertQuery);
    }
}
