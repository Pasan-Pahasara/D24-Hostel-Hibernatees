package lk.ijse.D24_Hostel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.Cache;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Student {
    @Id
    private String student_id;
    private String name;
    private String address;
    private String contact;
    private LocalDate dob;
    private String gender;

    @OneToMany(mappedBy = "student_id", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Reserve> studentList = new ArrayList();

    public Student(String student_id, String name, String address, String contact, LocalDate dob, String gender) {
        this.student_id = student_id;
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.dob = dob;
        this.gender = gender;
    }
}
