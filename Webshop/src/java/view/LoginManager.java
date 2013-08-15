package view;

import controller.AccountFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Users;

/**
 * All the relations between the xhtml file of Login and its controller are handled in this class
 * @author Mary
 * @author Sina
 */
@Named(value = "loginManager")
@ConversationScoped
public class LoginManager implements Serializable {
    
    /** Users object of the logged in user. */
    private Users user;
    /** Entered username for the login. */
    private String username;
    /** Entered password for the login. */
    private String password;
    /** Shows the access level of the user. Admin or an ordinary user */
    private boolean admin;
    /** Facade object for handling the logic of Login process*/
    @EJB
    private AccountFacade accountFacade;
    /** Conversation object for communication between this object and Facade in ConversationScoped*/
    @Inject
    private Conversation conversation;
    /** Exception which may occurs and should be shown to the user */
    private Exception exception;

    /**
     * Default Constructor
     */
    public LoginManager() {
    }

    /**
     * Returns the logged in Users object.
     * @return the logged in Users object
     */
    public Users getUser() {
        return user;
    }
    
    /**
     * Indicates if the process was successful or not.
     * @return true if there are no exceptions, false otherwise
     */
    public boolean getSuccess() {
        return exception == null;
    }
    
    /**
     * Returns the access level of the logged in user.
     * @return true if the logged in user is admin, false otherwise
     */
    public boolean getAdmin(){
        return admin;
    }

    /**
     * Sets the entered username in the Login form.
     * @param username entered username in the Login form
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the entered password in the Login form.
     * @param password entered password in the Login form
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the entered username in the Login form.
     * @return the entered username in the Login form
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the entered password in the Login form.
     * @return the entered password in the Login form
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns any exception occurred in this scope.
     * @return the exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Starts the conversation object for communication with Facade.
     */
    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }
    
    /**
     * Stops the conversation object used for communication with Facade.
     */
    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }

    /**
     * Handles the context when any exception occurs
     * @param ex the exception occurs
     */
    private void handleException(Exception ex) {
        stopConversation();
        ex.printStackTrace(System.err);
        exception = ex;
    }

    /**
     * Handles the login process for the user due to the entered values in the form.
     */
    public void loginAccount() {
        if (username.equals("") || password.equals("")) {
            exception = new Exception("Please complete all the fields");
        } else {
            startConversation();
            try {
                user = accountFacade.loginAccount(username, password);
                if(user.isAccessControl()){
                    admin = true;
                }
            } catch (Exception ex) {
                handleException(ex);
            }
        }
    }
}
