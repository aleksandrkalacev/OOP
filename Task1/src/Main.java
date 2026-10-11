import javax.swing.text.TabExpander;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    public static void main() {

        Scanner sc = new Scanner(System.in);


        Subscription sub = null;
        Client client = null;


        System.out.println("Здравствуйте, представьтесь, чтобы я мог заполнить ваши данные в нашей системе...");

        String nameUser = sc.next();
        String LastNameUser = sc.next();

        client = new Client(nameUser,LastNameUser, BigDecimal.valueOf(3000));

        //client = new Client(nameUser,LastNameUser, BigDecimal.valueOf(0));

        client.getHistory().add("Регистрация, пользователя " + nameUser + " " + LastNameUser + ". " + LocalDate.now());

        //System.out.println(client.getHistory());

        System.out.println("Вы успешно добавлены в систему, теперь можете выбрать свой тариф");

        System.out.println("1)Разовое посещение, цена 1000р: \n" +
                            "2)Месячный абонимент, цена 2100р\n" +
                            "3)Студенческий, цена 1500р");
        int tar = sc.nextInt();


        switch (tar){
            case 1:
                sub = new Subscription(client, Tarif.DISPOSABLE, LocalDate.now(),(LocalDate.now().plusDays(1)));

                client.getHistory().add("\nОформление одноразового посещения, пользователя " + nameUser + " " + LastNameUser + ". " + LocalDate.now());
                System.out.println("Вы успешно купили разовое посещение");
                break;




            case 2:
                sub = new Subscription(client, Tarif.MONTHLY, LocalDate.now(),(LocalDate.now().plusMonths(1)));

                client.getHistory().add("\nОформлен месячного абонимент, пользователя " + nameUser + " " + LastNameUser + ". " + LocalDate.now());

                System.out.println("Вы успешно купили месячный абонимент\n"
                + "Ваш абонимент действителен с " + LocalDate.now() + " и до " + LocalDate.now().plusMonths(1));
                break;
            case 3:
                sub = new Subscription(client, Tarif.STUDENT, LocalDate.now(),(LocalDate.now().plusMonths(1)));

                client.getHistory().add("\nОформлен студентческий абонимент, пользователя " + nameUser + " " + LastNameUser + ". " + LocalDate.now());

                System.out.println("Вы успешно купили месячный абонимент\n"
                        + "Ваш абонимент действителен с " + LocalDate.now() + " и до " + LocalDate.now().plusMonths(1));

                break;
        }


        while (true) {
            System.out.println("\nВыберите действие\n"
                    + "1) Отменить подписку\n"
                    + "2) Заморозить подписку\n"
                    + "3) Сменить тариф\n"
                    + "4) Посмотреть историю\n"
                    + "5) Проверить проход в зал\n"
                    + "0) Выход");

            int d = sc.nextInt();
            if (d == 0) break;

            try {
                switch (d) {
                    case 1:
                        if (sub == null) {
                            System.out.println("Подписки не обнаружено");
                            break;
                        }

                        if(sub.tariffEnd(LocalDate.now().toEpochDay(),sub.getEndSubs().toEpochDay())){
                            System.out.println("Срок подпсиски подошел к концу");
                            sub = null;
                            break;
                        }

                        BigDecimal returnMoney = sub.calculateRefundAmount();
                        client.setBalance(client.getBalance().add(returnMoney));

                        client.getHistory().add("\nОтмена подписки, пользователя " + nameUser + " " + LastNameUser + ". " + LocalDate.now());
                        System.out.println("Подписка успешно отменена! Возвращено " + returnMoney + " руб. Баланс: " + client.getBalance());

                        sub = null;
                        break;

                    case 2:
                        if (sub == null) {
                            System.out.println("Подписки не обнаружено");
                            break;
                        }

                        sub.applyFreeze(5);

                        client.getHistory().add("Заморозка подписки на 5 дней. Дата окончания сдвинута на: " + sub.getEndSubs() + ". Дата: " + LocalDate.now());
                        System.out.println("Подписка успешно замороженна до " + sub.getEndSubs() + "! Срок окончания сдвинут на " + sub.getEndSubs());
                        break;

                    case 3:
                        if (sub == null) {
                            System.out.println("Подписки не обнаружено");
                            break;
                        }


                        if (sub.tariffEnd(LocalDate.now().toEpochDay(), sub.getEndSubs().toEpochDay())) {
                            throw new SubscriptionExpiredException("Срок подписки истек, сменить тариф нельзя.");
                        }

                        System.out.println("Выберите новый тариф: 1) Разовый 2) Месячный 3) Студенческий");
                        int newTarIndex = sc.nextInt();
                        Tarif newTarif = switch (newTarIndex) {
                            case 1 -> Tarif.DISPOSABLE;
                            case 2 -> Tarif.MONTHLY;
                            case 3 -> Tarif.STUDENT;
                            default -> null;
                        };


                        if (newTarif == null) {
                            System.out.println("Неверный тариф");
                            break;
                        }


                        BigDecimal diff = sub.calculChangeTariff(newTarif);


                        if (diff.compareTo(BigDecimal.ZERO) > 0) {
                            if (client.getBalance().compareTo(diff) < 0) throw new InsufficientFundsException("Недостаточно средств для доплаты: " + diff + " руб.");
                            client.setBalance(client.getBalance().subtract(diff));
                        } else {
                            client.setBalance(client.getBalance().add(diff.negate()));
                        }


                        sub.setTarif(newTarif);
                        client.getHistory().add("Смена тарифа на " + newTarif.getTitle() + ". Взаимозачет: " + diff + " руб. Дата: " + LocalDate.now());
                        System.out.println("Тариф изменен на " + newTarif.getTitle() + ". Баланс: " + client.getBalance());
                        break;


                    case 4:
                        System.out.println("\n--------ИСТОРИЯ КЛИЕНТА--------");
                        for (String record : client.getHistory()) {
                            System.out.println(record);
                        }
                        System.out.println("-----------------------");
                        break;

                    case 5:
                        if (sub == null) throw new SubscriptionExpiredException("У вас нет активной подписки!");

                        LocalDate checkDate = LocalDate.now();
                        if (checkDate.isAfter(sub.getEndSubs())) {
                            throw new SubscriptionExpiredException("Срок действия подписки истек " + sub.getEndSubs());
                        }
                        if (sub.isFrozenOn(checkDate)) {
                            throw new SubscriptionFrozenException("Доступ заблокирован: подписка сейчас находится в заморозке!");
                        }

                        client.getHistory().add("Успешное посещение зала. Дата: " + checkDate);
                        System.out.println("Проход разрешен! Приятной тренировки.");
                        break;
                }
            } catch (InsufficientFundsException | SubscriptionExpiredException | SubscriptionFrozenException e) {
                System.out.println("\n[ОТКАЗ В ОПЕРАЦИИ]: " + e.getMessage());
            }
        }










    }
}