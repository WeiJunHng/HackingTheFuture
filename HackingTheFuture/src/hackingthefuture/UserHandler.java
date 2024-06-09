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

    public static int getPoints(Student student) {
        return dBHandler.getPointsByID(student.getID());
    }

    public static List<String> getStudentDetails(Student student) {
        return dBHandler.getStudentDetailsByID(student.getID());
    }

    public static String getChildrenID(Parent parent) {
        return dBHandler.getChildrenIDByID(parent.getID());
    }

    public static String getEventCreated(Educator educator) {
        return dBHandler.getEventCreatedByID(educator.getID());
    }

    public static String getQuizCreated(Educator educator) {
        return dBHandler.getQuizCreatedByID(educator.getID());
    }

    public static boolean updateQuizDone(Student student, Quiz quiz) {
        return dBHandler.studentUpdateQuizDone(student, quiz.getID());
    }

    public static boolean updateRegisteredEvent(Student student, Event event) {
        return dBHandler.studentUpdateRegisteredEvent(student, event.getID());
    }

    public static List<Event> getRegisteredEventList(Student student) {
        return dBHandler.studentGetRegisteredEvents(student);
    }

    public static void updatePastBooking(Parent parent, Booking booking) {
        dBHandler.updatePastBooking(parent, booking);
    }

    public static List<Booking> getPastBooking(User user) {
        if (user instanceof Parent parent) {
            return dBHandler.parentGetPastBookings(parent);
        }
        if (user instanceof Student student) {
            return dBHandler.studentGetPastBookings(student);
        }
        return null;
    }
    
    public static boolean isBookingClashed(Parent parent, LocalDate date) {
        return dBHandler.isBookingClashed(parent, date);
    }
    
    public static boolean isBookingClashed(Student student, LocalDate date) {
        return dBHandler.isBookingClashed(student, date);
    }
    
    public static String studentGetFriend(Student student){
        return dBHandler.getFriend(student);
    }
    
    public static String studentGetFriendRequest(Student student){
        return dBHandler.getFriendRequest(student);
    }
    
    public static void saveFriendRequest(Student sender, Student receiver){
        dBHandler.saveFriendRequest(receiver, sender.getID());
    }
    
    public static void saveFriend(Student currentStudent, Student friend){
        dBHandler.saveFriend(currentStudent, friend);
    }
    
    public static void deleteFriendRequest(Student currentStudent, Student friend){
        dBHandler.deleteFriendRequest(currentStudent, friend);
    }

}
