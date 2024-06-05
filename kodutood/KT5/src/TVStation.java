import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TVStation extends Broadcaster { // Broadcaster alamklass TVStation.

    private ArrayList<String> news;
    private String nimi;

    public TVStation(String nimi, ArrayList<String> news) { // Konstruktor võtab parameetriks listi uudistest.
        super();
        this.news = news;
        this.nimi = nimi;
    }

    void sendNews() { // Lisa meetod void sendNews(), mis broadcastib (ülemklassi meetodi abil) uudiste listist suvalise uudise.
        Random rand = new Random();
        int randomIndex = rand.nextInt(this.news.size());
        super.broadcast(this.news.get(randomIndex));
    }
}



class TestTV {
    public static void main(String[] args) { //Loo testklass, mis funktsionaalsust testib.

        //Luuakse TVStation objektid "Põhja-Korea rahvuslikud uudised" ja "Fox News" jaoks. Mõlemal on oma erinev uudiste list.
        ArrayList<String> uudised = new ArrayList<>();
        uudised.add("pk a");
        uudised.add("pk b");

        ArrayList<String> uudised2 = new ArrayList<>();
        uudised2.add("foxn c");
        uudised2.add("foxn d");

        TVStation pk = new TVStation("Põhja-Korea rahvuslikud uudised", uudised);
        TVStation fn = new TVStation("Fox News", uudised2);
        PirateStation ps = new PirateStation(); // Luuakse PirateStation, mis võtab vastu ja edastab mõlema eelneva uudiseid.

        TV kju = new TV("Kim Jong-un"); //Loo TV Kim Jong-un jaoks, mis kuulab Fox News ja rahvuslikke uudiseid (otse, mitte läbi piraatkanali).
        pk.subscribe(kju); // Põhja-Korea uudistekanal lisab Kim Jong-uni TV kuulajate hulka
        fn.subscribe(kju); // Fox News uudistekanal lisab Kim Jong-uni TV kuulajate hulka

        TV kyn = new TV("Kim Yong-nam"); // Loo TV Kim Yong-nam jaoks, mis kuulab ainult rahvuslikke uudiseid.
        pk.subscribe(kyn); // Põhja-Korea uudistekanal lisab Kim Yong-nami TV kuulajate hulka

        TV ppj = new TV("Pak Pong-ju"); //Loo TV Pak Pong-ju jaoks, mis kuulab piraatkanalit (selle kaudu mõlemaid teisi kanaleid).
        ps.subscribe(ppj); // Piraatkanal lisab Pak Pong-ju TV kuulajate hulka

        pk.subscribe(ps); // Põhja-Korea uudistekanal lisab piraatkanali kuulajate hulka
        fn.subscribe(ps); // Fox News uudistekanal lisab piraatkanali kuulajate hulka

        // Käivita mõlema uudistekanali sendNews() ja veendu, et õiged isikud õiged uudiseid kuulevad.
        pk.sendNews();
        fn.sendNews();

    }
}
