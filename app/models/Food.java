package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Food extends Model{

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
	public double price;
	
	
	@Formats.DateTime(pattern="H:i")
	public Date start_time;
	@Formats.DateTime(pattern="H:i")
	public Date end_time;	
	
	
	@ManyToOne
	public Storage storage;
	
	public Supplier supplier;
	
	public Food(String title,String description,String image,Storage storage,Date start_time,Date end_time,double price,Supplier supplier){
		this.title=title;
		this.description=description;
		this.image=image;
		this.storage=storage;
		this.start_time=start_time;
		this.end_time=end_time;
		this.supplier=supplier;
	}
	
	public static Finder<Integer,Food> find=new Finder<Integer, Food>(Integer.class, Food.class);
	
	
	 public static List<Food> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String title,String description,String image,Storage storage,Date start_time,Date end_time,double price,Supplier supplier){
		Food newFood=new Food(title, description,image,storage,start_time,end_time,price,supplier);
		newFood.save();
	}
	
	public static void update(int id,String title,String description,String image,Storage storage,Date start_time,Date end_time,double price,Supplier supplier){
		Food food=Food.find.ref(id);
		System.out.println("id:"+id);
		food.title=title;
		food.description=description;
		food.image=image;
		food.start_time=start_time;
		food.end_time=end_time;
		food.price=price;
		
		food.supplier=supplier;
		food.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}