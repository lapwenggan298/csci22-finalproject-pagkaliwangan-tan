/**
This is the GameServer Class that will be used for the final project.
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

import java.io.*;
import java.net.*;
import java.util.*;

public class GameServer {
    private ServerSocket ss;
    private ArrayList<Socket> connections;
    private int numPlayers, maxPlayers;
    private DataInputStream dataIn;
    private DataOutputStream dataOut;
    
    private Socket p1Socket, p2Socket;

    private ReadFromClient p1ReadRunnable, p2ReadRunnable;
    private WriteToClient p1WriteRunnable, p2WriteRunnable;
    private double p1x, p1y, p2x, p2y;

    /**GameServer Class Constructor */
    public GameServer() {
        connections = new ArrayList<>();
        System.out.println("=== FINAL PROJECT GAME SERVER BY PAGKALIWANGAN - TAN ===");
        System.out.println("Kafka needs more milk");
        numPlayers = 0;
        maxPlayers = 2;

        /**Initial X and Y coordinates of both players. */
        p1x = 200;
        p1y = 550;
        p2x = 400;
        p2y = 550;

        try {
            ss = new ServerSocket(42069); /**Port number of the GameServer. */
        } catch (IOException ex) {
            System.out.println("IOException from GameServer Constructor");
        }
        System.out.println("The Game Server has been connected.");
        closeSocketsOnShutdown();
    }

    /**closeSocketsOnShutdown */
    public void closeSocketsOnShutdown() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                System.out.println("Shutting down...");
                for (Socket conn : connections) {
                    conn.close();
                }
            } catch (IOException ex) {
                System.out.println("IOException from closeSocketsOnShutdown()");
            }
        }));
    }

    /**acceptConnections() method will make the GameServer allow clients/players to be connected, and set up the runnables/threads that correspond to each socket/client.*/
    public void acceptConnections() {
        try {
            System.out.println("Waiting for milk/connections...");
            while (numPlayers<maxPlayers) {
                Socket s = ss.accept();
                dataIn = new DataInputStream(s.getInputStream());
                dataOut = new DataOutputStream(s.getOutputStream());

                //numPlayers will also serve as their playerID
                numPlayers++;
                //player1 and then player2
                dataOut.writeInt(numPlayers);
                System.out.println(numPlayers + " players connected.");
                System.out.println("Player " + numPlayers + " online.");

                /**Set up threads for each client/player. */
                ReadFromClient rfc = new ReadFromClient(numPlayers, dataIn);
                WriteToClient wtc = new WriteToClient(numPlayers, dataOut);

                if (numPlayers == 1) {
                    p1Socket = s;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                } else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;

                    System.out.println("The server is now full, no longer accepting connections.");

                    /**Send Start Message after player 2 has connected. */
                    p1WriteRunnable.sendStartMsg();
                    p2WriteRunnable.sendStartMsg();

                    /**Initialize threads and start */
                    Thread readThreadPlayer1 = new Thread(p1ReadRunnable);
                    Thread readThreadPlayer2 = new Thread(p2ReadRunnable);
                    readThreadPlayer1.start();
                    readThreadPlayer2.start();
                    Thread writeThreadPlayer1 = new Thread(p1WriteRunnable);
                    Thread writeThreadPlayer2 = new Thread(p2WriteRunnable);
                    writeThreadPlayer1.start();
                    writeThreadPlayer2.start();
                }
            }

        } catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    /**Private Inner Class to be used by the GameServer */
    private class ReadFromClient implements Runnable {
        private DataInputStream dataIn;
        private int playerID; // Which player to receive data from.

        /** ReadFromClient Inner Class Constructor
         * @param id - the playerID which the thread will be tied to.
         * @param received - the dataInputStream which the thread itself will be tied to.
         */
        public ReadFromClient(int id, DataInputStream received) {
            this.playerID = id;
            dataIn = received;
            System.out.println("RFC " + playerID + " runnable created.");
            System.out.println("Read from Client/Player " + playerID +  " ");
        }

        /**ReadFromClient run overrides the run method of the Runnable interface or what it should do once it is run/started.
         * It constantly updates the server's record of the players' x and y coordinates.
        */
        @Override
        public void run() {
            try {
                while(true){
                    if(playerID == 1){
                        p1x = dataIn.readDouble();
                        p1y = dataIn.readDouble();
                    }
                    else{
                        p2x = dataIn.readDouble();
                        p2y = dataIn.readDouble();
                    }
                }
            } catch (IOException ex) {
                System.out.println("IOException from RFC run() method.");
            }
        }
    }

    private class WriteToClient implements Runnable{
        private DataOutputStream dataOut;
        private int playerID; //Which player to send data to.

        /** WriteToClient Inner Class Constructor
         * @param id - the playerID which the thread will be tied to.
         * @param write - the dataOutput which the thread itself will be tied to.
         */
        public WriteToClient(int id, DataOutputStream write) {
            this.playerID = id;
            dataOut = write;
            System.out.println("WTC " + playerID + " runnable created.");
            System.out.println("Sending to Client/Player " + playerID +  " ");

        }

         /**WriteToClient run overrides the run method of the Runnable interface or what it should do once it is run/started.
         * It updates the client/player's record of the opposing player's x and y coordinates.
        */
        @Override
        public void run(){
            try {
                while(true){
                    if(playerID == 1){
                        dataOut.writeDouble(p2x);
                        dataOut.writeDouble(p2y);
                        dataOut.flush();
                    }
                    else{
                        dataOut.writeDouble(p1x);
                        dataOut.writeDouble(p1y);
                        dataOut.flush();
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTC run() method due to thread.sleep.");
                        
                    } 
                }
            } catch (IOException ex) {
                System.out.println("IOException from WTC run() method.");
            }

        }

        /**sendStartMsg method is used in the acceptConnections method of the outer GameServer class. It is used once the GameServer has moved on to or detected the 2nd player. */
        public void sendStartMsg(){
            try {
                dataOut.writeUTF("We now have 2 players... BEGIN!");
            } catch (IOException ex) {
                System.out.println("IOException from WTC sendStartMsg() method.");
            }
        }

    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}
