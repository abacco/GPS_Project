
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import GestioneChatBot.Controller.GestioneChatBotController;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        assertTrue(response.equals("Solution not found"));
    }

    @Test
    public void testPrintCBPage() throws IOException {
        String expected = new String(Files.readAllBytes(Paths.get("./src/main/java/GestioneChatBot/Controller/ChatBot.html")));
        String result = myResource.printCBPage();

        assertEquals(result, expected);
    }

    @Test
    public void test_getScript() throws IOException {
        String expected = new String(Files.readAllBytes(Paths.get("./src/main/java/GestioneChatBot/Controller/ChatBotScript.js")));

        String result = myResource.getScript();

        assertEquals(result, expected);
    }
}

