import io.quarkus.test.junit.QuarkusTest;
import it.CardioTel.GestioneReport.GestioneReportController;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@QuarkusTest
public class GetReportTest {

    @Inject
    GestioneReportController grc;

    @Test
    public void testGetReportReturnType() {
        StreamingOutput result = null;
        try {
            result = grc.getReport("01/01/1990 01/01/1990");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(result);
    }
    @Test
    public void testGetReportReturnType2() {
        given().when().get("/localhost:8080")
                .then()
                .statusCode(200)
                .body(is(""));
    }
}
