import java.nio.file.LinkOption;
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


    public boolean inTheFreezer(LocalDate date){
        return !date.isBefore(start) && !date.isAfter(end);
    }
}
