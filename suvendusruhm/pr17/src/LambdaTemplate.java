import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LambdaTemplate {

    static BufferedReader reader;

    class FileFilterClass { // 1. FileFilter
        @FunctionalInterface
        interface FileFilter { // Funktsionaalne liides FileFilter
            boolean accept(Path path); // Meetod saab sisendiks java.nio.file.Path tüüpi muutuja ning tagastab täeväärtuse.
        }

        public static void printFiles(Path directory, FileFilter filter) {  // Meetob saab sissendiks java.nio.file.Path kujul kasuta ja FileFilter liidese representatsiooni (vb vale väljend, ei tea?)
            // Kasutas olevate failide kuvamise kood: https://www.geeksforgeeks.org/how-to-list-all-files-in-a-directory-in-java/

            try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) { // Leitakse kõik kasutas olevad failid
                // Kui Filter-liidese accept-meetodi tagastatav TV on tõene, siis väljastatakse faili nimi.
                for (Path file : stream) {if (filter.accept(file)) {System.out.println(file.getFileName());}}
            } catch (IOException e) {e.printStackTrace(); }
        }

        public static void main(String dirPath) { // Peameetod saab käsureal sisendiks kasuta nime

            // printFiles meetodit kutsutakse kaks korda välja
            printFiles(Path.of(dirPath), path -> path.toString().endsWith(".java")); // Esimesel korral filtreeritakse lambdafunktsiooni abil välja ainult .java-lõpulised failid.
            printFiles(Path.of(dirPath), path -> path.toString().endsWith(".class"));  // Teisel korral filtreeritakse lambdafunktsiooni abil välja ainult .class-lõpulised failid.
        }
    }

    class FileEditor {

        public static void edit(Path input, Path output, Function<String, String> transformer) { //Meetod saab sisendiks java.nio.file.Path kujul sisend- ja väljundfaili ning transformer-funktsiooni (Function<String, String>).
            // allikad: https://www.digitalocean.com/community/tutorials/java-read-file-line-by-line, https://www.geeksforgeeks.org/io-bufferedwriter-class-methods-java/

            try {
                reader = new BufferedReader(new FileReader(String.valueOf(input)));
                BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(output), true));

                String line = reader.readLine(); // Sisendafili loetakse ja töödeldakse rea kaupa

                while (line != null) {
                    writer.write( transformer.apply(line));  // Igale reale rakendatakse transformerfunktsiooni ning rida kirjutatakse väljundfaili
                    writer.newLine(); // reavahetus
                    line = reader.readLine(); // järgmise rea lugemine
                }
                reader.close();
                writer.close();
            } catch (IOException e) {e.printStackTrace();}

        }

        public static String tabsToSpaces(String line) { // Meetod asendab iga tabulaatori (\t) 4 tühikuga.
            return line.replace("\t", "    ");
        }

        public static String spacesToTabs(String line) { // Meetod asendab iga 4 järjestikust tühikut tabulaatoriga.
            return line.replace("    ", "\t");
        }

        public static void main(String[] args) { // Perameetod saab käsurealt sisendiks sisend- ja väljundfaili ning transformer-funktsioonile vastava sõna (kas "tabs" või "spaces").

            edit(Path.of(args[0]), // sisendafil
                    Path.of(args[1]),  // väljundfail
                    args[2].equals("tabs") ? FileEditor::spacesToTabs : FileEditor::tabsToSpaces); // kui transformer-käsk on "tabs", siis kasutatakse tabsToSpaces meetodit, kui "spaces", siis spacesToTabs meetodit.
        }
    }

    class wordFreqCounter { // 3. WordFreqCounter

        public static void main(String sisendFail) {

            HashMap<String, Integer> wordCounts = new HashMap<>(); // sõnade sageduste loendamiseks kasutatakse HashMapi

            try {
                for (String line : Files.readAllLines(Path.of(sisendFail))) {
                    for (String word : line.split("\\s+")) { // Faili loetakse sõnade kaupa (ehk iga rida tükeldatakse tühikute järgi sõnadeks)
                        String wordWOPunct = word.replaceAll("[^a-zA-Z]", ""); // Sõnast eemaldatakse kõik kirjavahemärgid
                        // Sõna tehakse väiketähtedeks ja selle sagedust  suurendatakse merge-meetodi abil 1 võrra
                        wordCounts.merge(wordWOPunct.toLowerCase(), 1,
                                (existingCount, newCount)  ->  existingCount + newCount);  // sageduse suurendamiseks kasutatakse lambdafunktsiooni
                    }
                    wordCounts.forEach((word, count) -> System.out.println(word + " " + count)); // sõnade sagedused väljastatakse forEach-meetodi abil ekraanile
                }

            } catch (IOException e) {e.printStackTrace();}

        }
    }

    static class HandCraftedMapFilter {

        public static List<String> filter (List<String> list, Predicate<String> predicate) { // Meetod saab sisendiks List<String> ja Predicate<String> tüüpi objektid ning tagastab List<String>-tüüpi objekti

            List<String> newList = new ArrayList<>(); // Uus list, kuhu sobivad elemendid lisatakse
            for (String element : list) { // Listi elementide iteratsioon
                if (predicate.test(element)) {newList.add(element);} // Kui element vastab predikaadile, siis see lisatakse uude listi
            }
            return newList; // sisaldab ainult predikaadile vastavaid elemente
        }

        public static List<String> map (List<String> list, Function<String, String> fn) { // Meetod saab sisendiks List<String> ja Function<String, String> tüüpi funktsiooni ning tagastab List<String>-tüüpi objekti

            List<String> newList = new ArrayList<>(); // Uus list, kuhu sobivad elemendid lisatakse
            for (String element : list) { // Listi elementide iteratsioon
               newList.add(fn.apply(element)); // Igale elemendile rakendatakse funktsiooni ja muudetud kuju lisatakse uude listi
            }
            return newList;
        }



        public static void main() { // Peameetodis luuakse sõnede list, milelle rakendatakse klassimeetodeid
            // 4. Hand crafted map-filter

            List<String> strings = List.of("kana", "koer", "kass", "kala", "küülik",  "kurg", "kukk", "kalkun"); // Sõnede list

            List<String> reversedStrings = map(strings, s -> new StringBuilder(s).reverse().toString()); // klassimeetodit map kasutatakse koos lambdafunktsiooniga iga sõne taguripidi pööramiseks
            List<String> min4lenReversedStrings = filter(reversedStrings, s -> s.length() > 3); // klassimeetodit filter kasutatakse koos lambdafunktsiooniga, et leida vähemalt 4 tähe pikkused sõned
            min4lenReversedStrings.forEach(System.out::println); // tulemuse väljastamine forEach-meetodi abil
        }

    }

    public static void main(String[] args) throws IOException {
        // 1. FileFilter
        FileFilterClass.main("../" ); // "kodutood/KT4/src"

        // 2. FileEditor
        //FileEditor.main(new String[]{"suvendusruhm/pr17/src/task2inputtest1.txt", "suvendusruhm/pr17/src/outputtest1.txt", "tabs"});
        //FileEditor.main(new String[] {"suvendusruhm/pr17/src/task2inputtest1.txt", "suvendusruhm/pr17/src/outputtest2.txt", "spaces"});

        // 3. WordFreqCounter
       //wordFreqCounter.main( "suvendusruhm/pr17/src/task2inputtest1.txt");

        // 4. HandCraftedMapFilter
       //HandCraftedMapFilter.main();

    }
}





