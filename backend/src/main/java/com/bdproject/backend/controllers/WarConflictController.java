package com.bdproject.backend.controllers;

import com.bdproject.backend.models.WarConflict;
import com.bdproject.backend.models.response.GetResponse;
import com.bdproject.backend.models.response.PostResponse;
import com.bdproject.backend.utilities.PostgreSQLDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@RestController
public class WarConflictController {

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/warconflict")
    public GetResponse<WarConflict> getDivision(WarConflict warConflict) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return new GetResponse<>(dao.retrieveWarConflict(warConflict));
    }

    @PostMapping("/warconflict")
    public ResponseEntity postDivision(WarConflict warConflict) {
        try {
            boolean success = dao.saveWarConflict(warConflict);
            PostResponse response = new PostResponse(success);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (Exception e) {
            PostResponse response = new PostResponse(false);
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(response);
        }

    }
}
