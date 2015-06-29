package hibernate;

import java.io.Serializable;

public class Detail implements Serializable{

    private static final long serialVersionUID = 1L;

    private Integer detId;
    private Contactbase contactbase;
    private String email;
    private short age;
    private String gender;
    private short height;
    private short weight;

    public Detail(){}

    public Detail(String email, short age, String gender, short height, short weight){
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }
    public Detail(Contactbase contactbase, String email, short age, String gender, short height, short weight){
        this.contactbase = contactbase;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }
   
    public Integer getDetId(){
        return this.detId;
    }
    public void setDetId(Integer detId){
        this.detId = detId;
    }
    
    public Contactbase getContactbase(){
        return this.contactbase;
    }
    public void setContactbase(Contactbase contactbase){
        this.contactbase = contactbase;
    }
    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public short getAge(){
        return this.age;
    }
    public void setAge(short age){
        this.age = age;
    }
    public String getGender(){
        return this.gender;
    }
    public void setGender(String gender){
        this.gender = gender;
    }
    public short getHeight(){
        return this.height;
    }
    public void setHeight(short height){
        this.height = height;
    }
    public short getWeight(){
        return this.weight;
    }
    public void setWeight(short weight){
        this.weight = weight;
    }
}