package controllers;

import play.*;
import play.libs.Json;
import play.mvc.*;
import views.html.*;
import models.User;
import play.data.*;
import static play.data.Form.*;

import com.fasterxml.jackson.databind.JsonNode;

public class Application extends Controller {

    public static class Login {
        public String email, password;

        public String validate() {
            if (User.authenticate(email, password) == null) { return "Имя пользователя или пароль неверены"; }
            return null;
        }
    }

    public static class Register {
        public String name, email, password, password2;

        public String validate() {
            if (!password.equals(password2)) { return "Invalid password repeated"; }
            return null;
        }
    }

    public static Result index() {
        /*if(session("email") != null) {
            if(User.isAdmin(session("email"))) { return redirect(routes.Restaurants.index()); }
            return redirect(routes.Application.login());
        }*/
        session().clear();
        return redirect(routes.Application.login());
	}
	
	public static Result login() {
    	//if(session("email") == null) {
            return ok(login.render(form(Login.class))); //}
    	//else { return redirect(routes.Application.registerForm()); }
    }
    
    public static Result list() {
        return ok(list.render(User.findAll()));
    }
    
    public static Result registerForm() { return ok(register.render(form(Register.class))); }
    
    public static Result register() {
    	Form<Register> regForm = form(Register.class).bindFromRequest();
        if(regForm.hasErrors()) { return ok(register.render(form(Register.class))); }
        else {
    		User newUser=User.create(regForm.get().name,regForm.get().email,regForm.get().password);
    		JsonNode personJson = Json.toJson(newUser); 
    		Logger.debug("User created: "+regForm.get().name+" "+regForm.get().email);
    		return ok(personJson);
    	}
    }

    //TODO: set a new one each time
    public static Result authenticate() {
    	Form<Login> loginForm = form(Login.class).bindFromRequest(); //TODO: clean it
        if(loginForm.hasErrors()) { return ok(login.render(loginForm)); }
    	else {
    		session().clear();
    		session("email", loginForm.get().email);
    		/*return redirect(routes.Restaurants.index());*/
            return redirect(routes.AdminPanel.index());
    	}
    }

    //TODO: login?
    public static Result confirm(String email) {
    	User user = User.find.ref(email);
    	user.update();
    	return redirect(routes.Application.login());
    }
    
    public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
		return redirect(routes.Application.login());
	}


    public static Result javascriptRoutes() {
        response().setContentType("text/javascript");
        return ok(
                Routes.javascriptRouter("myJsRoutes",
                        controllers.routes.javascript.Pricelist.index(),
                        controllers.routes.javascript.Pricelist.serviceList(),
                        controllers.routes.javascript.Pricelist.menuList(),
                        controllers.routes.javascript.Pricelist.categoryList(),
                        controllers.routes.javascript.Pricelist.itemList(),
                        controllers.routes.javascript.Pricelist.modificatorList()

                )
        );
    }

}

//TODO: index method: email parameter must be login
//TODO: why are all methods static?
//TODO: class variables are public?

//TODO: clear session and start with login page