package com.cinematch.project;

import com.cinematch.project.services.GenreParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenreParserTest {

    @Test
    void parsesTwoGenres() {
        List<String> g = GenreParser.parse("Action,Horror");
        assertEquals(List.of("ACTION", "HORROR"), g);
    }

    @Test
    void parsesFiveGenresWithSpaces() {
        List<String> g = GenreParser.parse("Sci-Fi, Romance, Horror, Drama, Comedy");
        assertEquals(List.of("SCI_FI", "ROMANCE", "HORROR", "DRAMA", "COMEDY"), g);
    }

    @Test
    void handlesEmptyOrNull() {
        assertEquals(List.of("ACTION"), GenreParser.parse(null));
        assertEquals(List.of("ACTION"), GenreParser.parse(""));
        assertEquals(List.of("ACTION"), GenreParser.parse("   "));
    }

    @Test
    void ignoresEmptyParts() {
        List<String> g = GenreParser.parse("Action,,Horror, ");
        assertEquals(List.of("ACTION", "HORROR"), g);
    }
}
