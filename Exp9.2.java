package com.example;

import jakarta.persistence.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Entity
@Table(name = "students")
class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

    @Override
    public String toString() {
        return "Student{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
}

public class MainApp {

    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        Session session = null;

        try {
            // CREATE
            session = factory.getCurrentSession();
            Student newStudent = new Student("Alice", 22);
            session.beginTransaction();
            session.save(newStudent);
            session.getTransaction().commit();
            System.out.println("Inserted: " + newStudent);

            // READ
            session = factory.getCurrentSession();
            session.beginTransaction();
            Student fetchedStudent = session.get(Student.class, newStudent.getId());
            session.getTransaction().commit();
            System.out.println("Fetched: " + fetchedStudent);

            // UPDATE
            session = factory.getCurrentSession();
            session.beginTransaction();
            fetchedStudent.setAge(23);
            session.update(fetchedStudent);
            session.getTransaction().commit();
            System.out.println("Updated: " + fetchedStudent);

            // DELETE
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.delete(fetchedStudent);
            session.getTransaction().commit();
            System.out.println("Deleted student with ID: " + fetchedStudent.getId());

        } finally {
            factory.close();
        }
    }
}

