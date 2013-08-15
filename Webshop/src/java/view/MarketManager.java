package view;

import controller.MarketFacade;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import model.History;
import model.Inventory;
import model.ShoppingCart;
import model.Users;

/**
 * All the relations for Market between the xhtml file of home and its controller are handled in this class
 * @author Mary
 * @author Sina
 */
@Named("marketManager")
@ConversationScoped
public class MarketManager implements Serializable{

    private List<Inventory> inventoryList;
    private List<ShoppingCart> shoppingList;
    private List<History> historyList;
    private HashMap<Integer,Integer> quantities = new HashMap<Integer, Integer>();
    private Inventory finalInventory;
    private Users user;
    private boolean market;
    private boolean shoppingCart;
    private boolean history;
    private String desiredUnit;
    private String lastQuantity;
    private String totalPrice;
    @EJB
    private MarketFacade marketFacade;
    @Inject
    private Conversation conversation;    
    private Exception exception;
    private String quantityField;
    /**
     * Creates a new instance of MarketManager
     */
    public MarketManager() {
        FacesContext context = FacesContext.getCurrentInstance();
        LoginManager loginManager = (LoginManager) context.getApplication().evaluateExpressionGet(context, "#{loginManager}", LoginManager.class);
        user = loginManager.getUser();
        market = true;
        shoppingCart = false;
        history = false;
    }

    /**
     * Returns the history page rendering status
     * @return the history page rendering status
     */
    public boolean isHistory() {
        return history;
    }

    /**
     * Sets the history page rendering
     * @param history
     */
    public void setHistory(boolean history) {
        this.history = history;
    }

    /**
     * Returns the final object to buy
     * @return the final object to buy
     */
    public Inventory getFinalInventory() {
        return finalInventory;
    }

    /**
     * Set the final object to buy
     * @param finalInventory the final object to buy
     */
    public void setFinalInventory(Inventory finalInventory) {
        this.finalInventory = finalInventory;
    }

    /**
     *
     * @return
     */
    public HashMap<Integer, Integer> getQuantities() {
        return quantities;
    }

    /**
     * 
     * @param quantities
     */
    public void setQuantities(HashMap<Integer, Integer> quantities) {
        this.quantities = quantities;
    }

    /**
     * Returns the total price of the shopping cart
     * @return the total price of the shopping cart
     */
    public String getTotalPrice() {
        int total = 0;
        for (ShoppingCart cart : getShoppingList()) {
            total+=cart.getGnome().getPrice()*cart.getBasketAmount();
        }
        return String.valueOf(total);
    }

    /**
     * Sets the total price of shopping cart
     * @param totalPrice total price of the shopping cart
     */
    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Returns the exception
     * @return exception
     */
    public Exception getException() {
        return exception;
    }

    /**
     * Sets the exception occurred
     * @param exception 
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }

    
    /**
     * Returns the inventory list of the market
     * @return the inventory list of the market
     */
    public List<Inventory> getInventoryList() {
        startConversation();
        inventoryList = marketFacade.getInventoryList();
        for (Inventory inventoryObj : inventoryList) {
            quantities.put(inventoryObj.getGnome().getId(),0);
        }
        return inventoryList;
    }

    /**
     * Returns the shopping cart of the user
     * @return the shopping cart of the user
     */
    public List<ShoppingCart> getShoppingList() {
        startConversation();
        shoppingList = marketFacade.getShoppingList(user);
        return shoppingList;
    }

    /**
     * Returns the history list of the user
     * @return the history list of the user
     */
    public List<History> getHistoryList() {
        historyList = marketFacade.getHistoryList(user);
        return historyList;
    }

    /**
     * Sets the history list
     * @param historyList history list
     */
    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }

    
    /**
     * Sets the selected gnome in order to buy
     * @param inventory
     */
    public void addGnome(Inventory inventory){
        finalInventory = inventory;
    }
    
    /**
     * Adds a new object to the shopping cart of the user
     */
    public void addShoppingCart(){
        if(quantityField.equals("")){
            handleException(new Exception("Please enter your desired unit."));
            return;
        }
        if(finalInventory.getAmount()<Integer.parseInt(quantityField)){
            handleException(new Exception("Your entered unit is greater than available amount"));
            return;
        }
        marketFacade.addToShoppingCart(finalInventory,Integer.parseInt(quantityField),user);
        finalInventory = null;
    }

    /**
     * Returns the quantity from the form
     * @return the quantity
     */
    public String getQuantityField() {
        return quantityField;
    }

    /** 
     * Sets the desired quantity
     * @param quantityField desired quantity from the form
     */
    public void setQuantityField(String quantityField) {
        this.quantityField = quantityField;
    }
    
    
    /**
     * Removes an object from the shopping cart of the user
     * @param cart removing object
     */
    public void removeFromCart(ShoppingCart cart){
        marketFacade.removeCart(cart);
    }
    /**
     * Returns market rendered form status
     * @return market rendered form status
     */
    public boolean isMarket() {
        return market;
    }

    /**
     * Returns shopping cart rendered form status
     * @return shopping cart rendered form status
     */
    public boolean isShoppingCart() {
        return shoppingCart;
    }

    /**
     * Returns the desired unit
     * @return desired unit
     */
    public String getDesiredUnit() {
        return desiredUnit;
    }

    /**
     * Sets the desired unit for buying
     * @param desiredUnit desired unit 
     */
    public void setDesiredUnit(String desiredUnit) {
        this.desiredUnit = desiredUnit;
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
     * Sets the Market form to displayed
     */
    public void marketPage(){
        market = true;
        shoppingCart = false;
        history = false;
    }
    
    /**
     * Sets the ShoppingCart form to displayed
     */
    public void shoppingPage(){
        market = false;
        shoppingCart = true;
        history = false;
    }
    
    /**
     * Sets the History form to displayed
     */
    public void historyPage(){
        market = false;
        shoppingCart = false;
        history = true;
    }
    
    /**
     * Logout the user from the system
     * @return logout string for navigation
     */
    public String logout(){
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext ec = context.getExternalContext();
        final HttpServletRequest request = (HttpServletRequest)ec.getRequest();
        request.getSession( false ).invalidate();
        return "logout";
    }
    
    /**
     * Buys the Gnomes in the shopping cart of the user
     */
    public void buy(){
        marketFacade.buyShoppingCart(user);
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
}
