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
public class OrderItem extends Model {

    @Id
    @Version
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition="TEXT")
    private String description;

    private int amount;
    private double price;

    private boolean status;
    @ManyToOne
    private Item item;
    @ManyToOne
    private Order order;



    public OrderItem(String description, boolean status,int amount,double price, Item item, Order order) {
        this.description = description;
        this.status = status;
        this.amount=amount;
        this.price=price;
        this.item=item;
        this.order=order;
    }

    public static Finder<Long, OrderItem> find = new Finder<>(Long.class, OrderItem.class);

    public List<OrderItem> findAll() {
        return find.all();
    }

    public static Long create(String description,boolean status,int amount,double price, Item item, Order order){
        OrderItem orderItem=new OrderItem(description,status,amount,price,item,order);
        orderItem.save();
        return  orderItem.id;
    }

    public void update(Long id, String description, boolean status,int amount,double price, Item item, Order order) {
        OrderItem orderItem = OrderItem.find.ref(id);
        orderItem.description = description;
        orderItem.status = status;
        orderItem.amount=amount;
        orderItem.price=price;
        orderItem.item=item;
        orderItem.order=order;
        orderItem.update();
    }

    public void delete(Long id){
        find.ref(id).delete();
    }
}
