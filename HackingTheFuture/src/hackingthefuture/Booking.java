/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Asus
 */
public class Booking {

    private Parent parent;
    private Destination destination;
    private LocalDate slot;
    private List<Student> childrenList;

    public Booking(Parent parent, Destination destination, LocalDate slot, List<Student> childrenList) {
        this.parent = parent;
        this.destination = destination;
        this.slot = slot;
        this.childrenList = childrenList;
    }

    public Parent getParent() {
        return parent;
    }

    public Destination getDestination() {
        return destination;
    }

    public LocalDate getSlot() {
        return slot;
    }

    public List<Student> getChildrenList() {
        return childrenList;
    }

    public List<String> getChildrenIDList() {
        List<String> res = new ArrayList<>();
        for (Student child : childrenList) {
            res.add(child.getID());
        }
        return res;
    }
    
    public String getFormattedSlot(){
        return slot.format(DateTimeFormatter.ofPattern("d-M-yyyy"));
    }
}
