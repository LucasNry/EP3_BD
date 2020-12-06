package com.bdproject.backend.controllers;

import com.bdproject.backend.models.Division;
import com.bdproject.backend.utilities.PostgreSQLDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DivisionController {

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/division")
    public Division getDivision(@RequestBody Division division) {
        return dao.retrieveDivision(division);
    }

    @PostMapping("/division")
    public ResponseEntity postDivision(@RequestBody Division division) {
        try {
            dao.saveDivision(division);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
