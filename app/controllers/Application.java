package controllers;

import play.*;
import play.libs.Json;
import play.mvc.*;
import views.html.*;
import models.User;
import models.Enums.RoleTypes;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.data.*;
import static play.data.Form.*;

import com.fasterxml.jackson.databind.JsonNode;


public class Application extends Controller {


    public static class Login{
        public String email;
        public String password;

        public String validate() {
            if (User.authenticate(email, password) == null) {
                return "Invalid user or password";
            }
            return null;
        }
    }

    public static class Register{
        public String name;
        public String email;
        public String password;
        public String password2;


        public String validate() {
            if (!password.equals(password2)) {
                return "Invalid password repeated";
            }
            return null;
        }
    }

    public static Result index() {
	if(session("email")!=null){
	if(User.isAdmin(session("email")))
        return redirect(routes.Restaurants.index());
	else
	 return redirect(routes.Application.login());
    }else{
	return redirect(routes.Application.login());
	}
	
	}
	
	
	public static Result login() {
    	if(session("email")==null)
        return ok(login.render(form(Login.class)));
    	else
    		return redirect(routes.Application.registerForm());
    }
    
    public static Result list() {
        return ok(list.render(User.findAll()));
    }
    
    public static Result registerForm() {
        return ok(register.render(form(Register.class)));
    }
    
    public static Result register() {
    	Form<Register> regForm = form(Register.class).bindFromRequest();
        if(regForm.hasErrors()){
        	return ok(register.render(form(Register.class)));
    	}
    	else{
    		User newUser=User.create(regForm.get().name,regForm.get().email,regForm.get().password);
    		JsonNode personJson = Json.toJson(newUser); 
    		Logger.debug("User created: "+regForm.get().name+" "+regForm.get().email);
    		return ok(personJson);
    		
    //return ok( registerSuccess.render( ));
    	}
    }
    
    public static Result authenticate() {
    	Form<Login> loginForm = form(Login.class).bindFromRequest();
        if(loginForm.hasErrors()){
        	return redirect(routes.Application.login());
    	}
    	else{
    		session().clear();
    		session("email",loginForm.get().email);

    		return redirect(routes.Application.index());
    	}
    }
    
    public static Result confirm(String email) {
    	User u=User.find.ref(email);
    	u.update();
    	return redirect(
				routes.Application.login()
				);
    }
    
    public static Result logout(){
		session().clear();
		flash("success","You've been logged out");
		return redirect(
				routes.Application.login()
				);
	}

}
