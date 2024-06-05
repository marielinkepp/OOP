import java.util.Arrays;

public class Raamat extends Teos { //5. Klass Raamat on klassi Teos alamklass.
    private String autor, pealkiri;

    public Raamat(String teoseKirjeldus, String teoseTahis, String laenutajaNimi, int paevadeArv) {
        super(teoseKirjeldus, teoseTahis, laenutajaNimi, paevadeArv);

        // Raamatute puhul on kirjelduseks autori nimi, koma koos tühikuga ja raamatu pealkiri.
        String[] kirjeldusSplit = teoseKirjeldus.split(", ");
        this.autor = kirjeldusSplit[0];
        this.pealkiri = kirjeldusSplit[1];
    }

    public String getAutor() {
        return autor;
    }
    public String getPealkiri() {
        return pealkiri;
    }

    @Override
    public String getTeoseKirjeldus() {
        return this.getPealkiri() + " (Autor: " + this.getAutor() + ")";
    }

    @Override
    public boolean kasHoidlast() { //Meetod kasHoidlast, mis tagastab true, kui raamatu tähis on kollane või sinine, vastasel juhul tagastatakse false.

        if (Arrays.asList(new String[]{"kollane", "sinine"}).contains(this.getTeoseTahis())) {
            return true;
        }

        return false;
    }


    @Override
    public String toString() { //Meetod toString, mis tagastab ülemklassi toString tulemuse koos märkusega, et tegemist on raamatuga.
        return "Tüüp: raamat\n" +
                // kui tegemist on Raamatu klassi isendiga, siis jäetakse peaklassi isendile omane kirjeldus ära ning asendatatkse raamatu infoga
                "Pealkiri: " + this.getPealkiri() + "\n" +
                "Autor: " + this.getAutor() + "\n" +
                "Tähis" + super.toString().split("Tähis")[1];
    }
}
