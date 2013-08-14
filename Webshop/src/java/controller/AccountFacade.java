package controller;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.Users;

/**
 * Handles all the actions related to Users Table for login and registration.
 *
 * @author Mary
 * @author Sina
 *
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class AccountFacade {

    @PersistenceContext(unitName = "WebshopPU")
    EntityManager em;

    /**
     * Registers new user
     *
     * @param username username of the user
     * @param password password of the user
     * @return The new user will be return
     */
    public Users registerAccount(String username, String password) {
        Users newUser = new Users(username, password, true, false);
        em.persist(newUser);
        return newUser;
    }

    /**
     * Checks user credentials for login to the system
     *
     * @param username username of the user
     * @param password password of the user
     * @return The registered user will be returned
     * @throws Exception If the user was not registered in the system exception
     * occurred
     */
    public Users loginAccount(String username, String password) throws Exception {
        Users found = em.find(Users.class, username);
        if (found == null) {
            System.out.println("exception");
            throw new Exception("No account with this username: " + username);
        } else if (!found.isActive()) {
            throw new Exception("Your account has been banned by the admin!");
        } else if (!found.getPassword().equals(password)) {
            throw new Exception("Password is wrong, please enter it again!");
        }
        return found;
    }
}
