public class Hekk {
    String nimi; // isendiväljad heki nime
    Põõsas[] poosadHekis; // ja selles hekis olevate põõsaste massiivi jaoks

    // konstruktor
    public Hekk(String nimi,  Põõsas[] poosadHekis) {
        this.nimi = nimi;
        this.poosadHekis = poosadHekis;
    }

    public String getNimi() {
        return this.nimi;
    }

    public double tagastaKorgeimPoosas() { // Meetod tagastab hekis oleva kõige kõrgema põõsa kõrguse.
        double maxPoosas = 0;
        for (Põõsas poosas: poosadHekis) {
            double vaadeldavaIsendiKorgus = poosas.getKorgus();
            if (vaadeldavaIsendiKorgus > maxPoosas) {
                maxPoosas = vaadeldavaIsendiKorgus;
            }
        }
        return maxPoosas;
    }

    public double tagastaHekiPikkus() { //  Meetod tagastab heki pikkuse ehk hekis olevate põõsaste laiuste summa.
        int summa = 0;
        for (int i = 0; i < poosadHekis.length; i++) {
            summa += poosadHekis[i].getLaius();
        }
        return summa;
    }

    public String toString() {
        return "Nimi: " + this.getNimi() + // heki nimi
                ", Kõige kõrgem põõsas: " + this.tagastaKorgeimPoosas() + // kõige kõrgema põõsa kõrgus
                ", Heki pikkus: " + this.tagastaHekiPikkus();  // heki pikkus

    }

}