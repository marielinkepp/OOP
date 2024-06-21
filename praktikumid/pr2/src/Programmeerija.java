class Programmeerija {
    private String programmeerijaNimi; // privaatne isendiväli programmeerija nime jaoks
    private double riduKoodi; // privaatne isendiväli programmeerija tulemuslikkuse näitamiseks ehk mitu rida koodi päevas ta keskmiselt kirjutab
    private int tasseKohvi; // privaatne isendiväli, mis kirjeldab, mitu tassi kohvi joob programmeerija päeva jooksul
    private Kohv lemmikkohv; // privaatne isendiväli programmeerija lemmikkohvi jaoks

    // konstruktor isendiväljade väärtustamiseks
    Programmeerija(String programmeerijaNimi, double riduKoodi, int tasseKohvi, Kohv lemmikkohv) {
        this.programmeerijaNimi = programmeerijaNimi;
        this.riduKoodi = riduKoodi;
        this.tasseKohvi = tasseKohvi;
        this.lemmikkohv = lemmikkohv;
    }

    // konstruktor juhuks, kui programmeerija kohvi ei joo.
    Programmeerija(String programmeerijaNimi, double riduKoodi) {
        this.programmeerijaNimi = programmeerijaNimi;
        this.riduKoodi = riduKoodi;
    }

    // isendiväljade tasseKohvi ja riduKoodi jaoks get- ja set-meetodid
    public void setTasse(int sisendTassideArv) {
        this.tasseKohvi = sisendTassideArv;
    }

    public void setRidu(double sisendKoodiridadeArv) {
        this.riduKoodi = sisendKoodiridadeArv;
    }

    public int getTassideArv() {
        return this.tasseKohvi;
    }

    public double getKoodiridadeArv() {
        return this.riduKoodi;
    }


    public boolean kasJoobKohvi() {
        if (lemmikkohv != null) { // kui lemmikkohv pole null,
            return true; // siis tagastab true
        } else {
            return false; // muidu tagastab false
        }
    }

    public double koodiTassiKohta() {
        if (kasJoobKohvi()) { // kui programmeerija joob kohvi,
            return this.riduKoodi / this.tasseKohvi; // siis tagastab, mitu rida koodi suudab programmeerija kirjutada, juues ühe tassi kohvi (valem: riduKoodi / tasseKohvi).
        } else { // Kui programmeerija kohvi ei joo
            System.out.println(this.programmeerijaNimi + ". Kohvi ei joo"); // väljastatakse ekraanile programmeerija nimi ja teade, et ta kohvi ei joo
            return -1; // tagastatakse -1;
        }
    }

    public String toString() { // toString meetod, mille tagastus sõltub sellest, kas programmeerija joob kohvi või mitte.
        if (kasJoobKohvi()) { // kui programmeerija joob kohvi, siis tagastab meetod:
            return this.programmeerijaNimi + // programmeerija nime,
                    ". Lemmikkohv on " + this.lemmikkohv + // lemmikkohvi sordi nime,
                    " hinnaga " + this.lemmikkohv.getHind() + // lemmikkohvi tassi hinna,
                    ". Igapäevaselt kohvi joomisele kulub " + this.lemmikkohv.tassideMaksumus(getTassideArv()) + " eurot. " +// rahasumma, mis igapäevaselt kulub kohvi joomisele (kasutades tassideMaksumus isendimeetodit),
                    "Programmeerija tulemuslikkus on " + this.koodiTassiKohta() + " rida/tass";  //meetodi koodiTassiKohta väärtuse.
        } else { // Kui programmeerija kohvi ei joo,
            return this.programmeerijaNimi + ". Kohvi ei joo"; // väljastatakse ekraanile programmeerija nimi ja teade, et ta kohvi ei joo
        }
    }
}