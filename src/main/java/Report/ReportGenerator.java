/*package Report;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ReportGenerator {


    @Counted(name = "performedChecks", description = "How many primality checks have been performed.")
    @Timed(name = "checksProcessedData", description = "A measure of how long it takes to read new incoming data.", unit = MetricUnits.MILLISECONDS)
    public String process(byte[] data) {
        String d = new String(data);
        System.out.println("Receiving readings: " + d);
        return d;
  }
}*/
