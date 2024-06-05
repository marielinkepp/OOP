import java.time.LocalDateTime;

public class VäliseMonitorigaArvuti extends Arvuti {// Klass VäliseMonitorigaArvuti on klassi Arvuti alamklass


    public VäliseMonitorigaArvuti(String tootja, String tooTuup, LocalDateTime registreerimiseAeg) {
        super(tootja, tooTuup, registreerimiseAeg);
    }

    @Override
    public double arvutaArveSumma(double baashind) {
        return super.arvutaArveSumma(baashind) + 1.0; // välise monitoriga arvuti puhul lisandub 1 euro
    }
}


