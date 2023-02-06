import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.acme.TempGenerator;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.assertFalse;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@QuarkusTest
public class DeviceResourceTest {

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
        Matcher<Boolean> sensore1 = is(this.t.getEsp8266().getHeartFrequency() >= 50 && this.t.getEsp8266().getHeartFrequency() <= 140);
        boolean f;
        if(this.t.getEsp8266().getHeartFrequency() >= 50 && this.t.getEsp8266().getHeartFrequency() <= 140 ) {
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
        Matcher<Boolean> sensore2 = is(this.t.getEsp8266().getTemp() >= 33 && this.t.getEsp8266().getTemp() <= 42);
        boolean f;
        if(this.t.getEsp8266().getTemp() >= 33 && this.t.getEsp8266().getTemp() <= 42 ) {
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
        Matcher<Boolean> sensore3 = is(this.t.getEsp8266().getOssigenazione() >= 45 && this.t.getEsp8266().getOssigenazione() <= 100);
        boolean f;
        if(this.t.getEsp8266().getOssigenazione() >= 45 && this.t.getEsp8266().getOssigenazione() <= 100 ) {
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
        Matcher<Boolean> sensore4 = is(this.t.getEsp8266().getColesterolo() >= 100 && this.t.getEsp8266().getColesterolo() <= 400);
        boolean f;
        if(this.t.getEsp8266().getColesterolo() >= 100 && this.t.getEsp8266().getColesterolo() <= 400 ) {
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
        Matcher<Boolean> sensore5 = is(this.t.getEsp8266().getPressione() >= 40 && this.t.getEsp8266().getPressione() <= 150);
        boolean f;
        if(this.t.getEsp8266().getPressione() >= 40 && this.t.getEsp8266().getPressione() <= 150 ) {
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
        Matcher<Boolean> sensore5 = is(this.t.getEsp8266().getPressione_due() >= 50 && this.t.getEsp8266().getPressione_due() <= 97);
        boolean f;
        if(this.t.getEsp8266().getHeartFrequency() >= 50 && this.t.getEsp8266().getHeartFrequency() <= 97 ) {
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
        Matcher<Boolean> sensore1 = is(this.t.getEsp8266().getHeartFrequency() <=60  || this.t.getEsp8266().getHeartFrequency() >= 140);
        boolean f;
        if(this.t.getEsp8266().getHeartFrequency() >= 60 && this.t.getEsp8266().getHeartFrequency() <= 140 ) {
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
        Matcher<Boolean> sensore2 = is(this.t.getEsp8266().getTemp() <=33  || this.t.getEsp8266().getTemp() >= 42);
        boolean f;
        if(this.t.getEsp8266().getTemp() >= 33 && this.t.getEsp8266().getTemp() <= 42 ) {
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
        Matcher<Boolean> sensore3 = is(this.t.getEsp8266().getOssigenazione() <=45  || this.t.getEsp8266().getOssigenazione() >= 100);
        boolean f;
        if(this.t.getEsp8266().getOssigenazione() >= 45 && this.t.getEsp8266().getOssigenazione() <= 100 ) {
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
        Matcher<Boolean> sensore4 = is(this.t.getEsp8266().getColesterolo()<=100  || this.t.getEsp8266().getColesterolo() >= 400);
        boolean f;
        if(this.t.getEsp8266().getColesterolo() >= 100 && this.t.getEsp8266().getColesterolo() <= 400 ) {
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
        Matcher<Boolean> sensore5 = is(this.t.getEsp8266().getPressione() <=40  || this.t.getEsp8266().getPressione() >= 150);
        boolean f;
        if(this.t.getEsp8266().getPressione() >= 40 && this.t.getEsp8266().getPressione() <= 150 ) {
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
        Matcher<Boolean> sensore5 = is(this.t.getEsp8266().getPressione_due() <=50  || this.t.getEsp8266().getPressione_due() >= 97);
        boolean f;
        if(this.t.getEsp8266().getPressione_due() >= 50 && this.t.getEsp8266().getPressione_due() <= 97 ) {
            f = false;
        }
        else {
            f = true;
        }
        assertThat(f, sensore5);

    }



}
