package com.cinematch.project.dto;

public record FaceMatchResult(
        String name,
        String imageUrl,
        double score
) {}
