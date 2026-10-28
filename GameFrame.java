/**
This is the GameFrame Class that will be used for the final project. 
It contains the code for jumping, moving, timer, and o
@author Luis Andrei P. Pagkaliwangan (243436), Jurvick Dietrich Tan (244392)
@version 18 May 2025
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
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.net.*;
import java.util.*;

public class GameFrame{

    private JFrame frame;
    private JPanel contentPane; 
    private int windowWidth, windowHeight;
    private javax.swing.Timer moveItTimer;

    private boolean leftPressed, rightPressed;
    private Scanner console;

    private GameCanvas gamingCanvas;
    private ArrayList<Platform> platformsDrawn;

    private Player me, opp;
    private int myPlayerID;

    private Socket socket;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;

    /**Class Constructor 
    * @param w is the width of the window
    * @param h is the height of the window
    */
    public GameFrame(int w, int h){
        windowWidth = w;
        windowHeight = h;
        leftPressed = false;
        rightPressed = false;
        console = new Scanner(System.in);
        
        frame = new JFrame();
        gamingCanvas = new GameCanvas();

        platformsDrawn = new ArrayList<Platform>();
    }

    /**setUpGUI Method
     * Includes the setUpDrawings(), setUpKeyBinds(), and setUpTimer() methods.
    */
    public void setUpGUI(){
        /**Set the content pane as focusable for keyBinds */
        contentPane = (JPanel) frame.getContentPane();
        contentPane.setFocusable(true);

        //Other Methods - Will Update
        gamingCanvas.setPreferredSize(new Dimension(windowWidth, windowHeight));

        setUpDrawings();
        setUpKeyBinds();
        setUpTimer();

        contentPane.add(gamingCanvas);

        //setUpListeningForUpdates();

        /**Make JFrame/GameFrame visible to client/player.*/
        frame.setTitle("Final Project: Squump - Pagkaliwangan - Tan" + "Player # " + myPlayerID);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**setUpDrawings will contain all platforms for the level in one array list and the array list will help for collision as well. 
     * The elements in the array list will then be put into the array list of the GameCanvas along with the 2 other players.
     * */ 
    public void setUpDrawings(){
        /**Platforms used for the level will be placed in the arrayList*/
        platformsDrawn.add( new Platform(0, 600, 2000, 50, Color.DARK_GRAY)); //Floor
        platformsDrawn.add( new Platform(250, 500, 100, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(200, 390, 50, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(350, 390, 50, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(250, 300, 100, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(250, 200, 100, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(110, 100, 380, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(380, 170, 20, 330, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(200, 170, 20, 330, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(390, 480, 210, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(0, 480, 210, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(110, 50, 20, 340, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(470, 50, 20, 340, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(90, 370, 40, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(470, 370, 40, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(-10, 280, 30, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(580, 280, 30, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(90, 200, 40, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(470, 200, 40, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(-10, 100, 30, 20, Color.DARK_GRAY));
        platformsDrawn.add( new Platform(580, 100, 30, 20, Color.DARK_GRAY));

        /**Trophy/Win Block */
        platformsDrawn.add( new Platform(280, 20, 40, 40, Color.YELLOW));
        
        for(Platform pD: platformsDrawn){
            gamingCanvas.addToDrawingList(pD);
        }

        /**Player 1 is Color Blue, Player 2 is Color Red */
        if(myPlayerID == 1){
            me = new Player(200, 550, Color.BLUE);
            opp = new Player(400, 550, Color.RED);
        }
        else{
            me = new Player(400, 550, Color.RED);
            opp = new Player(200, 550, Color.BLUE);
        }

        gamingCanvas.addToDrawingList(me);
        gamingCanvas.addToDrawingList(opp);
        gamingCanvas.repaint();
    }

    /**setUpKeyBinds Method will set up the keybinds and the actions that will occur depending on the key/s pressed.
     * W, [SPACE], and UP arrow keys for jump.
     * D and RIGHT arrow keys for move right.
     * A and LEFT arrow keys for move left.
    */
    public void setUpKeyBinds(){
        ActionMap am = contentPane.getActionMap();
        InputMap im = contentPane.getInputMap();

        // Jump action (only on press, no release needed)
        Action jumper = new AbstractAction(){
            public void actionPerformed(ActionEvent ae){
                if(me.isPlayerGrounded()){
                    me.jump();
                    System.out.println("We jumped");
                    //csc.gameOn("PLAYER_JUMP");
                }
            }
        };

        // Pressed left
        am.put("moveLeftPressed", new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                System.out.println("We moved left");
                leftPressed = true;
            }
        });

        // Released left
        am.put("moveLeftReleased", new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                leftPressed = false;
            }
        });

        // Pressed right
        am.put("moveRightPressed", new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                System.out.println("We moved right");
                rightPressed = true;
            }
        });

        // Released right
        am.put("moveRightReleased", new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                rightPressed = false;
            }
        });

        // Bind jump action
        am.put("weJump", jumper);

        // Bind keys for jump
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "weJump");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, false), "weJump");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "weJump");

        // Bind keys for left pressed/released
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "moveLeftPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "moveLeftReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "moveLeftPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), "moveLeftReleased");

        // Bind keys for right pressed/released
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "moveRightPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "moveRightReleased");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "moveRightPressed");
        im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), "moveRightReleased");
    }

    private void checkPlatformCollisions(Player player) {
        Rectangle2D playerRect = player.getBounds();

        for (Platform dn : platformsDrawn) {
            if (dn instanceof Platform) {
                Platform platform = (Platform) dn;
                Rectangle2D platRect = platform.getBounds();

                if (playerRect.intersects(platRect)) {
                    Rectangle2D intersection = playerRect.createIntersection(platRect);

                    // Check where the collision happens the most
                    double dx = intersection.getWidth();
                    double dy = intersection.getHeight();

                    if (dy < dx) {
                        // Vertical collision
                        if (player.getYPos() < platform.getY()) {
                            // Landed on top
                            player.setYPos(platform.getY() - player.getPlayerHeight());
                            player.setVariableVerticalVelocity(0);
                            player.setIsOnGroundTrue();
                        } else {
                            // Hit the bottom
                            player.setYPos(platform.getY() + platform.getHeight());
                            player.setVariableVerticalVelocity(1); // push downward
                        }
                    } else {
                        // Horizontal collision
                        if (player.getXPos() < platform.getX()) {
                            // Hit left side of platform
                            player.setXPos(platform.getX() - player.getPlayerWidth());
                        } else {
                            // Hit right side of platform
                            player.setXPos(platform.getX() + platform.getWidth());
                        }
                    }
                }
            }
        }
    }  

    /**setUpTimer Method which sets up the animationTimer beautifully named moveItTimer.
     * Responsible for the gravity and light collision checking with the floor and the roof/top of the window.
    */
    public void setUpTimer(){
        moveItTimer = new javax.swing.Timer(16, new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent ae){
            final double gravity = me.getPullingForce();
            final double terminalVelocity = me.getMaxDownSpeed();

            // Handle horizontal movement based on key states
            if (leftPressed && me.getXPos() > 0) {
                me.moveX(1); // Move left
                //checkPlatformCollisions(me);

            }
            if (rightPressed && ((me.getXPos() + me.getPlayerWidth()) < windowWidth)) {
                me.moveX(0); // Move right
                //checkPlatformCollisions(me);
            }

            // If player falls below or is on the floor, correct position
            if ((me.getYPos() + me.getPlayerHeight()) >= 595) {
                me.setVariableVerticalVelocity(0);
                me.setYPos(600 - me.getPlayerHeight());
                me.setIsOnGroundTrue();
            }

            checkPlatformCollisions(me);
            me.moveY(me.getVariableVerticalVelocity());
            gamingCanvas.repaint();

            /**for(Platform pD: platformsDrawn){
                if(pD.isCollidingWithPlayerSide(me)== true){
                    if((me.getXPos() + me.getPlayerWidth()) >= (pD.getX())){//left of box
                        me.setXPos((pD.getX()-me.getPlayerWidth()));
                    }
                    else if ((me.getXPos())<=(pD.getX() + pD.getWidth())){//right of box
                        me.setXPos(pD.getX()+pD.getWidth());
                    }

                }
                else if(pD.isCollidingWithPlayerTopBot(me) == true){
                    if((me.getYPos()) <=(pD.getY() + pD.getHeight())){ //bottom of box
                        me.setVariableVerticalVelocity(0);
                        me.setYPos(pD.getY()+pD.getHeight());
                        me.setIsOnGroundFalse();
                    }
                    else if ((me.getYPos() + me.getPlayerHeight()) >= pD.getY()){ //top
                        me.setIsOnGroundTrue();
                        me.setYPos(pD.getY()  -me.getPlayerHeight());
                    }
                }
            }**/
        }
        });
        moveItTimer.start();
    }

    /** connectToServer method will connect the gameFrame to the gameServer. It will also set up the runnable threads, readFromServer and writeToServer so it can send and receive data to and from the GameServer.
     */
    public void connectToServer(){
        try {
            System.out.print("Please input the server's or host's IP Address: ");
            String ipAddress = console.nextLine();

            /** Port Number is 42069 */
            System.out.print("Please input the port number: ");
            int portNum = Integer.parseInt(console.nextLine());

            System.out.println("ATTEMPTING TO CONNECT TO SERVER...");
            socket = new Socket(ipAddress, portNum);
            System.out.println("CONNECTION SUCCESSFUL!");

            DataInputStream dataIn = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOut = new DataOutputStream(socket.getOutputStream());

            myPlayerID = dataIn.readInt();
            System.out.println("You are Player " + myPlayerID + ".");

            if(myPlayerID == 1){
                System.out.println("Waiting for Player 2 to connect...");
            }

            rfsRunnable = new ReadFromServer(dataIn);
            wtsRunnable = new WriteToServer(dataOut);

            rfsRunnable.waitForStartMsg();

        } catch (IOException ex) {
            System.out.println("IOException in connectToServer() method of GameFrame.");
        }
    }

    /**Inner Class ReadFromServer which implements Runnable */
    private class ReadFromServer implements Runnable{

        private DataInputStream dataIn;

        /**ReadFromServer Inner Class Constructor 
         * @param received - the dataStream which will be used to receive data from the GameServer.
        */
        public ReadFromServer(DataInputStream received){
            dataIn = received;
            System.out.println("RFS Runnable created.");
        }

        /**Override run method of Runnable. It will move the opponent's position on the x and y axes based on what the GameServer received from opponent client. */
        @Override
        public void run(){
            try {
                while(true){
                    double oppX = dataIn.readDouble();
                    double oppY = dataIn.readDouble();
                    if(opp != null){
                        opp.setXPos(oppX);
                        opp.setYPos(oppY);
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFS run() method.");
            }
        }

        /**waitForStartMsg method that will ensure that the game will only start once the gameFrame/Client receives the message from the GameServer. */
        public void waitForStartMsg(){
            try {
                String startMsg = dataIn.readUTF();
                System.out.println("Message from server: "+ startMsg);

                Thread readerThread = new Thread(rfsRunnable);
                Thread writerThread = new Thread(wtsRunnable);

                readerThread.start();
                writerThread.start();

            } catch (IOException ex) {
                System.out.println("IOException from RFS waitForStartMsg() method.");
            }
        }

    }

    /**Inner Class WriteToServer which implements Runnable */
    private class WriteToServer implements Runnable{

        private DataOutputStream dataOut;

        /**WriteToServer Inner Class Constructor 
         * @param sent - the dataStream which will be used to send out data to the GameServer.
        */
        public WriteToServer(DataOutputStream sent){
            dataOut = sent;
            System.out.println("WTS Runnable created.");
        }

        /**Override run method of Runnable. It will send the player's x and y coordinates to the GameServer.*/
        public void run(){
            try {
                while(true){
                    if(me != null){
                        dataOut.writeDouble(me.getXPos());
                        dataOut.writeDouble(me.getYPos());
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTS run() method due to thread.sleep.");
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTS run() method.");
            }
        }
        
    }

}