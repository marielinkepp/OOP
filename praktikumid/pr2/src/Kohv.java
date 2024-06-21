class Kohv {

    private String kohvisort; // privaatne isendiväli kohvisordi jaoks
    private double hind; // privaatne isendiväli tassi kohvi hinna jaoks

    // konstruktor isendiväljade väärtustamiseks
    Kohv(String kohvisort, double hind) {
        this.kohvisort = kohvisort;
        this.hind = hind;
    }

    // mõlema isendivälja jaoks get-meetodid
    public String getKohvisort() {
        return this.kohvisort;
    }

    public double getHind() {
        return this.hind;
    }

    public double tassideMaksumus(int tassideArv) {
        return tassideArv * this.hind;
    }

    public String toString() { // toString meetod, mille tagastus sõltub sellest, kas programmeerija joob kohvi või mitte.
        return this.kohvisort; // väljastatakse ekraanile programmeerija nimi ja teade, et ta kohvi ei joo
    }
}