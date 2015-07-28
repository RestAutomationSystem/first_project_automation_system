package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Map;

import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.item.*;

@Security.Authenticated(Secured.class)
public class Items extends Controller{

    public static Form<Item> itemForm=form(Item.class);

    public static Result index() {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Item.findAll(),
                        itemForm,
                        UnitType.findAll()
                )
        );
    }


    public static Result newItem() throws ParseException{
        //Form<Item> filledForm=form(Item.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));
                   UnitType ut=UnitType.find.byId(Integer.parseInt(filledForm.get("unit_type")));
        Item.create(filledForm.get("title"), filledForm.get("description"),"","",Double.parseDouble(filledForm.get("price_original")),Double.parseDouble(filledForm.get("price_for_sale")),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),ut);


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Item.findAll(),
                        itemForm,
                        UnitType.findAll()
                )
        );

    }




    public static Result itemPage(int id) {


        session("item",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Item.find.byId(id),
                        UnitType.findAll()
                )
        );


    }


    public static Result updateItem(int id) throws ParseException{
        //Form<Item> filledForm=form(Item.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        UnitType ut=UnitType.find.byId(Integer.parseInt(filledForm.get("unit_type")));
        Item.update(id, filledForm.get("title"), filledForm.get("description"),"","",Double.parseDouble(filledForm.get("price_original")),Double.parseDouble(filledForm.get("price_for_sale")),sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),ut);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Item.findAll(),
                        itemForm,
                        UnitType.findAll()
                )
        );

    }

    public static Result deleteItem(int id) {
        Item.delete(id);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Item.findAll(),
                        itemForm,
                        UnitType.findAll()
                )
        );

    }


}