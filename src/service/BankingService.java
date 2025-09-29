package service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import dao.AccountDao;
import dao.TransactionDao;
import model.Account;

public class BankingService {
    public static Account createAccountForUser(int userId, String accountNumber) throws SQLException {
        int accId = AccountDao.createAccount(userId, accountNumber);
        return AccountDao.findByAccountNumber(accountNumber);
    }

    public static boolean deposit(Account account, BigDecimal amount, String remarks) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) return false;
        BigDecimal newBal = account.getBalance().add(amount);
        boolean ok = AccountDao.updateBalance(account.getId(), newBal);
        if (ok) TransactionDao.createTransaction(account.getId(), "DEPOSIT", amount, remarks, null);
        return ok;
    }

    public static boolean withdraw(Account account, BigDecimal amount, String remarks) throws SQLException {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) return false;
        if (account.getBalance().compareTo(amount) < 0) return false;
        BigDecimal newBal = account.getBalance().subtract(amount);
        boolean ok = AccountDao.updateBalance(account.getId(), newBal);
        if (ok) TransactionDao.createTransaction(account.getId(), "WITHDRAWAL", amount, remarks, null);
        return ok;
    }

    public static boolean transfer(Account from, Account to, BigDecimal amount, String remarks) throws SQLException {
        if (from.getBalance().compareTo(amount) < 0) return false;
        // Note: For safety, transaction handling / rollback should be implemented with DB transactions.
        BigDecimal newFrom = from.getBalance().subtract(amount);
        BigDecimal newTo = to.getBalance().add(amount);
        boolean ok1 = AccountDao.updateBalance(from.getId(), newFrom);
        boolean ok2 = AccountDao.updateBalance(to.getId(), newTo);
        if (ok1 && ok2) {
            TransactionDao.createTransaction(from.getId(), "TRANSFER", amount.negate(), remarks, to.getAccountNumber());
            TransactionDao.createTransaction(to.getId(), "TRANSFER", amount, remarks, from.getAccountNumber());
            return true;
        }
        return false;
    }

    public static List<model.TransactionRecord> getHistory(Account account) throws SQLException {
        return TransactionDao.getTransactionsForAccount(account.getId());
    }
}