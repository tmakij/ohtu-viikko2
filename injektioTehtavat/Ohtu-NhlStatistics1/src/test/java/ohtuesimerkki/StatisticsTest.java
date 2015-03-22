package ohtuesimerkki;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.Test;

public final class StatisticsTest {

    private Statistics stats;
    private final String somePlayerName = "Semenko";
    private final Player somePlayer = new Player(somePlayerName, "EDM", 4, 12);
    private final List<Player> teamEDM = Arrays.asList(new Player("Kurri", "EDM", 37, 53), new Player("Gretzky", "EDM", 35, 89), somePlayer);
    private final List<Player> topPlayers3 = Arrays.asList(new Player("SUPERMAN1", "GOT", 9001, 9001), new Player("SUPERMAN1", "GOT", 1, 9001), new Player("SUPERMAN1", "GOT", 9001, 1));
    private final Reader offlineReader = new Reader() {
        @Override
        public List<Player> getPlayers() {
            List<Player> players = new ArrayList<Player>();
            players.addAll(teamEDM);
            players.addAll(topPlayers3);
            players.add(new Player("Lemieux", "PIT", 45, 54));
            players.add(new Player("Yzerman", "DET", 42, 56));
            players.add(new Player("Aatu", "PIT", 3, 4));
            players.add(new Player("Aatu2", "LEI", 1, 2));
            players.add(new Player("Aatu3", "LEI", 5, 5));
            players.add(new Player("Aatu4", "LEI", 7, 6));
            return players;
        }
    };

    @Before
    public void setUp() {
        stats = new Statistics(offlineReader);
    }

    @Test
    public void testSearchRealPlayer() {
        Player found = stats.search(somePlayerName);
        assertEquals(somePlayer, found);
    }

    @Test
    public void testSearchUnrealPlayer() {
        Player found = stats.search("asdasdijgrunafgils");
        assertNull(found);
    }

    @Test
    public void testUnrealTeamPlayerList() {
        List<Player> found = stats.team("asdasdijgrunafgils");
        assertEquals(Collections.EMPTY_LIST, found);
    }

    @Test
    public void testRealTeamPlayerList() {
        List<Player> found = stats.team("EDM");
        assertEquals(teamEDM, found);
    }

    @Test
    public void testTopScorersInvalidCountNegative() {
        List<Player> found = stats.topScorers(-23);
        assertEquals(Collections.EMPTY_LIST, found);
    }

    @Test
    public void testTopScorersInvalidCountNegativeOne() {
        List<Player> found = stats.topScorers(-1);
        assertEquals(Collections.EMPTY_LIST, found);
    }

    @Test
    public void testTopScorersCorrectCountZero() {
        testSizes(0);
    }

    @Test
    public void testTopScorersCorrectCountFive() {
        testSizes(5);
    }

    private void testSizes(int count) {
        List<Player> found = stats.topScorers(count);
        assertEquals(count + 1, found.size());
    }

    @Test
    public void testTopScorersCorrectOrderFive() {
        List<Player> found = stats.topScorers(2);
        assertEquals(topPlayers3, found);
    }
}
