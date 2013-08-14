package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Represents Inventory objects which represents available Gnome objects in the
 * market
 *
 * @author Mary
 * @author Sina
 */
@NamedQueries({
    @NamedQuery(name = "GET_ALL",
    query = "SELECT inventoryObj "
    + "FROM Inventory inventoryObj"),
    @NamedQuery(name = "GET_INVENTORY",
    query = "SELECT inventoryObj "
    + "FROM Inventory inventoryObj "
    + "WHERE inventoryObj.gnome=:gnome")})
@Entity
public class Inventory implements Serializable {

    /**
     * Static string represents the query name
     */
    public static final String QUERY_GET_ALL = "GET_ALL";
    /**
     * Static string represents the query name
     */
    public static final String QUERY_GET_INVENTORY = "GET_INVENTORY";
    /**
     * Static string represents the gnome column name.
     */
    public static final String COLUMN_GNOME = "gnome";
    @Id
    @ManyToOne
    /**
     * Gnome of this object
     */
    private Gnome gnome;
    /**
     * Amount of available objects
     */
    private Integer amount;

    /**
     * Default Constructor
     */
    public Inventory() {
    }

    /**
     * Inventory Constructor
     *
     * @param gnome Gnome of the
     * @param amount
     */
    public Inventory(Gnome gnome, Integer amount) {
        this.gnome = gnome;
        this.amount = amount;
    }

    /**
     * Returns Gnome of this Inventory object
     *
     * @return Gnome of this Inventory object
     */
    public Gnome getGnome() {
        return gnome;
    }

    /**
     * Returns available amounts of this Inventory object
     *
     * @return Available amounts of this Inventory object
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * Sets the available amounts of this in Inventory object
     *
     * @param amount the available amounts of this Inventory object
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Returns a hash code for this Inventory
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 0;
        return new Integer(gnome.getId()).hashCode();
    }

    /**
     * Compares this inventory to the specified object. The result is true if
     * and only if the argument is not null and is an Inventory object that has
     * same id as this object.
     *
     * @param object The object to compare this Inventory against.
     * @return true if the given object represents an Inventory equivalent to
     * this Inventory, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Inventory)) {
            return false;
        }
        Inventory other = (Inventory) object;
        return this.gnome.getId() == other.gnome.getId();
    }

    /**
     * Returns the string value of this object
     *
     * @return string value for Inventory
     */
    @Override
    public String toString() {
        return "model.Inventory[ id=" + gnome.getId() + " ]";
    }
}
