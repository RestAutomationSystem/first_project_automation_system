package controllers;

/**
 * Created with IntelliJ IDEA.
 * User: Администратор
 * Date: 04.08.15
 * Time: 15:05
 * To change this template use File | Settings | File Templates.
 */
import models.*;


import play.libs.Json;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.pricelist.*;

@Security.Authenticated(Secured.class)
public class Pricelist extends Controller {

    public static Result index() {
        if(Secured.isAdmin()) {
            return ok(index.render(User.find.where().eq("email", request().username()).findUnique(),
                    Restaurant.findAll()

                    ));
        }else{
            return ok("Officiant page");
        }
    }

    public static Result serviceList(int id) {
        if(Secured.isAdmin()) {

            return ok(Json.toJson(Service.findByRestaurant(id)));

        }else{
            return ok("Officiant page");
        }
    }

    public static Result menuList(int id) {
        if(Secured.isAdmin()) {

            return ok(Json.toJson(Menu.findByService(id)));

        }else{
            return ok("Officiant page");
        }
    }

    public static Result categoryList(int id) {
        if(Secured.isAdmin()) {

            return ok(Json.toJson(Category.findByMenu(id)));

        }else{
            return ok("Officiant page");
        }
    }

    public static Result itemList(int id) {
        if(Secured.isAdmin()) {

            return ok(Json.toJson(Item.findByCategory(id)));

        }else{
            return ok("Officiant page");
        }
    }

    public static Result modificatorList(int id) {
        if(Secured.isAdmin()) {

            return ok(Json.toJson(Modificator.findByItem(id)));

        }else{
            return ok("Officiant page");
        }
    }

    public static Result itemPrice(int id) {
        if(Secured.isAdmin()) {

            return ok(item.render(User.find.where().eq("email", request().username()).findUnique(),
                    Item.find.ref(id),PriceListHasProduct.findByItem(id)
            ));

        }else{
            return ok("Officiant page");
        }
    }

    public static Result modificatorPrice(int id) {
        if(Secured.isAdmin()) {

            return ok(modificator.render(User.find.where().eq("email", request().username()).findUnique(),
                    Modificator.find.ref(id),PriceListHasModificator.findByModificator(id)
            ));

        }else{
            return ok("Officiant page");
        }
    }

    public static Result addingItemPricePage(int id){
        return ok(add_item_price.render(User.find.where().eq("email", request().username()).findUnique(),
                Item.find.byId(id)));
    }

    public static Result addItemPrice(int id) throws ParseException {
        //Form<Item> filledForm=form(Item.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Item product=Item.find.byId(id);
        int i_id= PriceListHasProduct.create(filledForm.get("title"), filledForm.get("description"), true,sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")), Double.parseDouble(filledForm.get("price")), product);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String desc="Создан новый прайс лист:"+i_id+" для продукта:"+id+" промежуток времени:"+filledForm.get("start_time")+"-"+filledForm.get("end_time") +" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nЦена:"+ filledForm.get("price")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
        Event event=new Event("PRICE_LIST",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        event.save();
        Logger.info(desc);

        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        product,PriceListHasProduct.findByItem(id)
                )
        );

    }

    public static Result addingModificatorPricePage(int id){
        return ok(add_modificator_price.render(User.find.where().eq("email", request().username()).findUnique(),
                Modificator.find.byId(id)));
    }

    public static Result addModificatorPrice(int id) throws ParseException {
        //Form<Modificator> filledForm=form(Modificator.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Modificator modificatorO=Modificator.find.byId(id);
        int i_id= PriceListHasModificator.create(filledForm.get("title"), filledForm.get("description"), true, sdf2.parse(filledForm.get("start_time")), sdf2.parse(filledForm.get("end_time")), Double.parseDouble(filledForm.get("price")), modificatorO);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String desc="Создан новый прайс лист:"+i_id+" для продукта:"+id+" промежуток времени:"+filledForm.get("start_time")+"-"+filledForm.get("end_time") +" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nЦена:"+ filledForm.get("price")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
        Event event=new Event("PRICE_LIST",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
        event.save();
        Logger.info(desc);

        return ok(
                modificator.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        modificatorO,PriceListHasModificator.findByModificator(id)
                )
        );

    }
}