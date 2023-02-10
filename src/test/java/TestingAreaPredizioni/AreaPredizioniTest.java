package TestingAreaPredizioni;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import it.unisa.CardioTel.GestioneDevice.Device;
import it.unisa.CardioTel.GestioneAreaPredizioni.Service.MLModel;
import it.unisa.CardioTel.GestioneAreaPredizioni.Service.PredizioniAteroService;
import it.unisa.CardioTel.GestioneAreaPredizioni.Service.PredizioniInfartoService;
import it.unisa.CardioTel.GestioneAreaPredizioni.Controller.PredictionGenerator;
import it.unisa.CardioTel.GestioneAreaPredizioni.Controller.Predizione;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.core.converters.ConverterUtils;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class AreaPredizioniTest {

    @Inject
    PredictionGenerator p;
    @Inject
    MLModel ml;

    @Test
    @Order(4)
    public void CheckRischioCorrettoAtero() {
        Response r = (Response) given()
                .when()
                .get("/AreaPredizioni/stream");

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
                .get("/AreaPredizioni/stream");

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
                .get("/AreaPredizioni/stream");

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
                .get("/AreaPredizioni/stream");

        List<Predizione> predizioni =this.p.getPredizioni();
        Matcher<Boolean> predictionAttended = is(!checkPredictionCohesion(predizioni.get(1)));
        boolean f;

        if(checkPredictionCohesion(predizioni.get(1)))
            f=false;
        else
            f=true;

        assertThat(f, predictionAttended);
    }


    @Test
    @Order(5)
    public void CheckProbabiltyAtero() throws Exception {
        //calcola la predizione effettiva
        List<Device> rilevazioni = this.p.getRilevazioni();
        double predizioneEffettiva = 0;
        for(Device ril : rilevazioni){
            predizioneEffettiva += ml.calcolaPrediction(ril,"atero");
        }
        predizioneEffettiva = predizioneEffettiva/rilevazioni.size();

        //fa la predizione tramite modello e lo ottiene
        double predizioniCalcolate = PredizioniAteroService.getPredizioneAtero(rilevazioni).getPercentualeRischio();
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(ml.getArff("atero",rilevazioni));
        LinearRegression model = ml.getModel(source);

        //ottengo il mean absolute error per verificare lo scarto entro il quale deve essere preciso
        Evaluation eval= new Evaluation(PredizioniAteroService.getAsInstanceAtero(rilevazioni));
        eval.crossValidateModel(model, PredizioniAteroService.getAsInstanceAtero(rilevazioni),10,new Random());
        double meanAbsoluteError = eval.meanAbsoluteError();

        System.out.println(predizioniCalcolate + "%\n" + predizioneEffettiva +"%\nMAE\t " + meanAbsoluteError);
        //verifica se la predizione calcolata è uguale alla predizione effettiva per uno scarto di MAE
        assertTrue(predizioniCalcolate <= predizioneEffettiva + meanAbsoluteError &&
                predizioniCalcolate >= predizioneEffettiva - meanAbsoluteError   );

    }

    @Test
    @Order(6)
    public void CheckProbabiltyInfarto() throws Exception {
        //calcola la predizione effettiva
        List<Device> rilevazioni = this.p.getRilevazioni();
        double predizioneEffettiva = 0;
        for(Device ril : rilevazioni){
            predizioneEffettiva += ml.calcolaPrediction(ril,"infarto");
        }
        predizioneEffettiva = predizioneEffettiva/rilevazioni.size();

        //fa la predizione tramite modello e lo ottiene
        double predizioniCalcolate = PredizioniInfartoService.getPredizioneInfarto(rilevazioni).getPercentualeRischio();
        ConverterUtils.DataSource source = new ConverterUtils.DataSource(ml.getArff("infarto",rilevazioni));
        LinearRegression model = ml.getModel(source);

        //ottengo il mean absolute error per verificare lo scarto entro il quale deve essere preciso
        Evaluation eval= new Evaluation(PredizioniInfartoService.getAsInstanceInfarto(rilevazioni));
        eval.crossValidateModel(model, PredizioniInfartoService.getAsInstanceInfarto(rilevazioni),10,new Random());
        double meanAbsoluteError = eval.meanAbsoluteError();

        System.out.println(predizioniCalcolate + "%\n" + predizioneEffettiva +"%\nMAE\t " + meanAbsoluteError);
        //verifica se la predizione calcolata è uguale alla predizione effettiva per uno scarto di MAE
        assertTrue(predizioniCalcolate <= predizioneEffettiva + meanAbsoluteError &&
                predizioniCalcolate >= predizioneEffettiva - meanAbsoluteError   );

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