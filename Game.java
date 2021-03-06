import java.util.Stack;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Player player;
    private CommandWord word;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        parser = new Parser();
        player = new Player("nacho",createRooms());
    }


    /**
     * Create all the rooms and link their exits together.
     */
    private Room createRooms()
    {
        Room hall, salon, cocina, habitacion, terraza, comedor;

        // create the rooms
        hall = new Room("in the hall");
        salon = new Room("in the living room");
        cocina = new Room("in the kitchen");
        habitacion = new Room("in the room");
        terraza = new Room("in the  terrace");
        comedor = new Room("in the dinning room");
        // initialise room exits
        hall.setExits("south",salon);
        salon.setExits("southEast", habitacion);
        salon.setExits("north",hall);
        salon.setExits("northEast", comedor);
        salon.setExits("west",cocina);
        cocina.setExits("east",salon);
        cocina.setExits("south",terraza);
        habitacion.setExits("northwest", salon);
        terraza.setExits("north",cocina);
        comedor.setExits("southWest",salon);
        //sala anterior

        //ubicacion jugador

        //A�adir objetos
        comedor.addItem("coca","droga blanca", 200,true);
        comedor.addItem("m","pastillas acidas",5,false);
        salon.addItem("maria","hierba", 200,true);
        salon.addItem("speed","pastis", 350,true);

        return hall;  // start game hall
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        player.look();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command  command) 
    {
        boolean wantToQuit = false;
        switch (word){
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;
            case HELP:
                printHelp();
                break;
            case  LOOK:
                player.look();
                break;
            case EAT:
                player.eat();
                break;
            case GO:
                player.goRoom(command);
                break;
            case BACK:
                player.back();
                break;
            case TAKE:
                player.take(command);
                break;
            case ITEMS:
                player.items();
                break;
            case DROP:
                player.drop(command);
                break;
            case GIVE:
                player.give(command);
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
            
        
        
        }
        

        return wantToQuit;
    }
    // implementations of user commands:
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.getCommands());
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }


    
}
