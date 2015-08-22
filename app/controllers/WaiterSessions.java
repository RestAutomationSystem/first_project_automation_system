package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.user.Session;
import org.joda.time.DateTime;
import play.libs.Json;
import play.mvc.Content;
import play.mvc.Controller;
import play.mvc.Result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WaiterSessions extends Controller {
    static DateTime dt = new DateTime();
    static Long newUserTest;

    public static Result  index() {



        int month = dt.getMonthOfYear();
        //DateTime year2000 = dt.withYear(2000);
        // DateTime twoHoursLater = dt.plusHours(2);

        String frenchShortName = dt.monthOfYear().getAsShortText(Locale.UK);
        DateTime rounded = dt.dayOfMonth().roundFloorCopy();

        //  System.out.println("---"+ month + " "  + " " + monthName + " " );


        return null;
    }


    public static Result newsmena() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
        //session("email",loginForm.get().email);
        newUserTest= Session.open(session("email"), dateFormat.format(date),true);
        JsonNode personJson = Json.toJson(newUserTest);
        // Logger.debug("UserTest created: " + regForm.get().name + " " + regForm.get().email);
        return ok(views.html.officiant.mainpage.render(session("email"),dateFormat.format(date).toString()));

        //return ok( registerSuccess.render( ));

    }

    public static Result update() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48
        Long san = newUserTest;
        if(!(san ==null)){
            Session newUserTest= Session.update(san, dateFormat.format(date),false);
            JsonNode personJson = Json.toJson(newUserTest);
            // Logger.debug("UserTest created: " + regForm.get().name + " " + regForm.get().email);
            return ok(personJson);}
        else {
            return ok((Content) badRequest());
        }
        //return ok( registerSuccess.render( ));

    }

}
