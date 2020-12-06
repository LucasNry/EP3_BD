package com.bdproject.backend.controllers;

import com.bdproject.backend.models.MilitaryChief;
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
public class MilitaryChiefController {

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/militarychief")
    public List<MilitaryChief> getDivision(MilitaryChief militaryChief) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return dao.retrieveMilitaryChief(militaryChief);
    }

    @PostMapping("/militarychief")
    public ResponseEntity postDivision(MilitaryChief militaryChief) {
        try {
            dao.saveMilitaryChief(militaryChief);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
