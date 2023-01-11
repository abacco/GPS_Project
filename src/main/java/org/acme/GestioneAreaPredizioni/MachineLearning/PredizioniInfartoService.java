package org.acme.GestioneAreaPredizioni.MachineLearning;

import javax.enterprise.context.ApplicationScoped;

import org.acme.Device;
import org.acme.GestioneAreaPredizioni.PublisherSubscriber.Predizione;
import weka.classifiers.functions.LinearRegression;
import weka.core.Instance;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.List;

@ApplicationScoped
public class PredizioniInfartoService {

    //crea un dataset per il modello da un arff file generato tramite i dati nel db e effettua la predizione della nuova instance
    public static Predizione getPredizioneInfarto(List<Device> rilevazione){
        try {
            Predizione pr = new Predizione();

            //creazione datasource per Infarto con i dati del db
            DataSource source = new DataSource(getArffInfarto());

            //creazione Intance da classificare
            Instance instance = getAsInstanceInfarto(rilevazione);

            return MLModel.classifyInstance(instance,source);



        }catch (Exception e){
            e.printStackTrace();
        }

        return new Predizione();
    }

    //genera l'arff file da cui ottenere il datasource e restituisce il nome del file

    public static String getArffInfarto(){
        return null;
        //generate dataset for AteroModel
    }

    public static Instance getAsInstanceInfarto(List<Device> rilevazioni){
        return null;
        //trasforma la lista di misurazioni in una classe Instance
    }


}