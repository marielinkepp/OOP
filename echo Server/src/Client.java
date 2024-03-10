import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

public class Client {

    public static void main(String[] args) { // klass Client saab käsurea parameetritega mitu sõnumit (stringi)

        //String[] args = {"file", "shire.txt", "echo", "rivendell", "file", "mordor.jpg"}; // testimiseks
        try (Socket socket = new Socket("localhost", 1337)) {

            //  Client käsurea parameetritega saab määrata, mis requeste ja mis järjekorras saadetakse.
            //  Käsurea parameetrid käivad paari kaupa: kõigepealt "file" või "echo" ja seejärel faili nimi või sõnumi sisu.
            //  Käsurida võib sisaldada mitu paari, nt file shire.txt echo rivendell file mordor.jpg.
            HashMap<String, String> argsMap = getStringStringHashMap(args);

            // luuakse DataInputStream ja DataOutputStream: in saab Server klassilt info ja out saadab selle klassile
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("length:" + argsMap.size()); // Client saadab kõigepealt serverile requestide koguarvu; kõik variable length andmed peaks algama length prefixiga.

            argsMap.forEach((key, value) -> {

                int requestType = 0; // vaikimisi on päringu tüüp 0 (echo)

                // Iga request peaks algama arvuga, mis näitab requesti tüüpi.
                if (value.equals("file")) requestType = 1; // kui tahetakse failitöötlust, siis on päringu tüüp 1 (file)
                try {
                    out.writeUTF(requestType + " " + key); // Socketile saadetakse sõne kujul päringu tüüp + tühik + sõne (failinimi/ekraanile väljastatav sõne)
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    System.out.println(in.readUTF()); // Socket saadab sõne tagasi ja see väljastatakse ekraanile
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {throw new RuntimeException(e);}
    }

    private static HashMap<String, String> getStringStringHashMap(String[] args) {
        HashMap<String, String> argsMap = new HashMap<>(); // luuakse HashMap, kuhu hakatakse sisendit paarikaupa lisama
        for (int i = 0; i < args.length; i+=2) { // käsurea isesndi paarikaupa iteratsioon
            argsMap.put(args[i + 1], args[i]);
        }
        return argsMap;
    }
}
