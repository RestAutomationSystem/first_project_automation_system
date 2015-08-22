package controllers;

import models.User;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.officiant.*;

import static play.data.Form.form;

public class SignUp extends Controller {

  final static   Form<Application.Login> signupForm = form(Application.Login.class).bindFromRequest();

    public static Result blank() {
        return ok(form.render(signupForm));
    }

    public static Result submit() {

        if(signupForm.hasErrors()){
            return ok("sssssss");
        }
        else{
            session().clear();
            session("email",signupForm.get().email);

            return ok("aaaaaaaaa");
        }

       // Form<Application.Login> filledForm = signupForm.bindFromRequest();
        // Check if the username is valid

/*
        if (filledForm.hasErrors()) {
            return badRequest(form.render(filledForm));
        } else {
            UserTest cresated = filledForm.get();
            return ok(views.html.signup.summary.render(cresated));
        }*/

    }


    }





