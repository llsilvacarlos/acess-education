package br.com.portal.education.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "tutoring")
public class Tutoring extends BaseEntity<Long> {
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_turing")
    private Long id;
    
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_start")
    private Calendar dataStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_end")
    private Calendar dataEnd;
    
    
    @ManyToOne
    @JoinColumn(name = "id_discipline")
    private Discipline discipline;
    
    
    @ManyToMany
    @JoinTable(name = "tutoring_users", joinColumns = @JoinColumn(name = "id_turing"), inverseJoinColumns = @JoinColumn(name = "id_user"))
    private List<User> users;
    
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_tutoring_status", nullable = false)
    private TutoringStatus tutoringStatus;


    public Long getId() {
        return id;
    }


    public Discipline getDiscipline() {
        return discipline;
    }


    public List<User> getUsers() {
        return users;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }


    public void setUsers(List<User> users) {
        this.users = users;
    }


    public Calendar getDataStart() {
        return dataStart;
    }


    public Calendar getDataEnd() {
        return dataEnd;
    }


    public void setDataStart(Calendar dataStart) {
        this.dataStart = dataStart;
    }


    public void setDataEnd(Calendar dataEnd) {
        this.dataEnd = dataEnd;
    }

}
