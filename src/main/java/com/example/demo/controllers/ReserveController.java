package com.example.demo.controllers;

import com.example.demo.models.Reserve;
import com.example.demo.services.ReserveService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/reserve")
public class ReserveController {

    private final ReserveService reserveService;

    ReserveController(ReserveService reserveService) {
        this.reserveService = reserveService;
    }

    @GetMapping("all")
    public ResponseEntity<List<Reserve>> getAll() {
        List<Reserve> reserveList = reserveService.getAll();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity(reserveList, responseHeaders, HttpStatus.OK);
    }


}
