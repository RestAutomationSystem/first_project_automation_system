package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Menu extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	@Required
	public String name;
	
	@Column(columnDefinition="TEXT")
	public String description;
	
	
	
	
	public Menu(String name,String description){
		this.name=name;
		this.description=description;
	}
	
	public static Finder<Long,Menu> find=new Finder<Long, Menu>(Long.class, Menu.class);
	
	
	 public static List<Menu> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String name,String description){
		Menu newMenu=new Menu(name, description);
		newMenu.save();
	}
	
	public static void update(Long id,String name,String description){
		Menu restaurant=Menu.find.ref(id);
		System.out.println("id:"+id);
		restaurant.name=name;
		restaurant.description=description;
		restaurant.update();
		
		
	}
	public static void delete(Long id){
		find.ref(id).delete();
	}
}