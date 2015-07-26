package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Kitchen extends Model{

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
	
	
	@Formats.DateTime(pattern="H:i")
	public Date start_time;
	@Formats.DateTime(pattern="H:i")
	public Date end_time;	
	
	
	@ManyToOne
	public Restaurant restaurant;
	
	public Kitchen(String title,String description,String image,Restaurant restaurant,Date start_time,Date end_time){
		this.title=title;
		this.description=description;
		this.image=image;
		this.restaurant=restaurant;
		this.start_time=start_time;
		this.end_time=end_time;
	}
	
	public static Finder<Integer,Kitchen> find=new Finder<Integer, Kitchen>(Integer.class, Kitchen.class);
	
	
	 public static List<Kitchen> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String title,String description,String image,Restaurant restaurant,Date start_time,Date end_time){
		Kitchen newKitchen=new Kitchen(title, description,image,restaurant,start_time,end_time);
		newKitchen.save();
	}
	
	public static void update(int id,String title,String description,String image,Date start_time,Date end_time){
		Kitchen kitchen=Kitchen.find.ref(id);
		System.out.println("id:"+id);
		kitchen.title=title;
		kitchen.description=description;
		kitchen.image=image;
		kitchen.start_time=start_time;
		kitchen.end_time=end_time;
		kitchen.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}