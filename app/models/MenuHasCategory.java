package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class MenuHasCategory extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int id;
	
	public String status;
	
	@Formats.DateTime(pattern="H:i")
	public Date start_time;
	@Formats.DateTime(pattern="H:i")
	public Date end_time;	
	
	public Menu menu;
	public Category category;
	
	
	public MenuHasCategory(Menu menu,Category category,String status,Date start_time,Date end_time){
		this.menu=menu;
		this.category=category;
		this.status=status;
		this.start_time=start_time;
		this.end_time=end_time;
	}
	
	public static Finder<Integer,MenuHasCategory> find=new Finder<Integer, MenuHasCategory>(Integer.class, MenuHasCategory.class);
	
	
	 public static List<MenuHasCategory> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(Menu menu,Category category,String status,Date start_time,Date end_time){
		MenuHasCategory newMenuHasCategory=new MenuHasCategory( menu,category,status,start_time,end_time);
		newMenuHasCategory.save();
	}
	
	public static void update(int id,Menu menu,Category category,String status,Date start_time,Date end_time){
		MenuHasCategory obj=MenuHasCategory.find.ref(id);
		System.out.println("id:"+id);
		obj.menu=menu;
		obj.category=category;
		obj.status=status;
		
		obj.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}