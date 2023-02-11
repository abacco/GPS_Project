import io.quarkus.test.junit.QuarkusTest;
import it.unisa.CardioTel.GestioneReport.Service.GestioneReportServiceImpl;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class TestReportService {

    @Inject
    GestioneReportServiceImpl myResource;

  @Test
    public void testGetAverages() {
    ArrayList<Document> l = new ArrayList<>();
    Document doc1 = new Document();
    doc1.put("heartFrequency", 10);
    doc1.put("colesterolo", 20);
    doc1.put("ossigenazione", 50);
    doc1.put("pressione minima", 40);
    doc1.put("pressione massima", 90);
    doc1.put("temp", 40);
    doc1.put("date", new Date());
    doc1.put("numInstancies", 2);
    l.add(doc1);

    Document doc2 = new Document();
    doc2.put("heartFrequency", 20);
    doc2.put("colesterolo", 30);
    doc2.put("ossigenazione", 150);
    doc2.put("pressione minima", 50);
    doc2.put("pressione massima", 90);
    doc2.put("temp", 60);
    doc2.put("date", new Date());
    doc2.put("numInstancies", 2);
    l.add(doc2);

      ArrayList<String> actualResult = myResource.getAverages(l,pot);
      ArrayList<String> expectedResult = new ArrayList<>();
      String expected = "Data : " + (new SimpleDateFormat("dd-MM-yyyy").format(new Date())) + ":=" +
            "\nFrequenza cardiaca : 15;" +
            "\nTemperatura : 50;" +
            "\nOssigenazione : 100;" +
            "\nColesterolo : 25;" +
            "\nPressione Minima : 45;" +
            "\nPressione Massima : 90;";
      expectedResult.add(expected);

    assertEquals(expectedResult, actualResult);
  }

  private Date [] pot = {new Date(), new Date()};
}

