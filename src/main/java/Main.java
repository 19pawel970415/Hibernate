import entity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        // Saving a new Person entity
        session.save(new Person(1, "Paul", "Smith", 24));

        transaction.commit(); // Committing the transaction

        Person person = session.get(Person.class, 1);

        System.out.println(person);

        session.close();
        sessionFactory.close();
    }
}
