package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Item extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	
	@Required
	public String title;
	
	@Column(columnDefinition="TEXT")
	public String description;
	
	public String status;
	public String image_path;
	
	public double price_original;
	public double price_for_sale;
	
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date start_time;
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date end_time;	
	
	public UnitType unit_type;
	
	public Item(String title,String description,String status,String image_path,double price_original, double price_for_sale,Date start_time,Date end_time,UnitType unit_type){
		this.title=title;
		this.description=description;
		this.status=status;
		this.image_path=image_path;
		this.price_original=price_original;
		this.price_for_sale=price_for_sale;
		
		this.start_time=start_time;
		this.end_time=end_time;
		this.unit_type=unit_type;
	}
	
	public static Finder<Integer,Item> find=new Finder<Integer, Item>(Integer.class, Item.class);
	
	
	 public static List<Item> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String title,String description,String status,String image_path,double price_original, double price_for_sale,Date start_time,Date end_time,UnitType unit_type){
		Item newItem=new Item(title, description,status,image_path,price_original,price_for_sale,start_time,end_time,unit_type);
		newItem.save();
	}
	
	public static void update(int id,String title,String description,String status,String image_path,double price_original, double price_for_sale,Date start_time,Date end_time,UnitType unit_type){
		Item product=Item.find.ref(id);
		System.out.println("id:"+id);
		product.title=title;
		product.description=description;
		product.status=status;
		product.image_path=image_path;
		product.price_original=price_original;
		product.price_for_sale=price_for_sale;
		product.unit_type=unit_type;
		product.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}