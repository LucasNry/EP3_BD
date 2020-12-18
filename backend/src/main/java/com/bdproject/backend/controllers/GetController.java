package com.bdproject.backend.controllers;

import com.bdproject.backend.annotations.PrimaryKey;
import com.bdproject.backend.models.ConflictCountry;
import com.bdproject.backend.models.Division;
import com.bdproject.backend.models.MediatingOrganization;
import com.bdproject.backend.models.MediationStart;
import com.bdproject.backend.models.MilitaryChief;
import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.models.PoliticalLeader;
import com.bdproject.backend.models.Supplies;
import com.bdproject.backend.models.Table;
import com.bdproject.backend.models.WarConflict;
import com.bdproject.backend.models.response.GetResponse;
import com.bdproject.backend.models.response.GenericResponse;
import com.bdproject.backend.utilities.PostgreSQLDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@RestController
public class GetController {

    private static final String QUERY_PARAM_NAME = "query";

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/division")
    public GenericResponse getDivision(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToQueryObject(paramMap, Division.class));
    }

    @GetMapping("/division/query")
    public GenericResponse getDivisionFromQuery(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToSqlQuery(paramMap), Division.class);
    }

    @GetMapping("/militarychief")
    public GenericResponse getMilitaryChief(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToQueryObject(paramMap, MilitaryChief.class));
    }

    @GetMapping("/militarychief/query")
    public GenericResponse getMilitaryChiefFromQuery(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToSqlQuery(paramMap), MilitaryChief.class);
    }

    @GetMapping("/militarygroup")
    public GenericResponse getMilitaryGroup(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToQueryObject(paramMap, MilitaryGroup.class));
    }

    @GetMapping("/militarygroup/query")
    public GenericResponse getMilitaryGroupFromQuery(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToSqlQuery(paramMap), MilitaryGroup.class);
    }

    @GetMapping("/politicalleader")
    public GenericResponse getPoliticalLeader(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToQueryObject(paramMap, PoliticalLeader.class));
    }

    @GetMapping("/politicalleader/query")
    public GenericResponse getPoliticalLeaderFromQuery(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToSqlQuery(paramMap), PoliticalLeader.class);
    }

    @GetMapping("/warconflict")
    public GenericResponse getWarConflict(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToQueryObject(paramMap, WarConflict.class));
    }

    @GetMapping("/warconflict/query")
    public GenericResponse getWarConflictFromQuery(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToSqlQuery(paramMap), WarConflict.class);
    }

    @GetMapping("/supplies")
    public GenericResponse getSupplies(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToQueryObject(paramMap, Supplies.class));
    }

    @GetMapping("/supplies/query")
    public GenericResponse getSuppliesFromQuery(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToSqlQuery(paramMap), Supplies.class);
    }

    @GetMapping("/mediationstart")
    public GenericResponse getMediationStart(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToQueryObject(paramMap, MediationStart.class));
    }

    @GetMapping("/mediationstart/query")
    public GenericResponse getMediationStartFromQuery(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToSqlQuery(paramMap), MediationStart.class);
    }

    @GetMapping("/mediatingorganization")
    public GenericResponse getMediatingOrganization(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToQueryObject(paramMap, MediatingOrganization.class));
    }

    @GetMapping("/mediatingorganization/query")
    public GenericResponse getMediatingOrganizationFromQuery(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToSqlQuery(paramMap), MediatingOrganization.class);
    }

    @GetMapping("/conflictcountry")
    public GenericResponse getConflictCountry(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToQueryObject(paramMap, ConflictCountry.class));
    }

    @GetMapping("/conflictcountry/query")
    public GenericResponse getConflictCountryFromQuery(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapParamMapToSqlQuery(paramMap), ConflictCountry.class);
    }

    private <T extends Table> GenericResponse handleGetRequest(T object) {
        try {
            return new GetResponse<>(true, dao.retrieveFromModel(object));
        } catch (Exception e) {
            return new GenericResponse(false, e.getMessage());
        }
    }

    private <T extends Table> GenericResponse handleGetRequest(String sqlQuery, Class<T> clazz) {
        try {
            return new GetResponse<>(true, dao.retrieveFromQuery(sqlQuery, clazz));
        } catch (Exception e) {
            return new GenericResponse(false, e.getMessage());
        }
    }

    private <T extends Table> T mapParamMapToQueryObject(Map<String, String> paramMap, Class<T> objectClazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        T queryObject = objectClazz.getConstructor(new Class[]{}).newInstance();
        Field[] fields = objectClazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                String fieldName = field.getAnnotation(PrimaryKey.class).name();
                Class fieldType = field.getType();

                String fieldStringValue = paramMap.get(fieldName);
                Object fieldValue = convertToPrimitive(fieldStringValue, fieldType);

                if (fieldValue != null) {
                    field.setAccessible(true);

                    field.set(queryObject, fieldValue);

                    field.setAccessible(false);
                }
            }
        }

        return queryObject;
    }

    private String mapParamMapToSqlQuery(Map<String, String> paramMap) {
        return paramMap.get(QUERY_PARAM_NAME);
    }


        private Object convertToPrimitive(String stringValue, Class<?> fieldType) {
        if (stringValue == null) return null;
        if( Boolean.class.equals(fieldType) ) return Boolean.parseBoolean( stringValue );
        if( Byte.class.equals(fieldType) ) return Byte.parseByte( stringValue );
        if( Short.class.equals(fieldType) ) return Short.parseShort( stringValue );
        if( Integer.class.equals(fieldType) ) return Integer.parseInt( stringValue );
        if( Long.class.equals(fieldType) ) return Long.parseLong( stringValue );
        if( Float.class.equals(fieldType) ) return Float.parseFloat( stringValue );
        if( Double.class.equals(fieldType) ) return Double.parseDouble( stringValue );
        return stringValue;
    }
}
