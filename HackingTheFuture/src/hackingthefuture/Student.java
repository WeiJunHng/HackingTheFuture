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

    public String getQuizDone() {
        return quizDone;
    }

    public String getRegisteredEvent() {
        return registeredEvent;
    }

    public String getFriend() {
        return UserHandler.studentGetFriend(this);
    }

    public String getFriendRequest() {
        return UserHandler.studentGetFriendRequest(this);
    }

    public List<String> getParentList() {
        return Arrays.asList(parentID.split(","));
    }

    public List<String> getRegisteredEventList() {
        return Arrays.asList(registeredEvent.split(","));
    }

    public List<Event> getRegisteredEventObjectList() {
        return UserHandler.getRegisteredEventList(this);
    }

    public List<Booking> getRegisteredBookingList() {
        return UserHandler.getPastBooking(this);
    }

    public List<String> getQuizDoneList() {
        return Arrays.asList(quizDone.split(","));
    }

    public List<String> getFriendList() {
        String friend = getFriend();
        if (friend.isBlank()) {
            return List.of();
        }
        return Arrays.asList(friend.split(","));
    }

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

    public void joinEvent(Event event) {
        if (UserHandler.updateRegisteredEvent(this, event)) {
            this.point += 5;
        }
    }

    public void doQuiz(Quiz quiz) {
        if (UserHandler.updateQuizDone(this, quiz)) {
            this.point += 2;
        }
    }

    public void updateQuizDone(String quizDone) {
        this.quizDone = quizDone;
    }

    public Event getClashedEvent(Event event) {
        for (Event registeredEvent : getRegisteredEventObjectList()) {
            if (registeredEvent.getDate().isEqual(event.getDate())) {
                return registeredEvent;
            }
        }
        return null;
    }

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

    public boolean isFriend(Student currentStudent) {
        return getFriendList().contains(currentStudent.ID);
    }

    public boolean isFriendRequestSent(Student other) {
        return other.getFriendRequestList().contains(this.ID);
    }

    public String sendFriendRequest(Student receiver) {
        if (receiver.isFriendRequestSent(this)) {
            addFriend(receiver);
            return "Friend added!";
        } else {
            UserHandler.saveFriendRequest(this, receiver);
            return "Friend request sent successfully!";
        }
    }

    public void addFriend(Student friend) {
        UserHandler.saveFriend(this, friend);
    }

    public void deleteFriendRequest(Student friend) {
        UserHandler.deleteFriendRequest(this, friend);
    }
}
