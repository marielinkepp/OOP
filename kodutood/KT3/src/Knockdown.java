import java.util.ArrayList;

public class Knockdown implements Effect {
    // Knockdown - vähendab pihta minemise hetkel vastase actionPoints 0 peale
    @Override
    public void onHit(Dude effectTarget) {
        //effectTarget.addEffect(this);
        effectTarget.setActionPoints(0.0);
        effectTarget.decreaseArmor();

    }

    @Override
    public void onTurnStart(Dude effectTarget) {

    }

    @Override
    public void onTurnEnd(Dude effectTarget) {

    }

    @Override
    public int requiredActionPoints() {
        return 30;
    }

    @Override
    public boolean isExpired() {
        return false;
    }

}
