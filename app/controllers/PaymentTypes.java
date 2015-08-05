package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import play.*;
import models.*;
import play.mvc.*;
import play.data.*;
import static play.data.Form.*;
import views.html.paymentType.*;

@Security.Authenticated(Secured.class)
public class PaymentTypes extends Controller{

    public static Form<PaymentType> paymentTypeForm=form(PaymentType.class);

    public static Result index() {
        if(Secured.isAdmin()) {
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            PaymentType.findAll(),
                            paymentTypeForm
                    )
            );
        }else{
            return forbidden();
        }


    }


    public static Result newPaymentType() throws ParseException{
        if(Secured.isAdmin()) {
            //Form<PaymentType> filledForm=form(PaymentType.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();
            DateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);

            int p_id=PaymentType.create(filledForm.get("title"), filledForm.get("description"),true);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Создан новый вид платежа:"+p_id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description")+"\nСтатус:";
            Event event=new Event("PAYMENT_TYPE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            PaymentType.findAll(),
                            paymentTypeForm
                    )
            );
        }else{
            return forbidden();
        }



    }




    public static Result paymentTypePage(int id) {
        if(Secured.isAdmin()) {
            session("paymentType",id+"");
            return ok(
                    item.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            PaymentType.find.byId(id)
                    )
            );
        }else{
            return forbidden();
        }




    }


    public static Result updatePaymentType(int id) throws ParseException{
        if(Secured.isAdmin()) {
            //Form<PaymentType> filledForm=form(PaymentType.class).bindFromRequest();
            DynamicForm filledForm=form().bindFromRequest();

            SimpleDateFormat sdf2=new SimpleDateFormat("dd/MM/yyyy HH:mm");
            sdf2.setLenient(false);


            PaymentType paymentType=PaymentType.find.ref(id);

            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String desc="Изменен вид платежа:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ paymentType.title+"\nОписание:"+ paymentType.description+"\nСтатус:"+ paymentType.status
                    +"\nНовые значения:\nНазвание:"+ filledForm.get("title")+"\nОписание:"+ filledForm.get("description");

            Event event=new Event("PAYMENT_TYPE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            PaymentType.update(id, filledForm.get("title"), filledForm.get("description"),true);
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            PaymentType.findAll(),
                            paymentTypeForm
                    )
            );
        }else{
            return forbidden();
        }



    }

    public static Result deletePaymentType(int id) {
        if(Secured.isAdmin()) {
            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            PaymentType paymentType=PaymentType.find.ref(id);

            String desc="Удален вид платежа:"+id+" в:"+df.format(new Date())+" пользователем:"+request().username()+"\nСтарые значения:\nНазвание:"+ paymentType.title+"\nОписание:"+ paymentType.description+"\nСтатус:"+ paymentType.status;

            Event event=new Event("PAYMENT_TYPE",desc,"","",new Date(),User.find.where().eq("email", request().username()).findUnique());
            PaymentType.delete(id);
            event.save();
            Logger.debug(desc);
            return ok(
                    index.render(
                            User.find.where().eq("email", request().username()).findUnique(),
                            PaymentType.findAll(),
                            paymentTypeForm
                    )
            );
        }else{
            return forbidden();
        }



    }

}