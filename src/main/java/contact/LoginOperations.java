package contact;

import com.vaadin.ui.Notification;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;

import hibernate.*;


public class LoginOperations implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private transient Session session; // should not be serialized
    
    public LoginOperations(){
        
    }
    public int login(String email, String password){
        boolean match = false;
        int id = 0;
        
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        List<Detail> allDetails = session.createCriteria(Detail.class).list();
        
        for(int i = 0; i < allDetails.size(); i++){
            if(allDetails.get(i).getEmail().equals(email)){
                id = allDetails.get(i).getContactbase().getConId();
            }
        }
        try{
            Contactbase passCheck = (Contactbase)session.get(Contactbase.class, id);
            if(passCheck.getPassword().equals(password)){
                match = true;
            } 
        }catch(NullPointerException e){}             
        
        session.close();
        
        if(id == 0){
            return -1; // email does not exist
        }
        else if(!match){
            return 0; // password does not match
        }
        else{
            return 1; // login success
        }
    }
    public void register(Contact c){
        Contactbase cb = new Contactbase(c.getTCKno(), c.getFirstName(), c.getLastName(), c.getPassword());
        Detail cd = new Detail(cb, c.getEmail(), c.getAge(), 
                               c.getGender(), c.getHeight(), c.getWeight());
        
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        try{
            session.save(cb);
            session.save(cd);
            session.getTransaction().commit();
        }catch(Exception e){
            Notification.show("Contact already exists", Notification.Type.ERROR_MESSAGE);
        }
        
        session.close();
    }
    public String check(String tckno, String name, String surname){
        
        String password = "";
        
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        List<Contactbase> allContacts = session.createCriteria(Contactbase.class).list();
        
        for(int i = 0; i < allContacts.size(); i++){
            if(allContacts.get(i).getTckno().equals(tckno) && allContacts.get(i).getName().equals(name)
               && allContacts.get(i).getSurname().equals(surname)){
                password = allContacts.get(i).getPassword();
            }
        }
        
        session.close();
        
        return password;
    }
}