import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Arvutiparandus {

    protected static Arvuti loeArvuti(String tooKirjeldus) throws FormaadiErind { // NB! Kuna loeArvuti puhul on tegemist abimeetodiga, mida läheb vaja vaid peaklassis, siis ei tohiks olla võimalik seda teistest klassidest välja kutsuda.
        /* Meetod võtab argumendiks sõne töö kirjeldusega (selles formaadis, nagu ülalpool kirjeldatud) ja tagastab sellele vastava Arvuti või VäliseMonitorigaArvuti.

        Kui registreerimise aeg ja sellele eelnev @ puuduvad, siis peab tagastatava objekti getRegistreerimiseAeg() tagastama aja, millal sõne töötlemist alustati.

        Kui etteantud sõne ei vasta nõutud formaadile, peab loeArvuti meetod erindi viskama. Defineeri erindiklass FormaadiErind.
        Visatud erind peab sisaldama vea põhjust seletavat sõnumit.

        FormaadiErindi peab viskama järgnevate veatingimuste puhul (võib eeldada, et sisendis teistsuguseid vigu ei esine):

                semikoolonitega eraldatud väljade arv on väiksem, kui kaks, või suurem, kui kolm
                töö tüübi väljas olev väärtus ei ole "tavatöö" ega "kiirtöö"
                väljade arv on kolm, aga kolmanda välja väärtus ei ole "monitoriga"


            Vihje: LocalDateTime#parse

    Vihje: LocalDateTime#now

*/


        /*Mõlemal juhul koosneb tekst ridadest, kus iga rida tähistab ühe töö andmeid.
        Reas on semikooloniga eraldatuna kirjas arvuti tootja, tellimuse tüüp ("kiirtöö" või "tavatöö").
        Kui tegemist on välise monitoriga arvutiga, siis järgneb veel ;monitoriga.
        Peale neid andmeid tuleb @ ja seejärel töö registreerimise aeg.

Näited

Lenovo;tavatöö@2024-03-25T12:34:12
Ordi;kiirtöö;monitoriga@2024-04-12T10:12:45

*/

        //System.out.println(tooKirjeldus);


    Arvuti arvuti = null;
        Pattern pattern = Pattern.compile("^(?<arvutiNimi>\\w+)(;)(?<tooTuup>[äõüöa-zÄÕÜÖA-Z]+)(;)?(?<onMonitoriga>[äõüöa-zÄÕÜÖA-Z]+)?(;)?@?(?<registreerimiseAeg>\\d.*)?$");
        Matcher matcher = pattern.matcher(tooKirjeldus);


        //System.out.println(tooKirjeldus);
        //System.out.println(matcher.results().count());
       // if (tukeldatudKirjeldus.length < 2 || tukeldatudKirjeldus.length > 3) {throw new FormaadiErind(tooKirjeldus,  "Väljade arv on vale!");} // semikoolonitega eraldatud väljade arv on väiksem kui kaks, või suurem kui kolm


        if ( matcher.results().count() == 0) {throw new FormaadiErind("Rida: " + tooKirjeldus + " Vea selgitus: Väljade arv on vale!", "valeValjadeArv");} // semikoolonitega eraldatud väljade arv on väiksem kui kaks, või suurem kui kolm

        if (matcher.matches()) {

                String arvutiNimi = matcher.group("arvutiNimi");
                String tooTuup = matcher.group("tooTuup");

                if(!tooTuup.matches("kiirtöö|tavatöö")) {throw new FormaadiErind("Vigane sisestus! Teise välja väärtus ei ole \"tavatöö\" ega \"kiirtöö\"!", "viganeRida2");} //töö tüübi väljas olev väärtus ei ole "tavatöö" ega "kiirtöö"

                String onMonitoriga = matcher.group("onMonitoriga");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime registreerimiseAeg = matcher.group("registreerimiseAeg") != null ? LocalDateTime.parse(matcher.group("registreerimiseAeg"), formatter) : LocalDateTime.now();

                if(onMonitoriga != null) {
                    if (onMonitoriga.matches("monitoriga")) {
                        arvuti = new VäliseMonitorigaArvuti(arvutiNimi, tooTuup, registreerimiseAeg);
                    } else {throw new FormaadiErind("Vigane sisestus! Kolmanda välja väärtus ei ole \"monitoriga\"!", "viganeRida3");}  // väljade arv on kolm, aga kolmanda välja väärtus ei ole "monitoriga"
                } else {
                    arvuti = new Arvuti(arvutiNimi, tooTuup, registreerimiseAeg);
                }
            }

        return arvuti;
    }

    public static String kusiKasutajaltJargmineSisend(String sisendiPrompt, Scanner scanner) {
        System.out.println(sisendiPrompt);
        return  scanner.nextLine();
    }

    public static HashMap<String, Double> loeDatFaili(String failiNimi) {

        HashMap<String, Double> tootajadTunnitasudega = new HashMap<>();

        try (DataInputStream dis = new DataInputStream(new FileInputStream(failiNimi))) {

            /*töötajate arv
        esimese töötaja nimi
        esimese töötaja tunnitasu*/

            int tootajateArv = dis.readInt();

            for (int i = 0; i < tootajateArv; i++) {
                String tootajaNimi = dis.readUTF();
                double tootajaTunnitasu = dis.readDouble();
                tootajadTunnitasudega.put(tootajaNimi, tootajaTunnitasu);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tootajadTunnitasudega;
    }

    public static void kustutaOlemasolevFail(String failiNimi) {
        try {
            Files.deleteIfExists(Path.of(failiNimi));
        } catch (NoSuchFileException e) {
            System.out.println("Faili ei leitud!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void salvestaDatFail(String failiNimi, ArrayList<Arvuti> tehtud) {
        /* Meetod salvestab tehtud tööd  faili tehtud.dat kasutades selleks klassi java.io.DataOutputStream.

        Faili formaat: parandatud arvutite arv (int),
        millele järgneb iga arvuti kohta selle tootja (String),
        registreerimise aeg (String, kasuta LocalDateTime meetodit toString)
        arve summa (double). */
        kustutaOlemasolevFail(failiNimi); // kui sellenimeline fail juba olemas on, siis see asendatakse uuega

        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(failiNimi))) {

            dos.writeInt(tehtud.size()); // parandatud arvutite arv (int)

            for (Arvuti arvuti : tehtud) {
                dos.writeUTF(arvuti.getTootja()); // arvuti tootja
                dos.writeUTF(arvuti.getRegistreerimiseAeg().toString()); // registreerimise aeg
                dos.writeDouble(arvuti.arvutaArveSumma(0.0)); // arve summa
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void salvestaTxtFail(String failiNImi, PriorityQueue<Arvuti> ootelArvutid) {
        kustutaOlemasolevFail(failiNImi); // kui sellenimeline fail juba olemas on, siis see asendatakse uuega

        try {Files.write(Path.of(failiNImi), ootelArvutid.stream().map(Arvuti::toString).collect(Collectors.toList()));}
        catch (IOException e) {e.printStackTrace();}
    }

    public static void main(String[] argsx) throws IOException, FormaadiErind {

        HashMap<String, Double> tootajadTunnitasudega = loeDatFaili("naidiskt2/tunnitasud.dat");

        String valitudTegevus = "";
        double teenitudRaha = 0.0;

        PriorityQueue<Arvuti> ootelArvutid = new PriorityQueue<Arvuti>((a1, a2) -> {
            int kiirtooComparison = Boolean.compare(a2.onKiirtöö(), a1.onKiirtöö());
            if (kiirtooComparison != 0) {return kiirtooComparison;} else {    return a1.getRegistreerimiseAeg().compareTo(a2.getRegistreerimiseAeg());}
        });

        ArrayList<Arvuti> tehtud = new ArrayList<>();
        String[] args = new String[1];
        args[0] = "3";

        for (String line : Files.lines(Path.of("naidiskt2/ootel_arvutid.txt")).toList()) {
            try { ootelArvutid.add(loeArvuti(line));}
            catch (FormaadiErind formaadiErind) {System.out.println(formaadiErind.getMessage());}
        }


        Scanner scanner = new Scanner(System.in);

        while (!Objects.equals(valitudTegevus, "L")) {
            System.out.println("Kas soovid parandada (P), uut tööd registreerida (R) või lõpetada (L) ? "); //  Töötsükli igal kordusel küsitakse kasutajalt, kas ta tahab parandada (P), uut tööd registreerida (R) või lõpetada (L).
            valitudTegevus = scanner.nextLine();

            if (Objects.equals(valitudTegevus, "R")) { //Kui kasutaja valib uue töö registreerimise, siis laseb programm sisestada tal töö kirjelduse (samas formaadis nagu algsete tööde puhul, aga ilma kuupäeva ja sellele eelneva @-ta)
                boolean arvutiRegistreeritud = false;
                while (!arvutiRegistreeritud) {  // Kui sisestatud kirjeldus on vigane, siis tuleb küsimist korrata niikaua, kuni kasutaja sisestab õige formaadiga kirjelduse. Iga vigase sisestuse korral väljastada vea kirjeldus.
                    String arvutiInfoSisend = kusiKasutajaltJargmineSisend("Sisesta töö kirjeldus: ", scanner);
                    if (Objects.equals(arvutiInfoSisend, "L")) {valitudTegevus = "L"; break;}
                    try {
                        ootelArvutid.add(loeArvuti(arvutiInfoSisend));   // ning salvestab sellele vastava objekti (VäliseMonitorigaArvuti või Arvuti) ootel tööde nimekirja.
                        arvutiRegistreeritud = true;
                    }
                    catch (FormaadiErind formaadiErind) {System.out.println(formaadiErind.getMessage());}
                }

                System.out.println("Töö on registreeritud!");

            } else if (Objects.equals(valitudTegevus, "P")) { // Kui kasutaja valib parandamise, siis tuleb võtta ootel tööde hulgast ükskõik milline kiirtellimus või kui kiirtellimusi (enam) pole, siis ükskõik milline tavatellimus.

                Arvuti parandatavArvuti = ootelArvutid.poll();
                // Programm näitab tellimuse kirjelduse kasutajale ning jääb ootama, kuni kasutaja sisestab töö tegemiseks kulunud aja (täisarvuna, minutites) ja oma nime.
                System.out.println("Arvuti info: " + parandatavArvuti);

                String parandamiseksKulunudAeg = kusiKasutajaltJargmineSisend("Sisesta parandamiseks kulunud aeg (täisminutites): ", scanner);
                if (Objects.equals(parandamiseksKulunudAeg, "L")) {valitudTegevus = "L"; break;}
                int parandamiseksKulunudAegInt = Integer.parseInt(parandamiseksKulunudAeg);

                String parandajaNimi = kusiKasutajaltJargmineSisend("Sisesta enda nimi ", scanner);
                if (Objects.equals(parandajaNimi, "L")) {valitudTegevus = "L"; break;}

                // Peale seda tuleb määrata tööle hind ja sellega loetakse antud töö tehtuks.
                //Baashind arvutatakse parandamiseks kulunud aja ning parandaja tunnitasu põhjal.

                double arveSumma = parandatavArvuti.arvutaArveSumma(tootajadTunnitasudega.get(parandajaNimi) * (parandamiseksKulunudAegInt / 60));
                System.out.println("Töö tehtud, arve summa on " + arveSumma + "€!");
                teenitudRaha += arveSumma;
                tehtud.add(parandatavArvuti);

        }else if (!Objects.equals(valitudTegevus, "L")) {
                System.out.println("Tundmatu sisend!");
            }
        }

        System.out.println("Sessiooni kokkuvõte:");
        System.out.println("Teenitud raha: " + teenitudRaha + "€");
        System.out.println("Parandatud arvuteid: ");
        for (Arvuti arvuti : tehtud) {
            System.out.println("  " + arvuti.getTootja() + ": 1tk");
        }
        System.out.println("Ootele jäi " + ootelArvutid.size() + " arvuti(t)");

        salvestaDatFail("naidiskt2/tehtud.dat", tehtud);
        salvestaTxtFail("naidiskt2/ootel.txt", ootelArvutid);
        /* Ülesanne on kirjutada programm arvutiparandustöökojale.

Tööpäeva alguses käivitatakse programm käsurea argumendiga, mis viitab parandamist ootavate arvutite nimekirjale.
Programm loeb ootel olevate arvutite nimekirja sisse ja alustab töötsükliga. T
öötsükli igal kordusel küsitakse kasutajalt, kas ta tahab parandada (P), uut tööd registreerida (R) või lõpetada (L).

Näide
Kasutamise sessioon võiks näha välja midagi sellist:

Rida: Ordi@2024-04-21T14:00:23 Vea selgitus: Väljade arv on vale!

Kas soovid parandada (P), uut tööd registreerida (R) või lõpetada (L) ? P
Arvuti info: ﻿Lenovo;kiirtöö@2024-04-21T14:22:42
Sisesta parandamiseks kulunud aeg (täisminutites): 142
Sisesta enda nimi: Jaan
Töö tehtud, arve summa on 59.33 €!

Kas soovid parandada (P), uut tööd registreerida (R) või lõpetada (L) ? R
Sisesta töö kirjeldus: Dell;tavatöö;mnitoriga
Vigane sisestus! Kolmanda välja väärtus ei ole "monitoriga"!
Sisesta töö kirjeldus: Dell;tavatöö;monitoriga
Töö on registreeritud!

Kas soovid parandada (P), uut tööd registreerida (R) või lõpetada (L) ? P
Arvuti info: ML;kiirtöö;monitoriga@2024-04-21T14:08:09
Sisesta parandamiseks kulunud aeg (täisminutites): 42
Sisesta enda nimi: Peeter
Töö tehtud, arve summa on 36.45 €!

Kas soovid parandada (P), uut tööd registreerida (R) või lõpetada (L) ? P
Arvuti info: Dell;tavatöö;monitoriga@2024-04-20T13:08:30.080
Sisesta parandamiseks kulunud aeg (täisminutites): 75
Sisesta enda nimi: Peeter
Töö tehtud, arve summa on 44.88 €!

Kas soovid parandada (P), uut tööd registreerida (R) või lõpetada (L) ? L
Sessiooni kokkuvõte:
Teenitud raha: 140.66€
Parandatud arvuteid:
  ﻿Lenovo: 1tk
  Dell: 1tk
  ML: 1tk
Ootele jäi 2 arvuti(t).*/


            /*


Tööle hinna määramine
Baashind arvutatakse parandamiseks kulunud aja ning parandaja tunnitasu põhjal. Lõpphinna saamiseks lisatakse baashinnale fikseeritud summa töö vastuvõtmise eest (2€ arvuti eest ja täiendav +1€ välise monitori puhul) ning lisaks veel 10€ kiirtellimuse puhul.

Parandajate tunnitasud on kirjas failis tunnitasud.dat. See on binaarne fail, moodustatud klassi java.io.DataOutputStream abil, kus faili algul on töötajate arv (int), ning seejärel iga töötaja kohta tema nimi (String) ning tunnitasu (double), st. faili sisu on kujul:

töötajate arv
esimese töötaja nimi
esimese töötaja tunnitasu
...
viimase töötaja nimi
viimase töötaja tunnitasu



Tehtud ja tegemata tööde salvestamine
Programmi lõpus tuleb tehtud tööd salvestada faili tehtud.dat kasutades selleks klassi java.io.DataOutputStream.
Fail peab olema järgnevas formaadis: parandatud arvutite arv (int), millele järgneb iga arvuti kohta selle tootja (String), registreerimise aeg (String, kasuta LocalDateTime meetodit toString) ja arve summa (double). NB! Kui sellise nimega fail juba eksisteerib, siis kirjutada selle sisu üle.

Tegemata tööd tuleb salvestada tekstifaili nimega ootel.txt (kodeeringus UTF-8, samas formaadis nagu kasutati esialgsete ootel tööde puhul).

Tööde kokkuvõtte kuvamine
Tööde kokkuvõtteks tuleb programmi lõpus ekraanile kuvada antud sessioonis teenitud raha ning tootjate kaupa, mitu arvutit parandati. Lisaks tuleb näidata, mitu arvutit jäi ootele.
*/
    }



}
