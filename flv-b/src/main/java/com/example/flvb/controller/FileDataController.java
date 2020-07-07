package com.example.flvb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/file")
public class FileDataController {

    private final FileDataService fileDataService;

    @Autowired
    public FileDataController(FileDataService fileDataService) {
        this.fileDataService = fileDataService;
    }

    @GetMapping(path = "{fileName}.json")
	public String getJSON(@PathVariable("fileName") String fileName) {
        return fileDataService.getJSONFile(fileName);
    }
    
    @GetMapping(path = "{fileName}.csv")
	public String getCSV(@PathVariable("fileName") String fileName) {
		return String.format("Getting file %s.csv!", fileName);
	}

}
