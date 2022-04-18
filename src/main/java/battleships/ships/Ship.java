package main.java.battleships.ships;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract public class Ship {
    private int health;
    private char id;
    private String name;
    private boolean isSunk;

    /**
     * Default constructor
     */
    public Ship(){
        this.health=0;
        this.id='0';
        this.isSunk=true;
    }


    /**
     * Constructor for our ships
     * @param health length of our ship, is decreasing after each hit
     * @param id identifier of our ship on the gamebaord
     * @param isSunk boolean, true if health=0, else false
     */
    public Ship(int health, char id, boolean isSunk){
        this.health=health;
        this.id=id;
        this.isSunk=isSunk;
    }

}
