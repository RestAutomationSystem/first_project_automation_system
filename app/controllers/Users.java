package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
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
        User.update(email,regForm.get().name,regForm.get().password);

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
        User.deleteUser(email);
        return ok(
                index.render(
                        User.find.where().eq("email", request().username()).findUnique(),
                        User.findAllEmployee(),
                        userForm
                )
        );

    }


}