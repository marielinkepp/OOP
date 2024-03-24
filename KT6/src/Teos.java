import java.util.Arrays;
import java.util.HashMap;

public abstract class Teos implements Comparable<Teos>{ //Klass Teos realiseerib liidest Comparable<Teos>, kusjuures compareTo meetod realiseeritakse nii, et teoseid võrreldakse kirjelduse alusel.
    
    private String teoseKirjeldus, teoseTahis, laenutajaNimi;
    private int paevadeArv;

    //4. Abstraktsel klassil Teos peab olema konstruktor teose kirjelduse (String), teose tähise (String), laenutaja (String) ja päevade arvu (int) määramiseks.

    public Teos(String teoseKirjeldus, String teoseTahis, String laenutajaNimi, int paevadeArv) {
        this.teoseKirjeldus = teoseKirjeldus;
        this.teoseTahis = teoseTahis;
        this.laenutajaNimi = laenutajaNimi;
        this.paevadeArv = paevadeArv;
    }

    public String getTeoseKirjeldus() {
        return teoseKirjeldus;
    }

    public void setTeoseKirjeldus(String teoseKirjeldus) {
        this.teoseKirjeldus = teoseKirjeldus;
    }

    public String getTeoseTahis() {
        return teoseTahis;
    }

    public void setTeoseTahis(String teoseTahis) {
        this.teoseTahis = teoseTahis;
    }

    public String getLaenutajaNimi() {
        return laenutajaNimi;
    }

    public void setLaenutajaNimi(String laenutajaNimi) {
        this.laenutajaNimi = laenutajaNimi;
    }

    public int getPaevadeArv() {
        return paevadeArv;
    }

    public void setPaevadeArv(int paevadeArv) {
        this.paevadeArv = paevadeArv;
    }


    public abstract boolean kasHoidlast(); //Klassis on abstraktne boolean-tüüpi meetod kasHoidlast, mis näitab, kas teost on vaja tellida hoidlast.
    
    public int laenutusaeg() {
        //Samuti on parameetriteta int-tüüpi isendimeetod laenutusaeg, mis tagastab selle teose laenutusaja (ehk mitmeks päevaks teost tähise järgi on lubatud laenutada).

        HashMap<String, Integer> laenutusajaPikkused = new HashMap<>(); // luuakse HashMap tähiste ja neile vastavate laenutusaja pikkuste hodmiseks

        // Teoseid laenutatakse erinevateks tähtaegadeks vastavalt tähisele:
        laenutusajaPikkused.put("roheline", 1); // roheline tähis – 24 tunniks (ehk 1 päevaks),
        laenutusajaPikkused.put("puudub", 14); // tähis puudub – 14 päevaks,
        laenutusajaPikkused.put("kollane", 30); // kollane – 30 päevaks ja
        laenutusajaPikkused.put("sinine", 60); // sinine – 60 päevaks.

        String tahis = this.getTeoseTahis(); // leitakse teose tähis

        if(laenutusajaPikkused.containsKey(tahis)) {
           return laenutusajaPikkused.get(tahis);
        }
        return 0; // Kui tähis on erinev kui roheline, kollane, sinine, puudub, siis tagastatakse 0.
    }
    
    public double päevaneViivis() { //Samuti on klassis double-tüüpi parameetriteta meetod päevaneViivis, mis tagastab vastavalt tähisele sobiva viivise ühe tähtaega ületanud päeva kohta.

        HashMap<Integer, Double> laenutusajaleVastavadViivised = new HashMap<>(); // luuakse HashMap laenutusaja pikkuste ja nendele vastavate viiviste hodmiseks

        // Kui teoseid tähtajaks tagasi ei vii, tuleb hakata maksma viivist vastavalt korraldusele:
        laenutusajaleVastavadViivised.put(30, 0.05); // 30 ja 60 päeva laenutus – 0.05 eurot teose kohta iga ületatud päeva eest,
        laenutusajaleVastavadViivised.put(60, 0.05);
        laenutusajaleVastavadViivised.put(14, 0.15); // 14 päeva laenutus – 0.15 eurot teose kohta iga ületatud päeva eest,
        laenutusajaleVastavadViivised.put(1, 2.0); // 24 tunni laenutus – 2 eurot teose kohta iga ületatud päeva eest.

        return laenutusajaleVastavadViivised.get(this.laenutusaeg()); // viivis arvutatakse laenutusaja pikkuse järgi
    }

    public void arvutaViivis(Kontrollija kontrollija) { //Samuti on klassis void-tüüpi meetod arvutaViivis, mis võtab parameetriks Kontrollija isendi.

        // Luuakse muutujad, et isendimuutujaid mitu korda leidma ei peaks (sest kui raamat on tähtajast üle, ssi tuleb nende abil viivis arvutada)
        int paevad = this.getPaevadeArv();
        int laenutusaeg = this.laenutusaeg();

        if (paevad > laenutusaeg) { //Tähtaja ületanud laenutuse korral arvutab meetod viivise suuruse
            // ja laseb kontrollijal selle salvestada (salvestaViivis).

            kontrollija.salvestaViivis(this.getLaenutajaNimi(),
                    this.getTeoseKirjeldus(),
                    this.päevaneViivis() * (paevad - laenutusaeg));
        }
    }

    @Override
    public String toString() { //Samuti on klassis Teos meetod toString, mis aitab kogu teadaolevat infot teose ja selle laenutamise kohta sobivalt kujundada.

        String hoidlast = "Vaja hoidlast tellida ";
        if (!this.kasHoidlast()) hoidlast = "Kohapeal olemas ";

        return  "Teose kirjeldus: " + this.getTeoseKirjeldus() + "\n" + // millise teosega on tegemist?
                "Tähis: " + this.getTeoseTahis() + "\n" + // milline on teose tähis (määrab laenutusaja pikkuse)?
                "Laenutaja: " + this.getLaenutajaNimi() + "\n" + // kes raamatu laenutas?
                "Laenutusperiood (päevade arv): " + this.getPaevadeArv() + "\n" + // mitu päeva raamat laenutaja käes olnud on?
                "Kohal: " + hoidlast + "\n"; // Muuhulgas peab olema näidatud, kas teost on vaja tellida hoidlast.
    }

    @Override
    public int compareTo(Teos o) { // kusjuures compareTo meetod realiseeritakse nii, et teoseid võrreldakse kirjelduse alusel.
        return this.getTeoseKirjeldus().compareTo(o.getTeoseKirjeldus());
    }
}
