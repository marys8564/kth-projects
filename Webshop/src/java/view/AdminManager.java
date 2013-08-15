package view;

import controller.AdminFacade;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import model.Inventory;
import model.Users;

/** All the relations between the xhtml file of Admin and its controller are handled in this class
 * @author Mary
 * @author Sina 
 * 
 */
@Named(value = "adminManager")
@ConversationScoped
public class AdminManager implements Serializable{
    /** Username of the logged in user*/
    private String username;
    /** List of the registered users */
    private List<Users> userList;
    /** HashMap contains the username and their activation status */
    private HashMap<String, String> active = new HashMap<String, String>();
    /** Rendered value to show the user management form*/
    private boolean userManagement;
    /** Rendered value to show the user management form*/
    private boolean inventoryManagement;
    /** The type of the gnome*/
    private String gnomeType;
    /** The price of the gnome*/
    private String gnomePrice;
    /** the units of the gnome*/
    private String gnomeQuantity;
    /** Facade object for handling the logic of Administrative process*/
    @EJB
    private AdminFacade adminFacade;
    /** Conversation object for communication between this object and Facade in ConversationScoped*/
    @Inject
    private Conversation conversation;
    /** Exception that occurred in here*/
    private Exception exception;
    
    /**
     * The default constructor of this class
     */
    public AdminManager() {
        FacesContext context = FacesContext.getCurrentInstance();
        LoginManager loginManager = (LoginManager) context.getApplication().evaluateExpressionGet(context, "#{loginManager}", LoginManager.class);
        username = loginManager.getUser().getUsername();
        userManagement = true;
        inventoryManagement = false;
    }
    /**
     * getter method for gnome type
     * @return Type of gnome
     */
    public String getGnomeType() {
        return gnomeType;
    }
    /**
     * setter method for gnome type
     * @param gnomeType Type of the gnome
     */
    public void setGnomeType(String gnomeType) {
        this.gnomeType = gnomeType;
    }
    /**
     * getter method for the price of the gnome
     * @return price of the gnome
     */
    public String getGnomePrice() {
        return gnomePrice;
    }
    /**
     * setter method for the price of the gnome
     * @param gnomePrice price of the gnome
     */
    public void setGnomePrice(String gnomePrice) {
        this.gnomePrice = gnomePrice;
    }
     /**
     * getter method for the quantity of the gnome
     * @return gnomeQuantity quantity of the gnome
     */
    public String getGnomeQuantity() {
        return gnomeQuantity;
    }
    /**
     * setter method for the gnome quantity
     * @param gnomeQuantity quantity of the gnome
     */
    public void setGnomeQuantity(String gnomeQuantity) {
        this.gnomeQuantity = gnomeQuantity;
    }

    /**
     * getter method for the exception
     * @return exception occurred
     */
    public Exception getException() {
        return exception;
    }

    /**
     * setter method for the exception
     * @param exception exception occurred
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    
    /**
     * Add new gnome to the database
     */
    public void addGnome(){
        if(gnomeType.equals("") || gnomePrice.equals("") || gnomeQuantity.equals("")){
            exception = new Exception("Please complete all the fields.");
        }else{
            try{
                int tempPrice = Integer.parseInt(gnomePrice);
                int tempQuantity = Integer.parseInt(gnomeQuantity);
                if(tempPrice<=0 || tempQuantity<=0){
                    throw new Exception();
                }
                adminFacade.addGnome(gnomeType,tempPrice,tempQuantity);
            }catch(Exception ex){
                exception = new Exception("Price and Amount should be numbers greater than 0.");
            }
            gnomeType = "";
            gnomePrice = "";
            gnomeQuantity = "";
        }
    }
    
    /**
     * show the user management form
     * @return true if userManagment is true, false otherwise
     */
    public boolean isUserManagement() {
        return userManagement;
    }
    
    /**
     * show the inventory management form
     * @return true if inventoryManagement is true, false otherwise
     */
    public boolean isInventoryManagement() {
        return inventoryManagement;
    }

    /**
     * Returns the HashMap of users activation status
     * @return HashMap of users activation status
     */
    public HashMap<String, String> getActive() {
        return active;
    }
    /**
     * Get the all the inventory list 
     * @return inventory list
     */
    public List<Inventory> getInventoryList(){
        startConversation();
        return adminFacade.getInventoryList();
    }
    /**
     * Get all the users registered in the system
     * @return the user's list
     */
    public List<Users> getUserList(){
        startConversation();
        try {
            userList = adminFacade.getUserList();
            for (Users user : userList) {
                if(user.isActive()){
                    active.put(user.getUsername(), "Active");    
                }else{
                    active.put(user.getUsername(), "Inactive");    
                }
            }            
        } catch (Exception ex) {
            Logger.getLogger(AdminManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }
    /**
     * Getter method for username
     * @return username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Setter method for the username
     * @param username  username of the user
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Start the conversation between the manager and the facade
     */
    private void startConversation() {
        if (conversation.isTransient()) {
            conversation.begin();
        }
    }
/**
 * Stop the conversation between the manager and the facade
 */
    private void stopConversation() {
        if (!conversation.isTransient()) {
            conversation.end();
        }
    }
    /**
     * Handle all the exception that occur in this class
     * @param ex exception occurred
     */
    private void handleException(Exception ex) {
        stopConversation();
        ex.printStackTrace(System.err);
        exception = ex;
    }
    
    /**
     * Change the activity status of the user 
     * @param user user object
     */
    public void changeActive(Users user){
        
        user = adminFacade.updateActive(user);
        if(user.isActive()){
            active.put(user.getUsername(), "Active");    
        }else{
            active.put(user.getUsername(), "Inactive");    
        }   
    }
    /**
     * change the form of the user management page
     */
    public void userManagementPage(){
        userManagement = true;
        inventoryManagement = false;
    }
    /**
     * change the form of the inventory management page
     */
    public void inventoryManagementPage(){
        userManagement = false;
        inventoryManagement = true;
    }
    /**
     * logout the Admin
     * @return The String logout
     */
    public String logout(){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        final HttpServletRequest request = (HttpServletRequest)ec.getRequest();
        request.getSession( false ).invalidate();
        return "logout";
    }
    /**
     * Remove the gnome  from inventory
     * @param inventoryGnome  The gnome should be removed from inventory
     */
    public void removeGnome(Inventory inventoryGnome){
        startConversation();
        adminFacade.removeGnome(inventoryGnome);
    }
}
