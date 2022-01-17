package my.ua.model;

import javax.persistence.*;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "this_date")
    private Date thisDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "note_topic",
        joinColumns = @JoinColumn(name = "note_id"),
        inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private List<Topic> topics;

    @Column(name = "my_text")
    private String myText;

    public Note() {    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getThisDate() {
        return thisDate;
    }

    public void setThisDate(Date thisDate) {
        this.thisDate = thisDate;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public String getMyText() {
        return myText;
    }

    public void setMyText(String myText) {
        this.myText = myText;
    }

    //метод отсекает часы и минуты у даты и форматирует Date in String
    public String changeThisDateInString() {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd");
        return formatForDateNow.format(thisDate);
    }
    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", thisDate=" + thisDate +
                ", topics=" + topics +
                ", myText='" + myText + '\'' +
                '}' + "\n";
    }
}
