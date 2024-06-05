import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

            String receivedDirPath = System.getProperty("user.dir") + File.separator + "received"; // luuakse väljundkausta aadress
            Path path = Paths.get(receivedDirPath); // aadressiga luuakse Path objekt

            try {Files.createDirectories(path);} // kui kausta ei eksisteeri, siis see luuakse;m uul juhul ei tehta midagi
            catch (IOException e) {System.err.println("Kasuta ei saanud luua " + e.getMessage());} // kui kausta pole võimalik luua, väljastatkse veateade

            argsMap.forEach((key, value) -> {
                int requestType = 0; // vaikimisi on päringu tüüp 0 (echo)

                if (value.equals("file")) requestType = 1; // kui tahetakse failitöötlust, siis on päringu tüüp 1 (file)
                try {
                    out.writeInt(requestType); // Socketile saadetakse sõne kujul päringu tüüp (täisarv)
                    out.writeUTF(key); // Socketile saadetakse sõne (failinimi/ekraanile väljastatav sõne)

                    int responseType = in.readInt(); // loetakse vastuse tüüp

                    if ((requestType == 0) && (responseType != 400)) { // kui tegemist on sõnega, siis see väljastatakse ekraanile
                        String responseContent = in.readUTF(); // Socket saadab sõne tagasi ja see väljastatakse ekraanile
                        System.out.println(responseContent);
                    }
                    else if ((requestType == 1) && (responseType != 400)) { // kui tegemist on failiga, siis salvestatakse sisu kausta "received"
                        int fileSize = in.readInt(); // Sockets tagastab faili suuruse
                        byte[] fileContent = new byte[fileSize]; // luuakse uus bbaidimassiiv faili sisu hoidmiseks
                        in.readFully(fileContent); // loetakse faili sisu

                        Files.write(Path.of(path + File.separator + key), fileContent); // faili susu kirjutatakse kausta
                    }

                    else System.out.println("Tundmatu päringu tüüp!"); // muul juhul väljastatakse veateade

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

