import java.math.BigDecimal;
import java.math.RoundingMode;

public class HUl4 {
        public static void main () {
            Põõsas[] poosad = new Põõsas[5]; //Kõikidest Põõsas-tüüpi isenditest tehakse Põõsas[]-tüüpi massiiv. (Massiivi võib ka enne põõsaid luua ja järjest täita.)
            for (int i = 0; i < poosad.length; i++) {
                poosad[i] = new Põõsas( // ümardamisel abi ChatGPT-lt
                        BigDecimal.valueOf(Math.random()*5+25)
                        .setScale(2, RoundingMode.HALF_UP)
                        .doubleValue(),
                        BigDecimal.valueOf(Math.random()*5+100)
                                .setScale(2, RoundingMode.HALF_UP)
                                .doubleValue());
            }

            Hekk hekk = new Hekk("hekk", poosad); // Luuakse üks Hekk-tüüpi isend kasutades argumendina eelmainitud massiivi.
            System.out.println(hekk);// Heki andmed väljastatakse ekraanile.

            for (Põõsas elem: poosad) { // Tsükli abil kuvatakse massiivist põõsad ekraanile
                System.out.println(elem);
            }
        }
}
