import java.time.LocalDate;

public class Frozen {
    private LocalDate start;
    private LocalDate end;

    public Frozen(LocalDate start, LocalDate end){
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    /**
     * Проверяет, попадает ли указанная дата в текущий интервал заморозки абонемента.
     * Границы интервала (день начала и день окончания) включаются в проверку.
     *
     * @param date Проверяемая дата.
     * @return true, если дата находится внутри диапазона заморозки; false в противном случае.
     */

    public boolean inTheFreezer(LocalDate date){
        return !date.isBefore(start) && !date.isAfter(end);
    }
}