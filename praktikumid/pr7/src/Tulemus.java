public class Tulemus implements Comparable<Tulemus>{
    private String failiNimi, failiHtml;
    private Integer failiSumma;

    public Tulemus(String failiNimi, Integer failiSumma) {
        this.failiNimi = failiNimi;
        this.failiSumma = failiSumma;
    }

    public Tulemus(String failiNimi, String failiHtml) {
        this.failiNimi = failiNimi;
        this.failiHtml = failiHtml;
    }

    public synchronized String getFailiNimi() {
        return failiNimi;
    }

    public Integer getFailiSumma() {
        return failiSumma;
    }

    public String getFailiHtml() {
        return failiHtml;
    }

    @Override
    public synchronized String toString() {
        return "failiNimi: " + failiNimi + '\'' +
                ", failiSumma: " + failiSumma ;
    }

    @Override
    public synchronized int compareTo(Tulemus o) {
        return this.getFailiNimi().compareTo(o.getFailiNimi());
    }
}
