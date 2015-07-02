package view;

import contact.ContactOperations;
import contact.Contact;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.grid.HeightMode;
//import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import java.io.Serializable;
import java.util.ArrayList;
//import javax.annotation.PostConstruct;

@UIScope
//@SpringView(name = ContactBookView.NAME)
public class ContactBookView extends VerticalLayout implements View, Serializable{
    
    private static final long serialVersionUID = 1L;
    
    // navigate name
    public static final String NAME = "main";
    
    private final Button toDetails = new Button("Details Page");

    // user inputs
    private final TextField tckno = new TextField("TCK No");
    private final TextField firstName = new TextField("First Name");
    private final TextField lastName = new TextField("Last name");
    private final TextField email = new TextField("Email");
    
    private final TextField age = new TextField("Age");
    private final ComboBox gender = new ComboBox("Gender");
    private final TextField height = new TextField("Height");
    private final TextField weight = new TextField("Weight");
    
    private final TextField editTckno = new TextField();
    private final TextField editFirst = new TextField();
    private final TextField editLast = new TextField();
    private final TextField editEmail = new TextField();
    
    private final TextField editAge = new TextField();
    private final ComboBox editGender = new ComboBox();
    private final TextField editHeight = new TextField();
    private final TextField editWeight = new TextField();
    
    // logout
    private final Button logout = new Button("Logout");
    
    // create and edit contact
    private final Button newContact = new Button("Add new contact");
    private final Button editContact = new Button("Edit");
    private final Button saveContact = new Button("Save");
    private final Button cancelEdit = new Button("Cancel");
    
    // accordion preparation, put all the beginning properties into a vertical layout
    private final VerticalLayout textBoxLayout = new VerticalLayout(tckno, firstName, lastName, email, newContact);
    private final VerticalLayout detailsLayout = new VerticalLayout(age, gender, height, weight);
    private final HorizontalLayout logoutLayout = new HorizontalLayout(logout, toDetails);
    private final HorizontalLayout enterInfoLayout = new HorizontalLayout(textBoxLayout, detailsLayout, logoutLayout);
    
    
    // accordion to contain contacts grid
    private final Accordion tableAccordion = new Accordion();
    
    // bind all contacts to a container
    private final ContactOperations contactOperations = new ContactOperations(); // for file operations
    private final ArrayList<Contact> contacts = contactOperations.getContacts();
    private final BeanItemContainer<Contact> container = new BeanItemContainer<>(Contact.class, contacts);
    
    // create the grid
    private final Grid contactList = new Grid(container);
    
    // text label for displaying selected information
    private final Label personalInfo = new Label();
    
    private final Label tckLabel = new Label();
    private final Label nameLabel = new Label();
    private final Label surnameLabel = new Label();
    private final Label emailLabel = new Label();
    
    private final Label ageLabel = new Label();
    private final Label genderLabel = new Label();
    private final Label heightLabel = new Label();
    private final Label weightLabel = new Label();
    
    private final Label tckEditableLabel = new Label();
    private final Label nameEditableLabel = new Label();
    private final Label surnameEditableLabel = new Label();
    private final Label emailEditableLabel = new Label();
    
    private final Label ageEditableLabel = new Label();
    private final Label genderEditableLabel = new Label();
    private final Label heightEditableLabel = new Label();
    private final Label weightEditableLabel = new Label();
    
    private final VerticalLayout personalLayout = new VerticalLayout(personalInfo);
    private final VerticalLayout editButtonLayout = new VerticalLayout(editContact, saveContact, cancelEdit);
    
    private final HorizontalLayout tckLayout = new HorizontalLayout(tckLabel, tckEditableLabel, editTckno);
    private final HorizontalLayout nameLayout = new HorizontalLayout(nameLabel, nameEditableLabel, editFirst);
    private final HorizontalLayout surnameLayout = new HorizontalLayout(surnameLabel, surnameEditableLabel, editLast);
    private final HorizontalLayout emailLayout = new HorizontalLayout(emailLabel, emailEditableLabel, editEmail);
    
    private final HorizontalLayout ageLayout = new HorizontalLayout(ageLabel, ageEditableLabel, editAge);
    private final HorizontalLayout genderLayout = new HorizontalLayout(genderLabel, genderEditableLabel, editGender);
    private final HorizontalLayout heightLayout = new HorizontalLayout(heightLabel, heightEditableLabel, editHeight);
    private final HorizontalLayout weightLayout = new HorizontalLayout(weightLabel, weightEditableLabel, editWeight);
    
    private final VerticalLayout editFieldLayout = new VerticalLayout(tckLayout, nameLayout, surnameLayout, emailLayout);
    private final VerticalLayout editFieldLayout2 = new VerticalLayout(ageLayout, genderLayout, heightLayout, weightLayout);
    private final HorizontalLayout editLayout = new HorizontalLayout(editFieldLayout, editFieldLayout2, editButtonLayout);
    
    // contact object for selecting
    private Contact c;
    
//    @PostConstruct
//    public void init(){
//        configureComponents();
//        buildLayout();
//    }
    
    public ContactBookView(){
        configureComponents();
        buildLayout();
        
    }
    private void configureComponents(){
        
        configureTextFields();
        configureButtons();
        
        gender.setNewItemsAllowed(false);
        gender.setNullSelectionAllowed(false);
        gender.setTextInputAllowed(false);
        gender.addItem("Male");
        gender.addItem("Female");
        editGender.setNewItemsAllowed(false);
        editGender.setTextInputAllowed(false);
        editGender.setNullSelectionAllowed(false);
        editGender.addItem("Male");
        editGender.addItem("Female");
        
        // put contact list into accordion
        tableAccordion.addTab(enterInfoLayout, "Enter your information", null);
        //tableAccordion.addTab(contactList, "Display Contact Information", null);
        
        // contact list config
        contactList.setHeightMode(HeightMode.ROW);
        contactList.setHeightByRows(15.0);
        contactList.setColumnOrder("TCKno", "firstName", "lastName", "email", "age", "gender", "height", "weight");
        contactList.removeColumn("conId");
        contactList.removeColumn("detId");
        contactList.removeColumn("password");
        
        contactList.setSizeFull();
        contactList.setSelectionMode(Grid.SelectionMode.SINGLE);
        contactList.setImmediate(true);
        contactList.addSelectionListener((e)->{
            
            c = (Contact)contactList.getSelectedRow();
            
            personalInfo.setCaption("Personal Information");
            tckLabel.setValue("TCK No: ");
            nameLabel.setValue("First Name: ");
            surnameLabel.setValue("Last Name: ");
            emailLabel.setValue("E-Mail Address: ");
            ageLabel.setValue("Age: ");
            genderLabel.setValue("Gender: ");
            heightLabel.setValue("Height: ");
            weightLabel.setValue("Weight: ");
            
            tckEditableLabel.setValue(c.getTCKno());
            nameEditableLabel.setValue(c.getFirstName());
            surnameEditableLabel.setValue(c.getLastName());
            emailEditableLabel.setValue(c.getEmail());
            ageEditableLabel.setValue(Integer.toString(c.getAge()));
            genderEditableLabel.setValue(c.getGender());
            heightEditableLabel.setValue(Integer.toString(c.getHeight()));
            weightEditableLabel.setValue(Integer.toString(c.getWeight()));            
            
            editTckno.setVisible(false);
            editFirst.setVisible(false);
            editLast.setVisible(false);
            editEmail.setVisible(false);
            editAge.setVisible(false);
            editGender.setVisible(false);
            editHeight.setVisible(false);
            editWeight.setVisible(false);
            
            tckEditableLabel.setVisible(true);
            nameEditableLabel.setVisible(true);
            surnameEditableLabel.setVisible(true);
            emailEditableLabel.setVisible(true);
            ageEditableLabel.setVisible(true);
            genderEditableLabel.setVisible(true);
            heightEditableLabel.setVisible(true);
            weightEditableLabel.setVisible(true);
            
            saveContact.setVisible(false);
            cancelEdit.setVisible(false);
            editContact.setVisible(true);
        });
        
    }
    private void buildLayout(){
        
        // MarginInfo(top right bottom left)
        logoutLayout.setComponentAlignment(logout, Alignment.MIDDLE_CENTER);
        logoutLayout.setMargin(new MarginInfo(true, true, true, true));
        personalLayout.setMargin(new MarginInfo(true, false, false, true));
        
        editFieldLayout.setMargin(new MarginInfo(false, false, false, true)); // wraps tck, name, surname, email
        editFieldLayout2.setMargin(new MarginInfo(false, false, false, true)); // wraps phone, age, gender, weight, height
        editButtonLayout.setMargin(new MarginInfo(true, true, true, true)); // wraps editing buttons
        editLayout.setMargin(new MarginInfo(true, false, false, false)); // wraps editFieldLayout, editFieldLayout2, editButtonLayout

        VerticalLayout mainLayout = new VerticalLayout(tableAccordion, contactList, personalLayout, editLayout);
        
        mainLayout.setMargin(new MarginInfo(true, true, true, true));
        
        //mainLayout.setSizeFull();
        //mainLayout.setExpandRatio(layout2, 2);
        
        addComponent(mainLayout);
        
    }
    private void configureTextFields(){
        
        // hints for text fields
        tckno.setInputPrompt("Enter TCK no");
        firstName.setInputPrompt("Enter name");
        lastName.setInputPrompt("Enter surname");
        email.setInputPrompt("Enter e-mail");
        gender.setInputPrompt("Select gender");
        age.setInputPrompt("Enter age");
        height.setInputPrompt("Enter height");
        weight.setInputPrompt("Enter weight");
        
        // bigger text boxes
        tckno.setWidth("200px");
        firstName.setWidth("200px");
        lastName.setWidth("200px");
        email.setWidth("200px");
        gender.setWidth("200px");
        age.setWidth("200px");
        height.setWidth("200px");
        weight.setWidth("200px");
        
        tckno.setMaxLength(11);
        firstName.setMaxLength(20);
        lastName.setMaxLength(20);
        email.setMaxLength(50);
        age.setMaxLength(3);
        height.setMaxLength(3);
        weight.setMaxLength(3);
        
        // boxes cannot be left empty
        tckno.setRequired(true);
        firstName.setRequired(true);
        lastName.setRequired(true);
        email.setRequired(true);
        gender.setRequired(true);
        age.setRequired(true);
        height.setRequired(true);
        weight.setRequired(true);
        
        tckno.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+") || event.getText().length() != 11){
                tckno.setComponentError(new UserError("Must be a valid TCK no"));
            }
            else{
                tckno.setComponentError(null);
            }
        });
        firstName.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[a-zA-ZşŞıİçÇöÖüÜğĞ]+")){ // Turkish chars included
                firstName.setComponentError(new UserError("Must be a valid name"));
            }
            else{
                firstName.setComponentError(null);
            }
        });
        lastName.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[a-zA-ZşŞıİçÇöÖüÜğĞ]+")){
                lastName.setComponentError(new UserError("Must be a valid last name"));
            }
            else{
                lastName.setComponentError(null);
            }
        });
        email.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")){
                email.setComponentError(new UserError("Must be a valid e-mail address"));
            }
            else{
                email.setComponentError(null);
            }
        });
        age.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+")){
                age.setComponentError(new UserError("Must be a valid age"));
            }
            else{
                age.setComponentError(null);
            }
        });
        height.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+")){
                height.setComponentError(new UserError("Must be a valid height"));
            }
            else{
                height.setComponentError(null);
            }
        });
        weight.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+")){
                weight.setComponentError(new UserError("Must be a valid weight"));
            }
            else{
                weight.setComponentError(null);
            }
        });
        tckno.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        firstName.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        lastName.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        email.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        age.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        height.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        weight.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        
        // hints for text fields
        editTckno.setInputPrompt("Edit TCK no");
        editFirst.setInputPrompt("Edit first name");
        editLast.setInputPrompt("Edit last name");
        editEmail.setInputPrompt("Edit e-mail");
        editGender.setInputPrompt("Select gender");
        editAge.setInputPrompt("Edit age");
        editHeight.setInputPrompt("Edit height");
        editWeight.setInputPrompt("Edit weight");
        
        // bigger text boxes
        editTckno.setWidth("200px");
        editFirst.setWidth("200px");
        editLast.setWidth("200px");
        editEmail.setWidth("200px");
        editAge.setWidth("200px");
        editHeight.setWidth("200px");
        editWeight.setWidth("200px");
        
        editTckno.setMaxLength(11);
        editFirst.setMaxLength(20);
        editLast.setMaxLength(20);
        editEmail.setMaxLength(50);
        editAge.setMaxLength(3);
        editHeight.setMaxLength(3);
        editWeight.setMaxLength(3);
        
        // boxes cannot be left empty
        editTckno.setRequired(true);
        editFirst.setRequired(true);
        editLast.setRequired(true);
        editEmail.setRequired(true);
        editGender.setRequired(true);
        editAge.setRequired(true);
        editHeight.setRequired(true);
        editWeight.setRequired(true);
        
        editTckno.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+") || event.getText().length() != 11){
                editTckno.setComponentError(new UserError("Must be a valid TCK no"));
            }
            else{
                editTckno.setComponentError(null);
            }
        });
        editFirst.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[a-zA-ZşŞıİçÇöÖüÜğĞ]+")){
                editFirst.setComponentError(new UserError("Must be a valid name"));
            }
            else{
                editFirst.setComponentError(null);
            }
        });
        editLast.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[a-zA-ZşŞıİçÇöÖüÜğĞ]+")){
                editLast.setComponentError(new UserError("Must be a valid last name"));
            }
            else{
                editLast.setComponentError(null);
            }
        });
        editEmail.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")){
                editEmail.setComponentError(new UserError("Must be a valid e-mail address"));
            }
            else{
                editEmail.setComponentError(null);
            }
        });
        editAge.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+")){
                editAge.setComponentError(new UserError("Must be a valid age"));
            }
            else{
                editAge.setComponentError(null);
            }
        });
        editHeight.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+")){
                editHeight.setComponentError(new UserError("Must be a valid height"));
            }
            else{
               editHeight.setComponentError(null);
            }
        });
        editWeight.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+")){
                editWeight.setComponentError(new UserError("Must be a valid weight"));
            }
            else{
                editWeight.setComponentError(null);
            }
        });
        
        editTckno.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        editFirst.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        editLast.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        editEmail.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        editAge.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        editHeight.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        editWeight.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        
        editTckno.setVisible(false);
        editFirst.setVisible(false);
        editLast.setVisible(false);
        editEmail.setVisible(false);
        editGender.setVisible(false);
        editAge.setVisible(false);
        editHeight.setVisible(false);
        editWeight.setVisible(false);
    }
    private void configureButtons(){
        
        // button config
        newContact.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        newContact.addClickListener(e->{
            if(tckno.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() ||
               age.isEmpty() || height.isEmpty() || weight.isEmpty()){
                Notification.show("All fields must be filled", Notification.Type.ERROR_MESSAGE);
            }
            else if(tckno.getComponentError() != null ||firstName.getComponentError() != null || lastName.getComponentError() != null ||
                    email.getComponentError() != null || age.getComponentError() != null ||
                    height.getComponentError() != null || weight.getComponentError() != null){
                Notification.show("At least one of the fields is invalid", Notification.Type.ERROR_MESSAGE);
            }
            else{
                Contact con = new Contact(tckno.getValue(), firstName.getValue(), lastName.getValue(), 
                                          email.getValue(), Short.parseShort(age.getValue()), gender.getValue().toString(), 
                                          Short.parseShort(height.getValue()), Short.parseShort(weight.getValue()), "0");
                contactOperations.save(con);
                contacts.add(con);
                container.addBean(con);
                //Broadcaster.broadcast("hellooooooooooo");
                
                Notification.show("Contact saved", Notification.Type.TRAY_NOTIFICATION);

                //tableAccordion.setSelectedTab(contactList);
                tckno.clear();
                firstName.clear();
                lastName.clear();
                email.clear();
                age.clear();
                gender.clear();
                height.clear();
                weight.clear();
                tckno.focus();
            }
        });
        
        editContact.setVisible(false);
        saveContact.setVisible(false);
        cancelEdit.setVisible(false);
        
        // edit button actions        
        editContact.addClickListener(e->{
            
            newContact.removeClickShortcut();
            saveContact.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            
            tckEditableLabel.setVisible(false);
            nameEditableLabel.setVisible(false);
            surnameEditableLabel.setVisible(false);
            emailEditableLabel.setVisible(false);
            ageEditableLabel.setVisible(false);
            genderEditableLabel.setVisible(false);
            heightEditableLabel.setVisible(false);
            weightEditableLabel.setVisible(false);
            
            editTckno.setValue(tckEditableLabel.getValue());
            editFirst.setValue(nameEditableLabel.getValue());
            editLast.setValue(surnameEditableLabel.getValue());
            editEmail.setValue(emailEditableLabel.getValue());
            editAge.setValue(ageEditableLabel.getValue());
            editGender.setValue((String)genderEditableLabel.getValue());
            editHeight.setValue(heightEditableLabel.getValue());
            editWeight.setValue(weightEditableLabel.getValue());
            
            editTckno.setVisible(true);
            editFirst.setVisible(true);
            editLast.setVisible(true);
            editEmail.setVisible(true);
            editAge.setVisible(true);
            editGender.setVisible(true);
            editHeight.setVisible(true);
            editWeight.setVisible(true);
            
            editContact.setVisible(false);
            saveContact.setVisible(true);
            cancelEdit.setVisible(true);
        });
        saveContact.addClickListener(e->{
            if(editTckno.isEmpty() || editFirst.isEmpty() || editLast.isEmpty() || 
               editEmail.isEmpty() || editAge.isEmpty() || editGender.isEmpty() || editHeight.isEmpty() || editWeight.isEmpty()){
                Notification.show("All fields must be filled", Notification.Type.ERROR_MESSAGE);
            }
            else if(editFirst.getComponentError() != null || editLast.getComponentError() != null ||
                    editEmail.getComponentError() != null){
                Notification.show("At least one of the fields is invalid", Notification.Type.ERROR_MESSAGE);
            }
            else{
                BeanItemContainer<Contact> tempContainer = new BeanItemContainer<>(Contact.class); // temp bean for replacement
                
                Contact oldC = new Contact(c.getTCKno(), c.getFirstName(), c.getLastName(),c.getEmail(), c.getAge(), 
                                           c.getGender(), c.getHeight(), c.getWeight(), c.getPassword()); // temp contact for db edit
                oldC.setConId(c.getConId());
                oldC.setDetId(c.getDetId());
                int index = container.indexOfId(c); // find index for bean replacement
                c.setTCKno(editTckno.getValue());
                c.setFirstName(editFirst.getValue());
                c.setLastName(editLast.getValue());
                c.setEmail(editEmail.getValue());
                c.setAge(Short.parseShort(editAge.getValue()));
                c.setGender(editGender.getValue().toString());
                c.setHeight(Short.parseShort(editHeight.getValue()));
                c.setWeight(Short.parseShort(editWeight.getValue()));
                
                contacts.set(index, c);
                
                contactOperations.edit(c, oldC);
                
                for(int i = 0; i < index; i++){
                    tempContainer.addBean(container.getIdByIndex(i));
                }
                tempContainer.addBean(c);
                for(int i = index + 1; i < container.size(); i++){
                    tempContainer.addBean(container.getIdByIndex(i));
                }
                container.removeAllItems();
                for(int i = 0; i < tempContainer.size(); i++){
                    container.addBean(tempContainer.getIdByIndex(i));
                }
                
                saveContact.removeClickShortcut();
                newContact.setClickShortcut(ShortcutAction.KeyCode.ENTER);
                
                editTckno.clear();
                editFirst.clear();
                editLast.clear();
                editEmail.clear();
                editAge.clear();
                editGender.clear();
                editHeight.clear();
                editWeight.clear();
                editTckno.setVisible(false);
                editFirst.setVisible(false);
                editLast.setVisible(false);
                editEmail.setVisible(false);
                editAge.setVisible(false);
                editGender.setVisible(false);
                editHeight.setVisible(false);
                editWeight.setVisible(false);
            
                tckEditableLabel.setVisible(true);
                nameEditableLabel.setVisible(true);
                surnameEditableLabel.setVisible(true);
                emailEditableLabel.setVisible(true);
                ageEditableLabel.setVisible(true);
                genderEditableLabel.setVisible(true);
                heightEditableLabel.setVisible(true);
                weightEditableLabel.setVisible(true);
            
                saveContact.setVisible(false);
                cancelEdit.setVisible(false);
                editContact.setVisible(true);
                
                Notification.show("Contact edited", Notification.Type.TRAY_NOTIFICATION);
            }
        });
        cancelEdit.addClickListener(e->{
            
            saveContact.removeClickShortcut();
            newContact.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            
            editTckno.clear();
            editFirst.clear();
            editLast.clear();
            editEmail.clear();
            editAge.clear();
            editGender.clear();
            editHeight.clear();
            editWeight.clear();
            editTckno.setVisible(false);
            editFirst.setVisible(false);
            editLast.setVisible(false);
            editEmail.setVisible(false);
            editAge.setVisible(false);
            editGender.setVisible(false);
            editHeight.setVisible(false);
            editWeight.setVisible(false);
            
            tckEditableLabel.setVisible(true);
            nameEditableLabel.setVisible(true);
            surnameEditableLabel.setVisible(true);
            emailEditableLabel.setVisible(true);
            ageEditableLabel.setVisible(true);
            genderEditableLabel.setVisible(true);
            heightEditableLabel.setVisible(true);
            weightEditableLabel.setVisible(true);
            
            saveContact.setVisible(false);
            cancelEdit.setVisible(false);
            editContact.setVisible(true);
        });
        toDetails.addClickListener(e->{
            // reload view
            getUI().getNavigator().removeView(DetailsView.NAME);
            getUI().getNavigator().addView(DetailsView.NAME, new DetailsView());
            
            getUI().getNavigator().navigateTo(DetailsView.NAME);
        });
        logout.addClickListener(e->{
            //VaadinService service = getUI().getSession().getService();
            //getUI().getSession().close();
            //getUI().getSession().getSession().invalidate();
            UserInterface.getCurrent().getSession().setAttribute("account", null);
            Notification.show("You have logged out", Notification.Type.ERROR_MESSAGE);
            //VaadinSession.setCurrent(new VaadinSession(new VaadinService()));
            //LoginView.sessionId = VaadinSession.getCurrent().getSession().getId();
            getUI().getNavigator().navigateTo(LoginView.NAME);
        });
    }    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){}
}