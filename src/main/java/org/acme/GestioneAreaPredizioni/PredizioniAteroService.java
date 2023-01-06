package org.acme.GestioneAreaPredizioni;

import javax.enterprise.context.ApplicationScoped;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.functions.LinearRegression;

@ApplicationScoped
public class PredizioniAteroService implements PredizioneAtero {


    //crea un dataset per il modello da un arff file generato tramite i dati nel db e effettua la predizione della nuova instance
    public double getPredizioneAtero(Rilevazione rilevazione){
        try{
            DataSource source = new DataSource(getArffAtero());
            Instance instance = getAsInstanceAtero(rilevazione);
            return MLModel.getModel(source).classifyInstance(instance);
        }catch (Exception e){
        return 0;
    }


    }

    //genera l'arff file da cui ottenere il datasource
    public Instances getArffAtero(){
        return null;
        //generate dataset for AteroModel
    }

    public Instance getAsInstanceAtero(Rilevazione rilevazione){
        return null;
        //trasforma il POJO  in una classe Instance
    }



}