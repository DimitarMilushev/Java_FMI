import com.company.spotify.SpotifyExplorer;
import com.company.spotify.SpotifyTrack;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class SpotifyExplorerTest {
    private static List<SpotifyTrack> tracks;
    private static SpotifyExplorer explorer;
    private static SpotifyExplorer emptyExplorer;

    @BeforeClass
    public static void setUp() throws IOException {
        Reader tracksStream = Files.newBufferedReader
                (Paths.get("csv/spotify-test-data.csv"), StandardCharsets.UTF_8);

        Reader emptyStream = Files.newBufferedReader
                (Paths.get("csv/spotify-empty.csv"), StandardCharsets.UTF_8);

        try (BufferedReader reader = new BufferedReader(tracksStream)) {
            tracks = reader.lines()
                    .skip(1)
                    .map(SpotifyTrack::of)
                    .collect(Collectors.toList());
        }

        emptyExplorer = new SpotifyExplorer(emptyStream);
        explorer = new SpotifyExplorer(tracksStream);
    }

    @Test
    public void testEmptyExplorerShouldReturnEmptyCollection() {
        Collection<SpotifyTrack> actual = emptyExplorer.getAllSpotifyTracks();
        assertEquals(Collections.emptySet(), actual);
    }

    @Test
    public void testExplorerShouldNotReturnEmptyCollection() {
        Collection<SpotifyTrack> actual = explorer.getAllSpotifyTracks();
        assertNotEquals(Collections.emptySet(), actual);
    }
}
