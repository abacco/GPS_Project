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
            result = grc.getReport("dd/MM/YYYY dd/MM/YYYY");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(result);
    }
}
