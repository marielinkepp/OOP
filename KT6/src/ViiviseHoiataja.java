import java.util.ArrayList;
import java.util.List;

public class ViiviseHoiataja implements Kontrollija{ //2. Klass ViiviseHoiataja realiseerib liidest Kontrollija.

    private double lubatudViivis;
    private ArrayList<String> hoiatatavadLaenutajad;

    public ViiviseHoiataja(double lubatudViivis) { //Klassis peab olema ühe double argumendiga konstruktor, mille abil saab määrata lubatud viivise.
        this.lubatudViivis = lubatudViivis;
        this.hoiatatavadLaenutajad = new ArrayList<>();
    }

    public double getLubatudViivis() {
        return lubatudViivis;
    }



    public List<String> getHoiatatavadLaenutajad() {
        return this.hoiatatavadLaenutajad;
    }
    //Meetod getHoiatatavadLaenutajad peab tagastama listi kõigi nende laenutajate nimedega, kelle viivise suurus ületab lubatud piiri.



    public void addLaenutajaNimi(String nimi) {
        this.getHoiatatavadLaenutajad().add(nimi);
    }

    @Override
    public void salvestaViivis(String laenutajaNimi, String teoseKirjeldus, double viiviseSuurus) {

        // kui viivis ületab lubatud viivise piiri ja kui seda nime pole veel salvestatud.
        if (viiviseSuurus > this.getLubatudViivis() && !this.getHoiatatavadLaenutajad().contains(laenutajaNimi)) {

            this.addLaenutajaNimi(laenutajaNimi);  //Meetod salvestaViivis peab argumendiks saadud laenutaja nime meelde jätma,
        }
    }

}
