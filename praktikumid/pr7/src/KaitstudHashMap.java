import java.util.HashMap;
import java.util.Map;

public class KaitstudHashMap {

    private Map<String, String> andmed = new HashMap<>();

    public void lisa(String võti, String väärtus) {
        synchronized (this) {
            if (!andmed.containsKey(võti)) {
                andmed.put(võti, väärtus);
            }
        }
    }

    public String asenda(String võti, String väärtus) {
        synchronized (this) {
            if (andmed.containsKey(võti)) {
                String vanaVäärtus = andmed.get(võti);
                andmed.put(võti, väärtus);
                return vanaVäärtus;
            }
            return null;
        }
    }

    public String otsi(String võti, String kuiVäärtusPuudub) { // kaks lõime võivad korraga andmetele ligi pääseda
        synchronized (this) {
            if (andmed.containsKey(võti)) {
                return andmed.get(võti);
            }
            return kuiVäärtusPuudub;
        }
    }
}