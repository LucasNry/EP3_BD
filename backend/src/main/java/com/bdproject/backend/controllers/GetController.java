package com.bdproject.backend.controllers;

import com.bdproject.backend.annotations.PrimaryKey;
import com.bdproject.backend.models.Division;
import com.bdproject.backend.models.MilitaryChief;
import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.models.PoliticalLeader;
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

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/division")
    public GenericResponse getDivision(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapObjectMapToQueryObject(paramMap, Division.class));
    }

    @GetMapping("/militarychief")
    public GenericResponse getMilitaryChief(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapObjectMapToQueryObject(paramMap, MilitaryChief.class));
    }

    @GetMapping("/militarygroup")
    public GenericResponse getMilitaryGroup(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapObjectMapToQueryObject(paramMap, MilitaryGroup.class));
    }

    @GetMapping("/politicalLeader")
    public GenericResponse getPoliticalLeader(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapObjectMapToQueryObject(paramMap, PoliticalLeader.class));
    }

    @GetMapping("/warconflict")
    public GenericResponse getWarConflict(@RequestParam Map<String, String> paramMap) throws Exception {
        return handleGetRequest(mapObjectMapToQueryObject(paramMap, WarConflict.class));
    }

    private <T extends Table> GenericResponse handleGetRequest(T object) {
        try {
            return new GetResponse<>(true, dao.retrieveFromModel(object));
        } catch (Exception e) {
            return new GenericResponse(false, e.getMessage());
        }
    }

    private <T extends Table> T mapObjectMapToQueryObject(Map<String, String> paramMap, Class<T> objectClazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
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

    private Object convertToPrimitive(String stringValue, Class<?> fieldType) {
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
