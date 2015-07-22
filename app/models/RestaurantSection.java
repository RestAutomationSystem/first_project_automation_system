package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class RestaurantSection extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	@Required
	public String name;
	
	@Column(columnDefinition="TEXT")
	public String description;
	
	public String image;
	
	
	public RestaurantSection(String name,String description,String image){
		this.name=name;
		this.description=description;
		this.image=image;
	}
	
	public static Finder<Long,RestaurantSection> find=new Finder<Long, RestaurantSection>(Long.class, RestaurantSection.class);
	
	
	 public static List<RestaurantSection> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String name,String description,String image){
		RestaurantSection newRestaurantSection=new RestaurantSection(name, description,image);
		newRestaurantSection.save();
	}
	
	public static void update(Long id,String name,String description,String image){
		RestaurantSection restaurant=RestaurantSection.find.ref(id);
		System.out.println("id:"+id);
		restaurant.name=name;
		restaurant.description=description;
		restaurant.image=image;
		restaurant.update();
		
		
	}
	public static void delete(Long id){
		find.ref(id).delete();
	}
}