import io.quarkus.test.junit.QuarkusTest;
import it.CardioTel.GestioneReport.GestioneReportController;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@QuarkusTest
public class GetReportTest {

    int i =0;

    @Inject
    GestioneReportController grc;


    @Test
    public void testGetReportReturnType() {
        given().when().get("/Report")
                .then()
                .statusCode(200);

        StreamingOutput result = null;
        try {
            result = grc.getReport("1990-01-01,1990-01-01");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(result);
    }

}
