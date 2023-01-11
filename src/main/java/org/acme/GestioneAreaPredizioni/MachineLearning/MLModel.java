package org.acme.GestioneAreaPredizioni.MachineLearning;

import org.acme.GestioneAreaPredizioni.PublisherSubscriber.Predizione;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.functions.LinearRegression;

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


    public static Predizione classifyInstance(Instance instance, DataSource source) {
        //ottenimento modello dal dataset
        LinearRegression model = null;
        try {
            model = getModel(source);

            Predizione pr = new Predizione();

            //classificazione instance
            double percent = model.classifyInstance(instance);
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
}



