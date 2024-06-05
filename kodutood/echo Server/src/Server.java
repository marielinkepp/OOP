import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;

public class Server {

    public static void main(String[] args) throws IOException {

        int portNumber = 1337; // Klass võtab pordil 1337 ühendusi vastu.

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { // püütakse luua uut ühendust Server Socketiga
            System.out.println("Server on port " + portNumber);

            while (true) { // tsükkel kestab igavesti ehk server ei sulgu iseenesest
                final Socket clientSocket = serverSocket.accept(); // Server võtab ühenduse vastu
                new Thread(new MultiThreadSocketManager(clientSocket)).start(); // Iga ühenduse jaoks luuakse uus lõim
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

class MultiThreadSocketManager implements Runnable {
    private final Socket clientSocket;

    public MultiThreadSocketManager(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        // luuakse DataInputStream ja DataOutputStream: in saab Client klassilt sisendi ja out saadab selle klassile tagasi
        try (clientSocket; DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

            int messageAmount = Integer.parseInt(in.readUTF().split("length:")[1]); // Server võtab ühenduse vastu ja loeb requestide arvu (int).
            for (int i = 0; i < messageAmount; i++) {
                int requestType = in.readInt(); // loetakse päringu tüüp (0 või 1)
                String requestContent = in.readUTF(); // loetakse päringu sisu

                // Iga response peaks algama arvuga, mis näitab staatust (ok, error): kasutasin siin HTTP response koode (200 OK; 400 Bad Request)
                // Server peab vastu võtma kahte tüüpi sõnumeid
                if (requestType == 0) { // kui päringu tüüp on 0 (echo-request), siis sisaldab päringu sisu teksti
                    out.writeInt(200); // lisatakse kood 200, mis näitab staatust (ok)
                    out.writeUTF(requestContent); // respone saadetakse Client objektile tagasi
                } else if (requestType == 1) { // kui päringu tüüp on 1 (file-request), siis sisaldab päringu sisu failinime
                    File file = new File(requestContent); // luuakse uus file objekt

                    if (file.isAbsolute() || !file.exists()) {
                        out.writeInt(400);  // kui faili nimi on absolute path või faili ei leita, siis lisatakse kood 400, mis näitab staatust (error)
                    }

                    else { // muidu tuleb saata vastuseks faili sisu
                        out.writeInt(200); // lisatakse kood 200, mis näitab staatust (ok)

                        byte[] terveFail = Files.readAllBytes(file.toPath()); // faili sisu loetakse baitidena sisse
                        out.writeInt(terveFail.length); // faili suurus saadetakse kliendile tagasi (sest selle põhjal saab luuabaidimassiivi)
                        out.write(terveFail); // faili sisu tagatsatakse kliendile
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}