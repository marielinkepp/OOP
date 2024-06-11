import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Scanner;

import java.net.URL;
import java.util.zip.ZipFile;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class spreadsheetParser {
    /*Spreadsheet parser
    LibreOffice Calc on MS Office Excel alternatiiv, mis on open-source ja avatud failiformaadiga.
    Kirjuta programm, mis oskab lihtsaid LibreOffice spreadsheet faile (.ods) avada ja nende sisu näidata.
     ODS failid on tavalised zip failid, mis sisaldavad kindla struktuuriga xml faile.

    Programm võtav käsureal parameetriks ods faili nime, avab selle zip failina ja loeb zipist content.xml faili sisu.
    Seejärel kasutab programm mõnda XML töötlemise libraryt (nt dom4j), et leida xml seest spreadsheet lahtrite väärtused.
    Leitud väärtused peab käsureale väljastama, nii et on aru saada, mis väärtus tuli mis lahtrist (ei pea midagi keerulist tegema).

    Võib eeldada, et ods failis on ainult üks spreadsheet. Võib eeldada, et spreadsheetil on ainult tekst (pilte, graafikuid jms pole).

    Näide. Tabeli sisu:

    a1	b1	(tühi)
    a2	(tühi)	c2
    (tühi)	b3	c3
    Sellele vastav ods fail: Attach:sample.ods (ava 7-zip vms programmiga)*/

    public static void loeFailiSisu(String failinimi) throws IOException {

        try {
            ZipFile odsFile = new ZipFile(new File(failinimi));
            InputStream contentXmlStream = odsFile.getInputStream(odsFile.getEntry("content.xml"));

            SAXReader reader = new SAXReader();
            Document document = reader.read(contentXmlStream);

            Element root = document.getRootElement();
            for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ) {
                Element element = it.next();
                System.out.println(element.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        try {
            loeFailiSisu("boonusul/spreadsheetParser/sample.ods");
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*Scanner scanner = new Scanner(System.in);
        System.out.println("Sisesta faili nimi:");
        String userInput = scanner.nextLine();
        System.out.println("Faili sisu on: " + userInput);
        scanner.close();*/


    }
}
