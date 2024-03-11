import entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class MainInh {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        try {
            ParentInh parent = new ParentInh("University of California", new ArrayList<>());
            parent.setFirstName("Adam");
            parent.setLastName("Pickle");
            parent.setAge(34);

            ParentInh parent1 = new ParentInh("University of California", new ArrayList<>());
            parent1.setFirstName("Sophia");
            parent1.setLastName("Angelo");
            parent1.setAge(28);


            ChildInh child = new ChildInh("LA High School", new ArrayList<>());
            child.setFirstName("Anna");
            child.setLastName("Pickle");
            child.setAge(14);

            ChildInh child1 = new ChildInh("LA Junior High School", new ArrayList<>());
            child1.setFirstName("John");
            child1.setLastName("Pickle");
            child1.setAge(12);

            ChildInh child2 = new ChildInh("LA Junior High School", new ArrayList<>());
            child2.setFirstName("James");
            child2.setLastName("Pickle");
            child2.setAge(10);

            List<ParentInh> parents = new ArrayList<>();
            List<ChildInh> children = new ArrayList<>();
            parents.add(parent);
            parents.add(parent1);
            children.add(child);
            children.add(child1);
            children.add(child2);
            child.setParents(parents);
            child1.setParents(parents);
            child2.setParents(parents);
            parent.setChildren(children);
            parent1.setChildren(children);

            ClientInh client = new ClientInh(new ArrayList<>());
            client.setFirstName("Anna");
            client.setLastName("Smith");
            client.setAge(34);

            OrderId orderId = new OrderId('A', 1);
            OrderInh order = new OrderInh(orderId, "qwerty123", client);
            List<OrderInh> orders = new ArrayList<>();
            orders.add(order);
            client.setOrders(orders);

            OrderId orderId1 = new OrderId('B', 2);
            OrderInh order1 = new OrderInh(orderId1, "qwerty123456", client);
            orders.add(order1);
            client.setOrders(orders);

            session.save(parent);
            session.save(parent1);
            session.save(child);
            session.save(child1);
            session.save(child2);
            session.save(client);
            session.save(order);
            session.save(order1);

            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            // Close the session and session factory
            if (session != null) {
                session.close();
            }
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }
}
