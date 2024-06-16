/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Asus
 */
public class Educator extends User {
    
    private String eventCreated, quizCreated;

    public Educator(User user) {
        super(user);
        eventCreated = UserHandler.getEventCreated(this);
        quizCreated = UserHandler.getQuizCreated(this);
    }
    
    // Get list of IDs of event created
    public List<String> getEventCreatedList() {
        if(eventCreated.isEmpty()){
            return List.of();
        }
        return Arrays.asList(eventCreated.split(","));
    }
    
    // Get list of IDs of event created (In form of string)
    public String getEventCreated() {
        return eventCreated;
    }
    // Get list of IDs of quiz created
    public List<String> getQuizCreatedList() {
        if(quizCreated.isEmpty()){
            return List.of();
        }
        return Arrays.asList(quizCreated.split(","));
    }
    
    // Get list of IDs of quiz created (In form of string)
    public String getQuizCreated() {
        return quizCreated;
    }
    
    // Update events created
    public void updateEvent(String events){
        eventCreated = events;
    }
    
    // Update quiz created
    public void updateQuiz(String quiz){
        quizCreated = quiz;
    }
    
}
