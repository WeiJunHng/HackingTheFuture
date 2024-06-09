/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Asus
 */
public class LoginRegisterHandler {

    private static final DBHandler dBHandler = new DBHandler();

    public static boolean isValidateEmail(String email) {
        return email.matches("(^[^\\s]+\\@(gmail|yahoo|hotmail)\\.com$)|(^\\d{8}\\@siswa(365)?\\.um\\.edu\\.my$)|(m\\-\\d{7}\\@moe\\-dl\\.edu\\.my)");
    }

    public static boolean emailOrUsernameExist(String str) {
        if (isValidateEmail(str)) {
            return dBHandler.checkEmailExist(str);
        }
        return dBHandler.checkUsernameExist(str);
    }

    public static boolean isValidateKinRole(String role, String kinEmail) {
        if (role.equals(User.ROLE.STUDENT)) {
            return dBHandler.getRoleByEmail(kinEmail).equals(User.ROLE.PARENT);
        }
        if (role.equals(User.ROLE.PARENT)) {
            return dBHandler.getRoleByEmail(kinEmail).equals(User.ROLE.STUDENT);
        }
        return false;
    }

    public static boolean isValidateParentCount(String childEmail) {
        return dBHandler.getParentCountByEmail(childEmail) < 2;
    }

    // isValidateRegistration() and isValidateLogin() will only be executed when all the fields are checked to be validate
    public static boolean isValidateRegistration(String email, String username, String password, String confirmPassword) {
        return !dBHandler.checkEmailExist(email) && !dBHandler.checkUsernameExist(username) && password.equals(confirmPassword);
    }

    public static boolean isValidateLogin(String emailUsername, String password) {
        if (isValidateEmail(emailUsername)) {
            return password.equals(dBHandler.getPasswordByEmail(emailUsername)) || BCrypt.checkpw(password, dBHandler.getPasswordByEmail(emailUsername));
        } else {
            return password.equals(dBHandler.getPasswordByUsername(emailUsername)) || BCrypt.checkpw(password, dBHandler.getPasswordByUsername(emailUsername));
        }
    }

    public static void register(String role, String email, String username, String password, String kinEmail) {
        String kinID = null;
        String location = generateLocation();
        if (!kinEmail.isEmpty()) {
            kinID = dBHandler.getIDByEmail(kinEmail);
            location = UserHandler.getUser(kinID).getLocation();
        }
        dBHandler.saveUser(role, email, username, hashPassword(password), location, kinID);
    }

    public static void registerBoth(String role, String email, String username, String password, String kinRole, String kinEmail, String kinUsername, String kinPassword) {
        dBHandler.saveTwoUsers(role, email, username, hashPassword(password), kinRole, kinEmail, kinUsername, hashPassword(kinPassword), generateLocation());
    }

    public static User login(String emailUsername) {
        if (isValidateEmail(emailUsername)) {
            return dBHandler.getUserByEmail(emailUsername);
        }
        return dBHandler.getUserByUsername(emailUsername);
    }
    
    public static void changeUsername(String username, User user){
        dBHandler.changeUsername(username, user);
    }
    
    public static void changeEmail(String email, User user){
        dBHandler.changeEmail(email, user);
    }
    
    public static void changePassword(String password, User user){
        dBHandler.changePassword(hashPassword(password), user);
    }

    private static String generateLocation() {
        Random rand = new Random();
        return String.format("(%.2f,%.2f)", rand.nextDouble(-500, 500), rand.nextDouble(-500, 500));
    }
    
    private static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
}
