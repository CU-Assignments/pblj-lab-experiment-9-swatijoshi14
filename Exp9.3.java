package com.example.banking;

import jakarta.persistence.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.*;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String owner;
    private double balance;

    public Account() {}
    public Account(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public int getId() { return id; }
    public String getOwner() { return owner; }
    public double getBalance() { return balance; }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String toString() {
        return "Account{id=" + id + ", owner='" + owner + "', balance=" + balance + '}';
    }
}

@Entity
@Table(name = "transactions")
class TransferRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int fromAccount;
    private int toAccount;
    private double amount;
    private Date timestamp;

    public TransferRecord() {}
    public TransferRecord(int from, int to, double amount) {
        this.fromAccount = from;
        this.toAccount = to;
        this.amount = amount;
        this.timestamp = new Date();
    }

    public String toString() {
        return "Transfer{id=" + id + ", from=" + fromAccount + ", to=" + toAccount +
                ", amount=" + amount + ", time=" + timestamp + '}';
    }
}

interface BankService {
    void transferMoney(int fromId, int toId, double amount);
    void showAccounts();
}

class BankServiceImpl implements BankService {

    private final SessionFactory sessionFactory;

    public BankServiceImpl(SessionFactory factory) {
        this.sessionFactory = factory;
    }

    @Override
    @Transactional
    public void transferMoney(int fromId, int toId, double amount) {
        var session = sessionFactory.getCurrentSession();

        Account from = session.get(Account.class, fromId);
        Account to = session.get(Account.class, toId);

        if (from == null || to == null) throw new RuntimeException("Invalid account ID(s)");

        if (from.getBalance() < amount) throw new RuntimeException("Insufficient funds");

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() + amount);

        session.persist(new TransferRecord(fromId, toId, amount));
    }

    @Override
    @Transactional(readOnly = true)
    public void showAccounts() {
        var session = sessionFactory.getCurrentSession();
        List<Account> accounts = session.createQuery("from Account", Account.class).list();
        accounts.forEach(System.out::println);
    }
}

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.example.banking")
class AppConfig {

    @Bean
    public SessionFactory sessionFactory() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Account.class)
                .addAnnotatedClass(TransferRecord.class)
                .buildSessionFactory();
    }

    @Bean
    public BankService bankService() {
        return new BankServiceImpl(sessionFactory());
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        return new HibernateTransactionManager(sessionFactory());
    }
}
package com.example.banking;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);
        var service = context.getBean(BankService.class);

        // Create accounts (run once)
        var session = context.getBean("sessionFactory", org.hibernate.SessionFactory.class).openSession();
        session.beginTransaction();
        session.save(new Account("Alice", 1000));
        session.save(new Account("Bob", 500));
        session.getTransaction().commit();
        session.close();

        System.out.println("Initial state:");
        service.showAccounts();

        System.out.println("\n--- Performing successful transfer ---");
        try {
            service.transferMoney(1, 2, 200);
        } catch (Exception e) {
            System.out.println("Failed: " + e.getMessage());
        }
        service.showAccounts();

        System.out.println("\n--- Performing failed transfer (insufficient funds) ---");
        try {
            service.transferMoney(1, 2, 5000); // should fail
        } catch (Exception e) {
            System.out.println("Failed: " + e.getMessage());
        }
        service.showAccounts();

        context.close();
    }
}

