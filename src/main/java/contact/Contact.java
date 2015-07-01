package contact;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import java.io.Serializable;

@SpringComponent
@UIScope
public class Contact implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private Integer conId;
    private Integer detId;
    private String TCKno;
    private String firstName;
    private String lastName;
    private String email;
    private short age;
    private String gender;
    private short height;
    private short weight;
    private String password;
    
    public Contact(){}

    public Contact(String tck, String f, String l, String e, short a, String g, short h, short w, String p){
        TCKno = tck;
        firstName = f;
	lastName = l;
	email = e;
        age = a;
        gender = g;
        height = h;
        weight = w;
        password = p;
    }
    
    public Integer getConId(){
        return conId;
    }
    public void setConId(Integer conId){
        this.conId = conId;
    }
    public Integer getDetId(){
        return detId;
    }
    public void setDetId(Integer detId){
        this.detId = detId;
    }
    
    public String getTCKno(){
        return TCKno;
    }
    public void setTCKno(String TCKno){
        this.TCKno = TCKno;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public short getAge(){
        return age;
    }
    public void setAge(short age){
        this.age = age;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public short getHeight(){
        return height;
    }
    public void setHeight(short height){
        this.height = height;
    }
    public short getWeight(){
        return weight;
    }
    public void setWeight(short weight){
        this.weight = weight;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }
}