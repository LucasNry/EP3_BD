package com.bdproject.backend.utilities;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class PostgreSQLDAO extends AbstractDAO {
    private static final String FIELD_EXPERSSION_TEMPLATE = "%s=\"%s\" ";
    private static final String QUERY_BASE = "select * from %s where ";
    private static final String EXPRESSION_ENDING_CHAR = ";";

    private Connection connection;

    private Statement statement;

    private void init() throws SQLException {
        connection = DriverManager.getConnection(Config.DB_URL, Config.DB_USER, Config.DB_PASSWORD);
        statement = connection.createStatement();
    }

    public PostgreSQLDAO() throws SQLException {
        init();
    }

    @Override
    public Division retrieveDivision(Division division) {
        return null;
    }

    @Override
    public MilitaryGroup retrieveMilitaryGroup(MilitaryGroup militaryGroup) {
        return null;
    }

    @Override
    public WarConflict retrieveWarConflict(WarConflict warConflict) {
        return null;
    }

    @Override
    public PoliticalLeader retrievePoliticalLeader(PoliticalLeader politicalLeader) {
        return null;
    }

    @Override
    public MilitaryChief retrieveMilitaryChief(MilitaryChief militaryChief) {
        return null;
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

    @SuppressWarnings("unchecked")
    private String mapObjectToQuery(Table tableObject) throws IllegalAccessException {
        StringBuilder finalQuery = new StringBuilder();
        finalQuery.append(String.format(QUERY_BASE, tableObject.getTableName()));

        Class<Table> objectClazz = (Class<Table>) tableObject.getClass();
        Field[] fields = objectClazz.getFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                String fieldName = field.getAnnotation(PrimaryKey.class).name();
                Object fieldValue = field.get(tableObject);

                String fieldExpression = String.format(FIELD_EXPERSSION_TEMPLATE, fieldName, fieldValue);
                finalQuery.append(fieldExpression);
            }
        }

        finalQuery.append(EXPRESSION_ENDING_CHAR);

        return finalQuery.toString();
    }
}
