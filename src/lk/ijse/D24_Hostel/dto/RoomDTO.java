package lk.ijse.D24_Hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private String room_type_id;
    private String type;
    private Double key_money;
    private String qty;

    public RoomDTO(String qty) {
    }
}
