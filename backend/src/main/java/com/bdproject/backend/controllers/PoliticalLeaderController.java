package com.bdproject.backend.controllers;

import com.bdproject.backend.models.PoliticalLeader;
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
public class PoliticalLeaderController {

    @Autowired
    private PostgreSQLDAO dao;

    @GetMapping("/politicalleader")
    public GetResponse<PoliticalLeader> getDivision(PoliticalLeader politicalLeader) throws InvocationTargetException, SQLException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        return new GetResponse<>(dao.retrievePoliticalLeader(politicalLeader));
    }

    @PostMapping("/politicalleader")
    public ResponseEntity postDivision(PoliticalLeader politicalLeader) {
        try {
            boolean success = dao.savePoliticalLeader(politicalLeader);
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
