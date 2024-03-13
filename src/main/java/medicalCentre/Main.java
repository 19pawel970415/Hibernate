package medicalCentre;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        SessionFactory sessionFactory = new Configuration()
                .configure()
                .buildSessionFactory();

        MedicalService medicalService = new MedicalService(sessionFactory);

        medicalService.setDataInDb();
        List<Appointment> allAppointmentsOfPatient = medicalService.getAllAppointmentsOfPatient("97050535928");

        for (Appointment appointment : allAppointmentsOfPatient) {
            System.out.println(appointment);
        }

        List<Appointment> allAppointmentsOfDoctor = medicalService.getAllAppointmentsOfDoctor("12345");

        for (Appointment appointment : allAppointmentsOfDoctor) {
            System.out.println(appointment);
        }

        List<Doctor> allDoctorsBeing = medicalService.getAllDoctorsBeing(SpecialityType.INTERNISTA);

        for (Doctor doctor : allDoctorsBeing) {
            System.out.println(doctor);
        }

        Address address = new Address();
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        medicalService.makeAnAppointment(patient, doctor, LocalDateTime.now().plusMonths(1), LocalDateTime.now().plusMonths(2));

        Address patientsAddress = medicalService.getPatientsAddress("97050535928");

        System.out.println(patientsAddress);

        sessionFactory.close();
    }
}
