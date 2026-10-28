# **CSCI 22 Final Project: Multiplayer Network Game**

Game Title: Squump

Here is our [Game Manual](https://docs.google.com/document/d/1S9nv5Bgh7uldMaor6D6Xf4L6hmdzisbDB1c0In47uWY/edit?tab=t.0 "Squump Game Manual").

## **Authors and Contributions**

1. Pagkaliwangan, Luis Andrei P (lapwenggan298)
    - Did the backend and server logic.
    - Wrote javadoc comments for the methods of each class that needed one, aside from the main methods.
    - Made majority of the classes, their methods, and the basic things required for them.
        - Most were boilerplate code or code reused from our Midterm Project.
    - Ensured that our program met the specified requirements and made adjustments wherever needed.
2. Tan, Jurvick Dietrich (BiteSizedKaiju)
    - Made the art assets/figures/graphics used in the project.
        - The draw method of each specific polygon/object used.
    - Also wrote some javadoc comments.
    - Debugged and fixed some errors in collisions.
    - Clean up of code.
    - Fixed the velocity logic for the players.
    - Some println texts for checking to see if program is running properly.
        - e.g., Line 37 of GameServer.java

        ```java
        System.out.println("Kafka needs more milk.");
        ```

### **Last Update/Edit Done**

- 18th of May 2025

## **Project Overview**

### **From Specifications**

- The final project challenges us to incorporate as many Java topics we have covered in CSCI 21 and 22.
- We are tasked to design and implement a multiplayer network game.

### **Other Details and/or things to note by us**

- Our game for the final project is a platformer game where two players race to reach the gold/yellow block.
    - Very simple game as we were in a time constraint, and we were left to figure things out for ourselves.
    - We wanted to add a few more levels for variation, but again, due to time constraints, we were forced to just have 1 level.
- The level design is symmetrical.
- We have at least 1 bug we have noticed during testing and in the  presentation where there is weird collision, and that side or player will automatically drop down to a platform and not on the top of the wall, making it either easier or harder for the player.
    - Only 1 side has this bug.
- Initial code for collision, logic, and methods came from our Midterm Project.
- Please refer to our Certificate of Authorship (COA), *if available*, for more information on sources we have relied on.

## **Restrictions**

- **Except** for **Bricks, Pong, Space Invaders, Tetris, Snake,** and **Memory Games**, the game may be based on existing games such as board games, card games, or other graphic multiplayer games, but they cannot be copies of the existing game.
- They **must** be somehow **modified**.
- You are <u>**not**</u> allowed to find a game development tutorial and submit the output from following that tutorial. It can be an original game as well.

## **Project Requirements**

### **Required Classes and Description**

1. **GameServer**
    - Contains the code that manages the game server's functionality.
    - It also contains the **main** method that instantiates and starts the server.
2. **GameFrame**
    - This class contains the code that sets up the main **JFrame** for the player.
3. **GameCanvas**
    - This class extends the **JComponent** and overrides the **paintComponent** method in order to create this custom drawing.
4. **Player**
    - This class contains the code that manages the player's appearance and functionality.
5. **Game Starter**
    - This class contains the **main** method that will start the game from the player/client's side.

### ***Our Custom/Added Classes/Interfaces***

1. DrawingNats
    - an interface implemented by the classes: Player, and Platform.
    - ArrayList of type DrawingNats also used in GameCanvas.
2. Platform
    - the level/platform used in the game itself.

### **Other/Regarding Other Classes**

- You may decide what other classes to have and what to name them, but make sure that the clas names are descriptive of what they are for/what they do.

### **Program Requirements**

---

The program **must** contain, but not limited to, the following:

- graphical user interface
- class inheritance
    - you must define **a class or an abstract class** that will be ***extended*** by **at least two (2)** of the other classes you also defined.
- inner classes
- animation using Threads or Timers
- networking to send and receive game data

### **Networking Requirments**

---

The game **must** accomodate **at least two (2) human players** who will play against, or in cooperation with, each other. The game **must** run two (2) or more separate machines:

- one machine running both the server and one (1) client player
- ***n*** - 1 more machines running more client players (where ***n*** is the number of machines).

You may add a chat system, but it will not be considered as a fulfillment of the networking requirement. Examples of what are considered to fulfill the network requirement are: sending and receiving player coordinates, generating enemies/obstacles via information sent by the server, etc.

You may receive a higher grade for more complex networking functionality. Generally, a game where each player's movements are simultaneously mirrored on the other player's screen is more complex to implement than a turn-based game.

### **Documentation**

---

- For **how to play our game**, please refer to our [game manual](#csci-22-final-project-multiplayer-network-game).
- Java files are decently commented with each class and its methods having descriptions.

## **How to Run Our Program**

1. Make sure you have a java on your system, **at least Java JDK 17**.
2. On Windows, open the Command Prompt, or Windows Powershell
3. On the machine(s) you wish to open the server and the client(s) on, write the code snippet to compile the java files.

```console
javac *.java
```

4. Next, run the server application, again, via the command line.

```console
java GameServer
```

5. Finally, run the player/client application on two separate machines.

```console
java GameStarter
```

## **Note on Port Connection**

You can run the player applications once or twice on the same machine, should you want to do so.

If prompted for a port number to connect to, please enter the port number: **42069**

This was the port number we have decided on during testing and the presentation. Have fun playing and/or criticizing our project!

If you have any comments, please leave them in the repository comments if possible. We have not yet decided if we wish to add our contact details/emails. Please be patient as we are still undergraduate students as of the writing and pushing of this program on to GitHub.

Thank you! :heart:
