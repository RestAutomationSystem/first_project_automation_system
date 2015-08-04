package models;

import java.util.*;

import javax.persistence.*;
import play.data.format.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class PriceListHasModificator extends Model{

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
    public Modificator modificator;


    public PriceListHasModificator(String title,String description,boolean status,Date start_time,Date end_time,double price,Modificator modificator){
        this.title=title;
        this.description=description;
        this.status=status;
        this.start_time=start_time;
        this.end_time=end_time;
        this.price=price;
        this.modificator=modificator;
    }


    public static Finder<Integer,PriceListHasModificator> find=new Finder<Integer, PriceListHasModificator>(Integer.class, PriceListHasModificator.class);


    public static List<PriceListHasModificator> findAll() {
        return find.all();
    }

    public static List<PriceListHasModificator> findByModificator(int id) {
        return find.where().eq("modificator.id", id).findList();
    }



    public static int create(String title,String description,boolean status,Date start_time,Date end_time,double price,Modificator modificator){
        PriceListHasModificator newPriceListHasModificator=new PriceListHasModificator(title, description,status,start_time,end_time,price,modificator);
        newPriceListHasModificator.save();
        return newPriceListHasModificator.id;
    }


    public static void update(int id,String title,String description,boolean status,Date start_time,Date end_time,double price){
        PriceListHasModificator priceListHasProduct=PriceListHasModificator.find.ref(id);
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