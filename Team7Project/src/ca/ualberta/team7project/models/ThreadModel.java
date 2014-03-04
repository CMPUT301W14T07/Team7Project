/**
 * The class for which comments and topics are derived.
 * 
 * @author Michael Raypold
 */
package ca.ualberta.team7project.models;

import java.util.Date;
import java.util.LinkedList;

import android.graphics.Bitmap;
import android.location.Location;

public class ThreadModel {

    protected String comment;
    protected Bitmap image;
    protected String authorName;
    protected String authorUnique;
    protected Date lastUpdatedDate;
    protected Location location;
    protected Integer uniqueID;

    // TODO
    // There must be a better way than putting a class of subclass in the
    // superclass.......
    // Doesn't seem 'correct'
    protected LinkedList<CommentModel> comments;

    /**
     * Constructs the ThreadModel with appropriate parameters.
     * 
     * @param comment associated with the thread
     * @param image associated with the thread (may be null)
     * @param user associated with the thread
     * @param location the comment was written at
     */
    public ThreadModel(String comment, Bitmap image, UserModel user,
	    Location location) {
	super();
	this.comment = comment;
	this.image = image;
	this.authorName = user.getName();
	this.authorUnique = user.getUniqueName();
	this.location = location;
	this.comments = new LinkedList<CommentModel>();

    }

    public String getComment() {

	return comment;
    }

    public void setComment(String comment) {

	this.comment = comment;
    }

    public Bitmap getImage() {

	return image;
    }

    public void setImage(Bitmap image) {

	this.image = image;
    }

    public String getAuthorName() {

	return authorName;
    }

    public void setAuthorName(String authorName) {

	this.authorName = authorName;
    }

    public String getAuthorUnique() {

	return authorUnique;
    }

    public void setAuthorUnique(String authorUnique) {

	this.authorUnique = authorUnique;
    }

    public Date getLastUpdatedDate() {

	return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {

	this.lastUpdatedDate = lastUpdatedDate;
    }

    public Location getLocation() {

	return location;
    }

    public void setLocation(Location location) {

	this.location = location;
    }

    public Integer getUniqueID() {

	return uniqueID;
    }

    public void setUniqueID(Integer uniqueID) {
	this.uniqueID = uniqueID;
    }

    public LinkedList<CommentModel> getComments() {

	return comments;
    }

    public void addComment(CommentModel comment) {

	this.comments.add(comment);
    }

}
