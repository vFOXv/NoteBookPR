package my.ua.model;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_topic")
    private String name;

    @ManyToMany(mappedBy = "topics")
    private List<Note> notes;

    public Topic(){ }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }


    @Override
    public String toString() {
        return  name + "\n";
    }
}
