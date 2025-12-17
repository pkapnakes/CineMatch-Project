package com.cinematch.project.controllers;

import com.cinematch.project.dto.FaceMatchResult;
import com.cinematch.project.services.FaceMatchMemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FaceMatchController {

    @Autowired
    private FaceMatchMemoryService faceMatchService;

    @PostMapping("/api/face-match")
    public FaceMatchResult matchActor(@RequestParam("file") MultipartFile file) throws Exception {
        return faceMatchService.match(file);
    }
}
