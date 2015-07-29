package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Modificator extends Model{

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
    @ManyToOne
	public UnitType unit_type;
    @ManyToOne
    public Item item;
	
	public Modificator(String title,String description,String status,String image_path,double price_original, double price_for_sale,Date start_time,Date end_time,UnitType unit_type,Item item){
		this.title=title;
		this.description=description;
		this.status=status;
		this.image_path=image_path;
		this.price_original=price_original;
		this.price_for_sale=price_for_sale;
		
		this.start_time=start_time;
		this.end_time=end_time;
		this.unit_type=unit_type;
        this.item=item;
	}
	
	public static Finder<Integer,Modificator> find=new Finder<Integer, Modificator>(Integer.class, Modificator.class);
	
	
	 public static List<Modificator> findAll() {
	        return find.all();
	    }

    public static List<Modificator> findByItem(int id) {
        return find.where().eq("item.id", id).findList();
    }
	
	public static void create(String title,String description,String status,String image_path,double price_original, double price_for_sale,Date start_time,Date end_time,UnitType unit_type,Item item){
		Modificator newModificator=new Modificator(title, description,status,image_path,price_original,price_for_sale,start_time,end_time,unit_type,item);
		newModificator.save();
	}
	
	public static void update(int id,String title,String description,String status,String image_path,double price_original, double price_for_sale,Date start_time,Date end_time,UnitType unit_type){
		Modificator modificator=Modificator.find.ref(id);
		System.out.println("id:"+id);
		modificator.title=title;
		modificator.description=description;
		modificator.status=status;
		modificator.image_path=image_path;
		modificator.price_original=price_original;
		modificator.price_for_sale=price_for_sale;
		modificator.unit_type=unit_type;
        modificator.start_time=start_time;
        modificator.end_time=end_time;
		modificator.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}
}