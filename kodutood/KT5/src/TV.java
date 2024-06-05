public class TV implements BroadcastListener { // klass TV, mis implementib BroadcastListener

    private String ownerName;

    public TV(String ownerName) { // TV konstruktoriga peab määrama omaniku nime.
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public void listen(String s) { // Kõik kohale jõudvad uudised prindi koos TV omaniku nimega System.out-i.
        System.out.println(this.getOwnerName() + " " + s);
    }
}
