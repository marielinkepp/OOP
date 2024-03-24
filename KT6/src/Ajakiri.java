import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ajakiri extends Teos { //6. Klass Raamat on klassi Ajakiri alamklass.
    private String ajakirjaNimi;
    private int aasta, ajakirjaNr;

    //Klassis on konstruktor vajaliku info määramiseks ja vähemalt järgmised meetodid.

    public Ajakiri(String teoseKirjeldus, String teoseTahis, String laenutajaNimi, int paevadeArv) {
        super(teoseKirjeldus, teoseTahis, laenutajaNimi, paevadeArv);

        Pattern pattern = Pattern.compile("(.+)[/](\\d{4})[,](\\d{2})"); // regex ajakirja kirjelduse alaosade leidmiseks
        Matcher matcher = pattern.matcher(teoseKirjeldus);

        // Ajakirjade puhul on kirjelduseks pealkiri, kaldkriips, aasta number, koma ja ajakirja number.
        if (matcher.find()) {
            this.ajakirjaNimi = matcher.group(1);
            this.aasta = Integer.parseInt(matcher.group(2));
            this.ajakirjaNr = Integer.parseInt(matcher.group(3));
        } else {
            System.out.println("Ajakirja kirjelduse formaat ei vasta oodatule.");
        }

    }

    public String getAjakirjaNimi() {
        return ajakirjaNimi;
    }

    public int getAasta() {
        return aasta;
    }

    public int getAjakirjaNr() {
        return ajakirjaNr;
    }

    @Override
    public String getTeoseKirjeldus() {
        return this.getAjakirjaNimi() + " (Aasta: " + this.getAasta() + ", Nr: " + this.getAjakirjaNr() + ")";
    }

    @Override
    public boolean kasHoidlast() { //Meetod kasHoidlast, mis tagastab true, kui ajakiri ilmus aastal 2000 või varem, vastasel juhul tagastatakse false

        if (this.getAasta() <= 2000) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() { //Meetod toString, mille ülekatmisel on rakendatud ülemklassi meetodit toString lisades märkuse selle kohta, et tegemist on ajakirjaga.

        return "Tüüp: ajakiri\n" +
                // kui tegemist on Ajakirja klassi isendiga, siis jäetakse peaklassi isendile omane kirjeldus ära ning asendatatkse ajakirja infoga
                "Ajakirja nimi: " + this.getAjakirjaNimi() + "\n" +
                "Ajakirja nr: " + this.getAjakirjaNr() + "\n" +
                "Aasta: " + this.getAasta() + "\n" +
                "Tähis" + super.toString().split("Tähis")[1];
    }
}
