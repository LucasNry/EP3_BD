package com.bdproject.backend.controllers;

import com.bdproject.backend.models.Division;
import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.models.request.EnrollDivisionRequest;
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
import java.rmi.UnexpectedException;
import java.sql.SQLException;

@RestController
public class DivisionController {

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/division")
    public GetResponse<Division> getDivision(@RequestBody Division division) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return new GetResponse<>(dao.retrieveDivision(division));
    }

    @PostMapping("/division")
    public ResponseEntity postDivision(@RequestBody Division division) {
        try {
            boolean success = dao.saveDivision(division);
            PostResponse response = new PostResponse(success);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (Exception e) {
            PostResponse response = new PostResponse(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/division/enroll")
    public ResponseEntity postDivisionIntoMilitaryGroup(@RequestBody EnrollDivisionRequest request) throws IllegalAccessException, InvocationTargetException, InstantiationException, SQLException, UnexpectedException, NoSuchMethodException {
        Division division = new Division();
        division.setNroDivisao(request.getNroDivisao());
        MilitaryGroup militaryGroup = new MilitaryGroup();
        militaryGroup.setCodigoG(request.getCodigoG());

        boolean success = dao.saveDivisionIntoMilitaryGroup(division, militaryGroup);
        PostResponse response = new PostResponse(success);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
