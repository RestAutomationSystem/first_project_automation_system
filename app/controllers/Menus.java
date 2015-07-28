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
import views.html.menu.*;

@Security.Authenticated(Secured.class)
public class Menus extends Controller{

    public static Form<Menu> menuForm=form(Menu.class);

    public static Result index() {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Menu.findAll(),
                        menuForm
                )
        );
    }


    public static Result newMenu() throws ParseException{
        //Form<Menu> filledForm=form(Menu.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));

        Menu.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Menu.findAll(),
                        menuForm
                )
        );

    }




    public static Result menuPage(int id) {


        session("menu",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Menu.find.byId(id)
                )
        );


    }


    public static Result updateMenu(int id) throws ParseException{
        //Form<Menu> filledForm=form(Menu.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        Menu.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Menu.findAll(),
                        menuForm
                )
        );

    }

    public static Result deleteMenu(int id) {
        Menu.delete(id);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Menu.findAll(),
                        menuForm
                )
        );

    }


    public static Result menuCategories(int id) {


        session("category",id+"");
        return ok(
                categories.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Menu.find.byId(id),
                        Category.findAll(),
                        Menu.find.byId(id).categories
                )
        );


    }


    public static Result setCategories(int id) throws ParseException{
        //Form<Menu> filledForm=form(Menu.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        Logger.debug("categoryList:"+filledForm.get());

        Map<String,String[]> keys = request().body().asFormUrlEncoded();
        Menu menu= Menu.find.byId(id);
        menu.categories=new ArrayList<Category>();

        for (Map.Entry<String, String[]> entry : keys.entrySet())
        {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            for(int i=0;i<entry.getValue().length;i++){
                Logger.debug("category:"+entry.getValue()[i]);
                menu.categories.add(Category.find.byId(Integer.parseInt(entry.getValue()[i])));
            }
        }

        menu.update();


        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Menu.find.byId(id)
                )
        );

    }
   


}