package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Supplier extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	
	@Required
	public String title;
	
	@Column(columnDefinition="TEXT")
	public String description;
	
	public String status;	
	
	
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date start_time;
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	public Date end_time;	
	
	
	public Supplier(String title,String description,String status,Date start_time,Date end_time){
		this.title=title;
		this.description=description;
		this.status=status;
		this.start_time=start_time;
		this.end_time=end_time;
		
	}
	
	public static Finder<Integer,Supplier> find=new Finder<Integer, Supplier>(Integer.class, Supplier.class);
	
	
	 public static List<Supplier> findAll() {
	        return find.all();
	    }
	 
	
	public static int create(String title,String description,String status,Date start_time,Date end_time){
		Supplier newSupplier=new Supplier(title, description,status,start_time,end_time);
		newSupplier.save();
        return newSupplier.id;
	}
	
	public static void update(int id,String title,String description,String status,Date start_time,Date end_time){
		Supplier supplier=Supplier.find.ref(id);
		System.out.println("id:"+id);
		supplier.title=title;
		supplier.description=description;
		supplier.status=status;
        supplier.start_time=start_time;
        supplier.end_time=end_time;
		supplier.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}