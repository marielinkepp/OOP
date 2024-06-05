public class FormaadiErind extends RuntimeException {
    private String veaSelgitus;

    public FormaadiErind(String s, String veaSelgitus) {
        super(s);
        this.veaSelgitus = veaSelgitus;
    }
}
