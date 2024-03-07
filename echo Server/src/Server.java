import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {

        int portNumber = 1337; // Klass võtab pordil 1337 ühendusi vastu.

        try (ServerSocket ss = new ServerSocket(portNumber)) { // püütakse luua uut ühendust Server Socketiga

            while (true) { // tsükkel kestab igavesti ehk server ei sulgu iseenesest
                try (Socket socket = ss.accept()) { // Server võtab ühenduse vastu

                    // luuakse DataInputStream ja DataOutputStream: in saab Client klassilt sisendi ja out saadab selle klassile tagasi
                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    int messageAmount = in.readInt(); // server loeb oodatavate sõnumite arvu
                    for (int i = 0; i < messageAmount; i++) {out.writeUTF(in.readUTF());} // server loeb sõnumite arv korda sõnumi ja saadab selle tagasi
                }
            }
        }
    }
}