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
import views.html.item.*;

@Security.Authenticated(Secured.class)
public class Items extends Controller{

    public static Form<Item> itemForm=form(Item.class);

    public static Result index(int id) {
        if(Secured.isAdmin()) {
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            Category.find.byId(id),
                            Item.findByCategory(id),
                            itemForm,
                            UnitType.findAll()
                    )
            );
        }else{
            return forbidden();
        }


    }


    public static Result newItem(int id) throws ParseException{
        if(Secured.isAdmin()) {
            //Form<Item> filledForm=form(Item.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();
            DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
            UnitType ut=UnitType.find.byId(Integer.parseInt(filledForm.get("unit_type")));
            int i_id=Item.create(filledForm.get("title"), filledForm.get("description"),"","",Double.parseDouble(filledForm.get("price_original")),Double.parseDouble(filledForm.get("price_for_sale")),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),ut,Category.find.byId(id));

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String desc="Создан новый продукт:"+i_id+" внутри категории:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСебестоимость:"+ filledForm.get("price_original")+"\nРозничная цена:"+ filledForm.get("price_for_sale")+"\nЕдиница измерение:"+ ut.title+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
            Event event=new Event("ITEM",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            event.save();
            Logger.info(desc);

            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            Category.find.byId(id),
                            Item.findByCategory(id),
                            itemForm,
                            UnitType.findAll()
                    )
            );
        }else{
            return forbidden();
        }




    }

    public static Result addingItemPage(int id){
        if(Secured.isAdmin()) {
            return ok(add_item.render(User.find.where().eq("email", request().username()).findUnique(),
                    Category.find.byId(id),UnitType.findAll()));
        }else{
            return forbidden();
        }


    }


    public static Result itemPage(int id) {
        if(Secured.isAdmin()) {
            session("item",id+"");
            return ok(
                    item.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            Item.find.byId(id),
                            UnitType.findAll()
                    )
            );
        }else{
            return forbidden();
        }

    }


    public static Result updateItem(int id) throws ParseException{
        if(Secured.isAdmin()) {
            //Form<Item> filledForm=form(Item.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();
            Category category=Item.find.byId(id).category;
            SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
            UnitType ut=UnitType.find.byId(Integer.parseInt(filledForm.get("unit_type")));

            Item item=Item.find.ref(id);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Изменен продукт:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ item.title+"\nОписание:"+ item.description+"\nСебестоимость:"+ item.price_original+"\nРозничная цена:"+ item.price_for_sale+"\nЕдиница измерения:"+ item.unit_type.title+"\nСтатус:\nНачало:"+ item.start_time+"\nКонец:"+ item.end_time
                    +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСебестоимость:"+ filledForm.get("price_original")+"\nРозничная цена:"+ filledForm.get("price_for_sale")+"\nЕдиница измерения:"+ ut.title;

            Event event=new Event("ITEM",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            Item.update(id, filledForm.get("title"), filledForm.get("description"),"","",Double.parseDouble(filledForm.get("price_original")),Double.parseDouble(filledForm.get("price_for_sale")),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),ut);

            event.save();
            Logger.info(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            category,
                            Item.findByCategory(category.id),
                            itemForm,
                            UnitType.findAll()
                    )
            );
        }else{
            return forbidden();
        }



    }

    public static Result deleteItem(int id) {
        if(Secured.isAdmin()) {
            Category category=Item.find.byId(id).category;

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Item item=Item.find.ref(id);

            String desc="Удален продукт:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ item.title+"\nОписание:"+ item.description+"\nСтатус:\nНачало:"+ item.start_time+"\nКонец:"+ item.end_time;

            Event event=new Event("ITEM",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            Item.delete(id);

            event.save();
            Logger.info(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            category,
                            Item.findByCategory(category.id),
                            itemForm,
                            UnitType.findAll()
                    )
            );
        }else{
            return forbidden();
        }



    }


}