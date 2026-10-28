/**
This is the GameStarter Class that will be used for the final project. 
It is much cleaner than our midterms starter/main method because we included the other setUp methods within the setUpGUI.
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

public class GameStarter{

    public static void main(String[] args){       
        GameFrame gF = new GameFrame(600, 768);
        gF.connectToServer();
        gF.setUpGUI();
    }

}