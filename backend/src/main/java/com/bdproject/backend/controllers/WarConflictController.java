package com.bdproject.backend.controllers;

import com.bdproject.backend.models.WarConflict;
import com.bdproject.backend.utilities.PostgreSQLDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WarConflictController {

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/warconflict")
    public WarConflict getDivision(WarConflict warConflict) {
        return dao.retrieveWarConflict(warConflict);
    }

    @PostMapping("/warconflict")
    public ResponseEntity postDivision(WarConflict warConflict) {
        try {
            dao.saveWarConflict(warConflict);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
