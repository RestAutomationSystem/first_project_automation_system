package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class UnitType extends Model{

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
	
	public String amount;
	
	
	public UnitType(String title,String description,String status,Date start_time,Date end_time,String amount){
		this.title=title;
		this.description=description;
		this.status=status;
		this.start_time=start_time;
		this.end_time=end_time;
		this.amount=amount;
	}
	
	public static Finder<Integer,UnitType> find=new Finder<Integer, UnitType>(Integer.class, UnitType.class);
	
	
	 public static List<UnitType> findAll() {
	        return find.all();
	    }
	 
	
	public static int create(String title,String description,String status,Date start_time,Date end_time,String amount){
		UnitType newUnitType=new UnitType(title, description,status,start_time,end_time,amount);
		newUnitType.save();
        return  newUnitType.id;
	}
	
	public static void update(int id,String title,String description,String status,Date start_time,Date end_time,String amount){
		UnitType unit_type=UnitType.find.ref(id);
		System.out.println("id:"+id);
		unit_type.title=title;
		unit_type.description=description;
		unit_type.status=status;
		unit_type.amount=amount;
		unit_type.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}