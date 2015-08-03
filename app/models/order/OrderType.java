package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import models.*;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

import play.data.validation.Constraints.Required;

@Entity
public class OrderType extends Model {

    @Id
    @Version
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;

    @Required
    public String title;

    @Column(columnDefinition="TEXT")
    public String description;

    public boolean status;

    public OrderType(String title, String description, boolean status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public static Finder<Integer, OrderType> find = new Finder<>(Integer.class, OrderType.class);

    public static List<OrderType> findAll() {
        return find.all();
    }


    public static int create(String title,String description,boolean status){
        OrderType newUnitType=new OrderType(title, description,status);
        newUnitType.save();
        return  newUnitType.id;
    }

    public static void update(int id, String title, String description, boolean status) {
        OrderType orderType = OrderType.find.ref(id);
        System.out.println("id:" + id);
        orderType.title = title;
        orderType.description = description;
        orderType.status = status;
        orderType.update();
    }

    public static void delete(int id){
        find.ref(id).delete();
    }
}
