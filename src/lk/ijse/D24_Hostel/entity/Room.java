package lk.ijse.D24_Hostel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Room {
    @Id
    private String room_type_id;
    private String type;
    private Double key_money;
    private String qty;

    @OneToMany(mappedBy = "room_type_id", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Reserve> roomDetails = new ArrayList<>();

    public Room(String room_type_id, String type, Double key_money, String qty) {
        this.room_type_id = room_type_id;
        this.type = type;
        this.key_money = key_money;
        this.qty = qty;
    }
}
