package view;

import com.vaadin.event.FieldEvents;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import java.io.Serializable;

import contact.*;

public class LoginView extends VerticalLayout implements View, Serializable{
    
    private static final long serialVersionUID = 1L;
    
    // navigate name (empty string for main view)
    public static final String NAME = "";
    
    public static String sessionId = "";
        
    // user inputs
    private final TextField email = new TextField("E-Mail Address");
    private final PasswordField password = new PasswordField("Password");
    
    private final TextField regTckno = new TextField("TCK No");
    private final TextField regName = new TextField("First Name");
    private final TextField regSurname = new TextField("Last Name");
    private final TextField regEmail = new TextField("E-Mail Address");
    private final PasswordField regPassword = new PasswordField("Password");
    
    private final TextField regAge = new TextField("Age");
    private final ComboBox regGender = new ComboBox("Gender");
    private final TextField regHeight = new TextField("Height");
    private final TextField regWeight = new TextField("Weight");
    
    private final TextField forgotTckno = new TextField("TCK No");
    private final TextField forgotName = new TextField("First Name");
    private final TextField forgotSurname = new TextField("Last Name");
    
    // buttons
    private final Button login = new Button("Login");
    private final Button forgotpass = new Button("Forgot password");
    private final Button register = new Button("Register");
    
    private final Button regConfirm = new Button("Confirm");
    private final Button regClear = new Button("Clear fields");
    private final Button regCancel = new Button("Cancel");
    
    private final Button forgotSend = new Button("Send");
    private final Button forgotClear = new Button("Clear fields");
    private final Button forgotCancel = new Button("Cancel");
    
    // wrap components
    private final VerticalLayout fieldLayout = new VerticalLayout(email, password);
    private final HorizontalLayout buttonLayout = new HorizontalLayout(forgotpass, register);
    
    private final VerticalLayout regLayout1 = new VerticalLayout(regTckno, regName, regSurname);
    private final VerticalLayout regLayout2 = new VerticalLayout(regEmail, regPassword, regAge);
    private final VerticalLayout regLayout3 = new VerticalLayout(regGender, regHeight, regWeight);
    private final HorizontalLayout regFieldLayout = new HorizontalLayout(regLayout1, regLayout2, regLayout3);
    private final HorizontalLayout regButtonLayout = new HorizontalLayout(regClear, regCancel);
    private final VerticalLayout registerLayout = new VerticalLayout(regFieldLayout, regConfirm, regButtonLayout);
    
    private final VerticalLayout forgotFieldLayout = new VerticalLayout(forgotTckno, forgotName, forgotSurname);
    private final HorizontalLayout forgotButtonLayout = new HorizontalLayout(forgotClear, forgotCancel);
    private final VerticalLayout forgotLayout = new VerticalLayout(forgotFieldLayout, forgotSend, forgotButtonLayout);
    
    // object for logging in, registering and retrieving forgotten password
    private final LoginOperations loginOperations = new LoginOperations();
    
    public LoginView(){
        configureComponents();
        buildLayout();
    }
    private void configureComponents(){
        configureButtons();
        configureTextFields();
        sessionId = VaadinSession.getCurrent().getSession().getId();
    }
    private void configureButtons(){
        
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.focus();
        
        login.addClickListener(e->{
            if(email.isEmpty()){
                Notification.show("Please enter e-mail" + sessionId, Notification.Type.ERROR_MESSAGE);
            }
            else if(email.getComponentError() != null){
                Notification.show("Enter a valid e-mail", Notification.Type.ERROR_MESSAGE);
            }
            else if(password.isEmpty()){
                Notification.show("Please enter a password", Notification.Type.ERROR_MESSAGE);
            }
            else{
                int checkLogin = loginOperations.login(email.getValue(), password.getValue());
                if(checkLogin == -1){
                    Notification.show("E-Mail does not exist", Notification.Type.ERROR_MESSAGE);
                }
                else if(checkLogin == 0){
                    Notification.show("Password does not match", Notification.Type.ERROR_MESSAGE);
                }
                else{
                    // login success
                    Notification.show("Login success", Notification.Type.ERROR_MESSAGE);
                    getUI().getSession().setAttribute("account", email.getValue());
                    getUI().getNavigator().navigateTo(ContactBookView.NAME);
                }
            }
        });
        forgotpass.addClickListener(e->{
            login.removeClickShortcut();
            regConfirm.removeClickShortcut();
            forgotSend.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            forgotSend.focus();
            registerLayout.setVisible(false);
            forgotLayout.setVisible(true);
        });
        register.addClickListener(e->{
            login.removeClickShortcut();
            forgotSend.removeClickShortcut();
            regConfirm.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            regConfirm.focus();
            forgotLayout.setVisible(false);
            registerLayout.setVisible(true);
        });
        
        regConfirm.addClickListener(e->{
            if(regTckno.isEmpty() || regName.isEmpty() || regSurname.isEmpty() || regEmail.isEmpty() ||
               regAge.isEmpty() || regGender.isEmpty() || regHeight.isEmpty() || regWeight.isEmpty() ||
               regPassword.isEmpty()){
                Notification.show("All fields must be filled", Notification.Type.ERROR_MESSAGE);
            }
            else if(regTckno.getComponentError() != null || regName.getComponentError() != null || regSurname.getComponentError() != null ||
                regEmail.getComponentError() != null || regAge.getComponentError() != null || regGender.getComponentError() != null ||
                regHeight.getComponentError() != null || regWeight.getComponentError() != null || regPassword.getComponentError() != null){
                Notification.show("At least one of the fields is invalid", Notification.Type.ERROR_MESSAGE);
            }
            else{
                loginOperations.register(new Contact(regTckno.getValue(), regName.getValue(), regSurname.getValue(), 
                        regEmail.getValue(), Short.parseShort(regAge.getValue()), regGender.getValue().toString(), 
                        Short.parseShort(regHeight.getValue()), Short.parseShort(regWeight.getValue()), regPassword.getValue()));
                
                Notification.show("Registration successful", Notification.Type.TRAY_NOTIFICATION);
            }
        });
        regClear.addClickListener(e->{
            regTckno.clear();
            regName.clear();
            regSurname.clear();
            regEmail.clear();
            regPassword.clear(); // sets it to "null"?
            regPassword.setValue("");
            regAge.clear();
            regGender.clear();
            regHeight.clear();
            regWeight.clear();
        });
        regCancel.addClickListener(e->{
            regConfirm.removeClickShortcut();
            forgotSend.removeClickShortcut();
            login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            login.focus();
            registerLayout.setVisible(false);
        });
        forgotSend.addClickListener(e->{
            if(forgotTckno.isEmpty() || forgotName.isEmpty() || forgotSurname.isEmpty()){
                Notification.show("All fields must be filled", Notification.Type.ERROR_MESSAGE);
            }
            else if(forgotTckno.getComponentError() != null || forgotName.getComponentError() != null || 
                    forgotSurname.getComponentError() != null){
                Notification.show("At least one of the fields is invalid", Notification.Type.ERROR_MESSAGE);
            }
            else{
                String retrievedPassword = loginOperations.check(forgotTckno.getValue(), forgotName.getValue(), forgotSurname.getValue());
                if("".equals(retrievedPassword)){
                    Notification.show("Account does not exist", Notification.Type.ERROR_MESSAGE);
                }
                else{
                    Notification.show("Your password is: " + retrievedPassword, Notification.Type.ERROR_MESSAGE);
                }
            }
        });
        forgotClear.addClickListener(e->{
            forgotTckno.clear();
            forgotName.clear();
            forgotSurname.clear();
        });
        forgotCancel.addClickListener(e->{
            forgotLayout.setVisible(false);
            regConfirm.removeClickShortcut();
            forgotSend.removeClickShortcut();
            login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
            login.focus();             
        });
        
    }
    private void configureTextFields(){
        
        regGender.setNewItemsAllowed(false);
        regGender.setTextInputAllowed(false);
        regGender.setNullSelectionAllowed(false);
        regGender.addItem("Male");
        regGender.addItem("Female");
        
        email.setWidth("200px");
        password.setWidth("200px");
        regTckno.setWidth("200px");
        regName.setWidth("200px");
        regSurname.setWidth("200px");
        regEmail.setWidth("200px");
        regPassword.setWidth("200px");
        regAge.setWidth("200px");
        regGender.setWidth("200px");
        regHeight.setWidth("200px");
        regWeight.setWidth("200px");
        forgotTckno.setWidth("200px");
        forgotName.setWidth("200px");
        forgotSurname.setWidth("200px");
        
        email.setInputPrompt("Enter e-mail");
        password.setInputPrompt("Enter password");
        regTckno.setInputPrompt("Enter TCK no");
        regName.setInputPrompt("Enter name");
        regSurname.setInputPrompt("Enter surname");
        regEmail.setInputPrompt("Enter email");
        regPassword.setInputPrompt("Enter password");
        regAge.setInputPrompt("Enter age");
        regGender.setInputPrompt("Enter gender");
        regHeight.setInputPrompt("Enter height");
        regWeight.setInputPrompt("Enter weight");
        forgotTckno.setInputPrompt("Enter TCK no");
        forgotName.setInputPrompt("Enter name");
        forgotSurname.setInputPrompt("Enter surname");
        
        email.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")){
                email.setComponentError(new UserError("Must be a valid e-mail address"));
            }
            else{
                email.setComponentError(null);
            }
        });
        regTckno.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+") || event.getText().length() != 11){
                regTckno.setComponentError(new UserError("Must be a valid TCK no"));
            }
            else{
                regTckno.setComponentError(null);
            }
        });
        regName.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[a-zA-ZşŞıİçÇöÖüÜğĞ]+")){ // Turkish chars included
                regName.setComponentError(new UserError("Must be a valid name"));
            }
            else{
                regName.setComponentError(null);
            }
        });
        regSurname.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[a-zA-ZşŞıİçÇöÖüÜğĞ]+")){
                regSurname.setComponentError(new UserError("Must be a valid last name"));
            }
            else{
                regSurname.setComponentError(null);
            }
        });
        regEmail.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")){
                regEmail.setComponentError(new UserError("Must be a valid e-mail address"));
            }
            else{
                regEmail.setComponentError(null);
            }
        });
        regPassword.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(event.getText().isEmpty() || "".equals(event.getText())){
                regPassword.setComponentError(new UserError("Must be a valid password"));
            }
            else{
                regPassword.setComponentError(null);
            }
        });
        regAge.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+")){
                regAge.setComponentError(new UserError("Must be a valid age"));
            }
            else{
                regAge.setComponentError(null);
            }
        });
        regHeight.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+")){
                regHeight.setComponentError(new UserError("Must be a valid height"));
            }
            else{
                regHeight.setComponentError(null);
            }
        });
        regWeight.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+")){
                regWeight.setComponentError(new UserError("Must be a valid weight"));
            }
            else{
                regWeight.setComponentError(null);
            }
        });
        forgotTckno.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[0-9]+") || event.getText().length() != 11){
                forgotTckno.setComponentError(new UserError("Must be a valid TCK no"));
            }
            else{
                forgotTckno.setComponentError(null);
            }
        });
        forgotName.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[a-zA-ZşŞıİçÇöÖüÜğĞ]+")){ // Turkish chars included
                forgotName.setComponentError(new UserError("Must be a valid name"));
            }
            else{
                forgotName.setComponentError(null);
            }
        });
        forgotSurname.addTextChangeListener((FieldEvents.TextChangeEvent event)->{
            if(!event.getText().matches("[a-zA-ZşŞıİçÇöÖüÜğĞ]+")){
                forgotSurname.setComponentError(new UserError("Must be a valid last name"));
            }
            else{
                forgotSurname.setComponentError(null);
            }
        });
        email.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        regTckno.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        regName.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        regSurname.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        regEmail.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        regPassword.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        regAge.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        regHeight.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        regWeight.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        forgotTckno.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        forgotName.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        forgotSurname.setTextChangeEventMode(AbstractTextField.TextChangeEventMode.EAGER);
        
        email.setMaxLength(50);
        password.setMaxLength(50);
        regTckno.setMaxLength(11);
        regName.setMaxLength(20);
        regSurname.setMaxLength(20);
        regEmail.setMaxLength(50);
        regAge.setMaxLength(3);
        regHeight.setMaxLength(3);
        regWeight.setMaxLength(3);
        forgotTckno.setMaxLength(11);
        forgotName.setMaxLength(20);
        forgotSurname.setMaxLength(20);
        
        regTckno.setRequired(true);
        regName.setRequired(true);
        regSurname.setRequired(true);
        regEmail.setRequired(true);
        regPassword.setRequired(true);
        regAge.setRequired(true);
        regGender.setRequired(true);
        regHeight.setRequired(true);
        regWeight.setRequired(true);
        forgotTckno.setRequired(true);
        forgotName.setRequired(true);
        forgotSurname.setRequired(true);
        
    }
    private void buildLayout(){
        
        fieldLayout.setMargin(new MarginInfo(true, true, true, true));
        buttonLayout.setMargin(new MarginInfo(true, true, true, true));
        
        fieldLayout.setComponentAlignment(email, Alignment.MIDDLE_CENTER);
        fieldLayout.setComponentAlignment(password, Alignment.MIDDLE_CENTER);
        
        buttonLayout.setComponentAlignment(forgotpass, Alignment.MIDDLE_CENTER);
        buttonLayout.setComponentAlignment(register, Alignment.MIDDLE_CENTER);
        
        VerticalLayout mainLayout = new VerticalLayout(fieldLayout, login, buttonLayout, registerLayout, forgotLayout);
        
        regLayout1.setMargin(new MarginInfo(true, true, true, true));
        regLayout2.setMargin(new MarginInfo(true, true, true, true));
        regLayout3.setMargin(new MarginInfo(true, true, true, true));
        regFieldLayout.setMargin(new MarginInfo(true, true, true, true));
        regButtonLayout.setMargin(new MarginInfo(true, true, true, true));
        
        registerLayout.setMargin(new MarginInfo(true, true, true, true));
        
        regLayout1.setComponentAlignment(regTckno, Alignment.MIDDLE_CENTER);
        regLayout1.setComponentAlignment(regName, Alignment.MIDDLE_CENTER);
        regLayout1.setComponentAlignment(regSurname, Alignment.MIDDLE_CENTER);        
        regLayout2.setComponentAlignment(regEmail, Alignment.MIDDLE_CENTER);
        regLayout2.setComponentAlignment(regAge, Alignment.MIDDLE_CENTER);
        regLayout2.setComponentAlignment(regPassword, Alignment.MIDDLE_CENTER);
        regLayout3.setComponentAlignment(regGender, Alignment.MIDDLE_CENTER);
        regLayout3.setComponentAlignment(regHeight, Alignment.MIDDLE_CENTER);
        regLayout3.setComponentAlignment(regWeight, Alignment.MIDDLE_CENTER);
        regFieldLayout.setComponentAlignment(regLayout1, Alignment.MIDDLE_CENTER);
        regFieldLayout.setComponentAlignment(regLayout2, Alignment.MIDDLE_CENTER);
        regFieldLayout.setComponentAlignment(regLayout3, Alignment.MIDDLE_CENTER);
        regButtonLayout.setComponentAlignment(regClear, Alignment.MIDDLE_CENTER);
        regButtonLayout.setComponentAlignment(regCancel, Alignment.MIDDLE_CENTER);
        registerLayout.setComponentAlignment(regFieldLayout, Alignment.MIDDLE_CENTER);
        registerLayout.setComponentAlignment(regConfirm, Alignment.MIDDLE_CENTER);
        registerLayout.setComponentAlignment(regButtonLayout, Alignment.MIDDLE_CENTER);
        
        registerLayout.setVisible(false);
        
        forgotFieldLayout.setMargin(new MarginInfo(true, true, true, true));
        forgotButtonLayout.setMargin(new MarginInfo(true, true, true, true));
        forgotLayout.setMargin(new MarginInfo(true, true, true, true));
        
        forgotFieldLayout.setComponentAlignment(forgotTckno, Alignment.MIDDLE_CENTER);
        forgotFieldLayout.setComponentAlignment(forgotName, Alignment.MIDDLE_CENTER);
        forgotFieldLayout.setComponentAlignment(forgotSurname, Alignment.MIDDLE_CENTER);
        forgotButtonLayout.setComponentAlignment(forgotClear, Alignment.MIDDLE_CENTER);
        forgotButtonLayout.setComponentAlignment(forgotCancel, Alignment.MIDDLE_CENTER);
        forgotLayout.setComponentAlignment(forgotFieldLayout, Alignment.MIDDLE_CENTER);
        forgotLayout.setComponentAlignment(forgotSend, Alignment.MIDDLE_CENTER);
        forgotLayout.setComponentAlignment(forgotButtonLayout, Alignment.MIDDLE_CENTER);
        
        forgotLayout.setVisible(false);
        
        mainLayout.setMargin(new MarginInfo(true, true, true, true));
        
        mainLayout.setComponentAlignment(fieldLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(login, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(buttonLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(registerLayout, Alignment.MIDDLE_CENTER);
        mainLayout.setComponentAlignment(forgotLayout, Alignment.MIDDLE_CENTER);
        
        addComponent(mainLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){}
}