import java.util.Stack;
/**
 * Write a description of class player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private Room currentRoom;
    private Stack<Room> stack;

    /**
     * Constructor for objects of class player
     */
    public Player(String name , Room startRoom)
    {
        name="nacho";
        stack = new Stack();
        this.currentRoom = startRoom;
    }

    public Room getRoom(){
        return currentRoom;
    }

    public void goRoom(Command command){
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
            look();
        }

    }
    public void look() 
    {
        System.out.println(currentRoom.getLongDescription());
        System.out.println(currentRoom.informacionObjetosDeLaSala());
    }

    public void eat(){
        System.out.println("You have eaten now and you are not hungry any more");
    }

    public void back(){
        if(!stack.empty()){
            currentRoom= stack.pop();
            look();
        }

        else{
            System.out.println("No se puede volver para atras");

        }

    }
}
