package org.acme.GestioneAreaPredizioni.MachineLearning;

import javax.enterprise.context.ApplicationScoped;

import org.acme.Device;
import org.acme.GestioneAreaPredizioni.PublisherSubscriber.Predizione;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.List;

@ApplicationScoped
public class PredizioniInfartoService {

    //crea un dataset per il modello da un arff file generato tramite i dati nel db e effettua la predizione della nuova instance
    public static Predizione getPredizioneInfarto(List<Device> rilevazione){


        //controlla se ci sono abbastanza instance per fare la tenfold validation
        if(rilevazione.size()>=10){

            try {
                Predizione pr = new Predizione();

                //creazione datasource per Infarto con i dati del db
                DataSource source = new DataSource(MLModel.getArff("infarto", rilevazione));

                //creazione Intance da classificare
                Instances instance = getAsInstanceInfarto(rilevazione);


                return MLModel.classifyInstance(instance,source);



            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return new Predizione();
    }


    public static Instances getAsInstanceInfarto(List<Device> rilevazioni){
        @Deprecated
        FastVector deviceList = new FastVector<>();

        @Deprecated
        FastVector instance = new FastVector<>();




        //creare fastvector con gli attributi necessari alla predizione

        instance.addElement(new Attribute("pressione"));
        instance.addElement(new Attribute("pressione_due"));
        instance.addElement(new Attribute("colesterolo"));
        instance.addElement(new Attribute("predizione"));

        Instances data = new Instances("dataSetAtero", instance, 0);


        for(int i=0;i<rilevazioni.size();i++){
            double[] vals = new double[data.numAttributes()];  // important: needs NEW array!

            Device rilevazione = rilevazioni.get(i);
            vals[0] = rilevazione.getPressione();
            vals[1] = rilevazione.getPressione_due();
            vals[2] = rilevazione.getColesterolo();
            vals[3] = MLModel.calcolaPrediction(rilevazione,"infarto");

            data.add(new DenseInstance(1.0,vals));

        }

        data.setClassIndex(data.numAttributes()-1);
        return data;

    }


}