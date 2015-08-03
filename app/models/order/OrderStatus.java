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
public class OrderStatus extends Model {

    @Id
    @Version
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;

    @Required
    public String title;

    @Column(columnDefinition="TEXT")
    public String description;


    public boolean status;

    public OrderStatus(String title, String description, boolean status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public static Finder<Integer, OrderStatus> find = new Finder<>(Integer.class, OrderStatus.class);

    public static List<OrderStatus> findAll() {
        return find.all();
    }


    public static int create(String title,String description,boolean status){
        OrderStatus orderStatus=new OrderStatus(title, description,status);
        orderStatus.save();
        return  orderStatus.id;
    }

    public static void update(int id, String title, String description, boolean status) {
        OrderStatus orderStatus = OrderStatus.find.ref(id);
        System.out.println("id:" + id);
        orderStatus.title = title;
        orderStatus.description = description;
        orderStatus.status = status;
        orderStatus.update();
    }

    public static void delete(int id){
        find.ref(id).delete();
    }
}
