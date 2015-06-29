package view;

import com.vaadin.annotations.*;;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.*;
import com.vaadin.ui.*;
import java.io.Serializable;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import hibernate.HibernateUtil;

@Title("Contact Book")
@Theme("reindeer")
public class UserInterface extends UI implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    private static Navigator navigator;
    
    @Override
    protected void init(VaadinRequest request){
        VaadinSession.getCurrent().getSession().setMaxInactiveInterval(120); // session gets destroyed after interval
        navigator = new Navigator(this, this);
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
    
    @WebServlet(urlPatterns = "/*")
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
        }

        @Override
        public void sessionInit(SessionInitEvent event) throws ServiceException{}

        @Override
        public void sessionDestroy(SessionDestroyEvent event){
            HibernateUtil.getSessionFactory().close(); // release hibernate resources
        }
    }
}