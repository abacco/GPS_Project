import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import GestioneChatBot.Controller.GestioneChatBotController;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class TestChatBot {

    @Inject
    private GestioneChatBotController myResource;

    @Test
    public void testGetSolutions_Infarto() {
        String response = myResource.getSolutions("Infarto");
        assertNotNull(response);
        assertTrue(response.length() > 2);
    }
    @Test
    public void testGetSolutions_Tachicardia() {
        String response = myResource.getSolutions("Tachicardia");
        assertNotNull(response);
        assertTrue(response.length() > 2);
    }
    @Test
    public void testGetSolutions_PressioneAlta() {
        String response = myResource.getSolutions("Pressione Alta");
        assertNotNull(response);
        assertTrue(response.length() > 2);
    }
    @Test
    public void testGetSolutions_PressioneBassa() {
        String response = myResource.getSolutions("Pressione Bassa");
        assertNotNull(response);
        assertTrue(response.length() > 2);
    }
    @Test
    public void testGetSolutions_Febbre() {
        String response = myResource.getSolutions("Febbre");
        assertNotNull(response);
        assertTrue(response.length() > 2);
    }

    @Test
    public void testGetSolutions_noSolutions() {
        String response = myResource.getSolutions("NotExistProblems");
        assertTrue(response == null);
    }
}

