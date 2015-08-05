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
        if(Secured.isAdmin()) {
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            User.findAllEmployee(),
                            userForm
                    )
            );
        }else{
            return forbidden();
        }


    }

    public static Result newUser() throws ParseException{
        if(Secured.isAdmin()) {
            Form<Application.Register> regForm=form(Application.Register.class).bindFromRequest();
            if(regForm.hasErrors()) {
                return ok(
                        add_user.render(
                                User.find.where().eq("email", request().username()).findUnique(), userForm,Role.findAll()
                        )
                );
            }
            else{
                Role role=Role.find.byId(regForm.get().role_id);
                User newUser=User.create(regForm.get().name,regForm.get().email,regForm.get().password,role);

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                String desc="Создан новый пользователь:"+newUser.email+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nИмя:"+ regForm.get().name+"\nEmail:"+ regForm.get().email+"\nСтатус:"+"\nРоль:"+ role.title;
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
        }else{
            return forbidden();
        }

    }

    public static Result addingUserPage(){
        if(Secured.isAdmin()) {
            return ok(
                    add_user.render(
                            User.find.where().eq("email", request().username()).findUnique(), userForm ,Role.findAll()
                    )
            );
        }else{
            return forbidden();
        }

    }

    public static Result userPage(String email) {
        if(Secured.isAdmin()) {
            return ok(
                    item.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            User.find.byId(email),Role.findAll()));
        }else{
            return forbidden();
        }


    }

    public static Result updateUser(String email) throws ParseException{
        if(Secured.isAdmin()) {
            Form<Application.Register> regForm=form(Application.Register.class).bindFromRequest();
            if(regForm.hasErrors()) {
                return ok(
                        item.render(
                                User.find.where().eq("email", request().username()).findUnique(),
                                User.find.byId(email),Role.findAll()));
            }
            else{

                User user= User.find.byId(email);
                Role role=Role.find.byId(regForm.get().role_id);

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

                String desc="Изменен ресторан:"+email+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nИмя:"+ user.name+"\nEmail:"+ user.email+"\nСтатус:" +"\nРоль:"+ user.role.title
                        +"\nНовые значения:\nИмя:"+ regForm.get().name+"\nEmail:"+ email+"\nСтатус:"+"\nРоль:"+ role.title;

                Event event=new Event("USER",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());

                User.update(email,regForm.get().name,regForm.get().password,role);
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
        }else{
            return forbidden();
        }

    }

    public static Result deleteUser(String email) {
        if(Secured.isAdmin()) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            User user=User.find.byId(email);

            String desc="Удален пользователь:"+email+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nИмя:"+ user.name+"\nEmail:"+ user.email+"\nСтатус:"+"\nРоль:"+ user.role.title;

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
        }else{
            return forbidden();
        }


    }


}