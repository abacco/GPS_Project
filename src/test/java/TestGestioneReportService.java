import io.quarkus.test.junit.QuarkusTest;
import it.CardioTel.GestioneReport.GestioneReportServiceImpl;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class TestGestioneReportService {

    @Inject
    GestioneReportServiceImpl myResource;

    @Test
    public void testGetAverageinString() {
        int freqCard = 160;
        int colesterolo = 20;
        int ossigenazione = 200;
        int preMin = 100;
        int preMax = 200;
        int temp = 70;
        Date tDate = new Date();
        int numInstancies = 2;

        String expected = "Frequenza cardiaca : 80" +
                "\nTemperatura : 35" +
                "\nOssigenazione : 100" +
                "\nColesterolo : 10" +
                "\nPressione Minima : 50" +
                "\nPressione Massima : 100" +
                "\nData : " + tDate.toString();

        String actual = myResource.GetAverageInString(freqCard, colesterolo, ossigenazione, preMin, preMax, temp, tDate, numInstancies);
        assertEquals(expected, actual);
    }

    @Test
    public void TestGetMeasurementsInAverage() {
        ArrayList<Document> l = new ArrayList<Document>();
        Document doc1 = new Document();
        doc1.put("heartFrequency", 10);
        doc1.put("colesterolo", 20);
        doc1.put("ossigenazione", 30);
        doc1.put("pressione minima", 40);
        doc1.put("pressione massima", 50);
        doc1.put("temp", 60);
        doc1.put("date", new Date());
        doc1.put("numInstancies", 2);
        l.add(doc1);

        Document doc2 = new Document();
        doc2.put("heartFrequency", 15);
        doc2.put("colesterolo", 25);
        doc2.put("ossigenazione", 35);
        doc2.put("pressione minima", 45);
        doc2.put("pressione massima", 55);
        doc2.put("temp", 65);
        doc2.put("date", new Date());
        doc2.put("numInstancies", 2);
        l.add(doc2);

        ArrayList<String> result = myResource.getMeasurentsInAverage(l);
        assertNotNull(result);
    }
    }

