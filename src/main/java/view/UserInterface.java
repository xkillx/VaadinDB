package view;

import com.vaadin.annotations.*;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.*;
//import com.vaadin.spring.navigator.SpringViewProvider;
//import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.*;
import java.io.Serializable;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
//import javax.servlet.annotation.WebListener;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.beans.factory.annotation.Autowired;

import hibernate.HibernateUtil;

@UIScope
@SpringUI
@Title("Contact Book")
@Theme("reindeer")
//@Push
public class UserInterface extends UI implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
//    @Autowired
//    private SpringViewProvider viewProvider;
    private Navigator navigator;
    
    @Override
    protected void init(VaadinRequest request){
        VaadinSession.getCurrent().getSession().setMaxInactiveInterval(120); // session gets destroyed after interval
        navigator = new Navigator(this, this);
        //navigator.addProvider(viewProvider);
        //viewProvider.setAccessDeniedViewClass(AccessDeniedView.class);
        //navigator.setErrorView(new ErrorView());
        navigator.addView(LoginView.NAME, new LoginView());
        navigator.addView(ContactBookView.NAME, new ContactBookView());
        navigator.addView(DetailsView.NAME, new DetailsView());
        navigator.addViewChangeListener(new ViewChangeListener(){
            
            @Override
            public boolean beforeViewChange(ViewChangeListener.ViewChangeEvent event){
                if(event.getNewView() instanceof ContactBookView &&
                        (String)getUI().getSession().getAttribute("account") == null){
                    Notification.show("NOT LOGGED IN", Notification.Type.ERROR_MESSAGE);
                    return false;
                }
                else if(event.getNewView() instanceof DetailsView &&
                        (String)getUI().getSession().getAttribute("account") == null){
                    Notification.show("NOT LOGGED IN", Notification.Type.ERROR_MESSAGE);
                    return false;
                }
                else{
                    return true;
                }
            }
            @Override
            public void afterViewChange(ViewChangeListener.ViewChangeEvent event){}
        });
    }
    
    @WebServlet(urlPatterns = "/*", asyncSupported = false)
    @VaadinServletConfiguration(ui = UserInterface.class, productionMode = false, heartbeatInterval = 60, // UI cleanup interval
                                closeIdleSessions = true)
    public static class MyUIServlet extends VaadinServlet implements SessionInitListener, SessionDestroyListener, Serializable{
        
        private static final long serialVersionUID = 1L;
        
        @Override
        protected void servletInitialized() throws ServletException{
            super.servletInitialized();
            // customization for servlet initialization
            getService().addSessionInitListener(this);
            getService().addSessionDestroyListener(this);
            
//            getService().setSystemMessagesProvider((SystemMessagesInfo systemMessagesInfo)->{
//                CustomizedSystemMessages messages = new CustomizedSystemMessages();
//                messages.setSessionExpiredNotificationEnabled(false);
//                messages.setSessionExpiredURL("http://localhost:8080/VaadinDB/?restartApplication");
//                return messages;
//            });
        }

        @Override
        public void sessionInit(SessionInitEvent event) throws ServiceException{}

        @Override
        public void sessionDestroy(SessionDestroyEvent event){
            //HibernateUtil.getSessionFactory().close(); // release hibernate resources
        }
    }
    
//    @WebListener
//    public static class MyContextLoaderListener extends ContextLoaderListener implements Serializable{
//        private static final long serialVersionUID = 1L;
//    }
//    
//    @Configuration
//    @EnableVaadin
//    //@Push
//    public static class MyConfiguration implements Serializable{
//        private static final long serialVersionUID = 1L;
//    }
}