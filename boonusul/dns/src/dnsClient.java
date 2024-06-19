

// https://docs.oracle.com/javase/8/docs/api/java/net/package-summary.html
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;
//import java.net.*;
import java.util.Random;

// https://docs.oracle.com/javase/8/docs/api/java/net/InetAddress.html
// https://docs.oracle.com/javase/8/docs/api/java/net/Inet4Address.html
// https://github.com/mbakhoff/sockets-template
// https://en.wikipedia.org/wiki/List_of_DNS_record_types
// https://www.baeldung.com/a-guide-to-java-sockets
// https://www.infoworld.com/article/2853780/socket-programming-for-scalable-systems.html
// https://docs.oracle.com/javase/8/docs/api/java/net/ServerSocket.html
// https://www.javatpoint.com/java-socket-getoutputstream-method

public class dnsClient {
    static final int DNS_SERVER_PORT = 53;


        public static void kirjutaHeader(DataOutputStream dataOutputStream) {
            /*4.1.1. Header (päis) section format

            Parameetrid:
            a) ID (16bit): päringu id, genereetud suvaline number

            b) peab ise looma:          QR      OPCODE    AA  TC  RD  RA    Z          RCODE
                                                     0         0000         0      0    1     0      000      0000

            - QR (1bit): päringu tüüp (käesolevas programmis 0 ehk päring)
            - OPCODE (4bit): päringu tüüp (käesolevas programmis 0000 - standardne päring)
            - AA (1bit?): kinitab, et päringule vastuse saatev serveril on õigus domeenilt vastuseid saata (aga ma ei saa täpselt aru, vb on teisiti)
            - TC (1bit?): teatatb, et kui sõnum ületab lubatud pikkust, siis seda on kärbitud
            - RD (1bit?, pole kohustuslik): paneb serveri päringut rekursiivselt esitama
            - RA (1bit?, pole kohustuslik): näitab, kas server toetab rekursiivseid päringuid
            - Z: selgituses on "reserved for future use", väärtus on 0 (aga ma ei saa täpselt aru, mis see on)
            - RCODE (4bit): vastuse kood (0 - edukas päring, 1 - formaadi viga, 2 - serveri viga, 3 - nime viga, 4 - server ei toeta päringu tüüpi, 5 - server keeldus päringut teostamast, 6-15 - reserved for future use)

            c)
            - QDCOUNT (16bit): küsimuste arv (käesolevas programmis 1)
            - ANCOUNT (16bit): vastuste arv (käesolevas programmis 0)
            - NSCOUNT (16bit): autoriteetide arv (käesolevas programmis 0)
            - ARCOUNT (16bit): täiendavate vastuste arv (käesolevas programmis 0)

            */

            try {
                dataOutputStream.writeShort((short)new Random().nextInt(32767)); // ID; a)

                short requestFlags = Short.parseShort("0000000100000000", 2);
                ByteBuffer byteBuffer = ByteBuffer.allocate(2).putShort(requestFlags);
                byte[] flagsByteArray = byteBuffer.array();
                dataOutputStream.write(flagsByteArray);

                // c)
                dataOutputStream.writeShort(1); // QDCOUNT
                dataOutputStream.writeShort(0); // ANCOUNT
                dataOutputStream.writeShort(0); // NSCOUNT
                dataOutputStream.writeShort(0); // ARCOUNT
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    public static void kirjutaQuestion(DataOutputStream dataOutputStream) {
            /* 4.1.2. Question section format: päringu sisu defineerivad parameetrid (QDCOUNT sisendit ehk käesolevas programmis 1)

            Parameetrid:
            - QNAME: domeeninimi (esitatud ühekaupa järjestatud osadena, üldkuju pikkusoctet n võrra octeteid; viimane el on 0-pikkusega octet paaris/paaritu)
             - QTYPE (2 oct e. 16bit): küsimuse tüüp (käesolevas programmis 1-  A ehk host address)
             - QCLASS (2 oct e. 16bit): küsimuse klass (käesolevas programmis 1 - IN ehk internet)
             */

            try {
                String domain = "google.com";
                String[] domainParts = domain.split("\\.");

                for (String part : domainParts) { // QNAME
                    byte[] domainBytes = part.getBytes(StandardCharsets.UTF_8);
                    dataOutputStream.writeByte(domainBytes.length);
                    dataOutputStream.write(domainBytes);
                }
                dataOutputStream.writeByte(0);

                dataOutputStream.writeShort(1); // QTYPE
                dataOutputStream.writeShort(1); // QCLASS

            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    private static short loeHeader(ByteBuffer responseBuffer) {
        System.out.println("Transaction ID: " + responseBuffer.getShort()); // ID
        responseBuffer.get();
        responseBuffer.get();

        System.out.println("QDCOUNT: " + responseBuffer.getShort());
        short ANCOUNT = responseBuffer.getShort();
        System.out.println("ANCOUNT: " + ANCOUNT);
        System.out.println("NSCOUNT: " + responseBuffer.getShort());
        System.out.println("ARCOUNT: " + responseBuffer.getShort());
        return ANCOUNT;
    }

    private static void readQuestion(ByteBuffer responseBuffer) {
        StringBuilder sb = new StringBuilder();
        int recLen2;

        while ((recLen2 = responseBuffer.get()) > 0) {
            byte[] record = new byte[recLen2];
            for (int i = 0; i < recLen2; i++) {record[i] =  responseBuffer.get();}
            sb.append(new String(record, StandardCharsets.UTF_8)).append(".");
        }
        if ((sb.length() > 0) && sb.charAt(sb.length() - 1) == '.'){
            sb.deleteCharAt(sb.length() - 1);
        }

        System.out.println("QNAME: " + sb);

        try {
            System.out.println("IP aadress (lahendus 2): " + InetAddress.getByName(sb.toString()).getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        System.out.println("QTYPE: " + responseBuffer.getShort());
        System.out.println("QCLASS: " + responseBuffer.getShort());
    }

    private static void loeVastus(ByteBuffer responseBuffer, short ANCOUNT, int responseLength){
        /* 4.1.3. Resource record format

        Parameetrid (2oct = 16bit = 2baiti):
        - NAME (2oct): domeeninimi (4.1.4 põhjal teame, et formaat on kokkupakitud), vt QNAME; esimesed 2baiti on offset ja lõpus (root) on 0
        - TYPE (2oct): ressursi tüüp
        - CLASS (2oct): ressursi klass
        - TTL (32bit): aeg, mille jooksul ressurssi võib kasutada
        - RDLENGTH (16bit): RDATA pikkus octetites
        - RDATA: ressursi andmed (käesolevas programmis on see 4 octetit pikk ARPA Interneti aadress)
        */

        int positsioon = responseBuffer.position();
        System.out.println("Vastuse alguseks on programm puhvris jõudnud positsioonile " + positsioon);
        System.out.println("Lugeda on veel " + responseBuffer.remaining());

        responseBuffer.get(); // NAME

        ByteArrayOutputStream label = new ByteArrayOutputStream();

        byte currentByte = responseBuffer.get();
        boolean stop = false;
        ByteBuffer responseBuffer2 = ByteBuffer.wrap(Arrays.copyOfRange(responseBuffer.array(), currentByte, responseLength));

        ArrayList<String> RDATA = new ArrayList<>();
        ArrayList<String> DOMAINS = new ArrayList<>();

        while (!stop) { // Tsükkel kestab baidipuhvri lõppu jõudmiseni
            byte nextByte = responseBuffer2.get();
            if (nextByte != 0) {
                byte[] currentLabel = new byte[nextByte];
                for (int j = 0; j < nextByte; j++) {currentLabel[j] = responseBuffer2.get();}
                try {
                    label.write(currentLabel);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            else {

                responseBuffer2.getShort(); // TYPE
                responseBuffer2.getShort(); // CLASS
                responseBuffer2.getInt(); // TTL

                int RDLENGTH = responseBuffer2.getInt(); // RDLENGTH
                responseBuffer2.getInt(); // filler, sest muidu pole IP-aaadressi formaat õige

                for (int s = 0; s < RDLENGTH; s++) { // IP-aadressi osade massiivi lisamine
                    if (responseBuffer2.hasRemaining()) {RDATA.add(String.valueOf(responseBuffer2.get() & 255));}
                    else {break;}
                }
                stop = true;
            }

            if (label.size() > 0) {
                DOMAINS.add(label.toString(StandardCharsets.UTF_8));
                label.reset();
            }
        }

        // Tulemus
        System.out.println("IP aadress (lahendus 3): " + String.join(".", RDATA) + " : " + String.join(".", DOMAINS));

    }


        public static void main(String[] args) {

            String vaikimisi = "8.8.8.8";

            if (args.length != 0) {
                vaikimisi = args[0];
            }

            try {
                InetAddress serveriAadress = InetAddress.getByName(vaikimisi); // serveri aadress
                System.out.println("Aadress vastab järgmisele serverile: " + serveriAadress.getHostName());

                //see "hack" annab ka tulemuse:
                InetAddress domeeniAadress = InetAddress.getByName(serveriAadress.getHostName().split("\\.")[1] + ".com");
                System.out.println("Domeeninimi: " + domeeniAadress.getHostName());
                System.out.println("IP aadress (lahendus 1): " + domeeniAadress.getHostAddress());

                Socket tcpSocket = new Socket(serveriAadress, DNS_SERVER_PORT);

                DataOutputStream dataOutputStream=new DataOutputStream(tcpSocket.getOutputStream());
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                DataOutputStream tempDataOutputStream = new DataOutputStream(byteArrayOutputStream);

                kirjutaHeader(tempDataOutputStream);
                kirjutaQuestion(tempDataOutputStream);

                byte[] serveriSonum = byteArrayOutputStream.toByteArray(); // sõnumi pikkus

                dataOutputStream.writeShort(serveriSonum.length); // enne sõnumit on kahebaidine pikkuse field, mis näitab sõnumi pikkust, (ilma pikksuenäitaja 2 baidita)
                dataOutputStream.write(serveriSonum); // kirjutatakse sõnum

                DataInputStream dis = new DataInputStream(tcpSocket.getInputStream()); // serverilt saadud vastuse lugemiseks luuakse voog

                // Read the length of the DNS response message
                byte[] lengthBytes = new byte[2];
                dis.read(lengthBytes);
                int responseLength = ByteBuffer.wrap(lengthBytes).asShortBuffer().get(); // luuaks evastuse pikkuse suurune puhver
                System.out.println("Vastuse pikkus baitides: " + responseLength);

                byte[] responseBytes = new byte[responseLength];
                dis.read(responseBytes); // loetakse vastus
                ByteBuffer responseBuffer = ByteBuffer.wrap(responseBytes); // vastus pannakse puhvrisse

                short ANCOUNT = loeHeader(responseBuffer); // päise lugemine, tagastab ANCOUNTi (1)
                readQuestion(responseBuffer); // küsimuse lugemine
                loeVastus(responseBuffer, ANCOUNT, responseLength); // vastuse lugemine

                tcpSocket.close(); // socketi sulgemine

            } catch (UnknownHostException e) {
                System.out.println("Ei leitud vastavat serverit.");
                System.exit(1); // veakood
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




