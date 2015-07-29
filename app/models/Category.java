package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Category extends Model{

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

    @OneToOne
    public Category parent_category;

    @ManyToOne
    public Menu menu;
	
	public Category(String title,String description,String status,Date start_time,Date end_time,Category parent_category,Menu menu){
        this.title=title;
        this.description=description;
        this.status=status;
        this.start_time=start_time;
        this.end_time=end_time;
        this.parent_category=parent_category;
        this.menu=menu;
    }

	
	public static Finder<Integer,Category> find=new Finder<Integer, Category>(Integer.class, Category.class);
	
	
	 public static List<Category> findAll() {
	        return find.all();
	    }

    public static List<Category> findByMenu(int id) {
        return find.where().eq("menu.id", id).eq("parent_category", null).findList();
    }

    public static List<Category> findByCategory(int id) {
        return find.where().eq("parent_category.id", id).findList();
    }
	
	public static void create(String title,String description,String status,Date start_time,Date end_time,Category parent_category,Menu menu){
		Category newCategory=new Category(title, description,status,start_time,end_time,parent_category,menu);
		newCategory.save();
	}

	
	public static void update(int id,String title,String description,String status,Date start_time,Date end_time){
		Category category=Category.find.ref(id);
		System.out.println("id:"+id);
		category.title=title;
		category.description=description;
		category.status=status;
        category.start_time=start_time;
        category.end_time=end_time;
		category.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}