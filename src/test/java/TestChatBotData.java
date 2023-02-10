import it.unisa.CardioTel.GestioneChatBot.Service.GestioneChatBotData;
import io.quarkus.test.junit.QuarkusTest;
import org.bson.Document;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class TestChatBotData {

    @Inject
    GestioneChatBotData myResource;

    @Test
    public void testAddProblema() {
        String problema = "Infarto";
        String soluzione = "soluzioneInfarto";
        Document result = myResource.addProblema(problema, soluzione);
        assertNotNull(result);
        assertEquals(problema, result.get("problem"));
        assertEquals(soluzione, result.get("solution"));
    }

}
