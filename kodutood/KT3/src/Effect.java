import java.util.ArrayList;

public interface Effect {

    void onTurnStart(Dude effectTarget); //, mille käivitab efektiga pihta saanud vastane iga oma käigu alguses {

    void onHit(Dude effectTarget);// , mille käivitab ründaja, kui vastane saab efektiga pihta
    void onTurnEnd(Dude effectTarget); //, mille käivitab efektiga pihta saanud vastane iga oma käigu lõpus
    int requiredActionPoints();
    boolean isExpired();
}
