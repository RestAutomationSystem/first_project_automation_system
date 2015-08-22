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
import views.html.user.*;

@Security.Authenticated(Secured.class)
public class Users extends Controller{


    public static Form<User> userForm=form(User.class);

    public static Result index() {
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        User.findAllEmployee(),
                        userForm
                )
        );
    }

    public static Result newUser() throws ParseException{
        Form<Application.Register> regForm=form(Application.Register.class).bindFromRequest();
        if(regForm.hasErrors()) {
            return ok(
                    add_user.render(
                            User.find.where().eq("email", request().username()).findUnique(), userForm
                    )
            );
        }
        else{
        User newUser=User.create(regForm.get().name,regForm.get().email,regForm.get().password);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Создан новый пользователь:"+newUser.email+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nИмя:"+ regForm.get().name+"\nEmail:"+ regForm.get().email+"\nСтатус:";
            Event event=new Event("USER",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            event.save();
            Logger.debug(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        User.findAllEmployee(),
                        userForm
                )
        );
        }
    }

    public static Result addingUserPage(){
        return ok(
                add_user.render(
                        User.find.where().eq("email", request().username()).findUnique(), userForm
                )
        );
    }

    public static Result userPage(String email) {

        return ok(
                item.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        User.find.byId(email)));
    }

    public static Result updateUser(String email) throws ParseException{
        Form<Application.Register> regForm=form(Application.Register.class).bindFromRequest();
        if(regForm.hasErrors()) {
            return ok(
                    item.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            User.find.byId(email)));
        }
        else{

            User user= User.find.byId(email);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Изменен ресторан:"+email+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nИмя:"+ user.name+"\nEmail:"+ user.email+"\nСтатус:"
                    +"\nНовые значения:\nИмя:"+ regForm.get().name+"\nEmail:"+ email+"\nСтатус:";

            Event event=new Event("USER",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

            User.update(email,regForm.get().name,regForm.get().password);
            event.save();
            Logger.debug(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        User.findAllEmployee(),
                        userForm
                )
        );
        }
    }

    public static Result deleteUser(String email) {
       DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        User user=User.find.byId(email);

        String desc="Удален пользователь:"+email+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nИмя:"+ user.name+"\nEmail:"+ user.email+"\nСтатус:";

        Event event=new Event("USER",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

        User.deleteUser(email);
        event.save();
        Logger.debug(desc);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        User.findAllEmployee(),
                        userForm
                )
        );

    }


}