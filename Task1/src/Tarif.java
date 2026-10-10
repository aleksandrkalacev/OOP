import java.math.BigDecimal;
import java.time.Period;

public enum Tarif {

    DISPOSABLE("Разовый") {
        @Override
        public BigDecimal periodCalculation(Period period) {
            long days = toDays(period);
            return BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(days));
        }
    },

    MONTHLY("Месячный") {
        @Override
        public BigDecimal periodCalculation(Period period) {
            long days = toDays(period);
            return BigDecimal.valueOf(70).multiply(BigDecimal.valueOf(days));
        }
    },

    STUDENT("Студенческий") {
        @Override
        public BigDecimal periodCalculation(Period period) {
            long days = toDays(period);
            return BigDecimal.valueOf(50).multiply(BigDecimal.valueOf(days));
        }
    };

    private final String title;

    Tarif(String title) {
        this.title = title;
    }

    public abstract BigDecimal periodCalculation(Period period);

    public String getTitle() {
        return this.title;
    }

    protected long toDays(Period period) {
        return period.getDays() + (period.getMonths() * 30L) + (period.getYears() * 365L);
    }
}