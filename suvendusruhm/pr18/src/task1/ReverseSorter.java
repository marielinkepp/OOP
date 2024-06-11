package task1;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * TASK 1.
 *
 * This is a shorter and simpler version of the "2. Failipuu läbimine" interfaces homework task:
 * https://courses.cs.ut.ee/2018/OOP/spring/Main/Practice5Harjutused.
 *
 * Now we try to use lambdas instead of separate classes to make everything really simple.
 */
public class ReverseSorter {
    public static void main(String[] args) throws IOException {

        Path rootPath = Paths.get(System.getProperty("user.dir") + "/suvendusruhm/pr18", "src"); //Paths.get("src"); // hardcoded root path with existing files to avoid messing with args: muutsin seda, et täätaks, kuna ei loonud uut projekti vaid lisasin failid siia
        List<String> filenames = new ArrayList<>();

        Files.walkFileTree(rootPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                filenames.add(file.toString().split("src/")[1]); //filenames.add(file.toString()); muutsin
                return FileVisitResult.CONTINUE;
            }
        });

        List<String> reverseSortedFilenames = filenames.stream().sorted((a, b) -> b.compareTo(a)).toList(); // failinimed sorteeritakse lambdafunktsiooni abil taguripidi
        reverseSortedFilenames.forEach( fn -> System.out.println(fn)); // failinimed väljastatakse lambdafunktsiooni abil eraldi ridadel
    }
}
