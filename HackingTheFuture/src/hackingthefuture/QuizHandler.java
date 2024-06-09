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

    public static void createQuiz(Quiz quiz, Educator creator) {
        dBHandler.saveQuiz(quiz, creator);
    }
    
    public static Quiz getQuiz(String ID){
        return dBHandler.getQuizByID(ID);
    }
    
    public static List<Quiz> getQuizList(){
        return dBHandler.getQuizzes();
    }
}
