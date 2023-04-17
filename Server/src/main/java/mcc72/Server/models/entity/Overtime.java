package mcc72.Server.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_overtime")
public class Overtime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String note;

    @Column(nullable = false)
    private String start_overtime;

    @Column(nullable = false)
    private String end_overtime;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @JsonIgnore
    @OneToMany(mappedBy = "overtime", cascade = CascadeType.ALL)
    private List<HistoryOvertime> historyOvertime;

    @ManyToOne
    @JoinColumn (name = "employee")
    private Employee employee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn (name = "manager")
    private Employee manager;

    @ManyToOne
    @JoinColumn (name = "project")
    private Project project;
}
