package my.ua.model;



import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;



@Entity
@Table(name = "topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_topic")
    @NotEmpty(message = "Name should not be empty!!!")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
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
