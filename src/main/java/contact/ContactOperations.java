package contact;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.*;
import com.vaadin.ui.Notification.Type;
import java.io.Serializable;
import java.util.ArrayList;
import org.hibernate.Session;

import hibernate.*;

@SpringComponent
@VaadinSessionScope
public class ContactOperations implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private final ArrayList<Contact> contacts = new ArrayList<>();
    private transient Session session; // should not be serialized

    public ContactOperations(){
        loadContacts();
    }
    
    public void save(Contact c){
                
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
    public void edit(Contact c, Contact oldC){
        
        // needs urgent fix
        
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        Contactbase cb = (Contactbase)session.load(Contactbase.class, oldC.getConId());
        Detail cd = (Detail)session.load(Detail.class, oldC.getDetId());
        
        cb.setConId(oldC.getConId()); // necessary?
        cb.setTckno(c.getTCKno());
        cb.setName(c.getFirstName());
        cb.setSurname(c.getLastName());
        cb.setPassword(c.getPassword());
        
        cd.setDetId(oldC.getDetId()); // necessary?
        cd.setEmail(c.getEmail());
        cd.setAge(c.getAge());
        cd.setGender(c.getGender());
        cd.setHeight(c.getHeight());
        cd.setWeight(c.getWeight());
        
        try{
            session.update(cb);
            session.update(cd);
            session.getTransaction().commit();
        }catch(Exception e){
            Notification.show("Failed to edit contact", Notification.Type.ERROR_MESSAGE);
        }
        
        session.close();
        
        Notification.show("Contact edited", Type.TRAY_NOTIFICATION);
    }
    private void loadContacts(){
        
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        ArrayList<Contactbase> cbList = new ArrayList<>();
        ArrayList<Detail> cdList = new ArrayList<>();
        
        cbList.addAll(session.createCriteria(Contactbase.class).list());
        cdList.addAll(session.createCriteria(Detail.class).list());
        if(cbList.size() == cdList.size()){
            for(int i = 0; i < cbList.size(); i++){ // base and detail sizes should be the same
            
                Contactbase cb = new Contactbase(cbList.get(i).getTckno(), cbList.get(i).getName(), 
                                                 cbList.get(i).getSurname(), cbList.get(i).getPassword());
            
                Detail cd = new Detail(cbList.get(i), cdList.get(i).getEmail(),
                                       cdList.get(i).getAge(), cdList.get(i).getGender(),
                                       cdList.get(i).getHeight(), cdList.get(i).getWeight());
                        
                Contact c = new Contact(cb.getTckno(), cb.getName(), cb.getSurname(),
                            cd.getEmail(), cd.getAge(), cd.getGender(), cd.getHeight(), cd.getWeight(), cb.getPassword());
                
                c.setConId(cbList.get(i).getConId());
                c.setDetId(cdList.get(i).getDetId());
                
                contacts.add(c);
            } 
        }
        else{
            Notification.show("Error loading contacts", Type.ERROR_MESSAGE);
        }
        
        session.close();
    }
    public ArrayList<Contact> getContacts(){
        return contacts;
    }
}