package models;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;
import java.util.List;

@Entity
public class Role extends Model {

    @Required
    @Column(columnDefinition="TEXT")
    private String title;

    @Column(columnDefinition="TEXT")
    private String description;

    @Formats.DateTime(pattern="dd/MM/yyyy/H:i")
    private Date start_time;

    @Formats.DateTime(pattern="dd/MM/yyyy/H:i")
    private Date end_time;

    private Boolean status;

    public Role(String title, String description, Boolean status, Date start_time, Date end_time) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public static Model.Finder<Long, Role> find = new Model.Finder<>(Long.class, Role.class);

    public List<Role> findAll() {
        return find.all();
    }

    public void update(Long id, String title, String description, Boolean status, Date end_time) {
        Role role = Role.find.ref(id);
        System.out.println("id:" + id);
        role.title = title;
        role.description = description;
        role.status = status;
        role.end_time = end_time;
        role.update();
    }

    public void delete(Long id){
        find.ref(id).delete();
    }
}
