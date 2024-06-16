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
public class Student extends User {

    private int point;
    private String registeredEvent, registeredBooking, quizDone, friend, friendRequest, parentID;

    public Student(User user) {
        super(user);
        point = UserHandler.getPoints(this);
        List<String> details = UserHandler.getStudentDetails(this);
        registeredEvent = details.get(0);
        registeredBooking = details.get(1);
        quizDone = details.get(2);
        friend = details.get(3);
        friendRequest = details.get(4);
        parentID = details.get(5);
    }

    public Student(String ID, String email, String username, String password, String location, int point, String registeredEvent, String registeredBooking, String quizDone, String friend, String friendRequest, String parentID) {
        super(ID, User.ROLE.STUDENT, email, username, password, location);
        this.point = point;
        this.registeredEvent = registeredEvent;
        this.registeredBooking = registeredBooking;
        this.quizDone = quizDone;
        this.friend = friend;
        this.friendRequest = friendRequest;
        this.parentID = parentID;
    }

    // Getter for an object
    public String getQuizDone() {
        return quizDone;
    }

    public String getRegisteredEvent() {
        return registeredEvent;
    }

    // Get friends (In form of string)
    public String getFriend() {
        return UserHandler.studentGetFriend(this);
    }
    
    // Get friend requests (In form of string)
    public String getFriendRequest() {
        return UserHandler.studentGetFriendRequest(this);
    }

    // Get list of parents (In form of string)
    public List<String> getParentList() {
        return Arrays.asList(parentID.split(","));
    }

    // Get list of registered events (In form of string)
    public List<String> getRegisteredEventList() {
        return Arrays.asList(registeredEvent.split(","));
    }

    // Get list of object of registered events
    public List<Event> getRegisteredEventObjectList() {
        return UserHandler.getRegisteredEventList(this);
    }

    // Get list of object of past bookings
    public List<Booking> getRegisteredBookingList() {
        return UserHandler.getPastBooking(this);
    }

    // Get list of quiz done (In form of string)
    public List<String> getQuizDoneList() {
        return Arrays.asList(quizDone.split(","));
    }

    // Get list of friends (In form of string)
    public List<String> getFriendList() {
        String friend = getFriend();
        if (friend.isBlank()) {
            return List.of();
        }
        return Arrays.asList(friend.split(","));
    }

    // Get list of friend requests (In form of string)
    public List<String> getFriendRequestList() {
        String friendRequest = getFriendRequest();
        if (friendRequest.isBlank()) {
            return List.of();
        }
        return Arrays.asList(friendRequest.split(","));
    }

    public int getPoint() {
        return point;
    }

    // Update registered event in database and add point
    public void joinEvent(Event event) {
        if (UserHandler.updateRegisteredEvent(this, event)) {
            this.point += 5;
        }
    }

    // Update quiz done in database and add point
    public void doQuiz(Quiz quiz) {
        if (UserHandler.updateQuizDone(this, quiz)) {
            this.point += 2;
        }
    }

    public void updateQuizDone(String quizDone) {
        this.quizDone = quizDone;
    }

    // Get registered event clashed with the event passed in
    public Event getClashedEvent(Event event) {
        for (Event registeredEvent : getRegisteredEventObjectList()) {
            if (registeredEvent.getDate().isEqual(event.getDate())) {
                return registeredEvent;
            }
        }
        return null;
    }

    // Check does the date clashed with any event registered
    public boolean isClashed(LocalDate date) {
        for (Event registeredEvent : getRegisteredEventObjectList()) {
            if (registeredEvent.getDate().isEqual(date)) {
                return true;
            }
        }
        return UserHandler.isBookingClashed(this, date);
    }

    public void updateRegisteredEvent(String registeredEvent) {
        this.registeredEvent = registeredEvent;
    }

    // Check is the Student is friend of this Student
    public boolean isFriend(Student currentStudent) {
        return getFriendList().contains(currentStudent.ID);
    }

    // Check does friend request sent to the Student
    public boolean isFriendRequestSent(Student other) {
        return other.getFriendRequestList().contains(this.ID);
    }

    // Send friend request to other Student
    public String sendFriendRequest(Student receiver) {
        // If the Student has sent friend request to this Student, add friend
        if (receiver.isFriendRequestSent(this)) {
            addFriend(receiver);
            return "Friend added!";
        } else {
            UserHandler.saveFriendRequest(this, receiver);
            return "Friend request sent successfully!";
        }
    }

    // Update friends in database
    public void addFriend(Student friend) {
        UserHandler.saveFriend(this, friend);
    }

    // Update friend requests in database
    public void deleteFriendRequest(Student friend) {
        UserHandler.deleteFriendRequest(this, friend);
    }
    
    // Update parent in database
    public void updateParent(String newParentID){
        parentID = newParentID;
    }
}
