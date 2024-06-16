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
public class DiscussionHandler {

    private static final DBHandler dBHandler = new DBHandler();

    // Save discussion into database
    public static void postDiscussion(Discussion discussion, User author) {
        dBHandler.postDiscussion(discussion, author);
    }
    
    // Save comment into database
    public static void postComment(Comment comment, User author) {
        dBHandler.postComment(comment, author);
    }

    // Get discussion from discussion using ID
    public static Discussion getDiscusison(String ID) {
        return dBHandler.getDiscussionByID(ID);
    }

    // Get list of all discussions from database
    public static List<Discussion> getDiscussionList() {
        return dBHandler.getDiscussionList();
    }
    
    // Get list of all comments from database
    public static List<Comment> getCommentList(Discussion discussion) {
        return dBHandler.getCommentList(discussion);
    }

    // Update users who liked the discussion in database
    public static void liked(Discussion discussion, User likedUser) {
        String userLikedNew = (discussion.getUserLiked() + "," + likedUser.getID()).replaceAll("^,", "");
        dBHandler.updateUserLiked(discussion, userLikedNew);
    }

    // Update users who liked the discussion in database
    public static void disliked(Discussion discussion, User dislikedUser) {
        String userLikedNew = discussion.getUserLiked().replaceAll(",?" + dislikedUser.getID(), "");
        dBHandler.updateUserLiked(discussion, userLikedNew);
    }
    
    // Update users who liked the comment in database
    public static void commentLiked(Comment comment, User likedUser) {
        String userLikedNew = (comment.getUserLiked() + "," + likedUser.getID()).replaceAll("^,", "");
        dBHandler.updateUserLiked(comment, userLikedNew);
    }

    // Update users who liked the comment in database
    public static void commentDisliked(Comment comment, User dislikedUser) {
        String userLikedNew = comment.getUserLiked().replaceAll(",?" + dislikedUser.getID(), "");
        dBHandler.updateUserLiked(comment, userLikedNew);
    }
}
