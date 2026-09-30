import java.math.BigDecimal;

public enum Tarif {



    DISPOSABLE("Разовый"){
        public BigDecimal PeriodCalculation(long period){
            long days = period;
            return BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(days));

        }
    },

    MONTHLY("Месячный"){
        public BigDecimal PeriodCalculation(long period){
            long days = period;
            return BigDecimal.valueOf(70).multiply(BigDecimal.valueOf(days));

        }
    },

    STUDENT("Студентческий"){
        public BigDecimal PeriodCalculation(long period){
            long days = period;
            return BigDecimal.valueOf(50).multiply(BigDecimal.valueOf(days));

        }
    };




    private final String title;

    Tarif(String title) {
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }








}
