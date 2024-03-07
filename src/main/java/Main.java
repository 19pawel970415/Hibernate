import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        Person person = new Person("Anna", "Pickle", 14);
        Person person1 = new Person("John", "Pickle", 12);
        Person person2 = new Person("JAmes", "Pickle", 10);
        Person person3 = new Person("Adam", "Pickle", 34);
        Person person4 = new Person("Anna", "Smith", 34);
        session.save(person);
        session.save(person1);
        session.save(person2);
        session.save(person3);
        session.save(person4);

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

        transaction.commit();

        session.close();
        sessionFactory.close();
    }
}
