package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Food extends Model{

	@Id
	@Version
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Required
	private String title;
	
	@Column(columnDefinition="TEXT")
	private String description;
	
	private String image;
	private String status;
	private double price;
	
	
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	private Date start_time;
	@Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
	private Date end_time;	
	
	
	@ManyToOne
    private Storage storage;

    private Supplier supplier;
	
	public Food(String title,String description,String image,Storage storage,Date start_time,Date end_time,double price,Supplier supplier){
		this.title=title;
		this.description=description;
		this.image=image;
		this.storage=storage;
		this.start_time=start_time;
		this.end_time=end_time;
		this.supplier=supplier;
	}
	
	public static Finder<Integer,Food> find=new Finder<Integer, Food>(Integer.class, Food.class);
	
	
	 public static List<Food> findAll() {
	        return find.all();
	    }
	 
	
	public static void create(String title,String description,String image,Storage storage,Date start_time,Date end_time,double price,Supplier supplier){
		Food newFood=new Food(title, description,image,storage,start_time,end_time,price,supplier);
		newFood.save();
	}
	
	public static void update(int id,String title,String description,String image,Date start_time,Date end_time,double price,Supplier supplier){
		Food food=Food.find.ref(id);
		System.out.println("id:"+id);
		food.title=title;
		food.description=description;
		food.image=image;
		food.start_time=start_time;
		food.end_time=end_time;
		food.price=price;
		
		food.supplier=supplier;
		food.update();
		
		
	}
	public static void delete(int id){
		find.ref(id).delete();
	}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}