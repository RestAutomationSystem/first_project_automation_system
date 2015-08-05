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
import views.html.orderType.*;

@Security.Authenticated(Secured.class)
public class OrderTypes extends Controller{

    public static Form<OrderType> orderTypeForm=form(OrderType.class);

    public static Result index() {
        if(Secured.isAdmin()) {
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            OrderType.findAll(),
                            orderTypeForm
                    )
            );
        }else{
            return forbidden();
        }


    }


    public static Result newOrderType() throws ParseException{
        if(Secured.isAdmin()) {
            //Form<OrderType> filledForm=form(OrderType.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();
            DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);

            int o_id=OrderType.create(filledForm.get("title"), filledForm.get("description"),true);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Создан новый тип заказа:"+o_id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:";
            Event event=new Event("ORDER_TYPE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            OrderType.findAll(),
                            orderTypeForm
                    )
            );
        }else{
            return forbidden();
        }



    }




    public static Result orderTypePage(int id) {
        if(Secured.isAdmin()) {
            session("orderType",id+"");
            return ok(
                    item.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            OrderType.find.byId(id)
                    )
            );
        }else{
            return forbidden();
        }




    }


    public static Result updateOrderType(int id) throws ParseException{
        if(Secured.isAdmin()) {
            //Form<OrderType> filledForm=form(OrderType.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();

            SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);


            OrderType orderType=OrderType.find.ref(id);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Изменен тип заказа:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ orderType.title+"\nОписание:"+ orderType.description+"\nСтатус:"+ orderType.status
                    +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

            Event event=new Event("ORDER_TYPE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            OrderType.update(id, filledForm.get("title"), filledForm.get("description"),true);
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            OrderType.findAll(),
                            orderTypeForm
                    )
            );
        }else{
            return forbidden();
        }



    }

    public static Result deleteOrderType(int id) {
        if(Secured.isAdmin()) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            OrderType orderType=OrderType.find.ref(id);

            String desc="Удален тип заказа:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ orderType.title+"\nОписание:"+ orderType.description+"\nСтатус:"+ orderType.status;

            Event event=new Event("ORDER_TYPE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            OrderType.delete(id);
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            OrderType.findAll(),
                            orderTypeForm
                    )
            );
        }else{
            return forbidden();
        }



    }

}