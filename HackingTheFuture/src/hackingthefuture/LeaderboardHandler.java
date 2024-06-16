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
public class LeaderboardHandler {
    private static final DBHandler dBHandler = new DBHandler();

    // Get list of all Students
    public static List<Student> getStudents() {
        return dBHandler.getLeaderboardStudents();
    }
}
