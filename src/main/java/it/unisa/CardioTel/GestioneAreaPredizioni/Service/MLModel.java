package it.unisa.CardioTel.GestioneAreaPredizioni.Service;

import it.unisa.CardioTel.GestioneAreaPredizioni.Controller.Predizione;
import it.unisa.CardioTel.GestioneDevice.DBQueries;
import it.unisa.CardioTel.GestioneDevice.Device;
import weka.classifiers.Evaluation;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.functions.LinearRegression;


import javax.enterprise.context.ApplicationScoped;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class MLModel {

    public static final double percentageRischio = 45;
    public static final double percentageSicuro = 35;

    //tramite il datasource fornito crea un modello da sfruttare per la predizione
    public static LinearRegression getModel(DataSource source) throws Exception {

        //get dataset from datasource
        Instances dataset = source.getDataSet();
        dataset.setClassIndex(dataset.numAttributes() - 1);

        //Build model
        LinearRegression model = new LinearRegression();
        model.buildClassifier(dataset);
        return model;

    }


    public static Predizione classifyInstance(Instances instances, DataSource source) {


        try {
            //ottenimento modello dal dataset
            LinearRegression model = getModel(source);
            Evaluation eval= new Evaluation(instances);
            eval.crossValidateModel(model,instances,10,new Random());

            Predizione pr = new Predizione();

            //classificazione instance, il metodo fa una media delle predizioni
            double percent = 0;
            int cont = 0;
            for (Instance i : instances) {
                percent = +model.classifyInstance(i);
                cont++;
            }

            percent += percent / cont;


            pr.setPercentualeRischio(percent);

            //valore per send alert
            if (percent >= percentageRischio) {
                pr.setRischio("rischio");
            } else if (percent <= percentageSicuro) {
                pr.setRischio("sotto_controllo");
            } else {
                pr.setRischio("attenzione");
            }

            //inserisce la valutazione del modello da stampare
            pr.setModello(eval.toSummaryString());
            return pr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //genera l'arff file da cui ottenere il datasource in base alla malattia
    public static String getArff(String malattia, List<Device> rilevazione) {
        DBQueries query = new DBQueries();
        Instances dataset = null;
        String outputFilename = "";

        if (malattia.equals("infarto")) {
            dataset = PredizioniInfartoService.getAsInstanceInfarto(rilevazione);
            outputFilename = "RilevazioniSetInfarto.arff";
        } else {
            dataset = PredizioniAteroService.getAsInstanceAtero(rilevazione);
            outputFilename = "RilevazioniSetAtero.arff";
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename));
            writer.write(dataset.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.err.println("Failed to save data to: " + outputFilename);
            e.printStackTrace();
        }

        return outputFilename;

    }


    //associa una percentuale di rischio in base al valore corrispondente
    public static double calcolaPrediction(Device rilevazione, String malattia) {
        List<Double> percent = new ArrayList<>();


        percent.add(normalise(rilevazione.getPressione(), Device.minPresMas, Device.maxPresMas));
        percent.add(normalise(rilevazione.getPressione_due(), Device.minPresMin, Device.maxPresMin));
        percent.add(normalise(rilevazione.getPressione(), Device.minCol, Device.maxCol));
        if (malattia.equals("infarto")) {
            percent.add(normalise(rilevazione.getHeartFrequency(), Device.minHeart, Device.maxHeart));
        }
        return percent.stream().mapToDouble(a -> a).average().getAsDouble();
    }

    public static double normalise(int inValue, int min, int max) {

        double perc = max - min;
        double value = inValue - min;
        return (value / perc) * 100;

    }



}



