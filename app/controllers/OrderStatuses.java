package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.orderStatus.*;

@Security.Authenticated(Secured.class)
public class OrderStatuses extends Controller{

    public static Form<OrderStatus> orderStatusForm=form(OrderStatus.class);

    public static Result index() {
        if(Secured.isAdmin()) {
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            OrderStatus.findAll(),
                            orderStatusForm
                    )
            );
        }else{
            return forbidden();
        }


    }


    public static Result newOrderStatus() throws ParseException{
        if(Secured.isAdmin()) {
            //Form<OrderStatus> filledForm=form(OrderStatus.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();
            DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);

            int o_id=OrderStatus.create(filledForm.get("title"), filledForm.get("description"),true);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Создан новый статус заказа:"+o_id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:";
            Event event=new Event("ORDER_STATUS",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            OrderStatus.findAll(),
                            orderStatusForm
                    )
            );
        }else{
            return forbidden();
        }



    }




    public static Result orderStatusPage(int id) {
        if(Secured.isAdmin()) {
            session("orderStatus",id+"");
            return ok(
                    item.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            OrderStatus.find.byId(id)
                    )
            );
        }else{
            return forbidden();
        }




    }


    public static Result updateOrderStatus(int id) throws ParseException{
        if(Secured.isAdmin()) {
            //Form<OrderStatus> filledForm=form(OrderStatus.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();

            SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);


            OrderStatus orderStatus=OrderStatus.find.ref(id);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Изменен статус заказа:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ orderStatus.title+"\nОписание:"+ orderStatus.description+"\nСтатус:"
                    +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

            Event event=new Event("ORDER_STATUS",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            OrderStatus.update(id, filledForm.get("title"), filledForm.get("description"),true);
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            OrderStatus.findAll(),
                            orderStatusForm
                    )
            );
        }else{
            return forbidden();
        }



    }

    public static Result deleteOrderStatus(int id) {
        if(Secured.isAdmin()) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            OrderStatus orderStatus=OrderStatus.find.ref(id);

            String desc="Удален статус заказа:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ orderStatus.title+"\nОписание:"+ orderStatus.description+"\nСтатус:"+ orderStatus.status;

            Event event=new Event("ORDER_STATUS",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            OrderStatus.delete(id);
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            OrderStatus.findAll(),
                            orderStatusForm
                    )
            );
        }else{
            return forbidden();
        }



    }

}