import java.util.Stack;
import java.util.ArrayList;
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
    private ArrayList<Item> mochila;
    private Item item;
    private int cargaActual;
    private static final int carga_maxima = 300;
    /**
     * Constructor for objects of class player
     */
    public Player(String name , Room startRoom)
    {
        name="nacho";
        stack = new Stack();
        this.currentRoom = startRoom;
        mochila = new ArrayList<>();
        cargaActual=0;
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

    public void take(Command command){
        String item = command.getSecondWord();
        Item itemACoger = currentRoom.itemACoger(item);
        if(itemACoger.getPeso() + cargaActual > carga_maxima){
            System.out.println("Peso maximo superado" + "desagase de algun objeto");
        }
        else{
            mochila.add(itemACoger);
            cargaActual += itemACoger.getPeso() ;
            System.out.println("Has recogido" + itemACoger.getDescription());
        }
    }

    public void items(){

        if (!mochila.isEmpty()) {
            for (Item itemActual : mochila) {
                System.out.println(itemActual.getDescription());
            }
        }
        else {
            System.out.println("Tienes la mochila vacía.");
        }
    }

    public void drop(Command command)
    {

        String item = command.getSecondWord();
        Item itemABorrar = null;
        for (Item itemASoltar : mochila) {
            if (itemASoltar.getId().equals(item)) {
                itemABorrar= itemASoltar;                    
            }
        }
        mochila.remove(itemABorrar);
        if (itemABorrar == null) {
            System.out.println("NO tienes ese objeto!");
        }
        else {
            currentRoom.itemASoltar(itemABorrar);
            cargaActual -= itemABorrar.getPeso();
            System.out.println("Has soltado " + itemABorrar.getDescription() + ", con un peso de " + itemABorrar.getPeso());
        }
    }
}
