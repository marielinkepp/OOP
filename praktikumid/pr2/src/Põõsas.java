public class Põõsas {
    private double korgus, laius; // privaatsed isendiväljad põõsa kõrguse (double) ja laiuse (double) jaoks

    // konstruktor
    public Põõsas(double korgus,  double laius) {
        this.korgus = korgus;
        this.laius = laius;
    }

    // Kõigi isendiväljade jaoks on vastavad get- ja set-meetodid.
    public void setKorgus(double sisendKorgus) {
        this.korgus = sisendKorgus;
    }

    public void setLaius(double sisendLaius) {
        this.laius = sisendLaius;
    }

    public double getKorgus() {
        return this.korgus;
    }

    public double getLaius() {
        return this.laius;
    }

    public double aastaJuurdekasv() {
        return Math.random()*0.1*this.getKorgus(); // tagastab kõrguse prognoositava aastajuurdekasvu, mis on juhuslik arv vahemikust 0 kuni 0,1*kõrgus. (Ümardada pole vaja.)
    }

    public String toString() {
        return "Kõrgus: " + this.getKorgus() +
                ", Laius: " + this.getLaius() +
                ", Prognoositav juurdekasv aastas: " + this.aastaJuurdekasv();  // Eelmise meetodi abil näidatakse ka prognoositav aastajuurdekasv.

    }
}