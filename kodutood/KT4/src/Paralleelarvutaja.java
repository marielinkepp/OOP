import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Paralleelarvutaja implements Runnable { // Paralleelarvutusprogramm, mis loeb ja töötleb paralleelselt mitut sisendfaili

    // järjekord, mille kaudu ülesandeid vastu võetakse
    private BlockingQueue<String> failiNimed;

    // järjekord, mille kaudu lahendused tagasi saadetakse
    private BlockingQueue<TulemuseHoidja> failiInfo;

    public Paralleelarvutaja(BlockingQueue<String> failiNimed, BlockingQueue<TulemuseHoidja> failiInfo) {
        this.failiNimed = failiNimed;
        this.failiInfo = failiInfo;
    }

    @Override
    public void run() {
        //    Sisendfailid on utf-8 tekstifailid, millest igaüks sisaldab väga väga palju täisarve.
        //    Failides võivad kõik arvud olla tühikutega eraldatuna ühel real, aga võivad olla ka mitme rea peale jaotatud.
        //    Igas failis on vähemalt üks arv. Failid ja ka üksikud read võivad olla nii suured, et need ei mahu korraga mälusse ära.
        //
        //    Arvud võivad olla suuremad, kui mahub long tüüpi muutujasse. Sellega hakkama saamiseks peab programm kasutama arvude hoidmiseks BigInteger klassi.
        //

        this.failiNimed.forEach(nimi -> {
            Scanner scanner = null;
            BigInteger summa = BigInteger.valueOf(0); // leitakse kõikide arvude kogusumma; algväärtus 0
            BigInteger suurimArv = BigInteger.valueOf(0); // leitakse suurm arv; algväärtus 0
            try {
                scanner = new Scanner(new File(nimi));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            while (scanner.hasNext()) {
                BigInteger vaadeldavArv = new BigInteger(scanner.next());
                summa = summa.add(vaadeldavArv); // arv liidetakse kogusummale

                if (vaadeldavArv.compareTo(suurimArv) == 1) suurimArv = vaadeldavArv; // kui vaadeldav arv on seni leitud suurimast suurem, siis määratakse see uueks suurimaks
            }
            scanner.close();

            this.failiInfo.add(new TulemuseHoidja(nimi, // lisatakse töödeldud faili nimi,
                                                suurimArv, // selle faili suurim üksik arv
                                                summa)); // ja kõigi arvude summa
        });
    }
}

class TulemuseHoidja {
    private String failiNimi;
    private BigInteger maxElement, elementideSumma;

    public TulemuseHoidja(String failiNimi, BigInteger maxElement, BigInteger elementideSumma) {
        this.failiNimi = failiNimi;
        this.maxElement = maxElement;
        this.elementideSumma = elementideSumma;
    }

    public String getFailiNimi() {
        return failiNimi;
    }

    public BigInteger getMaxElement() {
        return maxElement;
    }

    public BigInteger getElementideSumma() {
        return elementideSumma;
    }
}
class Test {
    public static void main(String[] argsx) {

        //    Programm saab käsurea parameetritega sisendfailide nimed (relative pathid).
        //    Sisendfaile võib olla väga palju (üle 100 000).
        //

        String[] args = {"testfile1.txt", "testfile2.txt"};

        // Programm peab failide töötlemiseks looma protsessorituumadega võrdse arvu threade (workerid).
        int cores = Runtime.getRuntime().availableProcessors(); // leitakse protsessorituumade arv
        ThreadFactory threadFactory = Executors.defaultThreadFactory(); // luuakse ThreadFactory

        // Mustri allikas: https://www.geeksforgeeks.org/threadfactory-interface-in-java-with-examples/
        for (int i = 0; i < cores; i++) { // luuakse lõimed

            BlockingQueue<String> failiNimed = new ArrayBlockingQueue<>(args.length); // luuakse ühine BlockingQueue, millest kõik workerid töötlemist vajavate failide nimesid võtavad
            BlockingQueue<TulemuseHoidja> failiInfo = new ArrayBlockingQueue<>(args.length); // luuakse ühine BlockingQueue, kuhu workerid iga faili kohta järgneva komplekti lisavad: töödeldud faili nimi, selle faili suurim üksik arv ja kõigi arvude summa
            failiNimed.addAll(List.of(args));

            Thread thread = threadFactory.newThread(new Paralleelarvutaja(failiNimed, failiInfo));
            thread.start(); // lõime käivitamine
        }


        //    Programmi lõppedes peab main threadist ekraanile väljastama:

        //    Kõikide leitud arvude kogusumma
        //    Kõige suurema leitud üksiku arvu väärtuse ja vastava faili nime
        //    Kõige väiksema arvude summaga faili nime

    }
}