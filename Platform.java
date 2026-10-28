/**
This is the Platform class implementing the DrawingNats interface for making rectangular platforms. 
It will make up the course that the players must finish.
@author Luis Andrei P. Pagkaliwangan (243436), Jurvick Dietrich Tan (244392)
@version 17 May 2025
We have not discussed the Java language code in our program
with anyone other than our instructor or the teaching assistants
assigned to this course.
We have not used Java language code obtained from another student,
or any other unauthorized source, either modified or unmodified.
If any Java language code or documentation used in our program
was obtained from another source, such as a textbook or website,
that has been clearly noted with a proper citation in the comments
of our program.
**/

import java.awt.*;
import java.awt.geom.*;

public class Platform implements DrawingNats {

    private double xPos, yPos, width, height;
    private Color color;
    
    /**Platform Class Constructor: 
     * @param x - the point on the x-axis at which the platform will be created.
     * @param y - the points on the y-axis at which the platform will be created.
     * @param w - the width.
     * @param h - the height.
     * @param c - the color.*/
    public Platform(double x, double y, double w, double h, Color c){
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        color = c;
    }

    /**Draw method which will draw the Platform in the specified x and y positions, with specific width and height. */
    public void draw(Graphics2D g2d){
        AffineTransform reset = g2d.getTransform();
        Rectangle2D.Double rect = new Rectangle2D.Double(xPos, yPos, width, height);
        g2d.setColor(color);

        g2d.fill(rect);

        g2d.setTransform(reset);
    }

    /** A method that allows an instance of the Platform class to move n units along the x-axis.
     * @param distance is of double value and accepts positive and negative values which will move the instance either to the left or right. The parameter also is added to the instance's current x position.
     */
    public void adjustX(double distance){
        xPos += distance;
    }

    /** A method that allows an instance of the Platform class to move n units along the y-axis.
     * @param distance is of double value and accepts positive and negative values which will move the instance either to the left or right. The parameter also is added to the instance's current y position.
     */
    public void adjustY(double distance){
        yPos += distance;
    }

    /** Returns the xPos variable of an instance of the Platform class.
    * @return the position of the drawn object's upper left corner along the x-axis.
    */
    public double getX(){
        return xPos;
    }

    /** Returns the yPos variable of an instance of the Platform class.
    * @return the position of the drawn object's upper left corner along the y-axis.
    */
    public double getY(){
        return yPos;
    }

    /** Returns the height variable of an instance of the Platform class.
    * @return height of an instance.
    */
    public double getHeight(){
        return height;
    }

    /** Returns the width variable of an instance of the Platform class.
    * @return width of an instance.
    */
    public double getWidth(){
        return width;
    }

    /**Gets the bounds or hitbox of an instance of the Platform class.
     * @return full platform.
     */
    public Rectangle2D getBounds() {
    return new Rectangle2D.Double(xPos, yPos, width, height);
    }

    public boolean isCollidingWithPlayerSide(Player p){
        boolean amICollidingX = !(((p.getXPos()+p.getPlayerWidth()) < this.xPos) || (p.getXPos() > (this.xPos + this.width)));
        return amICollidingX;
    }

    public boolean isCollidingWithPlayerTopBot(Player p){
        boolean amICollidingY = !(((p.getYPos()+p.getPlayerHeight()) < this.yPos) || (p.getYPos() > (this.yPos + this.height)));
        return amICollidingY;
    }

}