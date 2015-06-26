package hibernate;

import java.util.HashSet;
import java.util.Set;
import java.io.Serializable;

public class Contactbase implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private Integer conId;
    private String tckno;
    private String name;
    private String surname;
    private String password;
    private Set details = new HashSet(0);

    public Contactbase(){}

    public Contactbase(String tckno, String name, String surname, String password){
        this.tckno = tckno;
        this.name = name;
        this.surname = surname;
        this.password = password;
    }
    public Contactbase(String tckno, String name, String surname, String password, Set details){
        this.tckno = tckno;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.details = details;
    }
   
    public Integer getConId(){
        return this.conId;
    }
    public void setConId(Integer conId){
        this.conId = conId;
    }
    
    public String getTckno(){
        return this.tckno;
    }
    public void setTckno(String tckno){
        this.tckno = tckno;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getSurname(){
        return this.surname;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public Set getDetails(){
        return this.details;
    }
    public void setDetails(Set details){
        this.details = details;
    }
}