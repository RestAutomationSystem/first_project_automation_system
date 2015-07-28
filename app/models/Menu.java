package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Menu extends Model{

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

    @ManyToMany(cascade=CascadeType.REMOVE)
    public List<Category> categories=new ArrayList<Category>();
	
	
	public Menu(String title,String description,String status,Date start_time,Date end_time){
		this.title=title;
		this.description=description;
		this.status=status;
		this.start_time=start_time;
		this.end_time=end_time;
	}
	
	public static Finder<Integer,Menu> find=new Finder<Integer, Menu>(Integer.class, Menu.class);
	
	
	 public static List<Menu> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String title,String description,String status,Date start_time,Date end_time){
		Menu newMenu=new Menu(title, description,status,start_time,end_time);
		newMenu.save();
	}
	
	public static void update(int id,String title,String description,String status,Date start_time,Date end_time){
		Menu menu=Menu.find.ref(id);
		System.out.println("id:"+id);
		menu.title=title;
		menu.description=description;
		menu.status=status;
        menu.start_time=start_time;
        menu.end_time=end_time;
		menu.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}