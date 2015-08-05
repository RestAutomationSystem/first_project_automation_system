package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.*;
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
        if(Secured.isAdmin()) {
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            Menu.find.byId(id),
                            Category.findByMenu(id),
                            categoryForm
                    )
            );
        }else{
            return forbidden();
        }

    }


    public static Result newCategory(int id) throws ParseException{
        if(Secured.isAdmin()) {
            //Form<Category> filledForm=form(Category.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();
            DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
            Logger.debug("startDate:"+filledForm.get("start_time"));
            Logger.debug("deadline:"+filledForm.get("end_time"));
            String parent_text="вверхнего уровня";

            Category parent=null;
            if(!filledForm.get("category_id").equals("0")) {
                parent=Category.find.byId(Integer.parseInt(filledForm.get("category_id")));
                parent_text="внутри категории:"+parent.id;
            }
            int c_id=Category.create(filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")),parent,Menu.find.byId(id));

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            String desc="Создана новая категория:"+c_id+" внутри меню:"+id+" "+parent_text+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:\nНачало:"+ filledForm.get("start_time")+"\nКонец:"+ filledForm.get("end_time");
            Event event=new Event("CATEGORY",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            event.save();
            Logger.info(desc);

            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            Menu.find.byId(id),
                            Category.findByMenu(id),
                            categoryForm
                    )
            );
        }else{
            return forbidden();
        }


    }

    public static Result newChildCategory(int id) throws ParseException{
        if(Secured.isAdmin()) {
            //Form<Category> filledForm=form(Category.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();
            DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);
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
        }else{
            return forbidden();
        }



    }


    public static Result addingCategoryPage(int id){
        if(Secured.isAdmin()) {
            List<Category> categoryList= Category.findByMenu(id);
            if( categoryList==null)
                categoryList=new ArrayList<Category>();

            return ok(add_category.render(User.find.where().eq("email", request().username()).findUnique(),
                    Menu.find.byId(id),categoryForm,categoryList));


        }else{
            return forbidden();
        }


    }

    public static Result addingChildCategoryPage(int id){
        if(Secured.isAdmin()) {
            List<Category> categoryList= Category.findByCategory(id);
            if( categoryList==null)
                categoryList=new ArrayList<Category>();
            return ok(add_child_category.render(User.find.where().eq("email", request().username()).findUnique(),
                    Category.find.byId(id),categoryForm,categoryList));
        }else{
            return forbidden();
        }


    }


    public static Result categoryPage(int id) {
        if(Secured.isAdmin()) {
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
        }else{
            return forbidden();
        }




    }


    public static Result updateCategory(int id) throws ParseException{
        if(Secured.isAdmin()) {
            //Form<Category> filledForm=form(Category.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();
            Menu menu=Category.find.byId(id).menu;

            SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);


            Category category=Category.find.ref(id);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Изменена категория:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ category.title+"\nОписание:"+ category.description+"\nСтатус:\nНачало:"+ category.start_time+"\nКонец:"+ category.end_time
                    +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

            Event event=new Event("CATEGORY",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            Category.update(id, filledForm.get("title"), filledForm.get("description"),"",sdf2.parse(filledForm.get("start_time")),sdf2.parse(filledForm.get("end_time")));
            event.save();
            Logger.info(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            menu,
                            Category.findByMenu(menu.id),
                            categoryForm
                    )
            );
        }else{
            return forbidden();
        }



    }

    public static Result deleteCategory(int id) {
        if(Secured.isAdmin()) {
            Menu menu=Category.find.byId(id).menu;
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Category category=Category.find.ref(id);

            String desc="Удален сервис:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ category.title+"\nОписание:"+ category.description+"\nСтатус:\nНачало:"+ category.start_time+"\nКонец:"+ category.end_time;

            Event event=new Event("CATEGORY",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            Category.delete(id);

            event.save();
            Logger.info(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            menu,
                            Category.findByMenu(menu.id),
                            categoryForm
                    )
            );
        }else{
            return forbidden();
        }



    }






}