package com.bdproject.backend.controllers;

import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.models.response.GetResponse;
import com.bdproject.backend.models.response.PostResponse;
import com.bdproject.backend.utilities.PostgreSQLDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

@RestController
public class MilitaryGroupController {

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/militarygroup")
    public GetResponse<MilitaryGroup> getDivision(@RequestBody MilitaryGroup militaryGroup) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return new GetResponse<>(dao.retrieveMilitaryGroup(militaryGroup));
    }

    @PostMapping("/militarygroup")
    public ResponseEntity postDivision(@RequestBody MilitaryGroup militaryGroup) {
        try {
            boolean success = dao.saveMilitaryGroup(militaryGroup);
            PostResponse response = new PostResponse(success);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (Exception e) {
            PostResponse response = new PostResponse(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
