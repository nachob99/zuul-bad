import java.util.HashMap;
import java.util.ArrayList;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael KÃ¶lling and David J. Barnes
 * @version 2011.07.31
 */
public class Room 
{
    public String description;
    public Room northExit;
    public Room southExit;
    public Room southEastExit;
    public Room eastExit;
    public Room westExit;
    public Room northEastExit;
    public HashMap<String,Room> salidas;
    public Item item;
    private ArrayList<Item> listaItems;
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        salidas = new HashMap<>();
        listaItems = new ArrayList<Item>();
    }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     */
    public void setExits(String direccion , Room neighbor ) 
    {
        salidas.put( direccion,neighbor);
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public Room getExit(String direccion){
        Room salaADevolver = null;
        if(direccion.equals("north")){
            salaADevolver =salidas.get("north");
        }
        if(direccion.equals("northEast")){
            salaADevolver=salidas.get("northEast");
        }
        if(direccion.equals("south")){
            salaADevolver=salidas.get("south");
        }
        if(direccion.equals("southEast")){
            salaADevolver=salidas.get("southEast");
        }
        if(direccion.equals("east")){
            salaADevolver=salidas.get("east");
        }
        if(direccion.equals("west")){
            salaADevolver=salidas.get("west");
        }

        return salaADevolver;
    }

    /**
     * Return a description of the room's exits.
     * For example: "Exits: north east west"
     *
     * @ return A description of the available exits.
     */
    public String getExitString(){
        String exits = "";
        if(salidas.get("north") != null){
            exits += "north ";

        }
        if(salidas.get("northEast") != null){
            exits += "northEast ";

        }
        if(salidas.get("south") != null){
            exits += "south ";

        }
        if(salidas.get("east") != null){
            exits += "east ";

        }
        if(salidas.get("west") != null){
            exits += "west ";

        }
        if(salidas.get("southEast") != null){
            exits += "southEast ";

        }
        return exits;
    }

    /**
     * Return a long description of this room, of the form:
     *     You are in the 'name of room'
     *     Exits
     * @return A description of the room, including exits.
     */
    public String getLongDescription()
    {
        String descripcion = "You are " + description + "\nSalidas: \n" + getExitString();
        if (item != null){

            descripcion += "\nTu item :" + item.getDescription() + "tiene un peso:" + item.getPeso();
        }
        return descripcion;
    }

    public void addItem(String id ,String itemDescription, int itemWeigth, boolean cogerObjeto){
        Item itemAñadido = new Item (id,itemDescription,itemWeigth,cogerObjeto);
        listaItems.add(itemAñadido);

    }

    public String informacionObjetosDeLaSala(){
        String informacionObjetos = "";
        if(listaItems.size() < 0){
            informacionObjetos = "No hay ningun de objeto";
        }

        else{
            for(Item objetosDeLaLista  :listaItems){
                informacionObjetos += objetosDeLaLista.informacionItem();

            }

        }

        return informacionObjetos;
    }
    
    public ArrayList getItem(){
        return listaItems;
       
    }
    
    public void itemQueSoltar(Item item)
    {
        listaItems.add(item);        
    }
    
    public Item itemACoger(String item ){
        Item itemCogido = null;
        for(Item itemActual : listaItems){
            if(itemActual.getId().equals(item)){
                itemCogido = itemActual;
            }
        
        }
        listaItems.remove(itemCogido);
        return itemCogido;
    }
    
    public void itemASoltar(Item item){
        listaItems.add(item);
    
    }
}
