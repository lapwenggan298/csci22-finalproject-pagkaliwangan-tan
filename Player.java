/**
This is the Player class for the Finals Project.
This class contains the constructor, the default properties than an instance of a player will have, and other methods that will be used for the project.
@author Luis Andrei P. Pagkaliwangan (243436), Jurvick Dietrich Tan (244392)
@version 16 May 2025
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

public class Player implements DrawingNats{

    public double width, height;
    public double xPosition, yPosition;
    private double movementSpeedLR, jumpPower;
    private Color colorMeShimbers;
    public double variableVerticalVelocity;

    public static double pullingForce = 0.8; 
    public static double maxDownSpeed = 10;
    private double velY = 0; //variableVerticalVelocity

     
    private boolean isOnGround, faceLeft, faceRight;

    /**Player Class Constructor
     * @param x - the position on the x-axis which the instance of the player class will be on.
     * @param y - the position on the y-axis which the instance of the player class will be on.
     * @param c - the color of the player
     */
    public Player(int x, int y, Color c) {
        xPosition = x;
        yPosition = y;
        colorMeShimbers = c;

        isOnGround = true;
        faceLeft = false;
        faceRight = false;

        width = 5;
        height = 10;

        movementSpeedLR = 4;
        jumpPower = -15;
    }

    /**Draw method inherited from the DrawingNats interface implemented by this class. will draw the players according to the set xPos, yPos, width, and height. */
    public void draw(Graphics2D g2d){
        AffineTransform reset = g2d.getTransform();
        Rectangle2D.Double hitbox = new Rectangle2D.Double(xPosition,yPosition,width,height);
        g2d.setColor(colorMeShimbers);
        g2d.fill(hitbox);
        g2d.setTransform(reset);
    }

    /**This method sets the xPosition of an instance of this class to the specified number x.
     * @param x - the value at which the xPosition of an instance will be set to.
     */
    public void setXPos(double x){
        xPosition = x;
    }

    /**This method sets the yPosition of an instance of this class to the specified number y.
     * @param y - the value at which the yPosition of an instance will be set to.
     */
    public void setYPos(double y){
        yPosition = y;
    }

    
    /**This method returns the xPosition of an instance of the player class. Useful for collision.
     * @return xPosition of an instance of this class.
     */
    public double getXPos(){
        return xPosition;
    }

    /**This method returns the yPosition of an instance of the player class. Useful for collision.
     * @return yPosition of an instance of this class.
     */
    public double getYPos(){
        return yPosition;
    }

    /**This method allows the vertical velocity of an instance of this class to change. What will be added to variableVerticalVelocity depends on if the instance is moving up (n<0) or moving down (n>0).
     * @param num is what will be added to the variableVerticalVelocity if it is still not beyond maxDownSpeed.
     */
    public void changeVariableVerticalVelocity(double num){
        if (variableVerticalVelocity > 0 && (variableVerticalVelocity < maxDownSpeed) && (isOnGround == false)){
            variableVerticalVelocity += num;
        }
        else if (num <= 0){
            variableVerticalVelocity += pullingForce;
        }
        else if (variableVerticalVelocity == 0){
            isOnGround = true;
            variableVerticalVelocity = 0;
        }
    }

    /** This method allows the program to set the variableeVerticalVelocity as a certain number represented by boom.
     * @param boom is the number which the variableVerticalVelocity will be set as. 
     */
    public void setVariableVerticalVelocity(double boom){
        variableVerticalVelocity = boom;
    }

    /** This method returns the value of the variableVerticalVelocity of an instance of the Player class.
     * @return variableVerticalVelocity of an instance
     */
    public double getVariableVerticalVelocity(){
        return variableVerticalVelocity;
    }

    /** This method returns the gravity or the pulling force of the whole class
     * @return pullingForce of the class.
     */
    public static double getPullingForce(){
        return pullingForce;
    }

    /**This method returns the max speed at which the instances can fall down.
     * @return maxDownSpeed of the class.
    */
    public static double getMaxDownSpeed(){
        return maxDownSpeed;
    }

    /**This method returns the width of the instance of the player class. Useful for collision.
     * @return width of the instance of the class.
     */
    public double getPlayerWidth(){
        return this.width;
    }

    /**This method returns the height of the instance of the Player class. Useful for collisions.
     * @return height of the instance of the class.
     */
    public double getPlayerHeight(){
        return this.height;
    }
    
    /**This method returns the movement speed of the instance of the player class on the ground or on a surface.
     * @return movementSpeedLR of the instance of the class.
    */
    public double getSpeed(){
        return movementSpeedLR;
    }

    /** getJump method returns the jumpPower of an instance of the class.
     * @return jumpPower - The jumpPower of an instance of the Player class. 
    */
    public double getJump(){
        return jumpPower;
    }
    
    /**This method allows an instance of this class to move towards the right or left depending on the number input.
     * @param direction number which will determine if player moves towards the right or left.
    */
    public void moveX(int direction){
        if(direction == 0){
            xPosition += movementSpeedLR;
            faceRight = true;
            faceLeft = false;
        }
        else {
            xPosition -= movementSpeedLR;
            faceLeft = true;
            faceRight = false;
        }
    }

    /**This methods moves an instance of this class by speedY units along the yAxis by adding the speed on the instance's yPosition.
     * @param speedY - the number of units an instance of the player class will be moved on the y-axis.
     */
    public void moveY(double speedY){
        yPosition += speedY;
    }

    /**Sets the players vertical velocity to -15, causing them to jump. The velocity *decreases* by gravity constantly until the player reaches maxDownSpeed or the ground. Will only set velocity to -10 if player isOnGround == true.*/
    public void jump() {
        if (isPlayerGrounded()) {
            setVariableVerticalVelocity(jumpPower);
            setIsOnGroundFalse();
        }
    }

    /**Sets the condition isOnGround to false. */
    public void setIsOnGroundFalse(){
        isOnGround = false;
    }

    /**Sets the condition isOnGround to True. */
    public void setIsOnGroundTrue(){
        isOnGround = true;
    }

    /**Returns the isOnGround boolean of an instance of this class
     * @return isOnGround boolean which can be true or false depending on the situation.
    */
    public boolean isPlayerGrounded(){
        return isOnGround;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D.Double(xPosition, yPosition, width, height);
    }

    public boolean checkCollision(Player player){
        Rectangle2D playerRect = player.getBounds();
        return getBounds().intersects(playerRect);
    }

}