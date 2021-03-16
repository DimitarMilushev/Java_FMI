package com.company.tagger;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TaggerTest {
    private static final String WORLD_CITIES_EXAMPLE = """
    Aabenraa,Denmark
    Aachen,Germany
    Aalborg,Denmark
    Aalen,Germany
    Aalsmeer,Netherlands
    Aalst,Belgium
    Aalten,Netherlands
    Aalter,Belgium
    Aarau,Switzerland
    Aarschot,Belgium
    Aba,Nigeria
    Abadan,Iran""";

    private static Tagger tagger;

    @BeforeClass
    public static void initTagger() {
        tagger = new Tagger(new StringReader(WORLD_CITIES_EXAMPLE));
    }

    @Test
    public void testTagCitiesEmptyReader() {
        final String inputText = "";
        final String expected = "";

        Reader reader = new StringReader(inputText);
        Writer output = new StringWriter();
        tagger.tagCities(reader, output);

        assertEquals(expected, output.toString());
    }

    @Test
    public void testTagCities() {
        final String inputText = "Aabenraa is not the capital of Bulgaria.";
        final String expected = "<city country=\"Denmark\">Aabenraa</city> is not the capital of Bulgaria.";

        Reader reader = new StringReader(inputText);
        Writer output = new StringWriter();
        tagger.tagCities(reader, output);

        assertEquals(expected, output.toString());
    }

    @Test
    public void testGetNMostTaggedCities() {

    }
}
