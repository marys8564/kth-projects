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
 * @author Mary
 * @author Sina
 */
@NamedQueries({
    @NamedQuery(name = "GET_HISTORY",
    query = "SELECT h "
    + "FROM History h "
    + "WHERE h.users =:user")})
@IdClass(ShoppingCartID.class)
@Entity
public class History implements Serializable {

    /**
     * Static string represents the query name
     */
    public static final String QUERY_GET_HISTORY = "GET_HISTORY";
    /**
     * Static string represents the User column name.
     */
    public static final String COLUMN_USERS = "user";
    /**
     * part of the compound primary key of the History entity
     */
    @Id
    @ManyToOne
    private Gnome gnome;
    /**
     * part of the compound primary key of the History entity
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
     * Constructor of History object
     *
     * @param gnome
     * @param users
     * @param basketAmount
     */
    public History(Gnome gnome, Users users, Integer basketAmount) {
        this.gnome = gnome;
        this.users = users;
        this.basketAmount = basketAmount;
    }

    /**
     * Default Constructor
     */
    public History() {
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
     * Sets the amount of Gnome Gnome in History
     *
     * @param basketAmount amount of Gnome in History
     */
    public void setBasketAmount(Integer basketAmount) {
        this.basketAmount = basketAmount;
    }

    /**
     * Returns the Gnome of History
     *
     * @return the Gnome
     */
    public Gnome getGnome() {
        return gnome;
    }

    /**
     * Returns the user of History
     *
     * @return the User
     */
    public Users getUsers() {
        return users;
    }

    /**
     * Compares this History to the specified object. The result is true if and
     * only if the argument is not null and is a History object that has same id
     * as this object.
     *
     * @param o The object to compare this History against.
     * @return true if the given object represents a History equivalent to this
     * gnome, false otherwise
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
     *
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
