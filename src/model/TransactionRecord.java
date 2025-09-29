package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRecord {
    private int id;
    private int accountId;
    private String type;
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String remarks;
    private String relatedAccount;

    //getter
    public int getId() { return id; }
    public int getAccountId() { return accountId; }
    public String getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getRemarks() { return remarks; }
    public String getRelatedAccount() { return relatedAccount; }

    //setter
    public void setId(int id) { this.id = id; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public void setType(String type) { this.type = type; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public void setRelatedAccount(String relatedAccount) { this.relatedAccount = relatedAccount; }
}
