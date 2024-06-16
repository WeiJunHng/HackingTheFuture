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

    // Get the parent who made the booking
    public Parent getParent() {
        return parent;
    }

    // Get destination of the booking
    public Destination getDestination() {
        return destination;
    }

    // Get slot of the booking
    public LocalDate getSlot() {
        return slot;
    }

    // Get list of students who will be visiting the destination
    public List<Student> getChildrenList() {
        return childrenList;
    }
    
    // Get list if ID of the students
    public List<String> getChildrenIDList() {
        List<String> res = new ArrayList<>();
        for (Student child : childrenList) {
            res.add(child.getID());
        }
        return res;
    }
    
    // Get formatted date of slot of the booking
    public String getFormattedSlot(){
        return slot.format(DateTimeFormatter.ofPattern("d-M-yyyy"));
    }
}
