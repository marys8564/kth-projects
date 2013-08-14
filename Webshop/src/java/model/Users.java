package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Represents User objects which are the users of the system
 * @author Mary
 * @author Sina
 */
@NamedQueries({
    @NamedQuery(name = "GET_USER_LIST",
    query = "SELECT u "
    + "FROM Users u "
    + "WHERE u.accessControl <>:accessControl"
    )}
)
@Entity
public class Users implements Serializable {
    
    /** Static string represents the query name*/
    public static final String GET_ACTIVE_USER= "GET_USER_LIST";
    /** Static string represents the Type column name */
    public static final String COLUMN_ACCESS_CONTROL= "accessControl";
    
    /** username and primary key of the Users object*/
    @Id
    private String username;
    /** password of the User*/
    private String password;
    /** activation status of the User*/
    private boolean active;
    /** access level of the User*/
    private boolean accessControl;

    /**
     * Default Constructor
     */
    public Users() {
    }

    
    /**
     * Constructor of Users object
     * @param username username of the user
     * @param password password of the user
     * @param active activation status of the user
     * @param accessControl access level of the user
     */
    public Users(String username, String password, boolean active, boolean accessControl) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.accessControl = accessControl;
    }

    /**
     * Sets the activation status of the user
     * @param active activation status
     */
    public void setActive(boolean active) {
        this.active = active;
    }

     
    
    /**
     * Returns the username of the user
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the user
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the activation status of the user
     * @return true if user is active, otherwise false
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns the access level of the user
     * @return true if user is admin, false otherwise
     */
    public boolean isAccessControl() {
        return accessControl;
    }

    /**
     * Returns a hash code for this Inventory
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    /**
     * Compares this Users to the specified object. The result is true if and
     * only if the argument is not null and is a History object that has same username
     * as this object.
     *
     * @param object The object to compare this Users against.
     * @return true if the given object represents a Users equivalent to this
     * gnome, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns the string value of this object
     * @return string value for Users
     */
    @Override
    public String toString() {
        return "model.Users[ id=" + username + " ]";
    }
}
