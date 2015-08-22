package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Event extends Model{

    @Id
    @Version
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;

    public String event_type;

    @Column(columnDefinition="TEXT")
    public String description;

    public String old_value;
    public String new_value;

    @Formats.DateTime(pattern="dd/MM/yyyy HH:mm:ss")
    public Date event_time;

    @ManyToOne
    public User author;

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
}