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
	
	
	public Service(String title,String description,String status,Date start_time,Date end_time){
		this.title=title;
		this.description=description;
		this.status=status;
		this.start_time=start_time;
		this.end_time=end_time;
	}
	
	public static Finder<Integer,Service> find=new Finder<Integer, Service>(Integer.class, Service.class);
	
	
	 public static List<Service> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String title,String description,String status,Date start_time,Date end_time){
		Service newService=new Service(title, description,status,start_time,end_time);
		newService.save();
	}
	
	public static void update(int id,String title,String description,String status,Date start_time,Date end_time){
		Service service=Service.find.ref(id);
		System.out.println("id:"+id);
		service.title=title;
		service.description=description;
		service.status=status;
		
		service.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}