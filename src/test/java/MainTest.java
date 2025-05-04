import org.aniruth.Platoon;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.aniruth.Main.findWinningArrangement;
import static org.aniruth.Main.parsePlatoons;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void testParsePlatoonsValidInput() {
        String[] input = {"Spearmen#10", "Militia#30", "FootArcher#20"};
        List<Platoon> expected = Arrays.asList(new Platoon("Spearmen", 10), new Platoon("Militia", 30), new Platoon("FootArcher", 20));

        List<Platoon> result = parsePlatoons(input);
        assertEquals(expected, result, "The parsed platoons should match the expected list");
    }

    @Test
    void testParsePlatoonsEmptyInput() {
        String[] input = {};
        List<Platoon> result = parsePlatoons(input);
        assertTrue(result.isEmpty(), "The result should be an empty list for empty input");
    }

    @Test
    void testParsePlatoonsInvalidFormat() {
        String[] input = {"InvalidFormat"};
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> parsePlatoons(input), "Invalid input should throw ArrayIndexOutOfBoundsException");
    }

    @Test
    void testParsePlatoonsNullInput() {
        assertThrows(NullPointerException.class, () -> parsePlatoons(null), "Null input should throw NullPointerException");
    }

    @Test
    void testWinningArrangementExists() {
        List<Platoon> ownPlatoons = Arrays.asList(new Platoon("Spearmen", 10), new Platoon("Militia", 30), new Platoon("FootArcher", 20), new Platoon("LightCavalry", 1000), new Platoon("HeavyCavalry", 120));

        List<Platoon> opponentPlatoons = Arrays.asList(new Platoon("Militia", 10), new Platoon("Spearmen", 10), new Platoon("FootArcher", 1000), new Platoon("LightCavalry", 120), new Platoon("CavalryArcher", 100));

        List<Platoon> result = findWinningArrangement(ownPlatoons, opponentPlatoons);
        assertNotNull(result, "There should be a winning arrangement");
        assertEquals(5, result.size(), "The arrangement should have 5 platoons");
    }

    @Test
    void testNoWinningArrangement() {
        List<Platoon> ownPlatoons = Arrays.asList(new Platoon("Spearmen", 10), new Platoon("Militia", 10), new Platoon("FootArcher", 10), new Platoon("LightCavalry", 10), new Platoon("HeavyCavalry", 10));

        List<Platoon> opponentPlatoons = Arrays.asList(new Platoon("Militia", 100), new Platoon("Spearmen", 100), new Platoon("FootArcher", 100), new Platoon("LightCavalry", 100), new Platoon("CavalryArcher", 100));

        List<Platoon> result = findWinningArrangement(ownPlatoons, opponentPlatoons);
        assertNull(result, "There should be no winning arrangement");
    }

    @Test
    void testEdgeCaseExactWins() {
        List<Platoon> ownPlatoons = Arrays.asList(new Platoon("Spearmen", 10), new Platoon("Militia", 30), new Platoon("FootArcher", 20), new Platoon("LightCavalry", 1000), new Platoon("HeavyCavalry", 120));

        List<Platoon> opponentPlatoons = Arrays.asList(new Platoon("Militia", 10), new Platoon("Spearmen", 10), new Platoon("FootArcher", 1000), new Platoon("LightCavalry", 120), new Platoon("CavalryArcher", 200));

        List<Platoon> result = findWinningArrangement(ownPlatoons, opponentPlatoons);
        assertNotNull(result, "There should be a winning arrangement");
        assertEquals(5, result.size(), "The arrangement should have 5 platoons");
    }
}