package UnitTests;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class EngineAlgoTests {
    @BeforeEach @Test
    public void setUpMovieList() {
        movies = new MovieList();
    }

    /* Test 2: Adding a new movie to the list
     * [This test obliges you to add methods called "addMovie"
     * and "getRating".  (For now, only getRating needs to
     * so anything, and all it needs to do is return the
     * string "No rating".)]
     */
    @Test
    public void addAMovie() throws MovieListException {
        movies.addMovie("Star Wars");
        assertEquals("No rating", movies.getRating("Star Wars"),
                "Adding movie failed");
    }
}
