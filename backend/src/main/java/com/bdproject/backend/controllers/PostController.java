package com.bdproject.backend.controllers;

import com.bdproject.backend.models.Division;
import com.bdproject.backend.models.MediatingOrganization;
import com.bdproject.backend.models.MediationStart;
import com.bdproject.backend.models.MilitaryChief;
import com.bdproject.backend.models.MilitaryGroup;
import com.bdproject.backend.models.PoliticalLeader;
import com.bdproject.backend.models.Supplies;
import com.bdproject.backend.models.Table;
import com.bdproject.backend.models.WarConflict;
import com.bdproject.backend.models.request.EnrollDivisionRequest;
import com.bdproject.backend.models.response.GenericResponse;
import com.bdproject.backend.utilities.PostgreSQLDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class PostController {

    @Autowired
    private PostgreSQLDAO dao;

    @PostMapping("/division")
    public ResponseEntity postDivision(@RequestBody Division division) {
        return handlePostRequest(division);
    }

    @PostMapping("/militarychief")
    public ResponseEntity postMilitaryChief(@RequestBody MilitaryChief militaryChief) {
        return handlePostRequest(militaryChief);
    }

    @PostMapping("/militarygroup")
    public ResponseEntity postMilitaryGroup(@RequestBody MilitaryGroup militaryGroup) {
        return handlePostRequest(militaryGroup);
    }

    @PostMapping("/politicalleader")
    public ResponseEntity postPoliticalLeader(@RequestBody PoliticalLeader politicalLeader) {
        return handlePostRequest(politicalLeader);
    }

    @PostMapping("/warconflict")
    public ResponseEntity postWarConflict(@RequestBody WarConflict warConflict) {
        return handlePostRequest(warConflict);
    }

    @PostMapping("/supplies")
    public ResponseEntity postSupplies(@RequestBody Supplies supplies) {
        return handlePostRequest(supplies);
    }

    @PostMapping("/mediationstart")
    public ResponseEntity postMediationStart(@RequestBody MediationStart supplies) {
        return handlePostRequest(supplies);
    }

    @PostMapping("/mediatingorganization")
    public ResponseEntity postMediatingOrganization(@RequestBody MediatingOrganization supplies) {
        return handlePostRequest(supplies);
    }

    @PostMapping("/division/enroll")
    public ResponseEntity postDivisionIntoMilitaryGroup(@RequestBody EnrollDivisionRequest request) throws Exception {
        Division division = new Division();
        division.setNroDivisao(request.getNroDivisao());
        MilitaryGroup militaryGroup = new MilitaryGroup();
        militaryGroup.setCodigoG(request.getCodigoG());

        try {
            dao.saveDivisionIntoMilitaryGroup(division, militaryGroup);
            GenericResponse response = new GenericResponse(true);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (Exception e) {
            GenericResponse response = new GenericResponse(true, e.getMessage());

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
    }

    private <T extends Table> ResponseEntity handlePostRequest(T object) {
        try {
            dao.saveFromModel(object);
            GenericResponse response = new GenericResponse(true);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        } catch (Exception e) {
            GenericResponse response = new GenericResponse(false, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);
        }
    }
}
