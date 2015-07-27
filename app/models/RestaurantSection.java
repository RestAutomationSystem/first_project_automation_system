package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class RestaurantSection extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	
	@Required
	public String title;
	
	@Column(columnDefinition="TEXT")
	public String description;
	
	public String image;
	public String status;
	
	
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date start_time;
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date end_time;	
	
	@ManyToOne
	public Restaurant restaurant;
	
	public RestaurantSection(String title,String description,String image,Restaurant restaurant,Date start_time,Date end_time){
		this.title=title;
		this.description=description;
		this.image=image;
		this.restaurant=restaurant;
		this.start_time=start_time;
		this.end_time=end_time;
	}
	
	public static Finder<Integer,RestaurantSection> find=new Finder<Integer, RestaurantSection>(Integer.class, RestaurantSection.class);
	
	
	 public static List<RestaurantSection> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String title,String description,String image,Restaurant restaurant,Date start_time,Date end_time){
		RestaurantSection newRestaurantSection=new RestaurantSection(title, description,image,restaurant,start_time,end_time);
		newRestaurantSection.save();
	}
	
	public static void update(int id,String title,String description,String image,Date start_time,Date end_time){
		RestaurantSection section=RestaurantSection.find.ref(id);
		System.out.println("id:"+id);
		section.title=title;
		section.description=description;
		section.image=image;
		section.start_time=start_time;
		section.end_time=end_time;
		section.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}