import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Subscription {
    private final Client client;
    private Tarif tarif;
    private LocalDate startSubs;
    private LocalDate endSubs;
    private final List<Frozen> frozenHistory = new ArrayList<>();

    public Subscription(Client client, Tarif tarif, LocalDate startSubs, LocalDate endSubs){
        this.client = client;
        this.tarif = tarif;
        this.startSubs = startSubs;
        this.endSubs = endSubs;
    }

    public boolean active(LocalDate date){
        if(date.isBefore(startSubs) || date.isAfter(endSubs)) return false;

        for(Frozen f : frozenHistory){
            if(f.inTheFreezer(date)) return false;
        }

        return true;
    }

    public boolean isFrozenOn(LocalDate date) {
        for (Frozen f : frozenHistory) {
            if (f.inTheFreezer(date)) return true;
        }
        return false;
    }

    public Tarif getTarif() {
        return this.tarif;
    }

    public void setTarif(Tarif tarif) {
        this.tarif = tarif;
    }

    public List<Frozen> getFreezes(){
        return frozenHistory;
    }

    public void setStartSubs(LocalDate startSubs) {
        this.startSubs = startSubs;
    }

    public LocalDate getStartSubs() {
        return startSubs;
    }

    public void setEndSubs(LocalDate endSubs) {
        this.endSubs = endSubs;
    }

    public LocalDate getEndSubs() {
        return endSubs;
    }

}
