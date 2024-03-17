import java.util.ArrayList;
import java.util.List;

public class PirateStation extends Broadcaster implements BroadcastListener { // Broadcaster alamklass PirateStation, mis lisaks implementib BroadcastListener.

    public PirateStation() {super();}

    @Override
    public void listen(String s) { // PirateStation võtab teiste jaamade saateid vastu (listen) ja broadcastib need kohe kõigile oma kuulajatele edasi.
        super.broadcast(s);
    }

    @Override
    void subscribe(BroadcastListener bl) {super.subscribe(bl);}
}
