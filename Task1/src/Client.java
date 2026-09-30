import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class Client {
    private final String name;
    private final String surname;
    private BigDecimal balance;

    private List<String> history = new ArrayList<>();



    public Client(String name,String surname ,BigDecimal balance){
        this.name = name;
        this.surname = surname;
        this.balance = balance;
    }




    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getFullName(){
        return surname + " " + name;
    }

    public BigDecimal getBalance(){
        return balance;
    }

    public List<String> getHistory(){
        return history;
    }


    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }
}
