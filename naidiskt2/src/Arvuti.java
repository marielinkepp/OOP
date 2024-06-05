import java.time.LocalDateTime;

public class Arvuti {
    public String tootja, tooTuup;
    public LocalDateTime registreerimiseAeg;


    public Arvuti(String tootja, String tooTuup, LocalDateTime registreerimiseAeg) {
        this.tootja = tootja;
        this.tooTuup = tooTuup;
        this.registreerimiseAeg = registreerimiseAeg;
    }

    //Mõlema klassi isenditel peavad olema järgnevad meetodid töö kohta käiva info küsimiseks:

    public String getTootja() {
        return this.tootja;
    }
    public boolean onKiirtöö()  {
        return this.tooTuup.matches("kiirtöö");
    }
    public LocalDateTime getRegistreerimiseAeg() {
        return this.registreerimiseAeg;
    }

    //Veel peab olema meetod arvutaArveSumma, mis võtab argumendiks baashinna (double-tüüpi), ning tagastab töö lõpphinna (hinna määramise reeglid on allpool).
    public double arvutaArveSumma(double baashind) {

        /* Baashind arvutatakse parandamiseks kulunud aja ning parandaja tunnitasu põhjal.
        Lõpphinna saamiseks lisatakse baashinnale fikseeritud summa töö vastuvõtmise eest (2€ arvuti eest ja täiendav +1€ välise monitori puhul) ning lisaks veel 10€ kiirtellimuse puhul.
        */
        double tavatasu = baashind + 2.0;
        return onKiirtöö() ? tavatasu + 10.0 : tavatasu;
    }

    @Override
    public String toString() {
        return "Arvuti{" +
                "tootja='" + tootja + '\'' +
                ", tooTuup='" + tooTuup + '\'' +
                ", registreerimiseAeg=" + registreerimiseAeg +
                '}';
    }
}
