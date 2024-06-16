/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Asus
 */
public class Parent extends User {

    private String childrenID, pastBooking;
    private final List<String> childrenIDList = new ArrayList<>();
    private final List<Student> childrenList = new ArrayList<>();
    private final List<String> pastBookingList = new ArrayList<>();

    public Parent(User user) {
        super(user);
        childrenID = UserHandler.getChildrenID(this);
        
        for (String studentID : getChildrenList()) {
            childrenList.add((Student) UserHandler.getUser(studentID));
        }
    }

    // Getter for an object
    public List<String> getChildrenList() {
        return Arrays.asList(childrenID.split(","));
    }

    // Get list of objects of children
    public List<Student> getChildrenObjectList() {
        return childrenList;
    }

    // Get list of objects of past bookings
    public List<Booking> getPastBookingList() {
        return UserHandler.getPastBooking(this);
    }
    
    // Determine does the slot of booking chosen clashed with past booking or not
    public boolean isClashed(LocalDate date) {
        return UserHandler.isBookingClashed(this, date);
    }
    
    // Save the booking
    public void book(Booking booking){
        UserHandler.updatePastBooking(this, booking);
    }
    
    // Update children of the Parent
    public void updateChild(String newChildrenID){
        childrenID = newChildrenID;
    }
    

}
