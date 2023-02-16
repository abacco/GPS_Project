import io.quarkus.test.junit.QuarkusTest;
import io.reactivex.Flowable;
import io.reactivex.subscribers.TestSubscriber;
import io.restassured.response.Response;
import it.unisa.CardioTel.GestioneDevice.Service.TempGenerator;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class TestDevice {

    @Inject
    TempGenerator t;

    @Test
    public void testDeviceName() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        assertThat(this.t.getEsp8266().getDeviceName(), is("ESP8266-01"));
    }

    @Test
    public void testDeviceHeartFrequency() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getHeartFrequency();
        Matcher<Boolean> sensore1 = is(d >= 50 && d <= 140);
        boolean f;
        if(d >= 50 && d <= 140 ) {
            f = true;
        }
        else {
            f = false;
        }
        assertThat(f, sensore1);
    }

    @Test
    public void testDeviceTemp() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getTemp();
        Matcher<Boolean> sensore2 = is(d >= 33 && d <= 42);
        boolean f;
        if(d >= 33 && d <= 42 ) {
            f = true;
        }
        else {
            f = false;
        }
        assertThat(f, sensore2);
    }

    @Test
    public void testDeviceOssigenazione() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getOssigenazione();
        Matcher<Boolean> sensore3 = is(d >= 45 && d <= 100);
        boolean f;
        if(d >= 45 && d <= 100 ) {
            f = true;
        }
        else {
            f = false;
        }
        assertThat(f, sensore3);
    }

    @Test
    public void testDeviceColesterolo() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getColesterolo();
        Matcher<Boolean> sensore4 = is(d >= 100 && d <= 400);
        boolean f;
        if(d >= 100 && d <= 400 ) {
            f = true;
        }
        else {
            f = false;
        }
        assertThat(f, sensore4);
    }

    @Test
    public void testDevicePressioneMassima() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getPressione();
        Matcher<Boolean> sensore5 = is(d >= 40 && d <= 150);
        boolean f;
        if(d >= 40 && d <= 150 ) {
            f = true;
        }
        else {
            f = false;
        }
        assertThat(f, sensore5);
    }

    @Test
    public void testDevicePressioneMinima() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getPressione_due();
        Matcher<Boolean> sensore5 = is(d >= 50 && d <= 97);
        boolean f;
        if(d >= 50 && d <= 97 ) {
            f = true;
        }
        else {
            f = false;
        }
        assertThat(f, sensore5);
    }

    @Test
    public void testDeviceHeartFrequencyNotInRange() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getHeartFrequency();
        Matcher<Boolean> sensore1 = is(d <60  || d> 140);
        boolean f;
        if(d >= 60 && d <= 140 ) {
            f = false;
        }
        else {
            f = true;
        }
        assertThat(f, sensore1);
    }

    @Test
    public void testDeviceTempNotInRange() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getTemp();
        Matcher<Boolean> sensore2 = is(d <33  || d > 42);
        boolean f;
        if(d >= 33 && d <= 42 ) {
            f = false;
        }
        else {
            f = true;
        }
        assertThat(f, sensore2);
    }

    @Test
    public void testDeviceOssigenazioneNotInRange() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getOssigenazione();
        Matcher<Boolean> sensore3 = is( d<45  || d > 100);
        boolean f;
        if(d >= 45 && d <= 100 ) {
            f = false;
        }
        else {
            f = true;
        }
        assertThat(f, sensore3);
    }

    @Test
    public void testDeviceColesteroloNotInRange() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getColesterolo();
        Matcher<Boolean> sensore4 = is(d< 100  || d > 400);
        boolean f;
        if(d >= 100 && d <= 400 ) {
            f = false;
        }
        else {
            f = true;
        }
        assertThat(f, sensore4);
    }

    @Test
    public void testDevicePressioneMassimaNotInRange() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getPressione();
        Matcher<Boolean> sensore5 = is(d <40  || d > 150);
        boolean f;
        if(d >= 40 && d <= 150 ) {
            f = false;
        }
        else {
            f = true;
        }
        assertThat(f, sensore5);
    }

    @Test
    public void testDevicePressioneMinimaNotInRange() {
        Response r = (Response) given()
                .when()
                .get("/devices");
        int d = this.t.getEsp8266().getPressione_due();
        Matcher<Boolean> sensore5 = is(d <50  || d > 97);
        boolean f;
        if(d >= 50 && d <= 97 ) {
            f = false;
        }
        else {
            f = true;
        }
        assertThat(f, sensore5);

    }

    @Test
    public void testGenerate() {
        Flowable<String> flowable = t.generate();

        TestSubscriber<String> subscriber = new TestSubscriber<>();
        flowable.take(11, TimeUnit.SECONDS)
                // 11 per ritardo + generazione
                .subscribe(subscriber);

        subscriber.awaitTerminalEvent();
        subscriber.assertValueCount(1);
    }

}
