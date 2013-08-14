/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Exception for mismatch between password and password confirmation. 
 * @author Mary
 */
public class PasswordMismatchException extends UserExistException {

    /**
     * Constructor of PasswordMismatchException
     * @param msg Message which shows the cause of the exception
     */
    public PasswordMismatchException(String msg) {
        super(msg);
    }
    
}
