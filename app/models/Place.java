package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Place extends Model{

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
	
	@ManyToOne
	public RestaurantSection section;
	
	public Place(String title,String description,String image,RestaurantSection section){
		this.title=title;
		this.description=description;
		this.image=image;
		this.section=section;
	}
	
	public static Finder<Integer,Place> find=new Finder<Integer, Place>(Integer.class, Place.class);
	
	
	 public static List<Place> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String title,String description,String image,RestaurantSection section){
		Place newPlace=new Place(title, description,image,section);
		newPlace.save();
	}
	
	public static void update(int id,String title,String description,String image){
		Place table=Place.find.ref(id);
		System.out.println("id:"+id);
		table.title=title;
		table.description=description;
		table.image=image;
		table.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}