package models;

import models.*;
import play.data.format.Formats;
import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="order_table")
public class Order extends Model {

    @Id
    @Version
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Long id;

	@Column(columnDefinition="TEXT")
	private String description;

    @Formats.DateTime(pattern="dd/MM/yyyy")
	private Date start_time;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    private Date end_time;

	private boolean status;
	private double total;
	private int clients_quantity;

	private OrderType orderType;
	private Session session;
	private PaymentType paymentType;
	private Place table;

	public Order(String description, boolean status, Date start_time, Date end_time, double total,
	  		int clients_quantity, OrderType orderType, Session session, PaymentType paymentType,
				  Place table) {
		this.description = description;
		this.status = status;
		this.start_time = start_time;
		this.end_time = end_time;
		this.total = total;
		this.clients_quantity = clients_quantity;
		this.orderType = orderType;
		this.session = session;
		this.paymentType = paymentType;
		this.table = table;
	}
	
	public static Finder<Long, Order> find = new Finder<>(Long.class, Order.class);

	public List<Order> findAll() {
		return find.all();
	}
	
	public void update(Long id,String description, boolean status, Date end_time, double total,
	   int clients_quantity, OrderType orderType, Session session, PaymentType paymentType,
	   Place table) {
			Order order = Order.find.ref(id);
			System.out.println("id:" + id);
			order.description = description;
			order.status = status;
			order.end_time = end_time;
			order.total = total;
			order.clients_quantity = clients_quantity;
			order.orderType = orderType;
			order.session = session;
			order.paymentType = paymentType;
			order.table = table;
			order.update();
	}

	public void delete(Long id){
		find.ref(id).delete();
	}
}