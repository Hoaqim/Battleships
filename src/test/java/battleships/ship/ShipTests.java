package test.java.battleships.ship;

import main.java.battleships.ships.*;
import org.junit.Test;

public class ShipTests {

    @Test
    public void DefaultConstructorWorks(){
        Ship[] ship = new Ship[1];
    }

    @Test
    public void CanCreateShips(){
        new Destroyer();
        new Battleship();
        new Carrier();
        new Cruiser();
        new Submarine();
    }
}
