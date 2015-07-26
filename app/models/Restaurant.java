package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Restaurant extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	
	@Required
	public String title;
	
	@Column(columnDefinition="TEXT")
	public String description;
	
	public String status;
	
	@Formats.DateTime(pattern="H:i")
	public Date start_time;
	
	@Formats.DateTime(pattern="H:i")
	public Date end_time;	
	
	
	public Restaurant(String title,String description,String status,Date start_time,Date end_time){
		this.title=title;
		this.description=description;
		this.status=status;
		this.start_time=start_time;
		this.end_time=end_time;
	}
	
	public static Finder<Integer,Restaurant> find=new Finder<Integer, Restaurant>(Integer.class, Restaurant.class);
	
	
	 public static List<Restaurant> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String title,String description,String status,Date start_time,Date end_time){
		Restaurant newRestaurant=new Restaurant(title, description,status,start_time,end_time);
		newRestaurant.save();
	}
	
	public static void update(int id,String title,String description,String status,Date start_time,Date end_time){
		Restaurant restaurant=Restaurant.find.ref(id);
		System.out.println("id:"+id);
		restaurant.title=title;
		restaurant.description=description;
		restaurant.status=status;
		
		restaurant.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}