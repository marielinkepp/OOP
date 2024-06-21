public class Isik {
    private String nimi, isikukood; // isendiväljad isiku nime ja isikukoodi jaoks
    private Double pikkus, mass; // isendiväljad isiku pikkuse ja massi jaoks

    // konstruktor
    public Isik(String isikuNimi, double isikuPikkus, String isikukood, double mass) {
        this.nimi = isikuNimi; // määratatkse konstruktoris, ei saa hiljem muuta
        this.pikkus = isikuPikkus;
        this.isikukood = isikukood; // määratatkse konstruktoris, ei saa hiljem muuta
        this.mass = mass;
    }

    public Isik(String isikuNimi, double isikuPikkus) {
        nimi = isikuNimi;
        pikkus = isikuPikkus;
    }

    public Isik(String isikuNimi) {
        nimi = isikuNimi;
    }

    public Isik() {}

    public void setPikkus(double sisendPikkus) {
        this.pikkus = sisendPikkus;
    }

    public void setMass(double sisendMass) {
        this.mass = sisendMass;
    }

    public String getNimi() {
        return this.nimi;
    }

    public Double getPikkus() {
        return this.pikkus;
    }

    public String getIsikukood() {
        return this.isikukood;
    }

    public Double getMass() {
        return this.mass;
    }

    public int suusakepiPikkus() {
        Double isendiPikkus = this.getPikkus();
        if (isendiPikkus == null) {
            isendiPikkus = 0.0;
        }
        return (int) Math.round(0.85 * isendiPikkus * 100);
    }

    /* lisameetodid */
    public double getKmi() {
        return this.mass / Math.pow(this.pikkus, 2.0);
    }

    public void muudaPikkust(double uusPikkus) {
        double vanaPikkus = this.getMass();

        if (uusPikkus > vanaPikkus) { // saab muuta juhul, kui uus pikkus on suurem
            setPikkus(uusPikkus);
        }
    }

    public void muudaMassi(double uusMass) {
        double vanaMass = this.getMass();

        if (uusMass != vanaMass) { // saab muuta juhul, kui uus mass on vanast erinev
            setMass(uusMass);
        }
    }

    public String toString() { // Küsisin nullide kontrollimise juures ChatGPt-lt abi, sest ei suutunud ise lahendust leida
        String tagastatavSone = this.nimi;
        if (this.getIsikukood() != null  &&  this.getMass() != null) {
            tagastatavSone += this.isikukood + "; " + this.mass ;
        } else if (this.getPikkus() != null){
            tagastatavSone += "; " + this.pikkus; }

        return tagastatavSone;
    }

    // Error: java: non-static method suusakepiPikkus() cannot be referenced from a static context
    public void main(String[] args) { // Eemaldasin static võtmesõna?
        System.out.println("suusakepi pikkus on " + suusakepiPikkus());
    }
}


class TestIsik {
    public static void main (String[] argv) {
        Isik a = new Isik("Juhan Juurikas", 1.99);
        System.out.println("Isik a on " + a);
    }
}

