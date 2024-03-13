package medicalCentre;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class MedicalService {

    /*
    Stworzyć klasę service.MedicalService, a w niej publiczne metody:
    0. Metoda dodająca dane
    1. Metoda przyjmująca w argumencie String pesel i zwracająca typ List<Appointment>, która dla pacjenta o danym peselu zwróci jego wszystkie przyszłe wizyty.
    2. Metoda przyjmująca w argumencie String identificator i zwracająca typ List<Appointment>, która dla lekarza o danym identyfikatorze zwróci jego wszystkie przyszłe wizyty.
    3. Metoda przyjmująca w argumencie SpecialityType type i zwracająca List<Doctor>, która dla podanej w argumencie specjalności zwróci wszystkich lekarzy.
    4. Metoda przyjmująca w argumentach Patient patient, Doctor doctor, LocalDateTime dateFrom, LocalDateTime dateTo zapisująca w bazie wizytę dla pacjenta do danego doktora na dany przedział godzinowy. Metoda ma zwrócić obiekt Appointment.
    5. Metoda przyjmująca w argumencie String pesel i zwracająca typ Address, która dla pacjenta o danym peselu zwróci jego adres.
     */

    public SessionFactory sessionFactory;

    //0
    public void setDataInDb() {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Patient patient1 = new Patient(null, "Adam", "Smith", "97050535928", LocalDate.of(1997, 05, 05), null, null);
            Patient patient2 = new Patient(null, "Jordan", "Brake", "88020298321", LocalDate.of(1988, 02, 02), null, null);
            Patient patient3 = new Patient(null, "John", "Kowalski", "75030210928", LocalDate.of(1975, 03, 02), null, null);

            Address address1 = new Address(null, "LA", "45th", "10", null);
            Address address2 = new Address(null, "NYC", "15th", "20", null);
            Address address3 = new Address(null, "San Francisco", "25th", "30", null);

            patient1.setAddress(address1);
            patient2.setAddress(address2);
            patient3.setAddress(address3);

            address1.setPatient(patient1);
            address2.setPatient(patient2);
            address3.setPatient(patient3);

            Appointment appointment1 = new Appointment(null, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), patient1, null);
            Appointment appointment2 = new Appointment(null, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(7), patient2, null);
            Appointment appointment3 = new Appointment(null, LocalDateTime.now().plusDays(23), LocalDateTime.now(), patient3, null);
            Appointment appointment4 = new Appointment(null, LocalDateTime.now().plusDays(5), LocalDateTime.now().plusMonths(6), patient1, null);
            Appointment appointment5 = new Appointment(null, LocalDateTime.now().plusHours(8), LocalDateTime.now().plusYears(9), patient2, null);
            Appointment appointment6 = new Appointment(null, LocalDateTime.now(), LocalDateTime.now().plusHours(1), patient3, null);

            patient1.setAppointments(Arrays.asList(appointment1, appointment4));
            patient2.setAppointments(Arrays.asList(appointment2, appointment5));
            patient3.setAppointments(Arrays.asList(appointment3, appointment6));

            Doctor doctor1 = new Doctor(null, "Anna", "Crane", "12345", Arrays.asList(appointment1, appointment4), null);
            Doctor doctor2 = new Doctor(null, "Maria", "Ways", "23456", Arrays.asList(appointment2, appointment5), null);
            Doctor doctor3 = new Doctor(null, "Ian", "Pickle", "34567", Arrays.asList(appointment3, appointment6), null);

            appointment1.setDoctor(doctor1);
            appointment2.setDoctor(doctor2);
            appointment3.setDoctor(doctor3);
            appointment4.setDoctor(doctor1);
            appointment5.setDoctor(doctor2);
            appointment6.setDoctor(doctor3);

            Speciality speciality1 = new Speciality(null, SpecialityType.INTERNISTA, Arrays.asList(doctor1, doctor2, doctor3));
            Speciality speciality2 = new Speciality(null, SpecialityType.KARDIOLOG, Arrays.asList(doctor1, doctor2, doctor3));
            Speciality speciality3 = new Speciality(null, SpecialityType.ORTOPEDA, Arrays.asList(doctor1, doctor2, doctor3));

            doctor1.setSpecialities(Arrays.asList(speciality1, speciality2, speciality3));
            doctor2.setSpecialities(Arrays.asList(speciality1, speciality2, speciality3));
            doctor3.setSpecialities(Arrays.asList(speciality1, speciality2, speciality3));

            session.save(patient1);
            session.save(patient2);
            session.save(patient3);

            session.save(address1);
            session.save(address2);
            session.save(address3);

            session.save(appointment1);
            session.save(appointment2);
            session.save(appointment3);
            session.save(appointment4);
            session.save(appointment5);
            session.save(appointment6);

            session.save(doctor1);
            session.save(doctor2);
            session.save(doctor3);

            session.save(speciality1);
            session.save(speciality2);
            session.save(speciality3);

            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    //1
    public List<Appointment> getAllAppointmentsOfPatient(String pesel) {

        List<Appointment> appointmentsToReturn = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Patient patient = session.createQuery(
                    "select p from Patient p join fetch p.appointments where p.pesel = :inputPesel",
                    Patient.class)
                    .setParameter("inputPesel", pesel)
                    .uniqueResult();

            transaction.commit();

            if (patient != null) {
                for (Appointment appointment : patient.getAppointments()) {
                    if (appointment.getDateFrom().isAfter(LocalDateTime.now())) {
                        appointmentsToReturn.add(appointment);
                    }
                }
                return appointmentsToReturn;
            } else {
                System.out.println("No patient with such pesel found.");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return appointmentsToReturn;
    }

    //2
    public List<Appointment> getAllAppointmentsOfDoctor(String identifier) {

        List<Appointment> appointmentsToReturn = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Doctor doctor = (Doctor) session.createQuery("from Doctor d where d.identifier = :inputIdentifier")
                    .setParameter("inputIdentifier", identifier)
                    .getSingleResult();


            transaction.commit();

            if (doctor != null) {
                for (Appointment appointment : doctor.getAppointments()) {
                    if (appointment.getDateFrom().isAfter(LocalDateTime.now())) {
                        appointmentsToReturn.add(appointment);
                    }
                }
                return appointmentsToReturn;
            } else {
                System.out.println("No doctor with such identifier found.");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return appointmentsToReturn;
    }

    //3
    public List<Doctor> getAllDoctorsBeing(SpecialityType type) {

        List<Doctor> doctorsToReturn = new ArrayList<>();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Speciality speciality = session.createQuery("from Speciality s where s.name = :inputSpecialty", Speciality.class)
                    .setParameter("inputSpecialty", type)
                    .getSingleResult();

            List<Doctor> doctors = speciality.getDoctors();

            transaction.commit();

            if (doctors.size() > 0) {
                for (Doctor doctor : doctors) {
                    doctorsToReturn.add(doctor);
                }
                return doctorsToReturn;
            } else {
                System.out.println("No doctor with such speciality found.");
                return Collections.emptyList();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return doctorsToReturn;
    }

    //4
    public Appointment makeAnAppointment(Patient patient, Doctor doctor, LocalDateTime dadeFrom, LocalDateTime dateTo) {
        Appointment appointmentToReturn = new Appointment();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Appointment appointmentToSave = new Appointment(null, dadeFrom, dateTo, patient, doctor);
            session.save(appointmentToSave);
            appointmentToReturn = appointmentToSave;
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return appointmentToReturn;
    }

    //5
    public Address getPatientsAddress(String pesel) {

        Address addressToReturn = new Address();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            Patient patient = (Patient) session.createQuery("from Patient p where p.pesel = :inputPesel")
                    .setParameter("inputPesel", pesel)
                    .getSingleResult();


            transaction.commit();

            if (patient != null) {
                addressToReturn = patient.getAddress();
            } else {
                System.out.println("No patient with such pesel found.");
                return new Address();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return addressToReturn;
    }
}
