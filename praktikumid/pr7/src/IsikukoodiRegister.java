import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IsikukoodiRegister implements Runnable{

    private List<String> isikukoodid; // Klassis on List<String>, mis hoiab isikukoode

    public IsikukoodiRegister() {
        this.isikukoodid = new ArrayList<>();
    }

    public List<String> getIsikukoodid() {
        return isikukoodid;
    }

    public synchronized void registreeri(String isikukood) { // meetod lisab etteantud isikukoodi listi, kui seda seal veel ei ole.
        List<String> isikukoodid = this.getIsikukoodid();

        if(!isikukoodid.contains(isikukood)) isikukoodid.add(isikukood);
    }

    public synchronized int järjekorranumber(String isikukood) { // meetod tagastab etteantud isikukoodi indeksi registri listis või -1, kui isikukood ei ole registreeritud.
        List<String> isikukoodid = this.getIsikukoodid();

        return this.getIsikukoodid().indexOf(isikukood);
    }

    @Override
    public void run() {

        List<String> estonianIdCodes = Arrays.asList(
                "4671123001",
                "3691216594",
                "1521127082",
                "1270828667",
                "2740723722",
                "8640312561",
                "5250405529",
                "3930114315",
                "1540214659",
                "4680713186",
                "6290715790",
                "3280216675",
                "4570112960",
                "3250415687",
                "7990328478",
                "7760219715",
                "8360617272",
                "1270310272",
                "5591220336",
                "8970318082"
        );

        for (String isikukood: estonianIdCodes) {
            this.registreeri(isikukood);
        }

    }

    public static void main(String[] args) throws InterruptedException {

        // Koosta peameetod, mis loob tühja ArrayList<Integer> ja käivitab kaks lõime.
        ArrayList<Integer> tuhiList = new ArrayList<>();

        Loendur loendur = new Loendur();
        Thread t1 = new Thread(new IsikukoodiRegister());
        Thread t2 = new Thread(new IsikukoodiRegister());

        // Kasutada sünkroniseerimist, nii et registri list on võidujooksude eest kaitstud.
        // Kasutada listi isendit monitorina.

        // Käivitatud lõimed peavad paralleelselt lisama peameetodis loodud listi arvud 0..1000000.
        // käivitame threadid
        t1.start();
        t2.start();
        // ootame, et threadid töö lõpetaks
        t1.join();
        t2.join();
        System.out.println("Valmis!");

    }
}
