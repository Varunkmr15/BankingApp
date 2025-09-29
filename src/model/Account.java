package model;

import java.math.BigDecimal;

public class Account {
    private int id;
    private int userId;
    private String accountNumber;
    private BigDecimal balance;

    //getter
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getAccountNumber() { return accountNumber; }
    public BigDecimal getBalance() { return balance; }

    //setter
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

}
