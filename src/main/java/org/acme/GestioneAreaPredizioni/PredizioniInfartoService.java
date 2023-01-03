package org.acme.GestioneAreaPredizioni;

import javax.enterprise.context.ApplicationScoped;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.functions.LinearRegression;

@ApplicationScoped
public class PredizioniInfartoService implements PredizioneInfarto {


    public double getPredizioneInfarto(Rilevazione rilevazione){
        try {
            DataSource source = new DataSource(getArffInfarto());
            Instance instance = getAsInstanceInfarto(rilevazione);
            return MLModel.getModel(source).classifyInstance(instance);
        }catch (Exception e){
            return 0;
        }
    }

    //genera l'arff file da cui ottenere il datasource

    public Instances getArffInfarto(){
        return null;
        //generate dataset for AteroModel
    }

    public Instance getAsInstanceInfarto(Rilevazione rilevazione){
        return null;
        //trasforma il POJO  in una classe Instance
    }


}