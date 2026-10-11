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

    /**
     * Проверяет общую валидность и активность абонемента на конкретную дату.
     * Абонемент считается активным, если дата находится строго в диапазоне дат действия
     * подписки, и на эту дату не зафиксировано ни одной активной заморозки.
     *
     * @param date Проверяемая дата (обычно текущий день).
     * @return true, если проход по абонементу разрешен; false, если он просрочен или заморожен.
     */
    public boolean active(LocalDate date){
        if(date.isBefore(startSubs) || date.isAfter(endSubs)) return false;

        for(Frozen f : frozenHistory){
            if(f.inTheFreezer(date)) return false;
        }

        return true;
    }

    /**
     * Сканирует всю историю заморозок подписки, чтобы определить, заморожен ли абонемент
     * в указанный день. Используется в логике терминала для генерации точечных исключений.
     *
     * @param date Проверяемая дата.
     * @return true, если на этот день действует заморозка; false, если абонемент не заморожен.
     */
    public boolean isFrozenOn(LocalDate date) {
        for (Frozen f : frozenHistory) {
            if (f.inTheFreezer(date)) return true;
        }
        return false;
    }

    /**
     * Проверяет, истек ли срок действия тарифного плана на основе двух временных меток Эпохи.
     *
     * @param start Текущий день, переведенный в количество дней с 1970 года.
     * @param end   День окончания подписки, переведенный в количество дней с 1970 года.
     * @return true, если подписка полностью исчерпала свой срок; false, если она еще действует.
     */
    public boolean tariffEnd(long start, long end){
        return (end - start) < 0;
    }

    /**
     * Производит математический перерасчет стоимости при изменении тарифа в середине периода.
     * Вычисляет неиспользованный финансовый остаток текущего тарифа за оставшиеся дни,
     * рассчитывает стоимость нового тарифа на этот же срок и находит разницу (сумму доплаты или возврата).
     *
     * @param nTar Целевой тарифный план, на который переходит клиент.
     * @return Разница стоимости. Положительное значение означает необходимость доплаты со стороны клиента,
     *         отрицательное — излишек, возвращаемый на счет.
     */

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


    /**
     * Вычисляет точную сумму денежного возврата при досрочном аннулировании подписки.
     * Сумма рассчитывается пропорционально оставшимся дням действия абонемента:
     * Стоимость тарифа делится на общую изначальную длительность подписки и умножается на остаток дней.
     * Все операции используют BigDecimal для исключения погрешностей округления.
     *
     * @return Сумма к возврату на баланс клиента с точностью до 2 знаков после запятой.
     */
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


    /**
     * Применяет процедуру заморозки к текущему абонементу.
     * Добавляет новый интервал в историю заморозок и осуществляет сдвиг даты окончания
     * действия абонемента строго на указанное количество дней вперед.
     *
     * @param days Длительность периода заморозки в днях.
     */

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
