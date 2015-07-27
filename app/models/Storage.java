package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Storage extends Model{

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
	
	public Storage(String title,String description,String image,Restaurant restaurant,Date start_time,Date end_time){
		this.title=title;
		this.description=description;
		this.image=image;
		this.restaurant=restaurant;
		this.start_time=start_time;
		this.end_time=end_time;
	}
	
	public static Finder<Integer,Storage> find=new Finder<Integer, Storage>(Integer.class, Storage.class);
	
	
	 public static List<Storage> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String title,String description,String image,Restaurant restaurant,Date start_time,Date end_time){
		Storage newStorage=new Storage(title, description,image,restaurant,start_time,end_time);
		newStorage.save();
	}
	
	public static void update(int id,String title,String description,String image,Date start_time,Date end_time){
		Storage storage=Storage.find.ref(id);
		System.out.println("id:"+id);
		storage.title=title;
		storage.description=description;
		storage.image=image;
		storage.start_time=start_time;
		storage.end_time=end_time;
		storage.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}