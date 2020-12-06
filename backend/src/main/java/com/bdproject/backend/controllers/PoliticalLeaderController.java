package com.bdproject.backend.controllers;

import com.bdproject.backend.models.PoliticalLeader;
import com.bdproject.backend.utilities.PostgreSQLDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PoliticalLeaderController {

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/politicalleader")
    public PoliticalLeader getDivision(PoliticalLeader politicalLeader) {
        return dao.retrievePoliticalLeader(politicalLeader);
    }

    @PostMapping("/politicalleader")
    public ResponseEntity postDivision(PoliticalLeader politicalLeader) {
        try {
            dao.savePoliticalLeader(politicalLeader);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
