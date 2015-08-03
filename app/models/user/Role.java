package models;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import models.*;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="role_table")
public class Role extends Model {

    @Id
    @Version
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;

    @Required
    @Column(columnDefinition="TEXT")
    public String title;

    @Column(columnDefinition="TEXT")
    public String description;

    @Formats.DateTime(pattern="dd/MM/yyyy/H:i")
    public Date start_time;

    @Formats.DateTime(pattern="dd/MM/yyyy/H:i")
    public Date end_time;

    public boolean status;

    public Role(String title, String description, boolean status, Date start_time, Date end_time) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public static Model.Finder<Integer, Role> find = new Model.Finder<>(Integer.class, Role.class);

    public static List<Role> findAll() {
        return find.all();
    }

    public static int create(String title, String description, boolean status, Date start_time, Date end_time){
        Role newRole=new Role(title, description, status,start_time,end_time);

        newRole.save();
        return newRole.id;
    }

    public static void update(int id, String title, String description, boolean status,  Date start_time,Date end_time) {
        Role role = Role.find.ref(id);
        System.out.println("id:" + id);
        role.title = title;
        role.description = description;
        role.status = status;
        role.start_time = start_time;
        role.end_time = end_time;
        role.update();
    }

    public static void delete(int id){
        find.ref(id).delete();
    }
}
