import entity.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

public class MainJPA {
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myPersistenceUnit");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        transaction.begin();

        Person person = new Person("Anna", "Pickle", 14);
        Person person1 = new Person("John", "Pickle", 12);
        Person person2 = new Person("James", "Pickle", 10);
        Person person3 = new Person("Adam", "Pickle", 34);
        Person person4 = new Person("Anna", "Smith", 34);
        entityManager.persist(person);
        entityManager.persist(person1);
        entityManager.persist(person2);
        entityManager.persist(person3);
        entityManager.persist(person4);

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

        entityManager.persist(child);
        entityManager.persist(child1);
        entityManager.persist(child2);
        entityManager.persist(parent);
        entityManager.persist(parent1);

        Order order = new Order();
        Order order1 = new Order();
        Order order2 = new Order();
        OrderId orderId = new OrderId('A', 1);
        OrderId orderId1 = new OrderId('B', 2);
        OrderId orderId2 = new OrderId('C', 3);
        order.setOrderId(orderId);
        order1.setOrderId(orderId1);
        order2.setOrderId(orderId2);
        order.setOrderNumber("qwerty123456");
        order1.setOrderNumber("654321ytrewq");
        order2.setOrderNumber("sthnotimp");
        Client client = new Client();
        client.setPerson(person4);
        List<Order> orders = new ArrayList<>();
        orders.add(order);
        orders.add(order1);
        client.setOrders(orders);
        order.setClient(client);
        order1.setClient(client);

        entityManager.persist(order);
        entityManager.persist(order1);
        entityManager.persist(order2);
        entityManager.persist(client);

        OrderId orderIdToFind = new OrderId('C', 3);
        Order orderToPrint = entityManager.find(Order.class, orderIdToFind);
        if (orderToPrint != null) {
            System.out.println(orderToPrint);
        } else {
            System.out.println("Order not found");
        }

        person.setAge(16);
        entityManager.merge(person);

        entityManager.remove(order2);

        orderToPrint = entityManager.find(Order.class, orderIdToFind);
        if (orderToPrint != null) {
            System.out.println(orderToPrint);
        } else {
            System.out.println("Order not found");
        }

        Person personToPrint = entityManager.find(Person.class, 1);
        if (personToPrint != null) {
            System.out.println(personToPrint);
        } else {
            System.out.println("Person with ID 1 not found.");
        }

        Person personToPrint1 = entityManager.find(Person.class, 2);
        if (personToPrint1 != null) {
            System.out.println(personToPrint1);
        } else {
            System.out.println("Person with ID 2 not found.");
        }

        Person personToPrint2 = entityManager.find(Person.class, 3);
        if (personToPrint2 != null) {
            System.out.println(personToPrint2);
        } else {
            System.out.println("Person with ID 3 not found.");
        }

        Person personToPrint3 = entityManager.find(Person.class, 4);
        if (personToPrint3 != null) {
            System.out.println(personToPrint3);
        } else {
            System.out.println("Person with ID 4 not found.");
        }

        Person personToPrint4 = entityManager.find(Person.class, 5);
        if (personToPrint4 != null) {
            System.out.println(personToPrint4);
        } else {
            System.out.println("Person with ID 5 not found.");
        }



        transaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}

