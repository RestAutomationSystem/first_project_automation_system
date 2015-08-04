package models;

import java.util.*;
import com.avaje.ebean.*;
import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Event extends Model{

    @Id
    @Version
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    private String event_type;

    @Column(columnDefinition="TEXT")
    private String description;

    private String old_value;
    private String new_value;

    @Formats.DateTime(pattern="dd/MM/yyyy HH:mm:ss")
    private Date event_time;

    @ManyToOne
    private User author;

    public Event(String event_type,String description,String old_value,String new_value,Date event_time,User author){
        this.event_type=event_type;
        this.description=description;
        this.old_value=old_value;
        this.new_value=new_value;
        this.event_time=event_time;
        this.author=author;
    }

    public static Finder<Integer,Event> find=new Finder<Integer, Event>(Integer.class, Event.class);


    public static List<Event> findAll() {
        return find.all();
    }

    public static Page<Event> page(int page, int pageSize, String sortBy, String order, String filter) {
        return
                find.where()
                        .ilike("event_type", "%" + filter + "%")
                        .orderBy(sortBy + " " + order)
                        .fetch("author")
                        .findPagingList(pageSize)
                        .setFetchAhead(false)
                        .getPage(page);
    }


    public static void create(String event_type,String description,String old_value,String new_value,Date event_time,User author){
        Event newEvent=new Event(event_type, description,old_value,new_value,event_time,author);
        newEvent.save();
    }

    public static void update(int id,String event_type,String description,String old_value,String new_value,Date event_time,User author){
        Event unit_type=Event.find.ref(id);
        System.out.println("id:"+id);
        unit_type.event_type=event_type;
        unit_type.description=description;
        unit_type.old_value=old_value;
        unit_type.author=author;
        unit_type.update();


    }
    public static void delete(int id){
        find.ref(id).delete();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOld_value() {
        return old_value;
    }

    public void setOld_value(String old_value) {
        this.old_value = old_value;
    }

    public String getNew_value() {
        return new_value;
    }

    public void setNew_value(String new_value) {
        this.new_value = new_value;
    }

    public Date getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Date event_time) {
        this.event_time = event_time;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}