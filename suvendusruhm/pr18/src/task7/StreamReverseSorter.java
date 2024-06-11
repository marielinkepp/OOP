package task7;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * TASK 7.
 *
 * This is task 1 taken to extremes.
 *
 * Now we try to do the same thing but with a single stream.
 */
public class StreamReverseSorter {
    public static void main(String[] args) throws IOException {

        Files.walk(Paths.get(System.getProperty("user.dir") + "/suvendusruhm/pr18", "src")) // see võimaldab kasuats olevaid faile vaadata ilma SimpleFileVisitor klassi kasutamata, AGA näitab ka kaustu
                .filter(Files::isRegularFile) // filtreerime voos välja ainult failid (mida vist teeks muidu walkFileTree)
                .map(file -> file.toString().split("src/")[1]) // kuna ma tahan näidata ainult relative pathi, siis töötlen näidatavat nime (mid ategin ka ül1-s)
                .sorted((a, b) -> b.compareTo(a)).toList().forEach(System.out::println); // sama sorteerimisloogika mis ül1-s
    }
}
