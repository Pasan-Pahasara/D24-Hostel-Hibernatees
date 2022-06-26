package lk.ijse.D24_Hostel.dto;

import lk.ijse.D24_Hostel.entity.Room;
import lk.ijse.D24_Hostel.entity.Student;
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
public class ReserveDTO {
    private String res_id;
    private LocalDate date;
    private String student_id;
    private String room_type_id;
    private Double room_fee;
    private Double advance;
    private String status;
//
//    public ReserveDTO(String res_id, LocalDate date, Student student_id, Room room_type_id, String status) {
//
//    }
}
