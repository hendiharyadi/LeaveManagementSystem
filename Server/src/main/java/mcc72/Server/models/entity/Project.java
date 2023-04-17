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
@Table(name = "tb_project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manager")
    private Employee manager;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String start_project;

    @Column(nullable = false)
    private String end_project;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "tb_employee_project",
            joinColumns = @JoinColumn(
                    name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "employee_id", referencedColumnName = "id")
            )
    private List<Employee> employeeProject;

    @JsonIgnore
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    @Column(nullable = true)
    private List<Overtime> overtimes;
}
