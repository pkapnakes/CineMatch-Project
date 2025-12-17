package com.cinematch.project.services;

import com.cinematch.project.dto.ActorDto;
import com.cinematch.project.dto.FaceMatchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FaceMatchMemoryService {

    private final FaceService faceService;
    private final Map<String, ActorDto> actorTokens = new HashMap<>();

    @Autowired
    public FaceMatchMemoryService(FaceService faceService, TmdbService tmdbService) throws Exception {
        this.faceService = faceService;

        // Load top actors at startup
        List<ActorDto> actors = tmdbService.getPopularActors();
        for (ActorDto actor : actors) {
            String token = faceService.detectFaceFromUrl(actor.getImageUrl());
            if (token != null) {
                actor.setFaceToken(token);
                actorTokens.put(token, actor);
            }
        }
        System.out.println("Loaded " + actorTokens.size() + " actor face tokens in memory.");
    }

    public FaceMatchResult match(MultipartFile file) throws Exception {
        String userToken = faceService.detectFace(file);
        if (userToken == null) return new FaceMatchResult("No face detected", "", 0.0);

        ActorDto best = null;
        double bestScore = -1;
        for (ActorDto actor : actorTokens.values()) {
            double score = faceService.compareFaces(userToken, actor.getFaceToken());
            if (score > bestScore) {
                bestScore = score;
                best = actor;
            }
        }

        if (best != null) {
            return new FaceMatchResult(best.getName(), best.getImageUrl(), bestScore);
        }
        return new FaceMatchResult("No match found", "", 0.0);
    }
}
