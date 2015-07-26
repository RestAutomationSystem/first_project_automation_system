package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class ServiceHasMenu extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	
	public String status;
	
	@Formats.DateTime(pattern="H:i")
	public Date start_time;
	@Formats.DateTime(pattern="H:i")
	public Date end_time;	
	
	public Service service;
	public Menu menu;
	
	
	public ServiceHasMenu(Service service,Menu menu,String status,Date start_time,Date end_time){
		this.menu=menu;
		this.service=service;
		this.status=status;
		this.start_time=start_time;
		this.end_time=end_time;
	}
	
	public static Finder<Integer,ServiceHasMenu> find=new Finder<Integer, ServiceHasMenu>(Integer.class, ServiceHasMenu.class);
	
	
	 public static List<ServiceHasMenu> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(Service service,Menu menu,String status,Date start_time,Date end_time){
		ServiceHasMenu newServiceHasMenu=new ServiceHasMenu( service,menu,status,start_time,end_time);
		newServiceHasMenu.save();
	}
	

	public static void delete(int id){
		find.ref(id).delete();
	}
}