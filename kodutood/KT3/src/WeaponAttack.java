public class WeaponAttack implements Effect {
    // WeaponAttack - vähendab pihta minemise hetkel vastase health

    @Override
    public void onHit(Dude effectTarget) {
        if (effectTarget.getArmor() > 0) effectTarget.setHealth(effectTarget.getHealth() - 5);
        else effectTarget.setHealth(effectTarget.getHealth() - 15);
    }

    @Override
    public void onTurnStart(Dude effectTarget) {

    }

    @Override
    public void onTurnEnd(Dude effectTarget) {

    }

    @Override
    public int requiredActionPoints() {
        return 10;
    }

    @Override
    public boolean isExpired() {
        return false;
    }
    
}
