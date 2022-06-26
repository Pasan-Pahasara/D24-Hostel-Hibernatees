package lk.ijse.D24_Hostel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LoginDTO {
    @Id
    private String user_id;
    private String name;
    private String address;
    private String contact;
    private String password;
    private String gender;
}
