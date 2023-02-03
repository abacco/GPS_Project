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


    //transforma in stringa divisa da virgole per poter essere splittata
    @Override
    public String toString(){
        return Math.round(percentualeRischio)+"," +
                rischio+"," +
                modello;
    }

    //fa l'operazione inversa del toString, splitta e crea un'instanza di predizione
    public static Predizione parsePredizione(String s){
        String[] arg=s.split(",");
        Predizione p = new Predizione();
        p.setPercentualeRischio(Double.parseDouble(arg[0]));
        p.setRischio(arg[1]);
        p.setModello(arg[2]);
        return p;
    }
}