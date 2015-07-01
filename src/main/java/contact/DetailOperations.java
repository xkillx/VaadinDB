package contact;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import java.io.Serializable;
import org.hibernate.Session;
import java.util.List;

import hibernate.*;

@SpringComponent
@UIScope
public class DetailOperations implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private transient Session session;
    
    public DetailOperations(){
        
    }
    public Contact retrieveDetails(String email){
        
        Contact c = new Contact();
        
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        List<Detail> allDetails = session.createCriteria(Detail.class).list();
        
        for(int i = 0; i < allDetails.size(); i++){
            if(allDetails.get(i).getEmail().equals(email)){
                c.setConId(allDetails.get(i).getContactbase().getConId());
                c.setAge(allDetails.get(i).getAge());
                c.setGender(allDetails.get(i).getGender());
                c.setHeight(allDetails.get(i).getHeight());
                c.setWeight(allDetails.get(i).getWeight());
                c.setEmail(email);
                c.setDetId(allDetails.get(i).getDetId());
                c.setFirstName(allDetails.get(i).getContactbase().getName());
                c.setLastName(allDetails.get(i).getContactbase().getSurname());
                c.setPassword(allDetails.get(i).getContactbase().getPassword());
                c.setTCKno(allDetails.get(i).getContactbase().getTckno());
            }
        }
        
        session.close();
        
        return c;
    }
}