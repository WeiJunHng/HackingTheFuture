/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Asus
 */
public class UserHandler {

    private static final DBHandler dBHandler = new DBHandler();

    // Get User (downcasted based on role) from database based on ID given
    public static User getUser(String ID) {
        User user = dBHandler.getUserByID(ID);
        if (user.getRole().equals(User.ROLE.STUDENT)) {
            return new Student(user);
        } else if (user.getRole().equals(User.ROLE.PARENT)) {
            return new hackingthefuture.Parent(user);
        } else if (user.getRole().equals(User.ROLE.EDUCATOR)) {
            return new Educator(user);
        }
        return null;
    }
    
    // Get User (downcasted based on role) from database based on email given
    public static User getUserByEmail(String email) {
        User user = dBHandler.getUserByEmail(email);
        if (user.getRole().equals(User.ROLE.STUDENT)) {
            return new Student(user);
        } else if (user.getRole().equals(User.ROLE.PARENT)) {
            return new hackingthefuture.Parent(user);
        } else if (user.getRole().equals(User.ROLE.EDUCATOR)) {
            return new Educator(user);
        }
        return null;
    }

    // Get points of Student from database
    public static int getPoints(Student student) {
        return dBHandler.getPointsByID(student.getID());
    }

    // Get details of Student from database
    public static List<String> getStudentDetails(Student student) {
        return dBHandler.getStudentDetailsByID(student.getID());
    }

    // Get IDs of children of Parent from database
    public static String getChildrenID(Parent parent) {
        return dBHandler.getChildrenIDByID(parent.getID());
    }

    // Get event created (String) of Educator from database
    public static String getEventCreated(Educator educator) {
        return dBHandler.getEventCreatedByID(educator.getID());
    }

    // Get quiz created (String) of Educator from database
    public static String getQuizCreated(Educator educator) {
        return dBHandler.getQuizCreatedByID(educator.getID());
    }

    // Update quiz done (String) by the Student in database
    public static boolean updateQuizDone(Student student, Quiz quiz) {
        return dBHandler.studentUpdateQuizDone(student, quiz.getID());
    }

    // Update event registered (String) by the Student in database
    public static boolean updateRegisteredEvent(Student student, Event event) {
        return dBHandler.studentUpdateRegisteredEvent(student, event.getID());
    }

    // Get list of event registered (object) of Student from database
    public static List<Event> getRegisteredEventList(Student student) {
        return dBHandler.studentGetRegisteredEvents(student);
    }

    // Update past booking (String) of the Parent and children chosen in database
    public static void updatePastBooking(Parent parent, Booking booking) {
        dBHandler.updatePastBooking(parent, booking);
    }

    // Get list of past booking (object) of Student or Parent from database
    public static List<Booking> getPastBooking(User user) {
        if (user instanceof Parent parent) {
            return dBHandler.parentGetPastBookings(parent);
        }
        if (user instanceof Student student) {
            return dBHandler.studentGetPastBookings(student);
        }
        return null;
    }
    
    // Determine does the the date clashed with any past booking of the Parent
    public static boolean isBookingClashed(Parent parent, LocalDate date) {
        return dBHandler.isBookingClashed(parent, date);
    }
    
    // Determine does the the date clashed with any past booking of the Student
    public static boolean isBookingClashed(Student student, LocalDate date) {
        return dBHandler.isBookingClashed(student, date);
    }
    
    // Get friend (String) of Student from database
    public static String studentGetFriend(Student student){
        return dBHandler.getFriend(student);
    }
    
    // Get friend request (String) of Student from database
    public static String studentGetFriendRequest(Student student){
        return dBHandler.getFriendRequest(student);
    }
    
    // Update friend request (String) of the Student in database
    public static void saveFriendRequest(Student sender, Student receiver){
        dBHandler.saveFriendRequest(receiver, sender.getID());
    }
    
    // Update friend (String) of the Student in database
    public static void saveFriend(Student currentStudent, Student friend){
        dBHandler.saveFriend(currentStudent, friend);
    }
    
    // Update friend request (String) of the Student in database
    public static void deleteFriendRequest(Student currentStudent, Student friend){
        dBHandler.deleteFriendRequest(currentStudent, friend);
    }
    
    // Update parentID (String) of the Student in database
    // Update childrenID (String) of the Parent in database
    public static void addParent(Student student, Parent parent){
        student.updateParent(dBHandler.updateParent(student.getID(), parent.getID()));
        dBHandler.updateChildren(parent.getID(), student.getID());
    }
    
    // Update childrenID (String) of the Parent in database
    // Update parentID (String) of the Student in database
    public static void addChild(Parent parent, Student student){
        parent.updateChild(dBHandler.updateChildren(parent.getID(), student.getID()));
        dBHandler.updateParent(student.getID(), parent.getID());
    }

}
