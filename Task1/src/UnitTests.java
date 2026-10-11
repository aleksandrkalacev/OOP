import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class UnitTests {

    public static void main(String[] args) {
        System.out.println("=== START TESTS ===");

        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
        test10();
        test11();
        test12();
        test13();
        test14();
        test15();
        test16();

        System.out.println("=== END TESTS ===");
    }

    private static void test1() {
        BigDecimal res = Tarif.DISPOSABLE.periodCalculation(Period.ofDays(1));
        System.out.println("Тест 1 | Должно быть: 1000 | Вывело: " + res);
    }

    private static void test2() {
        BigDecimal res = Tarif.MONTHLY.periodCalculation(Period.ofDays(30));
        System.out.println("Тест 2 | Должно быть: 2100 | Вывело: " + res);
    }

    private static void test3() {
        BigDecimal res = Tarif.STUDENT.periodCalculation(Period.ofDays(30));
        System.out.println("Тест 3 | Должно быть: 1500 | Вывело: " + res);
    }

    private static void test4() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        Subscription s = new Subscription(c, Tarif.MONTHLY, LocalDate.now().minusDays(10), LocalDate.now().plusDays(20));
        System.out.println("Тест 4 | Должно быть: true | Вывело: " + s.active(LocalDate.now()));
    }

    private static void test5() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        Subscription s = new Subscription(c, Tarif.MONTHLY, LocalDate.now().plusDays(5), LocalDate.now().plusDays(35));
        System.out.println("Тест 5 | Должно быть: false | Вывело: " + s.active(LocalDate.now()));
    }

    private static void test6() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        Subscription s = new Subscription(c, Tarif.MONTHLY, LocalDate.now().minusDays(35), LocalDate.now().minusDays(5));
        System.out.println("Тест 6 | Должно быть: false | Вывело: " + s.active(LocalDate.now()));
    }

    private static void test7() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        LocalDate date = LocalDate.now();
        Subscription s = new Subscription(c, Tarif.MONTHLY, date, date.plusDays(30));
        System.out.println("Тест 7 | Должно быть: true | Вывело: " + s.active(date));
    }

    private static void test8() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        LocalDate date = LocalDate.now();
        Subscription s = new Subscription(c, Tarif.MONTHLY, date.minusDays(30), date);
        System.out.println("Тест 8 | Должно быть: true | Вывело: " + s.active(date));
    }

    private static void test9() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        LocalDate date = LocalDate.now();
        Subscription s = new Subscription(c, Tarif.MONTHLY, date.minusDays(31), date.minusDays(1));
        System.out.println("Тест 9 | Должно быть: false | Вывело: " + s.active(date));
    }

    private static void test10() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        LocalDate date = LocalDate.now();
        Subscription s = new Subscription(c, Tarif.MONTHLY, date.minusDays(5), date.plusDays(25));
        s.getFreezes().add(new Frozen(date.minusDays(1), date.plusDays(4)));
        System.out.println("Тест 10 | Должно быть: true | Вывело: " + s.isFrozenOn(date));
    }

    private static void test11() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        LocalDate date = LocalDate.now();
        Subscription s = new Subscription(c, Tarif.MONTHLY, date, date.plusDays(30));
        s.getFreezes().add(new Frozen(date, date.plusDays(5)));
        System.out.println("Тест 11 | Должно быть: true | Вывело: " + s.isFrozenOn(date));
    }

    private static void test12() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        LocalDate date = LocalDate.now();
        Subscription s = new Subscription(c, Tarif.MONTHLY, date.minusDays(5), date.plusDays(25));
        s.getFreezes().add(new Frozen(date.minusDays(5), date));
        System.out.println("Тест 12 | Должно быть: true | Вывело: " + s.isFrozenOn(date));
    }

    private static void test13() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        LocalDate date = LocalDate.now();
        Subscription s = new Subscription(c, Tarif.MONTHLY, date.minusDays(10), date.plusDays(20));
        s.getFreezes().add(new Frozen(date.minusDays(6), date.minusDays(1)));
        System.out.println("Тест 13 | Должно быть: false | Вывело: " + s.isFrozenOn(date));
    }

    private static void test14() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        Subscription s = new Subscription(c, Tarif.MONTHLY, LocalDate.now(), LocalDate.now());
        long now = LocalDate.now().toEpochDay();
        System.out.println("Тест 14 | Должно быть: false | Вывело: " + s.tariffEnd(now, now));
    }

    private static void test15() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        Subscription s = new Subscription(c, Tarif.MONTHLY, LocalDate.now(), LocalDate.now());
        long now = LocalDate.now().toEpochDay();
        System.out.println("Тест 15 | Должно быть: true | Вывело: " + s.tariffEnd(now + 1, now));
    }

    private static void test16() {
        Client c = new Client("A", "К", BigDecimal.ZERO);
        LocalDate date = LocalDate.now();
        Subscription s = new Subscription(c, Tarif.MONTHLY, date.minusDays(15), date.plusDays(15));
        System.out.println("Тест 16 | Должно быть: 1050.00 | Вывело: " + s.calculateRefundAmount());
    }
}
