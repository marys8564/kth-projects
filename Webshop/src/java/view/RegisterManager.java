/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.AccountFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.PasswordMismatchException;
import model.UserExistException;

/**
 * All the relations between the xhtml file of Register and its controller are handled in this class
 * @author Mary
 * @author Sina
 */
@Named(value = "registerManager")
@ConversationScoped
public class RegisterManager implements Serializable {

    /** entered username for the registration*/
    private String username;
    /** entered password for the registration*/
    private String password;
    /** entered retype password for the registration*/
    private String reTypePassword;
    /** Exception which may occurs and should be shown to the user */
    private Exception exception;
    /** Facade object for handling the logic of Registration process*/
    @EJB
    private AccountFacade accountFacade;
    /** Conversation object for communication between this object and Facade in ConversationScoped*/
    @Inject
    private Conversation conversation;

    /**
     * Default Constructor
     */
    public RegisterManager() {
    }

    /**
     * Sets the username of the User
     * @param username entered username in the form
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of the User
     * @param password entered password in the form
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the retype password
     * @param reTypePassword entered retype password in the form
     */
    public void setReTypePassword(String reTypePassword) {
        this.reTypePassword = reTypePassword;
    }

    /**
     * Returns the username of the User. 
     * @return the username entered in the form
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the password of the User
     * @return password entered in the form
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the retype password 
     * @return retype password entered in the form
     */
    public String getReTypePassword() {
        return reTypePassword;
    }

    /**
     * Returns the exception occurred
     * @return exception 
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
     * Returns the success
     * @return true if no exception occurred, false otherwise
     */
    public boolean getSuccess() {
        return exception == null;
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
     * Registers the user account based on the inputs
     */
    public void registerAccount() {
        if(username.equals("") || password.equals("") || reTypePassword.equals("") ){
            exception = new Exception("Please complete all the fields");
        }else if (reTypePassword.equals(password)) {
            System.out.println("Manager: " + username + " " + password);
            startConversation();
            try{
                accountFacade.registerAccount(username, password);
            }catch(Exception e){
                handleException(new UserExistException("This username: "+ username +" is already registered"));
            }
        } else {
            exception = new PasswordMismatchException("Your passwords don't match. Please enter your passwords again.");    
        }
    }
}
