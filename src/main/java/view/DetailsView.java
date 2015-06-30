package view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;

import contact.Contact;
import contact.ContactOperations;

@UIScope
@SpringView(name = DetailsView.NAME)
public class DetailsView extends VerticalLayout implements View, Serializable{
    
    private static final long serialVersionUID = 1L;
    
    public static final String NAME = "details";
    public static String accountEmail = "";
    
    private final Label text = new Label("Your account details:");
    
    private final Label sessionId = new Label();
    
    private final Label TCKno = new Label();
    private final Label name = new Label();
    private final Label surname = new Label();
    private final Label password = new Label();
    private final Label email = new Label();
    private final Label age = new Label();
    private final Label gender = new Label();
    private final Label height = new Label();
    private final Label weight = new Label();
    
    private final Button back = new Button("Go Back");
    private final Button logout = new Button("Logout");
    
    private final VerticalLayout vlayout = new VerticalLayout(text);
    private final VerticalLayout dlayout = new VerticalLayout(sessionId, TCKno, name, surname, password,
                                               email, age, gender, height, weight);
    private final HorizontalLayout buttonLayout = new HorizontalLayout(back, logout);
    
    private final VerticalLayout mainLayout = new VerticalLayout(vlayout, dlayout, buttonLayout);
    
    private final ContactOperations contactOperations = new ContactOperations();
    private final ArrayList<Contact> contacts = contactOperations.getContacts();
    
    @PostConstruct
    public void init(){
        configureComponents();
        buildLayout();
        //getUI().getSession().getAttribute("account");
    }
    private void configureComponents(){
        configureButtons();
        
        sessionId.setValue("The session id is: " + VaadinSession.getCurrent().getSession().getId());
        
        for(int i = 0; i < contacts.size(); i++){
            if(contacts.get(i).getEmail().equals(accountEmail)){
                TCKno.setValue("TCK no: " + contacts.get(i).getTCKno());
                name.setValue("Name: " + contacts.get(i).getFirstName());
                surname.setValue("Surname: " + contacts.get(i).getLastName());
                password.setValue("Password: " + contacts.get(i).getPassword());
                email.setValue("E-Mail: " + accountEmail);
                age.setValue("Age: " + Short.toString(contacts.get(i).getAge()));
                gender.setValue("Gender: " + contacts.get(i).getGender());
                height.setValue("Height: " + Short.toString(contacts.get(i).getHeight()));
                weight.setValue("Weight: " + Short.toString(contacts.get(i).getWeight()));
            }
        }
    }
    private void configureButtons(){
        
        back.addClickListener(e->{
            getUI().getNavigator().navigateTo(ContactBookView.NAME);
        });
        logout.addClickListener(e->{
            getUI().getSession().setAttribute("account", null);
            Notification.show("You have logged out", Notification.Type.ERROR_MESSAGE);
            getUI().getNavigator().navigateTo(LoginView.NAME);
        });
    }
    private void buildLayout(){
        
        dlayout.setMargin(new MarginInfo(true, true, true, true));
        dlayout.setComponentAlignment(sessionId, Alignment.MIDDLE_CENTER);
        dlayout.setComponentAlignment(TCKno, Alignment.MIDDLE_CENTER);
        dlayout.setComponentAlignment(name, Alignment.MIDDLE_CENTER);
        dlayout.setComponentAlignment(surname, Alignment.MIDDLE_CENTER);
        dlayout.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
        dlayout.setComponentAlignment(email, Alignment.MIDDLE_CENTER);
        dlayout.setComponentAlignment(age, Alignment.MIDDLE_CENTER);
        dlayout.setComponentAlignment(gender, Alignment.MIDDLE_CENTER);
        dlayout.setComponentAlignment(height, Alignment.MIDDLE_CENTER);
        dlayout.setComponentAlignment(weight, Alignment.MIDDLE_CENTER);
        
        buttonLayout.setMargin(new MarginInfo(true, true, true, true));
        buttonLayout.setComponentAlignment(back, Alignment.MIDDLE_LEFT);
        buttonLayout.setComponentAlignment(logout, Alignment.MIDDLE_LEFT);
        
        vlayout.setMargin(new MarginInfo(true, true, true, true));
        vlayout.setComponentAlignment(text, Alignment.MIDDLE_CENTER);
        
        mainLayout.setMargin(new MarginInfo(true, true, true, true));
        mainLayout.setComponentAlignment(vlayout, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(dlayout, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_LEFT);
        
        addComponent(mainLayout);
        setComponentAlignment(mainLayout, Alignment.MIDDLE_LEFT);
    }    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){}    
}