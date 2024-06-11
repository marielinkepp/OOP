import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;

// ...


public class calendarReader {
    /* Kirjuta programm, mis oskab online kalendreid lugeda ja sealt sündmusi näidata. Programm peab oskama lugeda iCalendar formaati (https://tools.ietf.org/html/rfc5545).

    Kasutaja sisestab käsurea parameetritega kalendri URLi, ajavahemiku alguse- ja lõpukuupäeva. Programm peab näitama selles ajavahemikus toimuvate sündmuste nimesi, kirjeldusi ja täpseid toimumisaegu.

    Täpsustused:

    programm peab lugema kalendrist sündmusi ja korduvaid sündmusi (ainult FREQ ja UNTIL reeglid). Korduvate sündmuste puhul näidata iga kordust eraldi.
    kalendri lugemise loogika tuleb ise kirjutada. olemasolevaid ical librarysi kasutada ei tohi.
    programm võib ignoreerida infot, mida pole vaja kasutajale näidata (st tervet kalendri formaati ei pea oskama lugeda).
    käsurealt sisestatud kuupäevad on ISO-8601 date formaadis (nt 2017-04-23). programmi väljastatud toimumisajad on ISO-8601 Combined date and time formaadis (nt 2017-04-23T19:03:35+03:00)
    kuupäevade arvutamiseks peab kasutama java.time klasse, nt LocalDateTime, Instant ja DateTimeFormatter. Ära kasuta vanu ja jaburaid klasse Date, Calendar, SimpleDateFormat.
    kalendri URList lugemist peaks testima kas google calendar või ut.ee ÕISi kalendriga. google jaoks vaata https://support.google.com/calendar/answer/37648?hl=en "See your calendar" ja ÕISis ainetele registreerimise vaates kalendri kõrval on nupuke "telli" - copy-paste selle link.
    Vihje: failiformaat on lihtsam, kui esialgu tundub. vaata nende google/ÕIS linkide sisse - tegu on ilusate tekstifailidega, mille formaat on täpselt ära spetsifitseeritud.*/

    public static String loeLeheSisu(URL url) throws IOException {
        URLConnection urlc = url.openConnection(); // ühenduse loomine
        BufferedReader in = new BufferedReader(new InputStreamReader(urlc.getInputStream())); // sisu lugemine
        StringBuilder s = new StringBuilder();

        // sisu sõnena kirjutamine
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            s.append(inputLine);
        in.close();

        return s.toString();
    }

    public static String tootleLeheSisu(String sisuAlgkuju, String kpv1, String kpv2) throws IOException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy'-'MM'-'dd");
        LocalDate algkpv = LocalDate.parse(kpv1, formatter);
        LocalDate loppkvp = LocalDate.parse(kpv2, formatter);

        String[] sündmused = sisuAlgkuju.split("BEGIN:VEVENT"); // sündmuse objekt: https://datatracker.ietf.org/doc/html/rfc5545#section-3.6.1

        for (int i = 1; i < sündmused.length; i++) {
            String sündmus = sündmused[i]; // vaadeldav sündmus

            String dateTimeStr = sündmus.split("DTSTART;TZID=Europe/Tallinn:")[1].split("RRULE:")[0];
            DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss");
            LocalDateTime algusAeg = LocalDateTime.parse(dateTimeStr, formatter2);

            if (algusAeg.isAfter(algkpv.atStartOfDay()) && algusAeg.isBefore(loppkvp.plusDays(1).atStartOfDay())) {
                System.out.println(sündmus.split("SUMMARY:")[1].split("CATEGORIES")[0]);  // sündmuse kirjeldus (lühem): https://datatracker.ietf.org/doc/html/rfc5545#section-3.8.1.12
                System.out.println(sündmus.split("DESCRIPTION:")[1].split("END:VEVENT")[0]);  // sündmuse kirjeldus (pikem, pigem on see täpsustus): https://datatracker.ietf.org/doc/html/rfc5545#section-3.8.1.5        }
                System.out.println(algusAeg);
                System.out.println(sündmus.split("DURATION:")[1].split("LOCATION:")[0]);  // sündmuse kirjeldus (pikem, pigem on see täpsustus): https://datatracker.ietf.org/doc/html/rfc5545#section-3.8.1.5        }
                String[] toimumised = sündmus.split(":FREQ=")[1].split("DURATION:")[0].split(";UNTIL=");


                //System.out.println(LocalDateTime.parse(sündmus.split("DTSTART;TZID=Europe/Tallinn:")[1].split("RRULE:")[0], formatter2));

                System.out.println(sündmus);
                //System.out.println(toimumised[0] + " " + toimumised[1]);

                DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmssX");
                LocalDateTime toimumisteLopp = LocalDateTime.parse(toimumised[1], formatter3);
                System.out.println(toimumised[0] + " " + toimumisteLopp);

                Duration duration = Duration.between(algusAeg, toimumisteLopp);

                System.out.println(duration);

                System.out.println("-------------------------------------------------");
            }



    }
        return sisuAlgkuju;
    }
    public static void main(String[] args) throws IOException {
        URL url = new URL("https://ois2.ut.ee/api/timetable/personal/link/8bf38d9ab00c498fa52393683212b641/et");

        //System.out.println(url);
        String leheSisu = loeLeheSisu(url);
        tootleLeheSisu(leheSisu, "2024-04-23", "2024-06-31");

        //leiaSundmusedVahemikus(leheSisu, "2024-05-23", "2021-05-31");
        //System.out.println(leheSisu);


    }
}
