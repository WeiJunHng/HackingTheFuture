/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

/**
 *
 * @author Asus
 */
public class User {

    public class ROLE {

        public static final String STUDENT = "Student";
        public static final String PARENT = "Parent";
        public static final String EDUCATOR = "Educator";

    }

    public static void register(String role, String email, String username, String password, String kinEmail) {
        LoginRegisterHandler.register(role, email, username, password, kinEmail);
    }

    public static void registerBoth(String role, String email, String username, String password, String kinRole, String kinEmail, String kinUsername, String kinPassword) {
        LoginRegisterHandler.registerBoth(role, email, username, password, kinRole, kinEmail, kinUsername, kinPassword);
    }

    public static User login(String emailUsername) {
        return LoginRegisterHandler.login(emailUsername);
    }

    protected String ID, email, username, password, role, location;

    protected User(String ID, String role, String email, String username, String password, String location) {
        this.ID = ID;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.location = location;
    }

    protected User(User user) {
        this.ID = user.getID();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.location = user.getLocation();
    }

    public String getID() {
        return ID;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getLocation() {
        return location;
    }

    public double getCoordinateX() {
        return Double.parseDouble(location.replaceAll("\\(|\\)", "").split(",")[0]);
    }

    public double getCoordinateY() {
        return Double.parseDouble(location.replaceAll("\\(|\\)", "").split(",")[1]);
    }

    public void changeUsername(String newUsername) {
        this.username = newUsername;
    }

    public void changeEmail(String newEmail) {
        this.email = newEmail;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

}
