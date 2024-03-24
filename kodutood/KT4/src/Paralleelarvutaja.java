import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class Paralleelarvutaja implements Runnable { // Paralleelarvutusprogramm, mis loeb ja töötleb paralleelselt mitut sisendfaili

    private BlockingQueue<String> failiNimed; // järjekord, mille kaudu ülesandeid vastu võetakse
    private BlockingQueue<TulemuseHoidja> failiInfo; // järjekord, mille kaudu lahendused tagasi saadetakse

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

        while (true) {
            // proovime järgmise ülesande vastu võtta
            String failiNimi = this.failiNimed.poll(); // proovitakse järgmist failinime lugeda
            if (failiNimi == null)
                break; // ülesanded said otsa, lõpetame töö

            BigInteger summa = BigInteger.ZERO; // leitakse kõikide arvude kogusumma; algväärtus null
            BigInteger suurimArv = null; // leitakse suurm arv; algväärtus 0

            try (Scanner scanner = new Scanner(new File(failiNimi))){ // faililugeja algväärtustamine TWR-blokis
                while (scanner.hasNextBigInteger()) {
                    BigInteger vaadeldavArv = scanner.nextBigInteger();
                    summa = summa.add(vaadeldavArv); // arv liidetakse kogusummale

                    if (suurimArv == null) suurimArv = vaadeldavArv; // kui summa on null ehk tühi, siis määratakse selle väärtuseks vaadeldav arv
                    else if (vaadeldavArv.compareTo(suurimArv) == 1) suurimArv = vaadeldavArv; // kui vaadeldav arv on seni leitud suurimast suurem, siis määratakse see uueks suurimaks
                }

            } catch (FileNotFoundException e) {throw new RuntimeException(e);}

            this.failiInfo.add(new TulemuseHoidja(failiNimi, // lisatakse töödeldud faili nimi,
                    suurimArv, // selle faili suurim üksik arv
                    summa)); // ja kõigi arvude summa
        }
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

    public String getFailiNimi() {return failiNimi;}

    public BigInteger getMaxElement() {return maxElement;}

    public BigInteger getElementideSumma() {return elementideSumma;}
}
class Test {
    public static void main(String[] argsx) {

        //    Programm saab käsurea parameetritega sisendfailide nimed (relative pathid).
        //    Sisendfaile võib olla väga palju (üle 100 000).
        //

        String[] args = {"testfile1.txt", "testfile2.txt"};

        // Programm peab failide töötlemiseks looma protsessorituumadega võrdse arvu threade (workerid).
        int cores = Runtime.getRuntime().availableProcessors(); // leitakse protsessorituumade arv
        ExecutorService executor = Executors.newFixedThreadPool(cores); // luuakse nii palju lõimi, nagu on protsessoril tuumi

        BlockingQueue<String> failiNimed = new ArrayBlockingQueue<>(args.length); // luuakse ühine BlockingQueue, millest kõik workerid töötlemist vajavate failide nimesid võtavad
        BlockingQueue<TulemuseHoidja> failiInfo = new ArrayBlockingQueue<>(args.length); // luuakse ühine BlockingQueue, kuhu workerid iga faili kohta järgneva komplekti lisavad: töödeldud faili nimi, selle faili suurim üksik arv ja kõigi arvude summa
        failiNimed.addAll(List.of(args));

        for (int i = 0; i < cores; i++) {
            executor.submit(new Paralleelarvutaja(failiNimed, failiInfo));
        }
        executor.shutdown(); // lõimede sulgemine

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // operatsioonid toimuvad peale kõigi lõimede töö lõppemist

            BigInteger kogusumma = BigInteger.ZERO;
            TulemuseHoidja thMaxVaartusFail = null;
            TulemuseHoidja thMinKogusummaFail = null;


            for (TulemuseHoidja th: failiInfo) {
                kogusumma.add(th.getElementideSumma()); // kõigi failide summad liidetakse kokku

                // kui summa on null ehk tühi, siis määratakse selle väärtuseks vaadeldav objekt; kui vaadeldav objekt on seni leitud suurimast suurem, siis määratakse see uueks suurimaks
                if ((thMaxVaartusFail == null) || (th.getMaxElement().compareTo(thMaxVaartusFail.getMaxElement()) == 1)) thMaxVaartusFail = th;

                // kui summa on null ehk tühi, siis määratakse selle väärtuseks vaadeldav objekt; kui vaadeldav objekt on seni leitud vähimast väiksem, siis määratakse see uueks vähimaks
                if ((thMinKogusummaFail == null) || (th.getElementideSumma().compareTo(thMinKogusummaFail.getElementideSumma()) == -1)) thMinKogusummaFail = th;

            }

            System.out.println("Elementide kogusumma: " + kogusumma); // Kõikide leitud arvude kogusumma
            System.out.println("Kõige suurem leitud üksiku arvu väärtus: " + thMaxVaartusFail.getMaxElement() + " " + thMaxVaartusFail.getFailiNimi()); // Kõige suurema leitud üksiku arvu väärtuse ja vastava faili nime
            System.out.println("Kõige väiksema arvude summaga faili nimi: " + thMinKogusummaFail.getFailiNimi()); // Kõige väiksema arvude summaga faili nime
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Interrupted while waiting for completion.");
        }
    }
}