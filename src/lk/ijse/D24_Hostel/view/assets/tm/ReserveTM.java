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
public class ReserveTM {
    private String res_id;
    private LocalDate date;
    private String student_id;
    private String room_type_id;
    private Double room_fee;
    private Double advance;
    private String status;

}
