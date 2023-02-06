package TestingAreaPredizioni;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.acme.GestioneAreaPredizioni.MachineLearning.MLModel;
import org.acme.GestioneAreaPredizioni.PredizioniResources;
import org.acme.GestioneAreaPredizioni.PublisherSubscriber.PredictionGenerator;
import org.acme.GestioneAreaPredizioni.PublisherSubscriber.Predizione;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import javax.inject.Inject;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class AreaPredizioniTest {

    @Inject
    PredictionGenerator p;

    @Test
    @Order(4)
    public void CheckRischioCorrettoAtero() {
        Response r = (Response) given()
                .when()
                .get("/AreaPredizioni");

        List<Predizione> predizioni =this.p.getPredizioni();
        Matcher<Boolean> predictionAttended = is(checkPredictionCohesion(predizioni.get(0)));
        boolean f;

        if(checkPredictionCohesion(predizioni.get(0)))
            f=true;
        else
            f=false;

        assertThat(f, predictionAttended);

    }

    @Test
    @Order(2)
    public void CheckRischioErratoAtero() {
        Response r = (Response) given()
                .when()
                .get("/AreaPredizioni");

        List<Predizione> predizioni =this.p.getPredizioni();
        Matcher<Boolean> predictionAttended = is(!checkPredictionCohesion(predizioni.get(0)));
        boolean f;

        if(checkPredictionCohesion(predizioni.get(0)))
            f=false;
        else
            f=true;

        assertThat(f, predictionAttended);
    }

    @Test
    @Order(3)
    public void CheckRischioCorrettoInfarto() {

        Response r = (Response) given()
                .when()
                .get("/AreaPredizioni");

        List<Predizione> predizioni =this.p.getPredizioni();


        Matcher<Boolean> predictionAttended = is(checkPredictionCohesion(predizioni.get(1)));
        boolean f;

        if(checkPredictionCohesion(predizioni.get(1)))
            f=true;
        else
            f=false;

        assertThat(f, predictionAttended);

    }

    @Test
    @Order(1)
    public void CheckRischioErratoInfarto() {
        Response r = (Response) given()
                .when()
                .get("/AreaPredizioni");

        List<Predizione> predizioni =this.p.getPredizioni();
        Matcher<Boolean> predictionAttended = is(!checkPredictionCohesion(predizioni.get(1)));
        boolean f;

        if(checkPredictionCohesion(predizioni.get(1)))
            f=false;
        else
            f=true;

        assertThat(f, predictionAttended);
    }




    //restituisce true se il rischio corrisponde alla percentuale corrispondente
    private boolean checkPredictionCohesion(Predizione p) {
        boolean f;
        double percent = p.getPercentualeRischio();

        if(p.getRischio().equals(Predizione.Rischio.sotto_controllo)) {
            if(percent<= MLModel.percentageSicuro)
                return true;
        }else if(p.getRischio().equals(Predizione.Rischio.attenzione)) {
            if(percent>MLModel.percentageSicuro && percent<MLModel.percentageRischio)
                return true;
        }else if(percent>=MLModel.percentageRischio){
            return true;
        }

        return false;
    }

}