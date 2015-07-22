package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import models.Enums.UnitTypes;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class MenuItem extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long id;
	
	@Required
	public String title;
	
	public String short_title;
	
	@Column(columnDefinition="TEXT")
	public String description;
	
	public int price_original;
	public int price_for_sale;
	
	public String image;
	
	public boolean status;
	
	public int cook_time;
	
	@Column(columnDefinition="TEXT")
	public String prep_instruction;
	
	@ManyToOne
	public Category category;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('кг','л','штук')")
	public UnitTypes unit;
	
	
	
	public MenuItem(String title,String short_title,String description,Category category,int price_original, int price_for_sale, String image,int cook_time,String prep_instruction,UnitTypes unit){
		this.title=title;
		this.short_title=short_title;
		this.description=description;
		this.category=category;
		this.price_original=price_original;
		this.price_for_sale=price_for_sale;
		this.image=image;
		this.cook_time=cook_time;
		this.prep_instruction=prep_instruction;
		this.status=false;
		this.unit=unit;
	}
	
	public static Finder<Long,MenuItem> find=new Finder<Long, MenuItem>(Long.class, MenuItem.class);
	
	
	 public static List<MenuItem> findAll() {
	        return find.all();
	    }
	 
	public static List<MenuItem> findByCategory(int category_id){
		return find.where().eq("category.id", category_id).findList();
	}
	
	public static boolean isActive(Long item){
		return find.where().eq("id", item).eq("status", true).findRowCount()>0;
	}
	
	public static void create(String title,String short_title,String description,Category category,int price_original, int price_for_sale,String image,int cook_time,String prep_instruction,UnitTypes unit){
		MenuItem newMenuItem=new MenuItem(title,short_title, description,category,price_original,price_for_sale,image,cook_time,prep_instruction,unit);
		newMenuItem.save();
	}
	
	public static void update(Long id,String title,String short_title,String description,int price_original, int price_for_sale,String image,int cook_time,String prep_instruction,boolean status,UnitTypes unit){
		MenuItem item=MenuItem.find.ref(id);
		System.out.println("id:"+id);
		item.title=title;
		item.short_title=short_title;
		item.description=description;
		item.price_original=price_original;
		item.price_for_sale=price_for_sale;
		item.image=image;
		item.cook_time=cook_time;
		item.prep_instruction=prep_instruction;
		item.status=status;
		item.unit=unit;
		item.update();
	
	}
	public static void delete(Long id){
		find.ref(id).delete();
	}
}