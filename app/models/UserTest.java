package models;

/**
 * Created with IntelliJ IDEA.
 * UserTest: SAMSUNG-X120
 * Date: 19.07.15
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class UserTest extends Model{

    @Id
    @Column(length=40, nullable=false)
    public String email;

    public String password;
    public String name;
    public int age;

    public boolean active;

    public UserTest(){
        active=false;
    }

    public UserTest(String name,String email,String password){
        this.email=email;
        this.name=name;
        this.password=password;
        this.active=false;
    }

    public static Finder<String, UserTest> find=new Finder<String, UserTest>(String.class, UserTest.class);

    public static List<UserTest> findAll(){
        return find.all();
    }

    public static UserTest authenticate(String email,String password){
        return find.where().eq("active", true).eq("email", email).eq("password", password).findUnique();
    }

    public static UserTest create(String name,String email,String password){
        UserTest newUserTest=new UserTest(name, email, password);

        newUserTest.save();
		return newUserTest;
    }
}
