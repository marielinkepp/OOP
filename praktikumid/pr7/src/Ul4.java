import java.util.Arrays;

class KaitstudMassiiv {

    private int[] andmed = new int[100];

    public synchronized void set(int positsioon, int väärtus) {
        andmed[positsioon] = väärtus;
    }

    public synchronized int suurus() {
        return andmed.length;
    }



    public synchronized int[] kõikVäärtused() {
        return Arrays.copyOf(andmed, andmed.length);
    }
}

class Test {
    public static void main(String[] args) {
        KaitstudMassiiv kaitstud = new KaitstudMassiiv();
        for (int i = 0; i < kaitstud.suurus(); i++) {
            kaitstud.set(i, i * 10);
        }
        int[] tulemus = kaitstud.kõikVäärtused();
        for (int element : tulemus) {
            System.out.println(element);
        }
    }
}