package team21.pylonconstructor;

import java.util.Date;

import io.searchbox.annotations.JestId;

/**
 * Created by Shivansh on 2017-03-27.
 */


/**
 * This class is used to represent notifications. Notifications are identified by a id and user
 * and contain info such as taggged by and moodid
 *
 * Notifications are stored on ElasticSearch.
 *
 * @see ElasticSearch
 *
 * @version 1.0
 */
public class Notification implements Comparable<Notification> {

    private String user;
    private String moodid;
    private String taggedby;
    private Date date;
    private String seenflag;

    @JestId
    private String id;

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Instantiates a new Notification.
     *
     * @param user     the user
     * @param taggedby the taggedby
     * @param moodid   the moodid
     */
    public Notification(String user, String taggedby, String moodid) {
        this.user = user;
        this.taggedby = taggedby;
        this.moodid = moodid;
        this.date = new Date();
        this.seenflag = "0";
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets moodid.
     *
     * @return the moodid
     */
    public String getMoodid() {
        return moodid;
    }

    /**
     * Sets moodid.
     *
     * @param moodid the moodid
     */
    public void setMoodid(String moodid) {
        this.moodid = moodid;
    }

    /**
     * Gets taggedby.
     *
     * @return the taggedby
     */
    public String getTaggedby() {
        return taggedby;
    }

    /**
     * Sets taggedby.
     *
     * @param taggedby the taggedby
     */
    public void setTaggedby(String taggedby) {
        this.taggedby = taggedby;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSeenflag() {
        return seenflag;
    }

    public void setSeenflag() {
        this.seenflag = "1";
    }


    @Override
    public int compareTo(Notification notification) {
        return getDate().compareTo(notification.getDate());
    }

}
