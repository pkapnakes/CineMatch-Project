package com.cinematch.project.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
public class FaceService {

    @Value("${facepp.api.key}")
    private String apiKey;

    @Value("${facepp.api.secret}")
    private String apiSecret;

    @Value("${facepp.detect.url}")
    private String detectUrl;

    @Value("${facepp.compare.url}")
    private String compareUrl;

    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Ανιχνεύει face_token από MultipartFile (multipart/form-data)
     */
    public String detectFace(MultipartFile file) throws IOException, InterruptedException {
        String boundary = "Boundary-" + UUID.randomUUID();

        // Δημιουργία body
        byte[] body = buildMultipartBody(file, boundary);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(detectUrl))
                .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                .POST(HttpRequest.BodyPublishers.ofByteArray(body))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode json = mapper.readTree(response.body());

        if (json.has("faces") && json.get("faces").size() > 0) {
            return json.get("faces").get(0).get("face_token").asText();
        }
        return null;
    }

    /**
     * Ανιχνεύει face_token από URL
     */
    public String detectFaceFromUrl(String imageUrl) throws IOException, InterruptedException {
        String form = "api_key=" + apiKey +
                "&api_secret=" + apiSecret +
                "&image_url=" + imageUrl;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(detectUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode json = mapper.readTree(response.body());

        if (json.has("faces") && json.get("faces").size() > 0) {
            return json.get("faces").get(0).get("face_token").asText();
        }
        return null;
    }

    /**
     * Συγκρίνει δύο face_tokens και επιστρέφει similarity %
     */
    public double compareFaces(String token1, String token2) throws IOException, InterruptedException {
        String form = "api_key=" + apiKey +
                "&api_secret=" + apiSecret +
                "&face_token1=" + token1 +
                "&face_token2=" + token2;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(compareUrl))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode json = mapper.readTree(response.body());

        if (json.has("confidence")) {
            return json.get("confidence").asDouble(); // 0–100
        }
        return 0.0;
    }

    /**
     * Helper: δημιουργεί body multipart/form-data
     */
    private byte[] buildMultipartBody(MultipartFile file, String boundary) throws IOException {
        StringBuilder sb = new StringBuilder();

        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"api_key\"\r\n\r\n").append(apiKey).append("\r\n");

        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"api_secret\"\r\n\r\n").append(apiSecret).append("\r\n");

        sb.append("--").append(boundary).append("\r\n");
        sb.append("Content-Disposition: form-data; name=\"image_file\"; filename=\"image.jpg\"\r\n");
        sb.append("Content-Type: application/octet-stream\r\n\r\n");

        byte[] fileBytes = file.getBytes();
        byte[] start = sb.toString().getBytes(StandardCharsets.UTF_8);
        byte[] end = ("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8);

        byte[] all = new byte[start.length + fileBytes.length + end.length];
        System.arraycopy(start, 0, all, 0, start.length);
        System.arraycopy(fileBytes, 0, all, start.length, fileBytes.length);
        System.arraycopy(end, 0, all, start.length + fileBytes.length, end.length);

        return all;
    }
}
