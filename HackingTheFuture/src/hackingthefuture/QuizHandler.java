/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.util.List;

/**
 *
 * @author Asus
 */
public class QuizHandler {
    private static final DBHandler dBHandler = new DBHandler();

    // Save quiz into database
    public static void createQuiz(Quiz quiz, Educator creator) {
        dBHandler.saveQuiz(quiz, creator);
    }
    
    // Get quiz from database based on ID given
    public static Quiz getQuiz(String ID){
        return dBHandler.getQuizByID(ID);
    }
    
    // Get list of all quiz
    public static List<Quiz> getQuizList(){
        return dBHandler.getQuizzes();
    }
}
