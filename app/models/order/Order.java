package models;

import models.*;
import play.data.format.Formats;
import play.db.ebean.Model;

import play.data.validation.Constraints.Required;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="order_table")
public class Order extends Model {

    @Id
    @Version
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

	@Column(columnDefinition="TEXT")
	private String description;

    @Formats.DateTime(pattern="dd/MM/yyyy")
	private Date start_time;

    @Formats.DateTime(pattern="dd/MM/yyyy")
    private Date end_time;

    @ManyToOne
	private OrderStatus status;

	private double total;
	private int clients_quantity;

    @ManyToOne
	private OrderType orderType;

    @ManyToOne
	private PaymentType paymentType;
    @ManyToOne
	private Place place;

	public Order(String description, OrderStatus status, Date start_time, double total,
	  		int clients_quantity, OrderType orderType, PaymentType paymentType,
				  Place place) {
		this.description = description;
		this.status = status;
		this.start_time = start_time;
		this.total = total;
		this.clients_quantity = clients_quantity;
		this.orderType = orderType;
		this.paymentType = paymentType;
		this.place = place;
	}
	
	public static Finder<Long, Order> find = new Finder<>(Long.class, Order.class);

	public static List<Order> findAll() {
		return find.all();
	}

    public Long create(String description, OrderStatus status, Date start_time, double total,
                       int clients_quantity, OrderType orderType, PaymentType paymentType,
                       Place place) {
        Order newOrder=new Order(description,status,start_time,total,clients_quantity,orderType,paymentType,place);
        newOrder.save();
        return newOrder.id;
    }
	
	public void update(Long id,String description, OrderStatus status, Date end_time, double total,
	   int clients_quantity, OrderType orderType,  PaymentType paymentType,
	   Place place) {
			Order order = Order.find.ref(id);
			System.out.println("id:" + id);
			order.description = description;
			order.status = status;
			order.end_time = end_time;
			order.total = total;
			order.clients_quantity = clients_quantity;
			order.orderType = orderType;
			order.paymentType = paymentType;
			order.place = place;
			order.update();
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getClients_quantity() {
        return clients_quantity;
    }

    public void setClients_quantity(int clients_quantity) {
        this.clients_quantity = clients_quantity;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }


    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}