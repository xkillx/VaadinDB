package view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import java.io.Serializable;

public class DetailsView extends VerticalLayout implements View, Serializable{
    
    private static final long serialVersionUID = 1L;
    
    public static final String NAME = "details";
    
    private final Label text = new Label("Test page");
    
    public DetailsView(){
        configureComponents();
        buildLayout();
    }
    private void configureComponents(){
        
    }
    private void buildLayout(){
        addComponent(text);
        setComponentAlignment(text, Alignment.MIDDLE_CENTER);
        
    }
    

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){}
    
}
