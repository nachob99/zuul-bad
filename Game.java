import java.util.Stack;
import java.util.ArrayList;
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
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Item item;
    private Room ultimaSala;
    private Stack<Room> stack;
    private ArrayList<Item> mochila;
    private int pesoMochila;
    private static final int maxWeigth=1000;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        stack = new Stack<>();
        mochila = new ArrayList<>();
        pesoMochila = 0;
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
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

        //Añadir objetos
        comedor.addItem("coca", 200);
        comedor.addItem("pastis",5);
        
        salon.addItem("m",100);

        currentRoom = hall;  // start game hall
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
        printLocationInfo();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("back")){
            back();

        }
        else if (commandWord.equals("take")){
            take(command);

        }
        else if (commandWord.equals("drop")){
            drop(command);

        }
        else if (commandWord.equals("items")){
            items();

        }
        else if (commandWord.equals("look")) {
            look();
        }
        else if (commandWord.equals("eat")) {
            eat();
        }

        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
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
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            stack.push(currentRoom);
            currentRoom = currentRoom.getExit(direction);
            printLocationInfo();
        }
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

    private void printLocationInfo(){
        System.out.println( currentRoom.getLongDescription());

    }

    private void look() 
    {
        System.out.println(currentRoom.getLongDescription());
        System.out.println(currentRoom.informacionObjetosDeLaSala());
    }

    private void eat(){
        System.out.println("You have eaten now and you are not hungry any more");
    }

    private void back(){
        if(!stack.empty()){
            currentRoom= stack.pop();
            printLocationInfo();
        }

        else{
            System.out.println("No se puede volver para atras");

        }

    }

    private void items() 
    {
        if (mochila.size() > 0){
            System.out.println("Tu mochila tiene los siguientes objetos");
            for (int i = 0; i < mochila.size(); i++){
                System.out.println(mochila.get(i).informacionItem());
            }
        }
        else{
            System.out.println("Tu mochila esta vacia");
        }
    }

    private void drop(Command command) 
    {
        if(!command.hasSecondWord()) {

            System.out.println("No se sabe  la posicion del objeto");
            return;
        }

        else {
            Item itemQueSoltar = null;
            String item = command.getSecondWord();
            
            for (Item itemASoltar : mochila) {
                //Realizamos bucle que nos mire todos los objetos y si coincide con el nombre es el objeto que buscamos
                if (itemASoltar.getDescription().equals(item)) {
                    itemQueSoltar = itemASoltar;                    
                }
            }
            // Se elimna de la mochila el objeto que se ha soltado
            mochila.remove(itemQueSoltar);
            if (itemQueSoltar == null) {
                System.out.println("¡No tienes ese objeto!");
            }
            else {
                currentRoom.itemQueSoltar(itemQueSoltar);
                //Restamos el peso del objeto a la mochila
                pesoMochila -= itemQueSoltar.getPeso();
            }

        }
    }

    private void take(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know the item to take...
            System.out.println("No has indicado la posicion del objeto a coger");
            return;
        }
        ArrayList<Item> mochilaActual = null;
        if (currentRoom.getItem().size() > 0){
            mochilaActual = currentRoom.getItem();
        }
        String posicionObjetoACoger = command.getSecondWord();

        if (mochilaActual != null && pesoMochila + mochilaActual.get(Integer.parseInt(posicionObjetoACoger)).getPeso() < maxWeigth){
            System.out.println("Has cogido el siguiente objeto:" + "\n");
            //Mostramos la posicion del objeto y la informacion del item
            System.out.println("Posicion: " + Integer.parseInt(posicionObjetoACoger) + "\n" + " " 
                + mochilaActual.get(Integer.parseInt(posicionObjetoACoger)).informacionItem());
            //Mostramos el peso de la mochila
            pesoMochila += mochilaActual.get(Integer.parseInt(posicionObjetoACoger)).getPeso();
            mochila.add(mochilaActual.get(Integer.parseInt(posicionObjetoACoger)));
            //Eliminamos la posicion del objeto que hemos cogido
            mochilaActual.remove(Integer.parseInt(posicionObjetoACoger));
        }

        else{
            if (mochilaActual == null){
                System.out.println("No hay objetos en la sala");
            }
            
        }
    }
}
