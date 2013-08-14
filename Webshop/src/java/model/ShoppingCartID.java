/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * Represents the compound primary key of ShoppingCard entity
 * @author Mary
 * @author Sina
 */
public class ShoppingCartID implements Serializable {
    
    /** id and primary key of the Gnome object*/
    private int gnome;
    /** username and primary key of the Users object*/
    private String users;
    
    /**
     * Default Constructor
     */
    public ShoppingCartID() {
    }

    /**
     * Constructor of ShoppingCartID
     * @param gnome id and primary key of the Gnome object
     * @param users username and primary key of the Users object
     */
    public ShoppingCartID(int gnome, String users) {
        this.gnome = gnome;
        this.users = users;
    }

    /**
     * Returns the gnome id
     * @return gnome id
     */
    public int getGnome() {
        return gnome;
    }

    /**
     * Sets the gnome id
     * @param gnome gnome id
     */
    public void setGnome(int gnome) {
        this.gnome = gnome;
    }   
    
    /**
     * Returns the username of the User
     * @return username
     */
    public String getUsers() {
        return users;
    }

    /**
     * Sets the username of the User
     * @param users username
     */
    public void setUsers(String users) {
        this.users = users;
    }
    /**
     * Returns a hash code for this Gnome
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return users.hashCode() + gnome;
    }

    /**
     * Compares this ShoppingCartID to the specified object. The result is true if
     * and only if the argument is not null and is a ShoppingCart object that
     * has same user id and gnome id as this object.
     *
     * @param obj The object to compare this ShoppingCartID against.
     * @return true if the given object represents a ShoppingCart equivalent to
     * this gnome, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ShoppingCartID)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        ShoppingCartID pk = (ShoppingCartID) obj;
        return (pk.getUsers() == null ? users == null : pk.getUsers().equals(users)) && pk.getGnome() == gnome;
    }
}
