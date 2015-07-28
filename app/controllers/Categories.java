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
import views.html.category.*;

@Security.Authenticated(Secured.class)
public class Categories extends Controller{

    public static Form<Category> categoryForm=form(Category.class);

    public static Result index() {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Category.findAll(),
                        categoryForm
                )
        );
    }


    public static Result newCategory() throws ParseException{
        //Form<Category> filledForm=form(Category.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));

        Category.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Category.findAll(),
                        categoryForm
                )
        );

    }




    public static Result categoryPage(int id) {


        session("category",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Category.find.byId(id)
                )
        );


    }


    public static Result updateCategory(int id) throws ParseException{
        //Form<Category> filledForm=form(Category.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        Category.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Category.findAll(),
                        categoryForm
                )
        );

    }

    public static Result deleteCategory(int id) {
        Category.delete(id);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Category.findAll(),
                        categoryForm
                )
        );

    }






}