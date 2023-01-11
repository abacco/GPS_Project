package org.acme.GestioneAreaPredizioni.MachineLearning;

import javax.enterprise.context.ApplicationScoped;

import org.acme.Device;
import org.acme.GestioneAreaPredizioni.PublisherSubscriber.Predizione;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.List;

@ApplicationScoped
public class PredizioniAteroService {


    //crea un dataset per il modello da un arff file generato tramite i dati nel db e effettua la predizione della nuova instance
    public static Predizione getPredizioneAtero(List<Device> rilevazione){
        try{
            DataSource source = new DataSource(getArffAtero());
            Instance instance = getAsInstanceAtero(rilevazione);

            return MLModel.classifyInstance(instance,source);

        }catch (Exception e){
            e.printStackTrace();
    }
        return new Predizione();


    }

    //genera l'arff file da cui ottenere il datasource
    public static Instances getArffAtero(){
        return null;
        //generate dataset for AteroModel
    }

    public static Instance getAsInstanceAtero(List<Device> rilevazioni){
        return null;
        //trasforma la lista di misurazioni in una classe Instance
    }



}