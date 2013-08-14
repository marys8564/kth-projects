/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Gnome;
import model.Inventory;
import model.Users;

/**
 * Handles all logics corresponded to Administrative tasks is defined in this
 * class.
 *
 * @author Sina
 * @author Mary
 *
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class AdminFacade {

    @PersistenceContext(unitName = "WebshopPU")
    EntityManager em;

    /**
     * Search in the database and return all the users.
     *
     * @return All the users registered in the system
     */
    public List<Users> getUserList() {
        return em.createNamedQuery(Users.GET_ACTIVE_USER, Users.class).
                setParameter(Users.COLUMN_ACCESS_CONTROL, true).
                getResultList();
    }

    /**
     * Changes the user activation status
     *
     * @param user User registered in the system
     * @return updated user
     */
    public Users updateActive(Users user) {
        user.setActive(!user.isActive());
        user = em.merge(user);
        return user;
    }

    /**
     * Returns all the inventory
     *
     * @return List of the all inventories.
     */
    public List<Inventory> getInventoryList() {
        return em.createNamedQuery(Inventory.QUERY_GET_ALL, Inventory.class).
                getResultList();
    }

    /**
     * Removes the gnome from inventory
     *
     * @param inventoryGnome The inventory object
     */
    public void removeGnome(Inventory inventoryGnome) {
        Inventory attached = (Inventory) em.merge(inventoryGnome);
        em.remove(attached);
    }

    /**
     * Adds gnome to the database
     *
     * @param gnomeType Type of the gnome object
     * @param gnomePrice The price of the gnome
     * @param gnomeQuantity Number of the units of each gnome
     */
    public void addGnome(String gnomeType, int gnomePrice, int gnomeQuantity) {
        List<Gnome> gnomes = em.createNamedQuery(Gnome.QUERY_GET_GNOME, Gnome.class).
                setParameter(Gnome.COLUMN_TYPE, gnomeType).
                setParameter(Gnome.COLUMN_PRICE, gnomePrice).
                getResultList();
        if (gnomes.isEmpty()) {
            Gnome gnome = em.merge(new Gnome(gnomeType, gnomePrice));
            Inventory inventory = new Inventory(gnome, gnomeQuantity);
            em.persist(inventory);
        } else {
            em.merge(new Inventory(gnomes.get(0), gnomeQuantity));
        }
    }
}
