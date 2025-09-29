package ui;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import service.BankingService;

import dao.AccountDao;
import model.Account;
import model.TransactionRecord;
import model.User;

public class ConsoleUI {
    private Scanner sc = new Scanner(System.in);

    public void userMenu(User u) throws Exception {
        while (true) {
            System.out.println("\n=== User Menu ===");
            System.out.println("1. View Profile\n2. Create Account\n3. Deposit\n4. Withdraw\n5. Transfer\n6. Check Balance\n7. Transaction History\n8. Logout");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            if (choice.equals("1")) {
                System.out.println("Username: " + u.getUsername());
                System.out.println("Full Name: " + u.getFullName());
                System.out.println("Email: " + u.getEmail());
                System.out.println("Phone: " + u.getPhone());
            } else if (choice.equals("2")) {
                System.out.print("Enter new account number: ");
                String accNum = sc.nextLine();
                Account acc = BankingService.createAccountForUser(u.getId(), accNum);
                System.out.println("Created account: " + acc.getAccountNumber());
            } else if (choice.equals("3")) {
                System.out.print("Account number: "); String accNum = sc.nextLine();
                Account acc = AccountDao.findByAccountNumber(accNum);
                System.out.print("Amount: "); BigDecimal amt = new BigDecimal(sc.nextLine());
                BankingService.deposit(acc, amt, "Deposit");
                System.out.println("Deposit successful.");
            } else if (choice.equals("4")) {
                System.out.print("Account number: "); String accNum = sc.nextLine();
                Account acc = AccountDao.findByAccountNumber(accNum);
                System.out.print("Amount: "); BigDecimal amt = new BigDecimal(sc.nextLine());
                if (BankingService.withdraw(acc, amt, "Withdraw")) System.out.println("Withdraw successful.");
                else System.out.println("Insufficient balance.");
            } else if (choice.equals("5")) {
                System.out.print("From account: "); String fromAccNum = sc.nextLine();
                System.out.print("To account: "); String toAccNum = sc.nextLine();
                System.out.print("Amount: "); BigDecimal amt = new BigDecimal(sc.nextLine());
                Account fromAcc = AccountDao.findByAccountNumber(fromAccNum);
                Account toAcc = AccountDao.findByAccountNumber(toAccNum);
                if (BankingService.transfer(fromAcc, toAcc, amt, "Transfer")) System.out.println("Transfer successful.");
                else System.out.println("Transfer failed.");
            } else if (choice.equals("6")) {
                System.out.print("Account number: "); String accNum = sc.nextLine();
                Account acc = AccountDao.findByAccountNumber(accNum);
                System.out.println("Balance: " + acc.getBalance());
            } else if (choice.equals("7")) {
                System.out.print("Account number: "); String accNum = sc.nextLine();
                Account acc = AccountDao.findByAccountNumber(accNum);
                List<TransactionRecord> history = BankingService.getHistory(acc);
                for (TransactionRecord t : history) {
                    System.out.println(t.getTimestamp() + " - " + t.getType() + " - " + t.getAmount() + " - " + t.getRemarks());
                }
            } else if (choice.equals("8")) {
                break;
            }
        }
    }
}

