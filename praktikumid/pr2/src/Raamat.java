public class Raamat {
    private Isik autor;
    private String pealkiri;

    public Raamat(Isik autor, String pealkiri) {
        this.autor = autor;
        this.pealkiri = pealkiri;
    }

    public String toString() {
        return "" + this.pealkiri + ", " + this.autor.getNimi() + "";
    }
}

class TestRaamat {
    public static void main (String[] argv) {
        Isik oskarLuts = new Isik("Oskar Luts");
        Raamat kevade = new Raamat(oskarLuts, "Kevade");

        Raamat[] riiul = new Raamat[100];

        riiul[8] = kevade;

        System.out.println(riiul[8]);

        Isik eduardVilde = new Isik("Eduard Vilde");

        for (int i = 0; i < riiul.length; i++) {
            riiul[i] = new Raamat(eduardVilde, "Kogutud teosed " + String.valueOf(i + 1));
        }

        System.out.println("10. raamat riiulil on " + riiul[9] + ".");
    }
}
