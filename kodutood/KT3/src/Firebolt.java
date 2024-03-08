import java.util.ArrayList;

public class Firebolt implements Effect {
    // Firebolt - vähendab vastase käigu lõpus vastase health. efekt lõppeb pärast health vähendamist.

    private boolean expired = false;
    @Override
    public void onHit(Dude effectTarget) {
        effectTarget.addEffect(this);
        effectTarget.decreaseArmor();
    }

    @Override
    public void onTurnStart(Dude effectTarget) {

    }

    @Override
    public void onTurnEnd(Dude effectTarget) {
        if (effectTarget.getArmor() > 0) effectTarget.setHealth(effectTarget.getHealth() - 10);
        else effectTarget.setHealth(effectTarget.getHealth() - 30);
        this.expired = true;
    }

    @Override
    public int requiredActionPoints() {
        return 10;
    }

    @Override
    public boolean isExpired() {
        return this.expired;
    }

}
