import java.util.ArrayList;

public class Broadcaster {

    private ArrayList<BroadcastListener> broadcastListeners;

    public Broadcaster() {
        this.broadcastListeners = new ArrayList<>();
    }

    void subscribe(BroadcastListener bl) { // Broadcaster jätab meelde kõik BroadcastListenerid, mis talle subscribe meetodiga ette antakse.
        this.broadcastListeners.add(bl);

    }
    void broadcast(String s) { // broadcast meetodi kutsumisel tuleb kõigile listeneridele ette antud string edasi anda (listen meetodit kutsuda).
        this.broadcastListeners.forEach(broadcastListener -> broadcastListener.listen(s));
    }

}
