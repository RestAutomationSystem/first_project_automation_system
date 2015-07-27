package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class RestaurantHasService extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	
	public String status;
	
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date start_time;
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date end_time;	
	
	public Restaurant restaurant;
	public Service service;
	
	
	public RestaurantHasService(Restaurant restaurant,Service service,String status,Date start_time,Date end_time){
		this.restaurant=restaurant;
		this.service=service;
		this.status=status;
		this.start_time=start_time;
		this.end_time=end_time;
	}
	
	public static Finder<Integer,RestaurantHasService> find=new Finder<Integer, RestaurantHasService>(Integer.class, RestaurantHasService.class);
	
	
	 public static List<RestaurantHasService> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(Restaurant restaurant,Service service,String status,Date start_time,Date end_time){
		RestaurantHasService newRestaurantHasService=new RestaurantHasService(restaurant, service,status,start_time,end_time);
		newRestaurantHasService.save();
	}
	
	public static void update(int id,Restaurant restaurant,Service service,String status,Date start_time,Date end_time){
		RestaurantHasService obj=RestaurantHasService.find.ref(id);
		System.out.println("id:"+id);
		obj.restaurant=restaurant;
		obj.service=service;
		obj.status=status;
		
		obj.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}