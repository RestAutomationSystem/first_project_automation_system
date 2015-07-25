package controllers;

import models.User;
import play.data.Form;
import play.libs.F;
import play.libs.WS;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.form;

import static play.data.Form.form;

public class SignUp extends Controller {
    static Boolean check=false;
    /**
     * Defines a form wrapping the User class.
     */
    final static Form<User> signupForm = form(User.class, User.All.class);

    /**
     * Display a blank form.
     */
    public static Result blank() {
        return ok(form.render(signupForm));
    }

    /**
     * Display a form pre-filled with an existing account.
     */


    /**
     * Handle the form submission.
     */

    public static Result submit() {




        Form<User> filledForm = signupForm.bindFromRequest();
        // Check if the username is valid
        if (!filledForm.hasErrors()) {
            if (filledForm.get().username.equals("admin") || filledForm.get().username.equals("guest")) {
                filledForm.reject("username", "This username is already taken");
            }
        }

        if (filledForm.hasErrors()) {
            return badRequest(form.render(filledForm));
        } else {Result notFound = notFound();
            indexmy();
            System.out.println(check);
            if (check==true){ User created = filledForm.get();
                return ok(views.html.signup.summary.render(created));}
            else return ok("Error  "+notFound);
            }




    }




    public static F.Promise<Result> indexmy() {
        final F.Promise<Result> resultPromise = WS.url("http://jsonplaceholder.typicode.com/posts?userId=1").get().flatMap(
                new F.Function<WS.Response, F.Promise<Result>>() {
                    public F.Promise<Result> apply(WS.Response response) {
                        if(response.asJson().findPath("userId").asText().equals("1")){
                            check=true;
                            System.out.println("SSSS"+check);

                        }
                        return WS.url(response.asJson().findPath("title").asText()).get().map(
                                new F.Function<WS.Response, Result>() {
                                    public Result apply(WS.Response response) {
                                        System.out.println("SSSS2"+check);

                                        return ok("Number of comments: " + response.asJson().findPath("id").asInt());
                                    }
                                }
                        );
                    }
                }
        );
        System.out.println("SSSS3"+check);

        return resultPromise;
    }
  
}