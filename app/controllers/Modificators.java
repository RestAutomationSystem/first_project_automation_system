package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.*;

import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.modificator.*;

@Security.Authenticated(Secured.class)
public class Modificators extends Controller{

    public static Form<Modificator> modificatorForm=form(Modificator.class);

    public static Result index(int id) {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Item.find.byId(id),
                        Modificator.findByItem(id),
                        modificatorForm,
                        UnitType.findAll()
                )
        );
    }


    public static Result newModificator(int id) throws ParseException{
        //Form<Modificator> filledForm=form(Modificator.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        UnitType ut=UnitType.find.byId(Integer.parseInt(filledForm.get("unit_type")));
        int m_id=Modificator.create(filledForm.get("title"), filledForm.get("description"),"","",Double.parseDouble(filledForm.get("price_original")),Double.parseDouble(filledForm.get("price_for_sale")),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),ut,Item.find.byId(id));

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String desc="Создан новый модификатор:"+m_id+" внутри продукта:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСебестоимость:"+ filledForm.get("price_original")+"\nРозничная цена:"+ filledForm.get("price_for_sale")+"\nЕдиница измерение:"+ ut.title+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
        Event event=new Event("MODIFICATOR",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        event.save();
        Logger.info(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Item.find.byId(id),
                        Modificator.findByItem(id),
                        modificatorForm,
                        UnitType.findAll()
                )
        );

    }




    public static Result modificatorPage(int id) {


        session("modificator",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Modificator.find.byId(id),
                        UnitType.findAll()
                )
        );


    }


    public static Result updateModificator(int id) throws ParseException{
        //Form<Modificator> filledForm=form(Modificator.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        Item item=Modificator.find.byId(id).item;

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        UnitType ut=UnitType.find.byId(Integer.parseInt(filledForm.get("unit_type")));

        Modificator modificator=Modificator.find.ref(id);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String desc="Изменен модификатор:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ modificator.title+"\nОписание:"+ modificator.description+"\nСебестоимость:"+ modificator.price_original+"\nРозничная цена:"+ modificator.price_for_sale+"\nЕдиница измерения:"+ modificator.unit_type.title+"\nСтатус:\nНачало:"+ modificator.start_time+"\nКонец:"+ modificator.end_time
                +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСебестоимость:"+ filledForm.get("price_original")+"\nРозничная цена:"+ filledForm.get("price_for_sale")+"\nЕдиница измерения:"+ ut.title;

        Event event=new Event("MODIFICATOR",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        Modificator.update(id, filledForm.get("title"), filledForm.get("description"),"","",Double.parseDouble(filledForm.get("price_original")),Double.parseDouble(filledForm.get("price_for_sale")),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),ut);
        event.save();
        Logger.info(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        item,
                        Modificator.findByItem(item.id),
                        modificatorForm,
                        UnitType.findAll()
                )
        );

    }

    public static Result deleteModificator(int id) {
        Item item=Modificator.find.byId(id).item;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Modificator modificator=Modificator.find.ref(id);

        String desc="Удален продукт:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ modificator.title+"\nОписание:"+ modificator.description+"\nСтатус:\nНачало:"+ modificator.start_time+"\nКонец:"+ modificator.end_time;

        Event event=new Event("MODIFICATOR",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

        Modificator.delete(id);
        event.save();
        Logger.info(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        item,
                        Modificator.findByItem(item.id),
                        modificatorForm,
                        UnitType.findAll()
                )
        );

    }


}