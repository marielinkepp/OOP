class Kohvijoomine { // peaklass
    public static void tagastaMassiiviIsendid(Programmeerija[] programmeerijateMassiiv) {
        for (Programmeerija elem: programmeerijateMassiiv) { // väljastan iga programmeerija kohta informatsiooni, kasutades meetodi toString tagastatud väärtust
            System.out.println(elem.toString());
        }
    }

    // luuakse kaks isendit klassist Kohv ja kolm isendit klassist Programmeerija (kusjuures üks neist kohvi ei joo)
    public static void main (String[] argv) {
        Kohv supremo = new Kohv("Supremo", 2.5);
        Kohv yirgacheffe = new Kohv("Yirgacheffe", 3.0);
        Programmeerija peeter = new Programmeerija("Peeter", 700);
        Programmeerija indrek = new Programmeerija("Indrek", 600, 5, supremo);
        Programmeerija sander = new Programmeerija("Sander", 700, 2, yirgacheffe);

        Programmeerija[] programmeerijad = new Programmeerija[]{peeter, indrek, sander};
        tagastaMassiiviIsendid(programmeerijad);

        System.out.println();
        System.out.println("Tähtaeg läheneb"); // Tähtaja lähenedes kirjutavad nad rohkem koodi ning joovad rohkem kohvi.
        System.out.println();
        for (Programmeerija vaadeldavProgrammeerija: programmeerijad) { // Suurendan vastavaid isendiväljade väärtusi, kasutades get- ja set-meetodeid:
            if (vaadeldavProgrammeerija.kasJoobKohvi()) {
                vaadeldavProgrammeerija.setTasse(vaadeldavProgrammeerija.getTassideArv() + 3);  // iga kohvijooja joob 3 tassi võrra rohkem kohvi kui tavaliselt
                vaadeldavProgrammeerija.setRidu(vaadeldavProgrammeerija.getKoodiridadeArv() * 2); // ning kirjutab kaks korda rohkem koodiridu päevas
            } else {
                vaadeldavProgrammeerija.setRidu(vaadeldavProgrammeerija.getKoodiridadeArv() + 100); // programmeerija, kes kohvi ei joo, kirjutab 100 rea võrra rohkem koodi, aga kohvi endiselt ei joo.
            }
        }
        tagastaMassiiviIsendid(programmeerijad); // väljastan iga programmeerija kohta informatsioon uuesti
    }
}
