package models.user;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Session extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;

	@Required
	public String name;

	@Column(columnDefinition="TEXT")
	public String opened;

    @Column(columnDefinition="TEXT")
    public String closed;

    public boolean status;


    /*//public Smeny(String name, Time opened,Time closed){
		this.name=name;
	}
	*/
	public static Finder<Long, Session> find=new Finder<Long, Session>(Long.class, Session.class);

    public Session(String name, String opened, Boolean status) {
        this.name = name;
        this.opened = opened;
        this.status = status;
    }


    public static List<Session> findAll() {
	        return find.all();
	    }
	 
	
	public static Long open(String name, String opened,Boolean status){

        Session newsmena = new Session(name,opened,status);
newsmena.save();
        System.out.println(newsmena.id + "d");
        return newsmena.id;
    }
	
	public static Session update(Long id, String closed,Boolean status){
		Session restaurant= Session.find.ref(id);

		restaurant.closed=closed;
        restaurant.status=status;
		//restaurant.description=description;
		restaurant.update();


        return restaurant;
    }
	public static void delete(Long id){
		find.ref(id).delete();
	}
}