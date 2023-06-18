package com.bence.mate;

import com.bence.mate.entity.TransactionHistory;

public class App {
    public static void main(String[] args) throws Exception {
        TransactionHistory sangeeta = new TransactionHistory(1L, 15331, "Sangeeta", "Credit", 10000);
        TransactionHistory mohit = new TransactionHistory(3L,19031, "Mohit", "Debit", 2000);
        TransactionHistory neha = new TransactionHistory(2L, 14531, "Neha", "Credit", 7000);
        TransactionHistory josh = new TransactionHistory(4L, 25389, "Josh", "Debit", 9000);

        Hibernate<TransactionHistory> hibernate = Hibernate.getConnection();

        TransactionHistory obj1 = hibernate.read(TransactionHistory.class, 1L);

        hibernate.write(sangeeta);
        hibernate.write(mohit);
        hibernate.write(neha);
        hibernate.write(josh);

        System.out.println(obj1);
    }
}
