package org.acme.GestioneAreaPredizioni.MachineLearning;

import org.acme.DBQueries;
import org.acme.Device;
import org.acme.GestioneAreaPredizioni.PublisherSubscriber.PredictionGenerator;
import org.acme.GestioneAreaPredizioni.PublisherSubscriber.Predizione;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.functions.LinearRegression;

import javax.enterprise.context.ApplicationScoped;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

import static org.acme.GestioneAreaPredizioni.MachineLearning.PredizioniAteroService.getAsInstanceAtero;
import static org.acme.GestioneAreaPredizioni.MachineLearning.PredizioniInfartoService.getAsInstanceInfarto;

@ApplicationScoped
public class MLModel {


    //tramite il datasource fornito crea un modello da sfruttare per la predizione
    public static LinearRegression getModel(DataSource source) throws Exception{

        //get dataset from datasource
        Instances dataset = source.getDataSet();
        dataset.setClassIndex(dataset.numAttributes()-1);

        //Build model
        LinearRegression model = new LinearRegression();
        model.buildClassifier(dataset);

        return model;

    }


    public static Predizione classifyInstance(Instances instances, DataSource source) {
        //ottenimento modello dal dataset
        LinearRegression model = null;
        try {
            model = getModel(source);


            Predizione pr = new Predizione();

            //classificazione instance, il metodo fa una media delle predizioni
            double percent=0;
            int cont =0;
            for(Instance i : instances){
                percent =+model.classifyInstance(i);
                cont++;
                }

            percent+=percent/cont;



            pr.setPercentualeRischio(percent);

            //valore per send alert
            if(percent >= 20.00){
                pr.setRischio("rischio");
            }else if(percent <= 10){
                pr.setRischio("sotto_controllo");
            }else{
                pr.setRischio("attenzione");
            }

            pr.setModello(model.toString());
            return pr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    //genera l'arff file da cui ottenere il datasource in base alla malattia
    public static String getArff(String malattia, List<Device> rilevazione){
        DBQueries query = new DBQueries();
        Instances dataset =  null;
        String outputFilename = "";

        if(malattia.equals("infarto")){
             dataset = getAsInstanceInfarto(rilevazione);
             outputFilename = "RilevazioniSetInfarto.arff";
        }else{
             dataset = getAsInstanceAtero(rilevazione);
             outputFilename = "RilevazioniSetAtero.arff";
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename));
            writer.write(dataset.toString());
            writer.flush();
            writer.close();
        }
        catch (Exception e) {
            System.err.println("Failed to save data to: " + outputFilename);
            e.printStackTrace();
        }

        return outputFilename;

    }
}



