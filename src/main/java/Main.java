import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        Person person = new Person("Anna", "Pickle", 14);
        Person person1 = new Person("John", "Pickle", 12);
        Person person2 = new Person("James", "Pickle", 10);
        Person person3 = new Person("Adam", "Pickle", 34);
        Person person4 = new Person("Anna", "Smith", 34);
        Person person5 = new Person("Sophia", "Angelo", 28);
        session.save(person);
        session.save(person1);
        session.save(person2);
        session.save(person3);
        session.save(person4);
        session.save(person5);

        ArrayList<Parent> parents = new ArrayList<>();
        ArrayList<Child> children = new ArrayList<>();
        Child child = new Child("LA High School", parents, person);
        Child child1 = new Child("LA Junior High School", parents, person1);
        Child child2 = new Child("LA Junior High School", parents, person2);
        Parent parent = new Parent("University of California", children, person3);
        Parent parent1 = new Parent("University of California", children, person4);
        children.add(child);
        children.add(child1);
        children.add(child2);
        parent.setChildren(children);
        parent1.setChildren(children);
        parents.add(parent);
        parents.add(parent1);
        child.setParents(parents);
        child1.setParents(parents);
        child2.setParents(parents);

        session.save(child);
        session.save(child1);
        session.save(child2);
        session.save(parent);
        session.save(parent1);

        Order order = new Order();
        Order order1 = new Order();
        OrderId orderId = new OrderId('A', 1);
        OrderId orderId1 = new OrderId('B', 2);
        order.setOrderId(orderId);
        order1.setOrderId(orderId1);
        order.setOrderNumber("qwerty123456");
        order1.setOrderNumber("654321ytrewq");
        Client client = new Client();
        client.setPerson(person4);
        List<Order> orders = new ArrayList<Order>();
        orders.add(order);
        orders.add(order1);
        client.setOrders(orders);
        order.setClient(client);
        order1.setClient(client);

        session.save(order);
        session.save(order1);
        session.save(client);

        Parent parent1FromDB = session.get(Parent.class, 1);
        Parent parent2FromDB = session.get(Parent.class, 2);

        for (Child c : parent1FromDB.getChildren()) {
            System.out.println(c.getPerson().getFirstName() + " " + c.getPerson().getLastName());
            for (Parent p : c.getParents()) {
                System.out.println(p.getPerson().getFirstName() + " " + p.getPerson().getLastName());
            }
        }

        parent2FromDB.setPerson(person5);

        session.remove(person4);

        List<Person> adultsFromDB = session.createQuery("from Person", Person.class).getResultStream()
                .filter(p -> p.getAge() >= 18)
                .collect(Collectors.toList());

        for (Person adult : adultsFromDB) {
            System.out.println(adult);
        }

        System.out.println();
        System.out.println();

        List<String> resultList = session.createQuery("select p.firstName from Person p where p.age < 18 order by p.firstName", String.class).getResultList();

        for (String name : resultList) {
            System.out.println(name);
        }


        List<Object[]> lastNamesAverages = session.createQuery("select p.lastName, avg(age) from Person p group by p.lastName").getResultList();

        for (Object[] result : lastNamesAverages) {
            String lastName = (String) result[0];
            Double averageAge = (Double) result[1];
            System.out.println("Last Name: " + lastName + ", Average Age: " + averageAge);
        }

        List<Object[]> resultList1 = session.createQuery("select p.firstName, p.lastName from child c left join Person p on c.person.id = p.id where p.age >= 12").getResultList();

        for (Object[] result : resultList1) {
            String firstName = (String) result[0];
            String lastName = (String) result[1];
            System.out.println(firstName + " " + lastName);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name: ");
        String name = scanner.nextLine();
        System.out.println("Enter the surname: ");
        String surname = scanner.nextLine();
        try {
            Person p = session.createQuery("from Person p where p.firstName = :firstName and p.lastName = :lastName", Person.class)
                    .setParameter("firstName", name)
                    .setParameter("lastName", surname)
                    .getSingleResult();
            System.out.println(p);
        } catch (NoResultException nre) {
            System.err.println(nre.getMessage());
        }

        List<Person> peopleWithAgeGreaterThanAvg = session.createQuery("from Person p where p.age > (select avg(age) from Person)", Person.class).getResultList();

        for (Person pr : peopleWithAgeGreaterThanAvg) {
            System.out.println(pr);
        }


        transaction.commit();

        session.close();
        sessionFactory.close();
    }
}
