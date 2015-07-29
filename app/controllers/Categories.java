package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
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

    public static Result index(int id) {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Menu.find.byId(id),
                        Category.findByMenu(id),
                        categoryForm
                )
        );
    }


    public static Result newCategory(int id) throws ParseException{
        //Form<Category> filledForm=form(Category.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));

        Category parent=null;
        if(!filledForm.get("category_id").equals("0"))
            parent=Category.find.byId(Integer.parseInt(filledForm.get("category_id")));
        Category.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),parent,Menu.find.byId(id));


        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        Menu.find.byId(id),
                        Category.findByMenu(id),
                        categoryForm
                )
        );

    }

    public static Result newChildCategory(int id) throws ParseException{
        //Form<Category> filledForm=form(Category.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);
        Logger.debug("startDate:"+filledForm.get("start_time"));
        Logger.debug("deadline:"+filledForm.get("end_time"));

        Category parent=Category.find.byId(id);

        Category.create(filledForm.get("title"), filledForm.get("description"), "", sdf2.parse(filledForm.get("start_time")), sdf2.parse(filledForm.get("end_time")), parent,parent.menu);


        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        parent,
                        Category.findByMenu(parent.menu.id),
                        Category.findByCategory(parent.id)
                )
        );

    }





    public static Result categoryPage(int id) {
        Category category=Category.find.byId(id);

        List<Category> categoryList= Category.findByCategory(category.id);
        if( categoryList==null)
            categoryList=new ArrayList<Category>();
        session("category",id+"");
        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        category,
                        Category.findByMenu(category.menu.id),
                        categoryList
                )
        );


    }


    public static Result updateCategory(int id) throws ParseException{
        //Form<Category> filledForm=form(Category.class).bindFromRequest();
        DynamicForm filledForm=form().bindFromRequest();
        Menu menu=Category.find.byId(id).menu;

        SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        sdf2.setLenient(false);

        Category.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        menu,
                        Category.findByMenu(menu.id),
                        categoryForm
                )
        );

    }

    public static Result deleteCategory(int id) {
        Menu menu=Category.find.byId(id).menu;

        Category.delete(id);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        menu,
                        Category.findByMenu(menu.id),
                        categoryForm
                )
        );

    }






}