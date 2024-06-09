/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Asus
 */
public class DBHandler {

    private final DataBase DB = new DataBase();

    protected DBHandler() {

    }

    protected boolean checkEmailExist(String email) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT Email FROM user WHERE Email=?")) {
            ps.setObject(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected boolean checkUsernameExist(String username) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT Username FROM user WHERE Username=?")) {
            ps.setObject(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected String getPasswordByEmail(String email) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT Password FROM user WHERE Email=?")) {
            ps.setObject(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Password");
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected String getPasswordByUsername(String username) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT Password FROM user WHERE Username=?")) {
            ps.setObject(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Password");
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected String getIDByEmail(String email) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT ID FROM user WHERE Email=?")) {
            ps.setObject(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("ID");
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected String getRoleByEmail(String email) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT Role FROM user WHERE Email=?")) {
            ps.setObject(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Role");
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected String getParentIDByID(String id) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT ParentID FROM student WHERE ID=?")) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("ParentID");
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected String getChildrenIDByID(String id) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT ChildrenID FROM parent WHERE ID=?")) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("ChildrenID");
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected String getPastBookingByID(String id) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT PastBooking FROM parent WHERE ID=?")) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("PastBooking");
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected List<String> getStudentDetailsByID(String id) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        List<String> res = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM student WHERE ID=?")) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    res.add(rs.getString("RegisteredEvent"));
                    res.add(rs.getString("RegisteredBooking"));
                    res.add(rs.getString("QuizDone"));
                    res.add(rs.getString("Friend"));
                    res.add(rs.getString("FriendRequest"));
                    res.add(rs.getString("ParentID"));
                    return res;
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected int getPointsByID(String id) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT Points FROM student WHERE ID=?")) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Points");
                }
                return -1;
            }
        } catch (Exception e) {
            return -1;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected String getEventCreatedByID(String id) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT EventCreated FROM educator WHERE ID=?")) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("EventCreated");
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected String getQuizCreatedByID(String id) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT QuizCreated FROM educator WHERE ID=?")) {
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("QuizCreated");
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected int getParentCountByEmail(String email) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT ParentCount FROM student WHERE Email=?")) {
            ps.setObject(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ParentCount");
                }
                return 0;
            }
        } catch (Exception e) {
            return 0;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void updateParent(String studentID, String parentID) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE student SET ParentID=?, ParentCount=ParentCount+1 WHERE ID=?");
            String parentsID = getParentIDByID(studentID);
            parentsID += parentsID.isEmpty() ? parentID : "," + parentID;
            ps.setObject(1, parentsID);
            ps.setObject(2, studentID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void updateChildren(String parentID, String childID) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE parent SET ChildrenID=? WHERE ID=?");
            String childrenID = getChildrenIDByID(parentID);
            childrenID += childrenID.isEmpty() ? childID : "," + childID;
            ps.setObject(1, childrenID);
            ps.setObject(2, parentID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void saveUser(String role, String email, String username, String password, String location, String kinID) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps;
            if (role.equals(User.ROLE.EDUCATOR)) {
                ps = conn.prepareStatement("INSERT INTO " + role + " (Email, Username, Password, Location) VALUES (?,?,?,?)");
                ps.setObject(1, email);
                ps.setObject(2, username);
                ps.setObject(3, password);
                ps.setObject(4, location);
            } else {
                String variable;
                if (role.equals(User.ROLE.STUDENT)) {
                    variable = "ParentID";
                } else {
                    variable = "ChildrenID";
                }
                ps = conn.prepareStatement("INSERT INTO " + role + " (Email, Username, Password, Location, " + variable + ") VALUES (?,?,?,?,?)");
                ps.setObject(1, email);
                ps.setObject(2, username);
                ps.setObject(3, password);
                ps.setObject(4, location);
                ps.setObject(5, kinID);
            }
            if (ps.executeUpdate() != 0) {
                if (role.equals(User.ROLE.PARENT)) {
                    updateParent(kinID, getIDByEmail(email));
                } else if (role.equals(User.ROLE.STUDENT)) {
                    updateChildren(kinID, getIDByEmail(email));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    public void saveTwoUsers(String role, String email, String username, String password, String kinRole, String kinEmail, String kinUsername, String kinPassword, String location) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO " + role + " (Email, Username, Password, Location) VALUES (?,?,?,?)");
            ps.setObject(1, email);
            ps.setObject(2, username);
            ps.setObject(3, password);
            ps.setObject(4, location);

            if (ps.executeUpdate() == 0) {
                return;
            }

            ps = conn.prepareStatement("INSERT INTO " + kinRole + " (Email, Username, Password, Location) VALUES (?,?,?,?)");
            ps.setObject(1, kinEmail);
            ps.setObject(2, kinUsername);
            ps.setObject(3, kinPassword);
            ps.setObject(4, location);

            if (ps.executeUpdate() != 0) {
                String ID = getIDByEmail(email);
                String kinID = getIDByEmail(kinEmail);
                if (role.equals(User.ROLE.PARENT)) {
                    updateChildren(ID, kinID);
                    updateParent(kinID, ID);
                } else {
                    updateParent(ID, kinID);
                    updateChildren(kinID, ID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected User getUserByEmail(String email) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE Email=?")) {
            ps.setObject(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String ID = rs.getString("ID");
                    String role = rs.getString("Role");
                    String username = rs.getString("Username");
                    String password = rs.getString("Password");
                    String location = rs.getString("Location");
                    return new User(ID, role, email, username, password, location);
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected User getUserByUsername(String username) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE Username=?")) {
            ps.setObject(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String ID = rs.getString("ID");
                    String role = rs.getString("Role");
                    String email = rs.getString("Email");
                    String password = rs.getString("Password");
                    String location = rs.getString("Location");
                    return new User(ID, role, email, username, password, location);
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected User getUserByID(String ID) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE ID=?")) {
            ps.setObject(1, ID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("Role");
                    String email = rs.getString("Email");
                    String username = rs.getString("username");
                    String password = rs.getString("Password");
                    String location = rs.getString("Location");
                    return new User(ID, role, email, username, password, location);
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void saveEvent(Event event, Educator creator) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO event (Title, `Desc`, Venue, `Date`, `Time`, EducatorID) VALUES (?,?,?,?,?,?)");
            ps.setObject(1, event.getTitle());
            ps.setObject(2, event.getDescription());
            ps.setObject(3, event.getVenue());
            ps.setDate(4, Date.valueOf(event.getDate()));
            ps.setTime(5, Time.valueOf(event.getTime()));
            ps.setObject(6, event.getEducatorID());

            if (ps.executeUpdate() == 0) {
                return;
            }

            ps = conn.prepareStatement("SELECT ID FROM event ORDER BY Idx DESC LIMIT 1");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return;
            }
            String ID = rs.getString("ID");

            String eventCreatedNew = (creator.getEventCreated() + "," + ID).replaceAll("^,", "");
            ps = conn.prepareStatement("UPDATE educator SET EventCreated = ? WHERE ID = ?");
            ps.setObject(1, eventCreatedNew);
            ps.setObject(2, creator.getID());

            if (ps.executeUpdate() != 0) {
                creator.updateEvent(eventCreatedNew);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void saveQuiz(Quiz quiz, Educator creator) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO quiz (Title, `Desc`, Theme, Content, EducatorID) VALUES (?,?,?,?,?)");
            ps.setObject(1, quiz.getTitle());
            ps.setObject(2, quiz.getDescription());
            ps.setObject(3, quiz.getTheme());
            ps.setObject(4, quiz.getContent());
            ps.setObject(5, quiz.getEducatorID());

            if (ps.executeUpdate() == 0) {
                return;
            }

            ps = conn.prepareStatement("SELECT ID FROM quiz ORDER BY Idx DESC LIMIT 1");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return;
            }
            String ID = rs.getString("ID");

            String quizCreatedNew = (creator.getQuizCreated() + "," + ID).replaceAll("^,", "");
            ps = conn.prepareStatement("UPDATE educator SET QuizCreated = ? WHERE ID = ?");
            ps.setObject(1, quizCreatedNew);
            ps.setObject(2, creator.getID());

            if (ps.executeUpdate() != 0) {
                creator.updateQuiz(quizCreatedNew);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected List<Event> getUpcomingEvents() {
        List<Event> res = new ArrayList<>();

        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM event WHERE `Date` > CURRENT_DATE ORDER BY STR_TO_DATE(CONCAT(`Date`, ' ', `Time`), '%Y-%m-%d %H:%i:%s')");

            ResultSet rs = ps.executeQuery();

            for (int i = 0; i < 3 && rs.next(); i++) {
                String ID = rs.getString("ID");
                String title = rs.getString("Title");
                String description = rs.getString("Desc");
                String venue = rs.getString("Venue");
                Date date = rs.getDate("Date");
                Time time = rs.getTime("Time");
                String educatorID = rs.getString("EducatorID");

                res.add(new Event(ID, title, description, venue, date.toLocalDate(), time.toLocalTime(), educatorID));
            }

            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected List<Event> getOngoingEvents() {
        List<Event> res = new ArrayList<>();

        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM event WHERE `Date` = CURRENT_DATE ORDER BY STR_TO_DATE(CONCAT(`Date`, ' ', `Time`), '%Y-%m-%d %H:%i:%s')");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String ID = rs.getString("ID");
                String title = rs.getString("Title");
                String description = rs.getString("Desc");
                String venue = rs.getString("Venue");
                Date date = rs.getDate("Date");
                Time time = rs.getTime("Time");
                String educatorID = rs.getString("EducatorID");

                res.add(new Event(ID, title, description, venue, date.toLocalDate(), time.toLocalTime(), educatorID));
            }

            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected List<Quiz> getQuizzes() {
        List<Quiz> res = new ArrayList<>();

        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM quiz ORDER BY Idx DESC");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String ID = rs.getString("ID");
                String title = rs.getString("Title");
                String description = rs.getString("Desc");
                String theme = rs.getString("Theme");
                String content = rs.getString("Content");
                String educatorID = rs.getString("EducatorID");

                res.add(new Quiz(ID, title, description, theme, content, educatorID));
            }

            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected Quiz getQuizByID(String ID) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM quiz WHERE ID=?");
            ps.setObject(1, ID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String title = rs.getString("Title");
                String description = rs.getString("Desc");
                String theme = rs.getString("Theme");
                String content = rs.getString("Content");
                String educatorID = rs.getString("EducatorID");
                return new Quiz(ID, title, description, theme, content, educatorID);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected boolean studentUpdateQuizDone(Student student, String quizID) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            String quizDoneNew = (student.getQuizDone() + "," + quizID).replaceAll("^,", "");
            PreparedStatement ps = conn.prepareStatement("UPDATE student SET QuizDone = ? WHERE ID = ?");
            ps.setObject(1, quizDoneNew);
            ps.setObject(2, student.getID());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            student.updateQuizDone(quizDoneNew);

            ps = conn.prepareStatement("UPDATE student SET Points = ?, PointUpdatedTime = CURRENT_TIMESTAMP WHERE ID = ?");
            ps.setInt(1, student.getPoint() + 2);
            ps.setObject(2, student.getID());

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected boolean studentUpdateRegisteredEvent(Student student, String eventID) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            String regiteredEventNew = (student.getRegisteredEvent() + "," + eventID).replaceAll("^,", "");
            PreparedStatement ps = conn.prepareStatement("UPDATE student SET RegisteredEvent = ? WHERE ID = ?");
            ps.setObject(1, regiteredEventNew);
            ps.setObject(2, student.getID());

            if (ps.executeUpdate() == 0) {
                return false;
            }

            student.updateRegisteredEvent(regiteredEventNew);

            ps = conn.prepareStatement("UPDATE student SET Points = ?, PointUpdatedTime = CURRENT_TIMESTAMP WHERE ID = ?");
            ps.setInt(1, student.getPoint() + 5);
            ps.setObject(2, student.getID());

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected List<Event> studentGetRegisteredEvents(Student student) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        List<Event> res = new ArrayList<>();

        try {
            for (String eventID : student.getRegisteredEventList()) {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM event WHERE ID=?");
                ps.setObject(1, eventID);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String ID = rs.getString("ID");
                    String title = rs.getString("Title");
                    String description = rs.getString("Desc");
                    String venue = rs.getString("Venue");
                    Date date = rs.getDate("Date");
                    Time time = rs.getTime("Time");
                    String educatorID = rs.getString("EducatorID");

                    res.add(new Event(ID, title, description, venue, date.toLocalDate(), time.toLocalTime(), educatorID));
                }
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void postDiscussion(Discussion discussion, User author) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO discussion (Title, DatePublished, Content, AuthorID) VALUES (?,?,?,?)");
            ps.setObject(1, discussion.getTitle());
            ps.setTimestamp(2, Timestamp.valueOf(discussion.getDatePublished()));
            ps.setObject(3, discussion.getContent());
            ps.setObject(4, discussion.getAuthor().getID());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void postComment(Comment comment, User author) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO comment (DatePublished, Content, DiscussionID, AuthorID) VALUES (?,?,?,?)");
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setObject(2, comment.getContent());
            ps.setObject(3, comment.getDiscussionID());
            ps.setObject(4, comment.getAuthor().getID());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected Discussion getDiscussionByID(String ID) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM discussion WHERE ID=?");
            ps.setObject(1, ID);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String title = rs.getString("Title");
                LocalDateTime datePublished = rs.getTimestamp("DatePublished").toLocalDateTime();
                String content = rs.getString("Content");
                String authorID = rs.getString("authorID");
                String userLiked = rs.getString("UserLiked");
                return new Discussion(ID, title, authorID, content, datePublished, userLiked);
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected List<Discussion> getDiscussionList() {
        List<Discussion> res = new ArrayList<>();

        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM discussion ORDER BY Idx DESC");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String ID = rs.getString("ID");
                String title = rs.getString("Title");
                LocalDateTime datePublished = rs.getTimestamp("DatePublished").toLocalDateTime();
                String content = rs.getString("Content");
                String authorID = rs.getString("authorID");
                String userLiked = rs.getString("UserLiked");

                res.add(new Discussion(ID, title, authorID, content, datePublished, userLiked));
            }

            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected List<Comment> getCommentList(Discussion discussion) {
        List<Comment> res = new ArrayList<>();

        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM comment WHERE DiscussionID=? ORDER BY DatePublished DESC");
            ps.setObject(1, discussion.getID());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String ID = rs.getString("ID");
                LocalDateTime datePublished = rs.getTimestamp("DatePublished").toLocalDateTime();
                String content = rs.getString("Content");
                String authorID = rs.getString("authorID");
                String userLiked = rs.getString("UserLiked");

                res.add(new Comment(ID, authorID, content, discussion.getID(), datePublished, userLiked));
            }

            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void updateUserLiked(Discussion discussion, String userLikedNew) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE discussion SET UserLiked = ? WHERE ID = ?");
            ps.setObject(1, userLikedNew);
            ps.setObject(2, discussion.getID());

            if (ps.executeUpdate() != 0) {
                discussion.updateUserLiked(userLikedNew);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void updateUserLiked(Comment comment, String userLikedNew) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE comment SET UserLiked = ? WHERE ID = ?");
            ps.setObject(1, userLikedNew);
            ps.setObject(2, comment.getID());

            if (ps.executeUpdate() != 0) {
                comment.updateUserLiked(userLikedNew);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void updatePastBooking(Parent parent, Booking booking) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO booking (Destination, Date, StudentID, ParentID) VALUES (?,?,?,?)");
            ps.setObject(1, booking.getDestination().getName());
            ps.setDate(2, Date.valueOf(booking.getSlot()));
            ps.setObject(4, booking.getParent().getID());

            for (Student child : booking.getChildrenList()) {
                ps.setObject(3, child.getID());
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected List<Booking> parentGetPastBookings(Parent parent) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        List<Booking> res = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT DISTINCT Date FROM booking WHERE ParentID=?");
            ps.setObject(1, parent.getID());
            ResultSet rs = ps.executeQuery();

            List<Date> slotList = new ArrayList<>();
            while (rs.next()) {
                slotList.add(rs.getDate("Date"));
            }

            for (Date slot : slotList) {
                ps = conn.prepareStatement("SELECT * FROM booking WHERE ParentID=? AND Date=?");
                ps.setObject(1, parent.getID());
                ps.setDate(2, slot);
                rs = ps.executeQuery();

                List<Student> childrenList = new ArrayList<>();
                String destination = "";
                while (rs.next()) {
                    destination = rs.getString("Destination");
                    childrenList.add((Student) UserHandler.getUser(rs.getString("StudentID")));
                }

                res.add(new Booking(parent, Destination.getDestination(destination), slot.toLocalDate(), childrenList));
            }

            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected List<Booking> studentGetPastBookings(Student student) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        List<Booking> res = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking WHERE StudentID=?");
            ps.setObject(1, student.getID());
            ResultSet rs = ps.executeQuery();

            List<String> destinationNameList = new ArrayList<>();
            List<Date> slotList = new ArrayList<>();
            List<String> parentIDList = new ArrayList<>();
            while (rs.next()) {
                destinationNameList.add(rs.getString("Destination"));
                slotList.add(rs.getDate("Date"));
                parentIDList.add(rs.getString("ParentID"));
            }

            for (int i = 0; i < slotList.size(); i++) {
                ps = conn.prepareStatement("SELECT * FROM booking WHERE ParentID=? AND Date=? AND Destination=?");
                ps.setObject(1, parentIDList.get(i));
                ps.setDate(2, slotList.get(i));
                ps.setString(3, destinationNameList.get(i));
                rs = ps.executeQuery();

                List<Student> childrenList = new ArrayList<>();
                while (rs.next()) {
                    childrenList.add((Student) UserHandler.getUser(rs.getString("StudentID")));
                }

                res.add(new Booking((Parent) UserHandler.getUser(parentIDList.get(i)), Destination.getDestination(destinationNameList.get(i)), slotList.get(i).toLocalDate(), childrenList));
            }

            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected boolean isBookingClashed(Parent parent, LocalDate date) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking WHERE ParentID=? AND Date=?");
            ps.setObject(1, parent.getID());
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected boolean isBookingClashed(Student student, LocalDate date) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM booking WHERE StudentID=? AND Date=?");
            ps.setObject(1, student.getID());
            ps.setDate(2, Date.valueOf(date));
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected List<Student> getLeaderboardStudents() {
        DB.connectDB();
        Connection conn = DB.getConnection();

        List<Student> res = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM student ORDER BY PointUpdatedTime")) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String ID = rs.getString("ID");
                    String email = rs.getString("Email");
                    String username = rs.getString("Username");
                    String password = rs.getString("Password");
                    String location = rs.getString("Location");
                    int point = rs.getInt("Points");
                    String registeredEvent = rs.getString("RegisteredEvent");
                    String registeredBooking = rs.getString("RegisteredBooking");
                    String quizDone = rs.getString("quizDone");
                    String friend = rs.getString("Friend");
                    String friendRequest = rs.getString("FriendRequest");
                    String parentID = rs.getString("ParentID");

                    res.add(new Student(ID, email, username, password, location, point, registeredEvent, registeredBooking, quizDone, friend, friendRequest, parentID));
                }
                return res;
            }
        } catch (Exception e) {
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected String getFriend(Student student) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM student WHERE ID = ?");
            ps.setObject(1, student.getID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("Friend");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected String getFriendRequest(Student student) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM student WHERE ID = ?");
            ps.setObject(1, student.getID());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("FriendRequest");
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void saveFriendRequest(Student receiver, String senderID) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            String friendRequestNew = (receiver.getFriendRequest() + "," + senderID).replaceAll("^,", "");
            PreparedStatement ps = conn.prepareStatement("UPDATE student SET FriendRequest = ? WHERE ID = ?");
            ps.setObject(1, friendRequestNew);
            ps.setObject(2, receiver.getID());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void saveFriend(Student currentStudent, Student friend) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            String friendNew1 = (currentStudent.getFriend() + "," + friend.getID()).replaceAll("^,", "");
            String friendNew2 = (friend.getFriend() + "," + currentStudent.getID()).replaceAll("^,", "");

            PreparedStatement ps = conn.prepareStatement("UPDATE student SET Friend = ? WHERE ID = ?");
            ps.setObject(1, friendNew1);
            ps.setObject(2, currentStudent.getID());

            if (ps.executeUpdate() == 0) {
                return;
            }

            ps.setObject(1, friendNew2);
            ps.setObject(2, friend.getID());

            if (ps.executeUpdate() == 0) {
                return;
            }

            currentStudent.deleteFriendRequest(friend);
            friend.deleteFriendRequest(currentStudent);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void deleteFriendRequest(Student currentStudent, Student friend) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            String friendRequestNew1 = currentStudent.getFriendRequest().replaceAll(friend.getID() + ",?", "");
            String friendRequestNew2 = friend.getFriendRequest().replaceAll(currentStudent.getID() + ",?", "");

            PreparedStatement ps = conn.prepareStatement("UPDATE student SET FriendRequest = ? WHERE ID = ?");
            ps.setObject(1, friendRequestNew1);
            ps.setObject(2, currentStudent.getID());

            if (ps.executeUpdate() == 0) {
                return;
            }

            ps.setObject(1, friendRequestNew2);
            ps.setObject(2, friend.getID());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

    protected void changeUsername(String username, User user) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET Username = ? WHERE ID = ?");
            ps.setObject(1, username);
            ps.setObject(2, user.getID());

            if (ps.executeUpdate() == 0) {
                return;
            }

            ps = conn.prepareStatement("UPDATE " + user.getRole() + " SET Username = ? WHERE ID = ?");
            ps.setObject(1, username);
            ps.setObject(2, user.getID());
            
            ps.executeUpdate();
            
            user.changeUsername(username);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }
    
    protected void changeEmail(String email, User user) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET Email = ? WHERE ID = ?");
            ps.setObject(1, email);
            ps.setObject(2, user.getID());

            if (ps.executeUpdate() == 0) {
                return;
            }

            ps = conn.prepareStatement("UPDATE " + user.getRole() + " SET Email = ? WHERE ID = ?");
            ps.setObject(1, email);
            ps.setObject(2, user.getID());
            
            ps.executeUpdate();
            
            user.changeEmail(email);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }
    
    protected void changePassword(String password, User user) {
        DB.connectDB();
        Connection conn = DB.getConnection();

        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE user SET Password = ? WHERE ID = ?");
            ps.setObject(1, password);
            ps.setObject(2, user.getID());

            if (ps.executeUpdate() == 0) {
                return;
            }

            ps = conn.prepareStatement("UPDATE " + user.getRole() + " SET Password = ? WHERE ID = ?");
            ps.setObject(1, password);
            ps.setObject(2, user.getID());
            
            ps.executeUpdate();
            
            user.changePassword(password);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DB.exitDB();
        }
    }

}
