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
            Instances instance = getAsInstanceAtero(rilevazione, "testing");

            return MLModel.classifyInstance(instance,source);

        }catch (Exception e){
            e.printStackTrace();
    }}
        return new Predizione();



    }


    public static Instances getAsInstanceAtero(List<Device> rilevazioni, String testing){
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
            double[] vals = new double[instance.size()];  // important: needs NEW array!

            Device rilevazione = rilevazioni.get(i);
            vals[0] = MLModel.normalise(rilevazione.getPressione(), Device.minPresMas, Device.maxPresMas);
            vals[1] = MLModel.normalise(rilevazione.getPressione_due(), Device.minPresMin, Device.maxPresMin);
            vals[2] = MLModel.normalise(rilevazione.getColesterolo(), Device.minCol, Device.maxCol);
            if (testing == null)
                vals[3] = MLModel.calcolaPrediction(rilevazione,"atero");


            data.add(new DenseInstance(1.0,vals));

        }


        return data;

    }



}