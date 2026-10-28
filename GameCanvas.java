/**
This is the GameCanvas for the Finals Project.
It will extend JComponent, and will draw the java_g2d objects that will be put in its list and these will have anti-aliasing.
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
import javax.swing.*;
import java.util.ArrayList;

public class GameCanvas extends JComponent{

    public ArrayList<DrawingNats> thingsToDraw;
    
    /**GameCanvas Class Constructor */
    public GameCanvas(){
        thingsToDraw = new ArrayList<DrawingNats>();
    }

    /**Method contains anti-aliasing, and a for loop to draw the objects and players needed for the finals*/
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        /**Anti-Aliasing */
        RenderingHints renderHint = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(renderHint);

        for(DrawingNats drawMe : thingsToDraw){
            drawMe.draw(g2d);
        }
    }

    /**addToDrawingList method allows the gameFrame to submit all java g2d objects to the GameCanvas.
     * For cleanliness of the GameCanvas
     * @param dN - elements or instances of classes that implement drawingNats
     */
    public void addToDrawingList(DrawingNats dN){
        thingsToDraw.add(dN);
    }

}