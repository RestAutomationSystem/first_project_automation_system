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
public class PaymentType extends Model {

    @Id
    @Version
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;

    @Required
    public String title;

    @Column(columnDefinition="TEXT")
    public String description;


    public boolean status;

    public PaymentType(String title, String description, boolean status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public static Finder<Integer, PaymentType> find = new Finder<>(Integer.class, PaymentType.class);

    public static List<PaymentType> findAll() {
        return find.all();
    }

    public static int create(String title,String description,boolean status){
        PaymentType newUnitType=new PaymentType(title, description,status);
        newUnitType.save();
        return  newUnitType.id;
    }

    public static void update(int id, String title, String description, boolean status) {
        PaymentType paymentType = PaymentType.find.ref(id);
        System.out.println("id:" + id);
        paymentType.title = title;
        paymentType.description = description;
        paymentType.status = status;
        paymentType.update();
    }

    public static void delete(int id){
        find.ref(id).delete();
    }
}
