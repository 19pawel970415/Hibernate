package medicalCentre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appointments")
@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;

    @ManyToOne
    @JoinColumn(name = "patientId")
    @ToString.Exclude
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctorId")
    @ToString.Exclude
    private Doctor doctor;

}
