import javax.swing.text.TabExpander;
import java.math.BigDecimal;
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

        client = new Client(nameUser,LastNameUser, BigDecimal.valueOf(1000));

        System.out.println("Вы успешно добавлены в систему, теперь можете выбрать свой тариф");

        System.out.println("1)Разовое посещение, цена 1000р: \n" +
                "2)Месячный абонимент, цена 2100р\n" +
                "3)Студенческий, цена 1500р");
        int tar = sc.nextInt();
        

        switch (tar){
            case 1:
                sub = new Subscription(client, Tarif.DISPOSABLE, LocalDate.now(),(LocalDate.now().plusDays(1)));
                System.out.println("Вы успешно купили разовое посещение");
                break;

            case 2:
                sub = new Subscription(client, Tarif.MONTHLY, LocalDate.now(),(LocalDate.now().plusMonths(1)));
                System.out.println("Вы успешно купили месячный абонимент\n"
                + "Ваш абонимент действителен с " + LocalDate.now() + " и до " + LocalDate.now().plusMonths(1));
                break;
            case 3:
                sub = new Subscription(client, Tarif.STUDENT, LocalDate.now(),(LocalDate.now().plusMonths(1)));
                System.out.println("Вы успешно купили месячный абонимент\n"
                        + "Ваш абонимент действителен с " + LocalDate.now() + " и до " + LocalDate.now().plusMonths(1));
                break;
        }


        while (true) {

        }









    }
}