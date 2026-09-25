import java.math.BigDecimal;


public class Client {
    private final String Name;
    private final String Surname;
    private BigDecimal balance;



    public Client(String name,String surname ,BigDecimal balance){
        this.Name = name;
        this.Surname = surname;
        this.balance = balance;
    }


    public String getName(){
        return Name;
    }


    public String getSurname(){
        return Surname;
    }

    public String getFullName(){
        return Surname + " " + Name;
    }

    public BigDecimal getBalance(){
        return balance;
    }


    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }
}
