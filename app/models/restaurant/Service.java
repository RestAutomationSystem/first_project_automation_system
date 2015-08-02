package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Service extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	
	@Required
	public String title;
	
	@Column(columnDefinition="TEXT")
	public String description;
	
	public String status;
	
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date start_time;
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date end_time;

    @ManyToOne
    public Restaurant restaurant;
	
	
	public Service(String title,String description,String status,Date start_time,Date end_time,Restaurant restaurant){
		this.title=title;
		this.description=description;
		this.status=status;
		this.start_time=start_time;
		this.end_time=end_time;
        this.restaurant=restaurant;
	}
	
	public static Finder<Integer,Service> find=new Finder<Integer, Service>(Integer.class, Service.class);
	
	
	 public static List<Service> findAll() {
	        return find.all();
	    }

    public static List<Service> findByRestaurant(int id) {
        return find.where().eq("restaurant.id", id).findList();
    }
	 
	
	public static int create(String title,String description,String status,Date start_time,Date end_time,Restaurant restaurant){
		Service newService=new Service(title, description,status,start_time,end_time,restaurant);
		newService.save();
        return newService.id;
	}
	
	public static void update(int id,String title,String description,String status,Date start_time,Date end_time){
		Service service=Service.find.ref(id);
		System.out.println("id:"+id);
		service.title=title;
		service.description=description;
		service.status=status;
        service.start_time=start_time;
        service.end_time=end_time;
		service.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}