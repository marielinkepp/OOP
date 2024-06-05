public class SuurimaViiviseLeidja implements Kontrollija { //2. Klass SuurimaViiviseLeidja realiseerib liidest Kontrollija.

    private double senineSuurimViivis;
    private String suurimaViivisegaLaenutajNimi, suurimaViivisegaTeoseKirjeldus;

    public SuurimaViiviseLeidja() {
        this.senineSuurimViivis = 0.0;
        this.suurimaViivisegaLaenutajNimi = "";
        this.suurimaViivisegaTeoseKirjeldus = "";
    }

    public double getSenineSuurimViivis() {
        return senineSuurimViivis;
    }

    public String getSuurimaViivisegaLaenutajNimi() {
        return suurimaViivisegaLaenutajNimi;
    }

    public String getSuurimaViivisegaTeoseKirjeldus() {
        return suurimaViivisegaTeoseKirjeldus;
    }

    public void setSenineSuurimViivis(double senineSuurimViivis) {
        this.senineSuurimViivis = senineSuurimViivis;
    }

    public void setSuurimaViivisegaLaenutajNimi(String suurimaViivisegaLaenutajNimi) {
        this.suurimaViivisegaLaenutajNimi = suurimaViivisegaLaenutajNimi;
    }

    public void setSuurimaViivisegaTeoseKirjeldus(String suurimaViivisegaTeoseKirjeldus) {
        this.suurimaViivisegaTeoseKirjeldus = suurimaViivisegaTeoseKirjeldus;
    }

    public void saadaHoiatus() { //Klassis peab olema void-tüüpi parameetriteta meetod saadaHoiatus,
        System.out.println("Suurim viivis on laenutajal " + this.getSuurimaViivisegaLaenutajNimi() + ". Tegemist on teosega " + this.getSuurimaViivisegaTeoseKirjeldus());  //mis prindib ekraanile kõige suurema viivisega laenutaja nime ja tema poolt laenutatud teose kirjelduse.
    }

    @Override
    public void salvestaViivis(String laenutajaNimi, String teoseKirjeldus, double viiviseSuurus) { //Mälu kokkuhoiu mõttes ei tohiks SuurimaViiviseLeidja jätta meelde rohkem andmeid, kui on tarvis suurima viivisega inimesele hoiatuse saatmiseks

        if (viiviseSuurus > this.getSenineSuurimViivis()) {
            this.setSenineSuurimViivis(viiviseSuurus);
            this.setSuurimaViivisegaLaenutajNimi(laenutajaNimi);
            this.setSuurimaViivisegaTeoseKirjeldus(teoseKirjeldus);
        }
    }
}
