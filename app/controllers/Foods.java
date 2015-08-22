package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.food.*;

@Security.Authenticated(Secured.class)
public class Foods extends Controller{

    public static Form<Food> foodForm=form(Food.class);

    public static Result index(int id) {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Storage.find.byId(id),
                        Food.findAll(),
                        foodForm,
                        Supplier.findAll()
                )
        );
    }


    public static Result newFood(int id) throws ParseException{
        //Form<Food> filledForm=form(Food.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
       Supplier supplier=Supplier.find.byId(Integer.parseInt(filledForm.get("supplier_id")));
        int f_id=Food.create(filledForm.get("title"), filledForm.get("description"),"",Storage.find.byId(id),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),
                Double.parseDouble(filledForm.get("price")),supplier);


        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String desc="Создан новый продукт:"+f_id+" внутри склада:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nЦена:"+ filledForm.get("price")+"\nПоставщик:"+ supplier.title+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
        Event event=new Event("FOOD",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        event.save();
        Logger.info(desc);

        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Storage.find.byId(id),
                        Food.findAll(),
                        foodForm,
                        Supplier.findAll()
                )
        );

    }


    public static Result foodPage(int id) {


        session("food",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Food.find.byId(id),
                        Supplier.findAll()
                )
        );


    }


    public static Result updateFood(int id) throws ParseException{
        //Form<Food> filledForm=form(Food.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        Food food=Food.find.ref(id);
        Supplier supplier=Supplier.find.byId(Integer.parseInt(filledForm.get("supplier_id")));

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String desc="Изменен продукт:"+id+" из склада в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ food.getTitle()+"\nОписание:"+ food.getDescription()+"\nЦена:"+ food.getPrice()+"\nПоставщик:"+ food.getSupplier().title+"\nСтатус:\nНачало:"+ food.getStart_time()+"\nКонец:"+ food.getEnd_time()
                +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСебестоимость:"+"\nЦена:"+ filledForm.get("price")+"\nПоставщик:"+ supplier.title;

        Event event=new Event("FOOD",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        Food.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),Double.parseDouble(filledForm.get("price")),Supplier.find.byId(Integer.parseInt(filledForm.get("supplier_id"))));
        event.save();
        Logger.info(desc);


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Food.find.byId(id).getStorage(),

                        Food.findAll(),
                        foodForm,
                        Supplier.findAll()
                )
        );

    }

    public static Result deleteFood(int id) {

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Food food=Food.find.ref(id);

        String desc="Удален продукт:"+id+" из склада в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ food.getTitle()+"\nОписание:"+ food.getDescription()+"\nСтатус:\nНачало:"+ food.getStart_time()+"\nКонец:"+ food.getEnd_time();

        Event event=new Event("FOOD",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());


        Food.delete(id);
        event.save();
        Logger.info(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Food.find.byId(id).getStorage(),

                        Food.findAll(),
                        foodForm,
                        Supplier.findAll()
                )
        );

    }



}