/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author sina & mary
 */
@NamedQueries({
    @NamedQuery(name = "GET_SHOPPING_CART",
    query = "SELECT sh "
    + "FROM ShoppingCart sh "
    + "WHERE sh.users =:user")})
@IdClass(ShoppingCartID.class)
@Entity
public class ShoppingCart implements Serializable {

    /**
     * Static string represents the query name
     */
    public static final String QUERY_GET_SHOPPING_CART = "GET_SHOPPING_CART";
    /**
     * Static string represents the user column name.
     */
    public static final String COLUMN_USERS = "user";
    /**
     * part of the compound primary key of the ShoppingCart entity
     */
    @Id
    @ManyToOne
    private Gnome gnome;
    /**
     * part of the compound primary key of the ShoppingCart entity
     */
    @Id
    @ManyToOne
    /**
     * user of this History
     */
    private Users users;
    /**
     * amount of the Gnome in this History
     */
    private Integer basketAmount;

    /**
     * Constructor for ShoppingCart object
     *
     * @param gnome
     * @param users
     * @param basketAmount
     */
    public ShoppingCart(Gnome gnome, Users users, Integer basketAmount) {
        this.gnome = gnome;
        this.users = users;
        this.basketAmount = basketAmount;
    }

    /**
     * Default Constructor
     */
    public ShoppingCart() {
    }

    /**
     * Returns the basket amount
     *
     * @return basket amount
     */
    public Integer getBasketAmount() {
        return basketAmount;
    }

    /**
     * Sets the gnome amount of this object
     *
     * @param basketAmount gnome amount
     */
    public void setBasketAmount(Integer basketAmount) {
        this.basketAmount = basketAmount;
    }

    /**
     * Returns the gnome of this object
     *
     * @return gnome
     */
    public Gnome getGnome() {
        return gnome;
    }

    /**
     * Returns the user of this object
     *
     * @return user
     */
    public Users getUsers() {
        return users;
    }

    /**
     * Compares this ShoppingCart to the specified object. The result is true if
     * and only if the argument is not null and is a ShoppingCart object that
     * has same id as this object.
     *
     * @param o The object to compare this ShoppingCart against.
     * @return true if the given object represents a ShoppingCart equivalent to
     * this gnome, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShoppingCart that = (ShoppingCart) o;

        if (getUsers() != null ? !getUsers().equals(that.getUsers())
                : that.getUsers() != null) {
            return false;
        }

        if (getGnome() != null ? !getGnome().equals(that.getGnome())
                : that.getGnome() != null) {
            return false;
        }

        return true;
    }

    /**
     * Returns a hash code for this Gnome
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        int result;
        result = (users != null ? users.hashCode() : 0);
        result = 31 * result + (gnome != null ? gnome.hashCode() : 0);
        return result;
    }
}
