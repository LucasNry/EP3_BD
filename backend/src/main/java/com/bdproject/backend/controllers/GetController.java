package com.bdproject.backend.controllers;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetController {

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/division")
    public GenericResponse getDivision(@RequestBody Division division) throws Exception {
        return handleGetRequest(division);
    }

    @GetMapping("/militarychief")
    public GenericResponse getMilitaryChief(@RequestBody MilitaryChief militaryChief) throws Exception {
        return handleGetRequest(militaryChief);
    }

    @GetMapping("/militarygroup")
    public GenericResponse getMilitaryGroup(@RequestBody MilitaryGroup militaryGroup) throws Exception {
        return handleGetRequest(militaryGroup);
    }

    @GetMapping("/politicalLeader")
    public GenericResponse getPoliticalLeader(@RequestBody PoliticalLeader politicalLeader) throws Exception {
        return handleGetRequest(politicalLeader);
    }

    @GetMapping("/warconflict")
    public GenericResponse getWarConflict(@RequestBody WarConflict warConflict) throws Exception {
        return handleGetRequest(warConflict);
    }

    private <T extends Table> GenericResponse handleGetRequest(T object) {
        try {
            return new GetResponse<>(true, dao.retrieveFromModel(object));
        } catch (Exception e) {
            return new GenericResponse(false, e.getMessage());
        }
    }
}
