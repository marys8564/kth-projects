/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Exception for not existing user
 * @author Mary
 * @author Sina
 */
public class UserExistException extends Exception {

    /**
     * Constructor of UserExistException
     * @param msg Message which shows the cause of the exception
     */
    public UserExistException(String msg) {
        super(msg); 
    }
}
