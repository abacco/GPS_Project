package org.acme.GestioneAreaPredizioni.PublisherSubscriber;

public class Predizione {

    public enum Rischio{sotto_controllo,attenzione,rischio};

    Rischio rischio;
    double percentualeRischio;
    String modello;

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public double getPercentualeRischio() {
        return percentualeRischio;
    }

    public void setPercentualeRischio(double percentualeRischio) {
        this.percentualeRischio = percentualeRischio;
    }

    public Predizione.Rischio getRischio() {
        return rischio;
    }

    public void setRischio(String rischio) {
        if(rischio.equals("sotto_controllo")){
            this.rischio = Rischio.sotto_controllo;
        }else if(rischio.equals("attenzione")){
            this.rischio = Rischio.attenzione;
        }else {
            this.rischio = Rischio.rischio;
        }

    }

    @Override
    public String toString(){
        return "{percentualeRischio:\"" +percentualeRischio+"\"," +
                "rischio:\""+rischio+"\"," +
                "modello:\"" +modello + "\"}";
    }
}