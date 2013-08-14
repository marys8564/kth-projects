package controller;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.History;
import model.Inventory;
import model.ShoppingCart;
import model.ShoppingCartID;
import model.Users;

/**
 * Handles all logics corresponded to Market tasks is defined in this class.
 *
 * @author Sina
 * @author Mary
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class MarketFacade {

    @PersistenceContext(unitName = "WebshopPU")
    EntityManager em;

    /**
     * Returns all the gnome in the inventory.
     *
     * @return List of all gnomes
     */
    public List<Inventory> getInventoryList() {
        return em.createNamedQuery(Inventory.QUERY_GET_ALL, Inventory.class).
                getResultList();
    }

    /**
     * Returns all the gnomes of user in his shopping cart
     *
     * @param user The user object
     * @return List of the gnomes in the shopping cart
     */
    public List<ShoppingCart> getShoppingList(Users user) {
        return em.createNamedQuery(ShoppingCart.QUERY_GET_SHOPPING_CART, ShoppingCart.class).
                setParameter(ShoppingCart.COLUMN_USERS, user).
                getResultList();
    }

    /**
     * Returns all the gnomes of user in his history
     *
     * @param user The user object
     * @return List of the gnomes in the history
     */
    public List<History> getHistoryList(Users user) {
        return em.createNamedQuery(History.QUERY_GET_HISTORY, History.class).
                setParameter(History.COLUMN_USERS, user).
                getResultList();
    }

    /**
     * Removes gnome from shopping cart and adds it to the history
     *
     * @param user User object
     */
    public void buyShoppingCart(Users user) {
        List<ShoppingCart> cart = getShoppingList(user);
        for (ShoppingCart shoppingCart : cart) {
            ShoppingCart attached = (ShoppingCart) em.merge(shoppingCart);
            em.remove(attached);
            History found = (History) em.find(History.class, new ShoppingCartID(shoppingCart.getGnome().getId(),
                    user.getUsername()));
            if (found == null) {
                found = new History(shoppingCart.getGnome(), shoppingCart.getUsers(),
                        shoppingCart.getBasketAmount());
            } else {
                found.setBasketAmount(found.getBasketAmount() + shoppingCart.getBasketAmount());
            }
            em.merge(found);
        }
    }

    /**
     * Adds gnome to the shopping card based on the user request
     *
     * @param inventory Inventory object
     * @param amount The amount of the gnome to be added
     * @param user The user that wants to add the gnome to the shopping cart
     */
    public void addToShoppingCart(Inventory inventory, int amount, Users user) {
        ShoppingCart found = (ShoppingCart) em.find(ShoppingCart.class, new ShoppingCartID(inventory.getGnome().getId(),
                user.getUsername()));
        if (found == null) {
            found = new ShoppingCart(inventory.getGnome(), user, amount);
        } else {
            found.setBasketAmount(found.getBasketAmount() + amount);
        }
        em.merge(found);
        int newAmount = inventory.getAmount() - amount;
        inventory.setAmount(newAmount);
        Inventory attached = (Inventory) em.merge(inventory);
        if (newAmount == 0) {
            em.remove(attached);
        }
    }

    /**
     * Removes the gnome from shopping cart
     *
     * @param cart Shopping cart object
     */
    public void removeCart(ShoppingCart cart) {
        ShoppingCart attached = (ShoppingCart) em.merge(cart);
        em.remove(attached);
        Inventory resultInventory = em.createNamedQuery(Inventory.QUERY_GET_INVENTORY, Inventory.class).
                setParameter(Inventory.COLUMN_GNOME, cart.getGnome()).
                getSingleResult();
        resultInventory.setAmount(resultInventory.getAmount() + cart.getBasketAmount());
        em.merge(resultInventory);
    }
}
