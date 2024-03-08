import java.util.ArrayList;
import java.util.Random;

public class Wizard extends Dude {
    private double  accuracy, armor, health, actionPoints; // Tal on muuhulgas järgnevad omadused: accuracy, armor, health ja action points.
    private ArrayList<Effect> effects;

    public Wizard(double accuracy, double armor, double health, double actionPoints) {
        super(accuracy, armor, health, actionPoints);
        this.accuracy = accuracy;
        this.armor = armor;
        this.health = health;
        this.actionPoints = actionPoints;
        this.effects = new ArrayList<Effect>();
    }

    @Override
    public void addActionPoints(double actionPointsToAdd) { // igal korral taastub 20 punktiga (joob võlueliksiiri 10 lisapunkti saamiseks võrreldes Fighteriga)
        super.addActionPoints(20);
    }

    @Override
    public Effect valiRundemeetod(Dude attackTarget) {

        Effect[] ekektid = {new Firebolt(), new Spiderweb()};
        return ekektid[new Random().nextInt(ekektid.length)];
    }

    @Override
    public String toString() {
        return "Wizard{" +
                "accuracy=" + this.getAccuracy() +
                ", armor=" + this.getArmor() +
                ", health=" + this.getHealth() +
                ", actionPoints=" + this.getActionPoints() +
                '}';
    }
}
