package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Category extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	@Required
	public String name;
	
	@Column(columnDefinition="TEXT")
	public String description;
	
	@ManyToOne
	public Menu menu;
	
	@OneToOne()
    @JoinColumn(name="parent_category")
    public Category parent_category;
	
	
	public Category(String name,String description,Menu menu,Category parent_category){
		this.name=name;
		this.description=description;
		this.menu=menu;
		this.parent_category=parent_category;
	}
	
	public static Finder<Long,Category> find=new Finder<Long, Category>(Long.class, Category.class);
	
	
	 public static List<Category> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String name,String description,Menu menu,Category parent_category){
		Category newCategory=new Category(name, description,menu,parent_category);
		newCategory.save();
	}
	
	public static void update(Long id,String name,String description,Category parent_category){
		Category restaurant=Category.find.ref(id);
		System.out.println("id:"+id);
		restaurant.name=name;
		restaurant.parent_category=parent_category;
		restaurant.description=description;
		restaurant.update();
		
		
	}
	public static void delete(Long id){
		find.ref(id).delete();
	}
}