package lk.ijse.D24_Hostel.view.assets.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentTM {
    private String student_id;
    private String name;
    private String address;
    private String contact;
    private LocalDate dob;
    private String gender;

}
