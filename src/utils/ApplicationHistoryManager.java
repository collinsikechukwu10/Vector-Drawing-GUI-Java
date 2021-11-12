package utils;

import model.shapes.GenericShape;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ApplicationHistoryManager implements PropertyChangeListener {


    @Override
    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        GenericShape obj = (GenericShape) propertyChangeEvent.getSource();


    }
}
