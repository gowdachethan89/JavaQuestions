package com.alti.account;

import java.util.*;

enum TransactionType {
    CREDIT,
    DEBIT
}

class Account {
    int accountId;
    String ownerName;

    Account(int accountId, String ownerName) {
        this.accountId = accountId;
        this.ownerName = ownerName;
    }
}

class Transaction {
    int transactionId;
    int accountId;
    TransactionType type;
    double amount;     // Always positive in inputs
    long timestampSec; // Unix-style seconds (monotonic for tests)

    Transaction(int transactionId, int accountId, TransactionType type, double amount, long timestampSec) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestampSec = timestampSec;
    }
}

public class AccountManager {
    Map<Integer, Account> accounts = new HashMap<>();
    List<Transaction> transactions = new ArrayList<>();

    void addAccount(Account account) {
        accounts.put(account.accountId, account);
    }

    void addTransaction(Transaction tx) {
        // Assume input transactions always refer to valid accounts for this question.
        transactions.add(tx);
    }

    // Returns the current balance for the given accountId.
    double getBalance(int accountId) {
        double balance = 0.0;
        for (Transaction tx : transactions) {
            if (tx.accountId == accountId) {
                if (tx.type == TransactionType.CREDIT) {
                    balance += tx.amount;
                }else {
                    balance -= tx.amount;
                }
            }
        }
        return balance;
    }

    Map<Integer, Double> getAverageTransactionAmountByAccount() {
        Map<Integer, Double> sumAccount = new HashMap<>();
        Map<Integer, Integer> countTransactions = new HashMap<>();

        for(Transaction transaction: transactions) {
            Double amount = Math.abs(transaction.amount);
            int accountId = transaction.accountId;
            if(sumAccount.containsKey(accountId)){
                sumAccount.put(accountId, sumAccount.get(accountId) + amount);
                countTransactions.put(accountId, countTransactions.get(accountId) + 1);
            } else {
                sumAccount.put(accountId, amount);
                countTransactions.put(accountId, 1);
            }
        }
        Map<Integer, Double> averages = new HashMap<>();
        for(Map.Entry<Integer, Double> entry: sumAccount.entrySet()) {
            int accountId = entry.getKey();
            averages.put(accountId, sumAccount.get(accountId)/countTransactions.get(accountId));
        }
        return averages;
    }
}