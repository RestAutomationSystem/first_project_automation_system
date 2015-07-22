package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Restaurant extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	@Required
	public String name;
	
	@Column(columnDefinition="TEXT")
	public String description;
	
	public String image;
	
	public String contact;
	
	@Column(columnDefinition="TEXT")
	public String address;
	
	
	public Restaurant(String name,String description,String image){
		this.name=name;
		this.description=description;
		this.image=image;
	}
	
	public static Finder<Long,Restaurant> find=new Finder<Long, Restaurant>(Long.class, Restaurant.class);
	
	
	 public static List<Restaurant> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String name,String description,String image){
		Restaurant newRestaurant=new Restaurant(name, description,image);
		newRestaurant.save();
	}
	
	public static void update(Long id,String name,String description){
		Restaurant restaurant=Restaurant.find.ref(id);
		System.out.println("id:"+id);
		restaurant.name=name;
		restaurant.description=description;
		restaurant.update();
		
		
	}
	public static void delete(Long id){
		find.ref(id).delete();
	}
}