import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class VeebileheLugeja implements Runnable {

    private String name;
    private Deque<String> urlid;
    private ArrayList<Tulemus> leheSisuMassiiv;

    public VeebileheLugeja(String name, Deque<String> urlid, ArrayList<Tulemus> leheSisu) {
        this.name = name;
        this.urlid = urlid;
        this.leheSisuMassiiv = leheSisu;
    }

    public synchronized Deque<String> getUrlid() {
        return urlid;
    }

    public synchronized ArrayList<Tulemus> getleheSisuMassiiv() {
        return leheSisuMassiiv;
    }

    public synchronized static Tulemus loeLeheSisu(String url) throws IOException { // Meetod loeb tühikutega eraldatud täisarv sisaldavast tekstifailist sisu

        URL leheUrl = new URL(url);

        URLConnection urlc = leheUrl.openConnection(); // ühenduse loomine
        BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream())); // sisu lugemine
        StringBuilder s = new StringBuilder();

        // sisu sõnena kirjutamine
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            s.append(inputLine);
        in.close();

        return new Tulemus(url, s.toString());
    }



    @Override
    public synchronized void run() {

        String aadress;
        while ((aadress = getUrlid().pollFirst()) != null) { // Keep polling until deque is empty
            synchronized (this) { // Synchronize on the shared object (or consider using a separate lock object for finer control)
                String finalAadress = aadress;
                if (getleheSisuMassiiv().stream().noneMatch(obj -> obj.getFailiNimi().equals(finalAadress))) {
                    try {
                        // Process and add the content only if it's not already added
                        Tulemus tulemus = loeLeheSisu(aadress); // Process the current URL
                        getleheSisuMassiiv().add(tulemus); // Add the processed result
                    } catch (IOException e) {
                        e.printStackTrace(); // Handle exceptions or log them as per requirement
                    }
                }
            }
        }
    }
}

class testLugeja {

    public static void main(String[] args) throws IOException, InterruptedException {

        // Ül 8: Teha eelmise programmiga analoogne programm, aga failinimede asemel kasuta veebiaadresse.
        ArrayList<Tulemus> leheSisu = new ArrayList<>();
        Deque<String> veebiaadressid = new ArrayDeque<>();
        veebiaadressid.add("https://et.wikipedia.org/wiki/Objektorienteeritud_programmeerimine");
        veebiaadressid.add("https://et.wikipedia.org/wiki/Lõim_(informaatika)");
        veebiaadressid.add("https://et.wikipedia.org/wiki/Programmeerimise_paradigma");

        // Ül 8
        Thread t1 = new Thread(new VeebileheLugeja("Lõim1", veebiaadressid, leheSisu));
        Thread t2 = new Thread(new VeebileheLugeja("Lõim2", veebiaadressid, leheSisu));
        Thread t3 = new Thread(new VeebileheLugeja("Lõim2", veebiaadressid, leheSisu));

        // käivitame threadid
        t1.start();
        t2.start();
        t3.start();
        // ootame, et threadid töö lõpetaks
        t1.join();
        t2.join();
        t3.join();
        System.out.println("Valmis!");

        // Ül8:
        // Programm võtab nimekirja veebiaadressidest, laeb paralleelselt iga lehekülje sisu alla ja annab tulemused järjekorra kaudu tagasi peameetodisse.
        // Peameetodis kirjutada iga alla laetud leht kettale.
        // Kasutada veebilehtede avamiseks voogude praktikumis tutvustatud URL ja URLConnection klasse.

        /*for (Tulemus s: leheSisu) {
            String[] f = s.getFailiNimi().split("/");


            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(f[f.length - 1] + ".bin"))) {
                byte[] bytes = s.getFailiHtml().getBytes(StandardCharsets.UTF_8);
                dos.writeInt(bytes.length); // Write the length of the string in bytes
                dos.write(bytes); // Write the string bytes themselves
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }*/

        try (DataInputStream dis = new DataInputStream(new FileInputStream("Lõim_(informaatika).bin"))) {

            int count = dis.available(); // leitakse olemasolevate baitide arv
            byte[] b = new byte[count]; // luuakse massiiv
            dis.read(b); // baidid loetakse massiivi

            StringBuilder s = new StringBuilder();
            for (byte by : b) {s.append((char)by);}

            System.out.println(s);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        /*public String readString(DataInputStream in) throws IOException {
            int length = in.readInt(); // Read the length of the byte array
            byte[] bytes = new byte[length]; // Create a byte array of the appropriate size
            in.readFully(bytes); // Read the string bytes
            return new String(bytes, StandardCharsets.UTF_8); // Convert the bytes back into a string
        }*/

    }



}
