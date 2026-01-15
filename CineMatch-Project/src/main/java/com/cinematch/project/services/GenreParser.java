package com.cinematch.project.services;

import java.util.ArrayList;
import java.util.List;

public class GenreParser {

    public static List<String> parse(String favorite) {
        if (favorite == null || favorite.trim().isEmpty()) {
            return List.of("ACTION");
        }

        String[] parts = favorite.split(",");
        ArrayList<String> result = new ArrayList<>();

        for (String p : parts) {
            String g = p.trim();
            if (g.isEmpty()) continue;

            g = g.toUpperCase();
            g = g.replace("-", "_").replace(" ", "_");

            if (g.equals("SCIFI")) g = "SCI_FI";
            if (g.equals("SCI__FI")) g = "SCI_FI";

            result.add(g);
        }

        if (result.isEmpty()) return List.of("ACTION");
        return result;
    }
}
