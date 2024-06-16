/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hackingthefuture;

/**
 *
 * @author Asus
 */

// Abstract class created so that controller of different pages share a common parent class
// Important for back navigation
public abstract class Controller {
    // Refresh method needed when switched back to a page
    public abstract void refresh();
}
