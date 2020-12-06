package com.bdproject.backend.controllers;

import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.utilities.PostgreSQLDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@RestController
public class MilitaryGroupController {

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/militarygroup")
    public List<MilitaryGroup> getDivision(MilitaryGroup militaryGroup) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return dao.retrieveMilitaryGroup(militaryGroup);
    }

    @PostMapping("/militarygroup")
    public ResponseEntity postDivision(MilitaryGroup militaryGroup) {
        try {
            dao.saveMilitaryGroup(militaryGroup);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
