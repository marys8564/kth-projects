package model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * Represents Gnome objects which are going to be presented on the WebShop.
 *
 * @author Mary
 * @author Sina
 */
@NamedQueries({
    @NamedQuery(name = "GET_GNOME",
    query = "SELECT g "
    + "FROM Gnome g "
    + "WHERE g.type =:type AND g.price =:price")})
@Entity
public class Gnome implements Serializable {

    /**
     * Static string represents the query name
     */
    public static final String QUERY_GET_GNOME = "GET_GNOME";
    /**
     * Static string represents the Type column name.
     */
    public static final String COLUMN_TYPE = "type";
    /**
     * Static string represents the Price column name.
     */
    public static final String COLUMN_PRICE = "price";
    /**
     * id and primary key of the Gnome entity
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    /**
     * type of the Gnome
     */
    private String type;
    /**
     * price of the Gnome
     */
    private float price;

    /**
     * Default Constructor
     */
    public Gnome() {
    }

    /**
     * Gnome Constructor
     *
     * @param type Type of the Gnome
     * @param price Price of the Gnome
     */
    public Gnome(String type, float price) {
        this.type = type;
        this.price = price;
    }

    /**
     * Returns type of the Gnome
     *
     * @return type of the Gnome
     */
    public String getType() {
        return type;
    }

    /**
     * Returns price of the Gnome
     *
     * @return price of the Gnome
     */
    public float getPrice() {
        return price;
    }

    /**
     * Returns id of the Gnome
     *
     * @return id of the Gnome
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id of the Gnome
     *
     * @param id id and primary key of the Gnome
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns a hash code for this Gnome
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compares this gnome to the specified object. The result is true if and
     * only if the argument is not null and is a Gnome object that has same id
     * as this object.
     *
     * @param object The object to compare this Gnome against.
     * @return true if the given object represents a Gnome equivalent to this
     * gnome, false otherwise
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Gnome)) {
            return false;
        }
        Gnome other = (Gnome) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Returns the string value of this object
     * @return string value for Gnome
     */
    @Override
    public String toString() {
        return "model.Gnome[ id=" + id + " ]";
    }
}
