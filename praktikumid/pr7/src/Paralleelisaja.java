import java.util.ArrayList;

class Loendur {

    private int n = 0;

    public synchronized void liida(int liidetav) {
        n += liidetav;
    }

    public synchronized int väärtus() {
        return n;
    }

    /*public void suurenda() {
        int algne = n;
        n = n + 1;
        if (n > algne + 1) {
            System.out.println("algne = " + algne + ", n = " + n);
        }
    }*/
}

public class Paralleelisaja implements Runnable{ // Ül 2

    private Loendur loendur;
    private String nimi;
    private ArrayList<Integer> list;


    public Paralleelisaja(Loendur loendur, String nimi, ArrayList<Integer> list) {
        this.loendur = loendur;
        this.nimi = nimi;
        this.list = list;
    }


    public Loendur getLoendur() {
        return loendur;
    }

    public void setLoendur(Loendur loendur) {
        this.loendur = loendur;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public ArrayList<Integer> getList() {
        return list;
    }

    public void updateList(Integer i) {
        this.getList().add(i);
    }

    @Override
    public void run() {
        System.out.println(nimi + " alustas");
        for (int i = 0; i < 10000000; i++) {
            //this.updateList(i);
//            loendur.suurenda();
            loendur.liida(i);
            this.updateList(loendur.väärtus());

        }
        System.out.println(nimi + " lõpetas");
    }

    public static void main(String[] args) throws Exception {

        // Koosta peameetod, mis loob tühja ArrayList<Integer> ja käivitab kaks lõime.
        ArrayList<Integer> tuhiList = new ArrayList<>();

        Loendur loendur = new Loendur();
        Thread t1 = new Thread(new Paralleelisaja(loendur, "Lõim-1", tuhiList));
        Thread t2 = new Thread(new Paralleelisaja(loendur, "Lõim-2", tuhiList));

        // Käivitatud lõimed peavad paralleelselt lisama peameetodis loodud listi arvud 0..1000000.
        // käivitame threadid
        t1.start();
        t2.start();
        // ootame, et threadid töö lõpetaks
        t1.join();
        t2.join();
        System.out.println("Valmis!");

        // Peameetod peab ootama, kuni mõlemad lõimed on töö lõpetanud ja seejärel väljastama listi suuruse.
        // Ära kasuta sünkroniseerimist ja käivita programm mitu korda.
        // Kas tulemus on ootuspärane? Ei. Nägin, et lõim võib varem algustada ja hilejm lõpetada või vastupidi. Smauti ei toimu uuendamine alati samas tempos.

        // Esimene:                 Teine:                      Kolmas:                 Neljas:
        // Lõim-1 alustas           Lõim-2 alustas              Lõim-2 alustas          Lõim-1 alustas
        // Lõim-2 alustas           Lõim-1 alustas              Lõim-1 alustas          Lõim-2 alustas
        // algne = 100, n = 120     algne = 1312, n = 1317      algne = 35, n = 51      algne = 1156, n = 1189
        // Lõim-2 lõpetas           Lõim-1 lõpetas              Lõim-2 lõpetas          Lõimes 1 ArrayIndexOutOfBoundsException
        // Lõim-1 lõpetas           Lõim-2 lõpetas              Lõim-1 lõpetas          Lõim-1 lõpetas
        // Valmis!                  Valmis!                     Valmis!                 Valmis!


        // Lisa puuduv sünkroniseerimine ja kontrolli, et programmi väljund oleks igal käivitamisel sama.

        // Esimene:                 Teine:                      Kolmas:                 Neljas:
        //Lõim-2 alustas            Lõim-2 alustas              Lõim-2 alustas          Lõim-2 alustas
        //Lõim-1 alustas            Lõim-1 alustas              Lõim-1 alustas          Lõim-1 alustas
        //Lõim-2 lõpetas            Lõim-2 lõpetas              Lõim-1 lõpetas          Lõim-1 lõpetas
        //Lõim-1 lõpetas            Lõim-1 lõpetas              Lõim-2 lõpetas          Lõim-2 lõpetas
        //Valmis!                   Valmis!                     Valmis!                 Valmis!

    }
}
