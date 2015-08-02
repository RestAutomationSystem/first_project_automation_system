package models;

import play.data.format.Formats;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import models.*;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class PaymentType extends Model {

    @Id
    @Version
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int id;

    @Constraints.Required
    @Column(columnDefinition="TEXT")
    private String title;

    @Column(columnDefinition="TEXT")
    private String description;

    @Formats.DateTime(pattern="dd/MM/yyyy/H:i")
    private Date start_time;

    @Formats.DateTime(pattern="dd/MM/yyyy/H:i")
    private Date end_time;

    private Boolean status;

    public PaymentType(String title, String description, Boolean status, Date start_time, Date end_time) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.start_time = start_time;
        this.end_time = end_time;
    }

    public static Finder<Integer, PaymentType> find = new Finder<>(Integer.class, PaymentType.class);

    public List<PaymentType> findAll() {
        return find.all();
    }

    public void update(int id, String title, String description, Boolean status, Date end_time) {
        PaymentType paymentType = PaymentType.find.ref(id);
        System.out.println("id:" + id);
        paymentType.title = title;
        paymentType.description = description;
        paymentType.status = status;
        paymentType.end_time = end_time;
        paymentType.update();
    }

    public void delete(int id){
        find.ref(id).delete();
    }
}
