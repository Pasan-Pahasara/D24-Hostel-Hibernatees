package lk.ijse.D24_Hostel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * @author : Pasan Pahasara
 * @since : 0.1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Reserve {
    @Id
    private String res_id;
    private LocalDate date;
    @ManyToOne
    private Student student_id;
    @ManyToOne
    private Room room_type_id;
    private Double room_fee;
    private Double advance;
    private String status;

    public Reserve(String res_id, LocalDate date, Student student_id, Room room_type_id) {
    }
}
