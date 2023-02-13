package it.unisa.CardioTel.GestioneAreaPredizioni.Service;

import javax.enterprise.context.ApplicationScoped;

import it.unisa.CardioTel.GestioneAreaPredizioni.Controller.Predizione;
import it.unisa.CardioTel.GestioneDevice.Service.Device;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Timed;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;

import java.util.List;

@Timed(name = "time", unit = MetricUnits.MILLISECONDS)
@ApplicationScoped
public class PredizioniAteroService {


    //crea un dataset per il modello da un arff file generato tramite i dati nel db e effettua la predizione della nuova instance
    public static Predizione getPredizioneAtero(List<Device> rilevazione){
        if(rilevazione.size()>=10){

            try{
            DataSource source = new DataSource(MLModel.getArff("atero",rilevazione));
            Instances instance = getAsInstanceAtero(rilevazione);

            return MLModel.classifyInstance(instance,source);

        }catch (Exception e){
            e.printStackTrace();
    }}
        return new Predizione();



    }


    public static Instances getAsInstanceAtero(List<Device> rilevazioni){
        @Deprecated
        FastVector deviceList = new FastVector<>();

        @Deprecated
        FastVector instance = new FastVector<>();




        //creare fastvector con gli attributi necessari alla predizione

        instance.addElement(new Attribute("heartFreq"));
        instance.addElement(new Attribute("pressione"));
        instance.addElement(new Attribute("pressione_due"));
        instance.addElement(new Attribute("colesterolo"));
        instance.addElement(new Attribute("predizione"));

        Instances data = new Instances("dataSetAtero", instance, 0);


        for(int i=0;i<rilevazioni.size();i++){
            double[] vals = new double[data.numAttributes()];  // important: needs NEW array!

            Device rilevazione = rilevazioni.get(i);
            vals[0] = rilevazione.getHeartFrequency();
            vals[1] = rilevazione.getPressione();
            vals[2] = rilevazione.getPressione_due();
            vals[3] = rilevazione.getColesterolo();
            vals[4] = MLModel.calcolaPrediction(rilevazione,"atero");

            data.add(new DenseInstance(1.0,vals));

        }
        data.setClassIndex(data.numAttributes()-1);
        return data;

    }



}