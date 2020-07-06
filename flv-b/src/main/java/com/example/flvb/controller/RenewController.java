package com.example.flvb.controller;

import com.example.flvb.model.*;
import com.example.flvb.service.RenewService;
import com.example.flvb.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/renew")
public class RenewController {
    @Autowired
    private RenewService renewService;

    @PostMapping
    public ResponseEntity add(@Validated @RequestBody) {
        var result = renewService.run();
        return ResponseEntity.ok(result);
    }
}