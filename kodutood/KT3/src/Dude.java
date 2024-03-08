import java.util.ArrayList;
public abstract class Dude { //Loo abstraktne klass Dude, kes on kõigi tegelaste aluseks.
    private double accuracy, armor, health, actionPoints; // Tal on muuhulgas järgnevad omadused: accuracy, armor, health ja action points.
    private ArrayList<Effect> effects;

    public Dude(double accuracy, double armor, double health, double actionPoints) {
        this.accuracy = accuracy;
        this.armor = armor;
        this.health = health;
        this.actionPoints = actionPoints;
        this.effects = new ArrayList<>();

    }



    public void takeTurn(Dude attackTarget) {

        this.effects.forEach(e-> e.onTurnStart(this)); // igale efektile rakendatakse käig ualguses vastavaid operatsioon (mõbe puhul ei tehta midagi)

        // kontroll piisava AP kohta; miinimumarv vajalikke action pointe on 10
        if (this.getActionPoints() >= 10) {

            // ründemeetod valitakse igal juhul (ehk punktid väehenevad ka siis, kui rünnak pihta ei lähe)
            Effect valitudEfekt = this.valiRundemeetod(attackTarget);
            this.decreaseActionPoints(valitudEfekt.requiredActionPoints()); // AP vähendamine

            // täringu veeretamine ja kontroll
            if (((int) (Math.random()*20) + 1 + this.getAccuracy()) >= attackTarget.getArmor()) { // kui rünnak ei lähe mööda, siis püüab ründaja kasutada mõlemat efekti korraga
                valitudEfekt.onHit(attackTarget); // vastase onHit ja lisamine tema mõjutatavate effektide listi
            }
        } else {
            System.out.println("pole piisavalt punkte");
        }

        this.effects.forEach(e-> e.onTurnEnd(this));
        this.addActionPoints(); // Dude objekt taastub
        this.effects.removeIf(Effect::isExpired); // efekt eemaldatakse, kui see enam ei kehti

    }

    public boolean isAlive() {
        return this.getHealth() > 0; // kui health punkte on rohkem kui 0, siis on Dude veel elus; muul juhul on ta surnud
    }

    public abstract Effect valiRundemeetod(Dude attackTarget); // Hea mõte on lisada Dude klassi abstract method efekti valimiseks. Iga alamklass saab selle endale sobival viisil implementeerida.

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getArmor() {
        return armor;
    }

    public void setArmor(double armor) {
        this.armor = armor;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getActionPoints() {
        return actionPoints;
    }

    public void setActionPoints(double actionPoints) {
        this.actionPoints = actionPoints;
    }

    public void addActionPoints() {
        this.actionPoints += 0;
    }

    public void addActionPoints(double actionPointsToAdd) {
        this.actionPoints += actionPointsToAdd;
    }

    public void decreaseActionPoints(double actionPointsToRemove) {
        this.actionPoints -= actionPointsToRemove;
    }

    public void decreaseArmor() {
        this.armor -= 20; // igal korral vähendatakse armorit 20 võrra
        if (this.armor < 0) this.armor = 0;
    }


    public ArrayList<Effect> getEffects() {
        return effects;
    }

    public void addEffect(Effect effect) {
        this.effects.add(effect);
    }

    public void removeEffects(ArrayList<Effect> effects) {
        this.effects.removeAll(effects);
    }

    @Override
    public String toString() {
        return "Dude{" +
                "accuracy=" + this.getAccuracy() +
                ", armor=" + this.getArmor() +
                ", health=" + this.getHealth() +
                ", actionPoints=" + this.getActionPoints() +
                '}';
    }
}



class TestDude {
    public static void main(String[] args) {

        //Selles ülesandes ehitame väga lihtsustatud lahingusimulaatori.
        // Loome erinevad tegelased, kes suudavad omavahel duelle pidada.
        // Duell kestab, kuni üks tegelastest langeb.
        // Tegelased käivad kordamööda ja saavad enda käigu ajal vastast erinevate efektidega rünnata.
        // Efektid kulutavad action pointe, mis taastuvad käigu lõpus.
        // Efektid võivad olla ühekordse mõjuga või kesta mitu käiku, mõjudes vastase käikude alguses ja/või lõpus.


        //Rünnakud võivad ebaõnnestuda (vastasest mööda minna).
        // Iga rünnaku pihta/mööda minemise tuvastamiseks kasutatakse valemit "d20 + ründaja täpsus >= vastase kaitse", kus d20 on juhuslik arv [1..20].

        // Ühele tegelasele saab korraga rakenduda mitu sama tüüpi effekti. Iga dude peaks ise arvet pidama, mis efektid teda hetkel mõjutavad ja iga efekt peaks ise arvet pidama, kui kaua ta veel kestab.
        // Näiteks wizard saab vastasele kaks käiku järjest spiderweb visata, mis juhul mõjutavad vastast vahepeal korraga kaks eraldiseisvat spiderwebi.

        //  Efekti-spetsiifiline loogika peaks olema vastava efekti klassis.

        //  Dude klassis olev ründamise loogika (takeTurn) peaks olema üldistatud ja kõigi tegelaste/efektide puhul täpselt ühesugune.
        //  Dude ei peaks teadma, mis alamklassid tal on ega mis konkreetseid efekte ründamiseks kasutatakse.
        //  Dude alamklassid teavad, mis efektidega nad rünnata oskavad, aga mitte seda, millega neid rünnata võidakse.
        //  Selle tulemusena on võimalik lisada uusi efekte ja Dude alamklasse, ilma et peaks teisi klasse muutma.

        // Kuidas Dude peaks valima rünnakuks sobiva efekti, kui ta ei tea konkreetsetest efektidest midagi?
        //Mõtle välja puudu olevad muutujad ja meetodid, nii et mäng toimiks. Tekita peameetod, mis tegelasi võitlema paneb ja tulemused välja kuulutab.

        // Main meetod loob mängu alguses kaks tegelast, kes on Dude alamklassid Fighter ja Wizard. Neil on erinevad accuracy, armor, health ja action points taastumise kiirus. Fighter ja Wizard kasutavad ründamiseks erinevaid efekte.
        Fighter fighter = new Fighter(85.0, 100.0, 100.0, 100.0);
        Wizard wizard = new Wizard(100.0, 100.0, 100.0, 100.0);

        // Pärast seda kutsutakse kordamööda dude objektidel takeTurn meetodit, kuni võitja on selgunud.

        Dude esimene = wizard, teine = fighter;

        if ((int)(Math.random()*2) + 1 == 1) {
            esimene = fighter;
            teine = wizard;
        }


        while (fighter.isAlive() && wizard.isAlive()) { // tsükkel kestab seni, kuni mõlemad elus on
            esimene.takeTurn(teine);
            teine.takeTurn(esimene);
        }

        System.out.println(fighter);
        System.out.println(wizard);

    }
}
