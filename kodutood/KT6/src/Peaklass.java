import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Peaklass { //7. Peaklass peab olema nimega Peaklass.

    //Raamatukogust saab laenutada erinevaid teoseid - raamatuid ja ajakirju.


// Tavaliselt tuuakse kõik teosed tähtajaks tagasi, aga mõned tegelased kipuvad tähtaegu täielikult ignoreerima.
    // Kirjutame programmi, mis otsib laenutuste nimekirjast väga suurte viivistega laenutusi ja kõige suurema viivisega laenutaja
    // ja väljastab neile hoiatusi.
    //
    //Laenutuste andmed on kirjas failis järgmises formaadis:
    //
    //Aksel Telgmaa, Matemaatika VI klassile; kollane; Joosep Kask; 125
    //Kodu ja aed/2013,10; kollane; Teele Tamm; 59
    //Oskar Luts, Kevade; sinine; Teele Tamm; 39
    //Artur Jürisson, Eesti-inglise õigussõnaraamat; puudub; Jaan Aruhein; 224
    //Juhani Püttsepp, Peipsimaa kaart; roheline; Joosep Kask; 22
    //Kroonika/2010,15; puudub; Joosep Kask; 4

    //Kontrolltöö seisneb teoste tagastamist arvestava programmi koostamises. Programm peab vastama alltoodud nõuetele (isegi kui need kummalised tunduvad).

    // Programm peab sisaldama abstraktse klassi Teos, liidest Kontrollija, klasse Ajakiri, Raamat, ViiviseHoiataja, SuurimaViiviseLeidja ning peaklassi.
    // Peaklassis loetakse sisse teoste nimekiri ja kasutatakse viivise kontrollijaid, et hilinejatele hoiatusi saata.
    // Peaklassis testitakse ka erinevate isendimeetodite tööd. Kõikide klasside kõik isendiväljad peavad olema privaatsed.
    //


    //




    public static List<Teos> loeTeosed(String failinimi)  throws IOException { //Klassis peab olema staatiline avalik meetod loeTeosed tagastustüübiga List<Teos>, mis võtab argumendiks failinime ja tagastab selles failis olevad laenutuste andmed.
        // Meetod võib visata erindi (st. meetodi signatuuris võib olla throws Exception).
        // Teoste arv failis ei ole teada (programm peaks töötama suvalise (ka tühja) failiga).


        ArrayList<Teos> teosed = new ArrayList<>();

        try {

            Files.lines(Path.of(failinimi)).forEach(s -> {
                String[] split = s.split("; "); // Eraldajaks on semikoolon koos tühikuga.

                String kirjeldus = split[0]; //Failis on igal real teose kirjeldus,
                String tahis = split[1]; // teose tähis,
                String laenutajaNimi = split[2]; // laenutaja nimi
                Integer laenutatudPaevi = Integer.parseInt(split[3]); // ja laenutatud päevade arv.

                if (kirjeldus.contains("/")) teosed.add(new Ajakiri(kirjeldus, tahis, laenutajaNimi, laenutatudPaevi)); // Võib eeldada, et kui kirjelduses kaldkripsu pole, siis tegu on raamatuga.
                else teosed.add(new Raamat(kirjeldus, tahis, laenutajaNimi, laenutatudPaevi));
            });

        } catch (NoSuchFileException e) { // kui faili ei leitud, väljastatakse veateade
            System.out.println("Faili ei leitud!");
        }

        return teosed;
    }

    public static void main(String[] args) throws IOException {

        List<Teos> teosed = loeTeosed("laenutused.txt"); //Rakendatakse vastavat staatilist meetodit, et lugeda failist teoste andmed.
        Collections.sort(teosed); // Teosed sorteeritakse vastavalt meetodis compareTo kirjeldatud järjekorrale

        System.out.println("Failist loeti järgmised raamatud (soreertiud pealkirja järgi tähestikulises järjekorras): ");
        for (Teos t: teosed) {
            System.out.println(t);
        }

        // Luuakse ViiviseHoiataja (lubatud viivis 0.2€) ja SuurimaViiviseLeidja isendid.
        ViiviseHoiataja vh = new ViiviseHoiataja(0.2);
        SuurimaViiviseLeidja svl = new SuurimaViiviseLeidja();

        teosed.forEach(teos -> {teos.arvutaViivis(vh);}); // Kutsutakse iga teose peal arvutaViivis, andes parameetriks ViiviseHoiataja isendi.
        // Pärast kõigi viiviste arvutamist prindi välja ViiviseHoiatajasse kogunenud laenutajate nimed.
        String hoiatusesaajadSonena = vh.getHoiatatavadLaenutajad().toString();
        System.out.println("Viivisehoiatuse saavad järgmised laenutajad: " + hoiatusesaajadSonena.subSequence(1, hoiatusesaajadSonena.length() - 1));

        teosed.forEach(teos -> {teos.arvutaViivis(svl);}); // Kutsutakse iga teose peal arvutaViivis, andes parameetriks SuurimaViiviseLeidja isendi.
        svl.saadaHoiatus(); // Pärast kõigi viiviste arvutamist saada suurima viivise omanikule hoiatus (kasutades vastavat SuurimaViiviseLeidja meetodit).
    }
}
