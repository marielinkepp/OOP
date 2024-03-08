import java.util.ArrayList;
import java.util.Random;

public class Fighter extends Dude {
    private double  accuracy, armor, health, actionPoints; // Tal on muuhulgas järgnevad omadused: accuracy, armor, health ja action points.
    private ArrayList<Effect> effects;

    public Fighter(double accuracy, double armor, double health, double actionPoints) {
        super(accuracy, armor, health, actionPoints);
        this.accuracy = accuracy;
        this.armor = armor;
        this.health = health;
        this.actionPoints = actionPoints;
        this.effects = new ArrayList<Effect>();
    }

    @Override
    public void addActionPoints(double actionPointsToAdd) { // igal korral taastub 10 punktiga
        super.addActionPoints(10);
    }

    @Override
    public Effect valiRundemeetod(Dude attackTarget) {

        Effect[] ekektid = {new WeaponAttack(), new Knockdown()};
        return ekektid[new Random().nextInt(ekektid.length)];
    }

    @Override
    public String toString() {
        return "Fighter{" +
                "accuracy=" + this.getAccuracy() +
                ", armor=" + this.getArmor() +
                ", health=" + this.getHealth() +
                ", actionPoints=" + this.getActionPoints() +
                '}';
    }
}
