package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class PriceListHasProduct extends Model{

    @Id
    @Version
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;

    public String title;

    @Column(columnDefinition="TEXT")
    public String description;

    public boolean status;

    @Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
    public Date start_time;

    @Formats.DateTime(pattern="dd/MM/yyyy HH:mm")
    public Date end_time;

    public double price;

    @ManyToOne
    public Item item;


    public PriceListHasProduct(String title,String description,boolean status,Date start_time,Date end_time,double price,Item item){
        this.title=title;
        this.description=description;
        this.status=status;
        this.start_time=start_time;
        this.end_time=end_time;
        this.price=price;
        this.item=item;
    }


    public static Finder<Integer,PriceListHasProduct> find=new Finder<Integer, PriceListHasProduct>(Integer.class, PriceListHasProduct.class);


    public static List<PriceListHasProduct> findAll() {
        return find.all();
    }

    public static List<PriceListHasProduct> findByItem(int id) {
        return find.where().eq("item.id", id).findList();
    }



    public static int create(String title,String description,boolean status,Date start_time,Date end_time,double price,Item item){
        PriceListHasProduct newPriceListHasProduct=new PriceListHasProduct(title, description,status,start_time,end_time,price,item);
        newPriceListHasProduct.save();
        return newPriceListHasProduct.id;
    }


    public static void update(int id,String title,String description,boolean status,Date start_time,Date end_time,double price){
        PriceListHasProduct priceListHasProduct=PriceListHasProduct.find.ref(id);
        System.out.println("id:"+id);
        priceListHasProduct.title=title;
        priceListHasProduct.description=description;
        priceListHasProduct.status=status;
        priceListHasProduct.start_time=start_time;
        priceListHasProduct.end_time=end_time;
        priceListHasProduct.price=price;
        priceListHasProduct.update();


    }
    public static void delete(int id){
        find.ref(id).delete();
    }
}