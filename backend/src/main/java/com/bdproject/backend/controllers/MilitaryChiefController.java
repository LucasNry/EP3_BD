package com.bdproject.backend.controllers;

import com.bdproject.backend.models.MilitaryChief;
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
public class MilitaryChiefController {

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/militarychief")
    public GetResponse<MilitaryChief> getDivision(MilitaryChief militaryChief) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return new GetResponse<>(dao.retrieveMilitaryChief(militaryChief));
    }

    @PostMapping("/militarychief")
    public ResponseEntity postDivision(MilitaryChief militaryChief) {
        try {
            boolean success = dao.saveMilitaryChief(militaryChief);
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
