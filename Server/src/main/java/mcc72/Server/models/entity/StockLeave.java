package mcc72.Server.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_stock_leave")
public class StockLeave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", length = 6)
    private Integer id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Employee employee;

    @Column (nullable = false)
    private Integer stock_available;
}
