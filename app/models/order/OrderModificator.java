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
public class OrderModificator extends Model {

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
    private Modificator modificator;
    @ManyToOne
    private Order order;



    public OrderModificator(String description, boolean status,int amount,double price, Modificator modificator, Order order) {
        this.description = description;
        this.status = status;
        this.amount=amount;
        this.price=price;
        this.modificator=modificator;
        this.order=order;
    }

    public static Finder<Long, OrderModificator> find = new Finder<>(Long.class, OrderModificator.class);

    public List<OrderModificator> findAll() {
        return find.all();
    }

    public static Long create(String description,boolean status,int amount,double price, Modificator modificator, Order order){
        OrderModificator orderModificator=new OrderModificator(description,status,amount,price,modificator,order);
        orderModificator.save();
        return  orderModificator.id;
    }

    public void update(Long id, String description, boolean status,int amount,double price, Modificator modificator, Order order) {
        OrderModificator orderModificator = OrderModificator.find.ref(id);
        orderModificator.description = description;
        orderModificator.status = status;
        orderModificator.amount=amount;
        orderModificator.price=price;
        orderModificator.modificator=modificator;
        orderModificator.order=order;
        orderModificator.update();
    }

    public void delete(Long id){
        find.ref(id).delete();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Modificator getModificator() {
        return modificator;
    }

    public void setModificator(Modificator modificator) {
        this.modificator = modificator;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
