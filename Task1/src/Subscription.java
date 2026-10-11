import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
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

    public boolean tariffEnd(long start, long end){
        return (end - start) < 0;
    }

    public BigDecimal calculChangeTariff(Tarif nTar){
        long daysNow = LocalDate.now().toEpochDay();
        long endSubDays = endSubs.toEpochDay();
        long startSub = startSubs.toEpochDay();
        long remDays = endSubDays - daysNow;
        long totalDays = endSubDays - startSub;


        BigDecimal oldPrice = tarif.periodCalculation(Period.ofMonths(1));
        BigDecimal oldPrDay = oldPrice.divide(BigDecimal.valueOf(totalDays),4, RoundingMode.HALF_UP);
        BigDecimal returnMoney = oldPrDay.multiply(BigDecimal.valueOf(remDays));

        BigDecimal newPr = nTar.periodCalculation(Period.ofDays((int) remDays));
        return newPr.subtract(returnMoney).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal calculateRefundAmount() {
        long todayDays = LocalDate.now().toEpochDay();
        long endDays = endSubs.toEpochDay();
        long startDays = startSubs.toEpochDay();

        long remainder = endDays - todayDays;
        long durationSubs = endDays - startDays;

        BigDecimal fullPrice = tarif.periodCalculation(Period.ofMonths(1));
        BigDecimal pricePerDay = fullPrice.divide(BigDecimal.valueOf(durationSubs), 4, RoundingMode.HALF_UP);
        return pricePerDay.multiply(BigDecimal.valueOf(remainder)).setScale(2, RoundingMode.HALF_UP);
    }


    public void applyFreeze(int days) {
        LocalDate today = LocalDate.now();
        LocalDate endFreeze = today.plusDays(days);
        frozenHistory.add(new Frozen(today, endFreeze));
        this.endSubs = this.endSubs.plusDays(days);
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
