import GestioneChatBot.Service.Solution;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.Test;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@QuarkusTest
public class TestSolution {

    @Test
    public void testSolution() {
        Solution solution = new Solution("Infarto", "soluzioneInfarto");
        assertNotNull(solution);
        assertEquals("Infarto", solution.getProblem());
        assertEquals("soluzioneInfarto", solution.getSolution());
    }

    @Test
    public void testEquals() {
        Solution solution1 = new Solution("Infarto", "soluzioneInfarto");
        Solution solution2 = new Solution("Febbre", "soluzioneInfarto");
        Solution solution3 = new Solution("Infarto", "soluzioneInfarto");
        assertNotEquals(solution1, solution2);
        assertEquals(solution1, solution3);
    }

    @Test
    public void testHashCode() {
        Solution solution1 = new Solution("Infarto", "soluzioneInfarto");
        Solution solution2 = new Solution("Febbre", "soluzioneInfarto");
        Solution solution3 = new Solution("Infarto", "soluzioneInfarto");
        assertNotEquals(solution1.hashCode(), solution2.hashCode());
        assertEquals(solution1.hashCode(), solution3.hashCode());
    }
}
