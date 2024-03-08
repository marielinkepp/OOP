import java.util.ArrayList;

public class Spiderweb implements Effect {
    // Spiderweb - vähendab vastase iga käigu alguses tema action points. efekt lõpeb, kui vastast on kahe tema käigu jooksul mõjutatud.

    private int mojutuskordi = 0;

    @Override
    public void onHit(Dude effectTarget) {
        effectTarget.addEffect(this);
        this.mojutuskordi += 1;
    }

    @Override
    public void onTurnStart(Dude effectTarget) {
        effectTarget.setActionPoints(effectTarget.getActionPoints() - 1);
        this.mojutuskordi += 1;
        // Iga rünnaku pihta/mööda minemise tuvastamiseks kasutatakse valemit "d20 + ründaja täpsus >= vastase kaitse", kus d20 on juhuslik arv [1..20].
    }

    @Override
    public void onTurnEnd(Dude effectTarget) {
         // kui Spiderweb on mõjutanud kaks korda, siis eemdalatakse see mõjuvatae efektide massiivist
    }

    @Override
    public int requiredActionPoints() {
        return 30;
    }

    @Override
    public boolean isExpired() {
        if (this.mojutuskordi == 2) return true;
        return false;
    }
}
