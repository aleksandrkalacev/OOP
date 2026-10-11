import java.math.BigDecimal;
import java.time.Period;

public enum Tarif {

    /**
     * Абстрактный метод для расчета стоимости тарифа на основе переданного периода.
     * Каждая константа перечисления реализует собственную формулу стоимости:
     * - DISPOSABLE: Фиксированная плата за каждый день.
     * - MONTHLY: Базовая посуточная ставка.
     * - STUDENT: Месячная ставка со скидкой 50%.
     *
     * @param period Временной интервал (дни, месяцы, годы), для которого рассчитывается цена.
     * @return Итоговая стоимость в формате BigDecimal с округлением до копеек.
     */

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

    /**
     * Вспомогательный метод для конвертации объекта Period в плоское количество дней.
     * Использует упрощенное допущение для учебного проекта: 1 месяц = 30 дней, 1 год = 365 дней.
     *
     * @param period Временной интервал для конвертации.
     * @return Общее количество дней в периоде в формате long.
     */

    protected long toDays(Period period) {
        return period.getDays() + (period.getMonths() * 30L) + (period.getYears() * 365L);
    }
}