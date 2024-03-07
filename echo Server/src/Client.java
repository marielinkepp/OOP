import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) { // klass Client saab käsurea parameetritega mitu sõnumit (stringi)

        try (Socket socket = new Socket("localhost", 1337)) {

            // Seejärel saadab client esimese sõnumi, ootab serverilt vastuse (echo) ja prindib selle välja.
            // Seejärel saadab järgmise sõnumi ja prindib vastuse välja.
            // Sama korrata iga sõnumiga.

            // luuakse DataInputStream ja DataOutputStream: in saab Server klassilt info ja out saadab selle klassile
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeInt(args.length); // serverile saadetakse info stringide arvu kohta

            for (String inputString: args) { // sisendi iteratsioon
                out.writeUTF(inputString); // iga sisendsõne saadetakse Socketile
                System.out.println(in.readUTF()); // Socket saadab sõne tagasi ja see väljastatakse ekraanile
            }
        } catch (IOException e) {throw new RuntimeException(e);}
    }
}
