import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

import java.nio.file.Path;

class FailiSummaLeidja implements Runnable {

    private String name;
    private Deque<Path> failinimedeJrk;
    private ArrayList<Integer> summad;
    private ArrayList<Tulemus> tulemused;

    public FailiSummaLeidja(String name, Deque<Path> failinimedeJrk, ArrayList<Tulemus> summad) {
        this.name = name;
        this.failinimedeJrk = failinimedeJrk;
        //this.summad = summad;
        this.tulemused = summad;
    }

    public String getName() {
        return name;
    }

    public synchronized Deque<Path> getFailinimedeJrk() {
        return failinimedeJrk;
    }

    public synchronized ArrayList<Integer> getSummad() {
        return summad;
    }

    public synchronized ArrayList<Tulemus> getTulemused() {
        return tulemused;
    }

    public static Integer loeFailist(String file) throws IOException { // Meetod loeb tühikutega eraldatud täisarv sisaldavast tekstifailist sisu

        Integer fileContentSum = Arrays.stream(Files.lines(Path.of(file)) // faili sisu lugemine
                .collect(Collectors.joining("\n")) // kõik read ühendatakse üheks sõneks
                .split(" ")) // sõne tükeldatakse tühiku kohalt
                .map(s -> Integer.parseInt(s)) // kõik sõne kujul täisarvud teisendatakse täisarvudeks
                .collect(Collectors.summingInt(Integer::intValue)); // leitakse kõigi täisarvude summa

        return fileContentSum;
    }

    public static Tulemus loeFailist(Path filepath) throws IOException { // Meetod loeb tühikutega eraldatud täisarv sisaldavast tekstifailist sisu

        Integer fileContentSum = Arrays.stream(Files.lines(filepath) // faili sisu lugemine
                        .collect(Collectors.joining("\n")) // kõik read ühendatakse üheks sõneks
                        .split(" ")) // sõne tükeldatakse tühiku kohalt
                .map(s -> Integer.parseInt(s)) // kõik sõne kujul täisarvud teisendatakse täisarvudeks
                .collect(Collectors.summingInt(Integer::intValue)); // leitakse kõigi täisarvude summa

        //return fileContentSum;
        //System.out.println(new Tulemus(filepath.getFileName().toString(), fileContentSum));
        return new Tulemus(filepath.getFileName().toString(), fileContentSum);
    }

    @Override
    public void run() {

        // Iga lõim võtab failinimede järjekorrast ühe faili, loeb selles olevad arvud sisse, liidab need kokku ja lisab summa tulemuste järjekorda.
        // Kui fail saab töödeldud, proovib lõim järjekorrast järgmist faili võtta ja seda töödelda jne.
        Deque<Path> f = this.getFailinimedeJrk();

        for (int i = 0; i < f.size(); i++) { // iga lõim käib läbi failinimede arvu võrra iteratsioone
            try {
                Path filePath = f.pollFirst(); // esimene liige eemaldatakse ja pannakse viimaseks
                f.offerLast(filePath);
                this.getTulemused().add(this.loeFailist(f.getFirst())); // summa listakase järjendisse

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class testClass {

    public static void main(String[] args) throws IOException, InterruptedException {

        // Ül6:
        // Lasin ChatGPT-l koostada mitu tekstifaili, milles on palju tühikutega eraldatud täisarve.
        // Programm loeb failidest arve ja leiab iga faili arvude summa.

        // Testklassi peameetodis luuakse järjekord, kuhu saab lisada saadud summad ja järjekord, kus hoitakse töötlemist ootavate failide nimesid.
        ArrayList<Tulemus> summad = new ArrayList<>();
        ArrayList<Path> failinimed = new ArrayList<>();

        // kausta path
        Path dir = Paths.get(Paths.get(System.getProperty("user.dir")).resolve("Praktikum/Praktikum7/Ul6_text").toString());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) { // try-with-resources
            for (Path file : stream) {
                if (!Files.isDirectory(file)) failinimed.add(file);  // kui fail pole kaust, siis see lisatakse failide järjekorda
                    //System.out.println("Järjekorda lisati fail: " + file.getFileName()); // ning selle kohta väljastatakse teade
            }
        } catch (IOException | DirectoryIteratorException e) {
            System.err.println("Error occurred: " + e.getMessage());
        }

        // Ül 6-7: Peameetodis pannakse kõikide failide nimed failinimede järjekorda ja pannakse käima kolm lõime.
        Collections.sort(failinimed);
        Deque<Path> failinimedeJrk = new ArrayDeque<>(failinimed);

        Thread t1 = new Thread(new FailiSummaLeidja("Lõim1", failinimedeJrk, summad));
        Thread t2 = new Thread(new FailiSummaLeidja("Lõim2", failinimedeJrk, summad));
        Thread t3 = new Thread(new FailiSummaLeidja("Lõim2", failinimedeJrk, summad));

        // käivitame threadid
        t1.start();
        t2.start();
        t3.start();
        // ootame, et threadid töö lõpetaks
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Valmis!");

        // Ül6: Peameetod peab tulemuste järjekorrast nii mitu arvu välja võtma ja ekraanile väljastama, kui palju faile ta algselt töösse andis.
        // System.out.println(Arrays.toString(Arrays.copyOfRange(summad.toArray(), 0, failinimedeJrk.size())));

        // Ül7:
        // Muuta eelmist programmi, nii et lisaks failis olnud arvude summale pannakse järjekorda ka loetud faili nimi.
        // Tekitada selle jaoks eraldi klass Tulemus, mille isendeid tulemuste listi saab lisada.

        for (int i = 0; i < failinimedeJrk.size(); i++) System.out.println(summad.get(i));

    }

}