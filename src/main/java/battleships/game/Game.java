package main.java.battleships.game;

import main.java.battleships.ships.*;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game {
    public static void main(String[] args) {
        char water = '-';
        char hit = 'X';
        char miss = '0';
        char gameboard[][] = createGameboard(7, 7, water);
        Ship[] ships = initializeShips();
        int floatingShips = ships.length;

        gameboard = placeShips(ships, gameboard, water);
        printGameboard(gameboard, '-');

        while (!win(floatingShips)) {
            floatingShips = 0;
            int[] coordinates = getUserCords(gameboard.length, gameboard[0].length);
            char target = Shot(gameboard, ships, coordinates, hit, miss);
            updateGameboard(gameboard, coordinates, target);
            printGameboard(gameboard, water);

            for (Ship ship : ships) {
                if (!ship.isSunk()) {
                    floatingShips++;
                }
            }
            System.out.println("You need to sunk " + floatingShips + " more ships to win");
        }
    }

    /**
     * @param water  characters that will fill our array
     * @param height  height of our gameboard, 7 is the minimum because of the game rules
     * @param width  width of our gameboard, 7 is the minimum because of the game rules
     * @return 2d char array filled with water characters
     */
    public static char[][] createGameboard(int height, int width, char water) {
        if(height<7||width<7) return null;
        char[][] gameboard = new char[height][width];
        for(char[] row: gameboard){
            Arrays.fill(row, water);
        }
        return gameboard;
    }

    /**
     * Initialization of ships that will be on our gameboard
     * @return Ship class array filled with types of ships(carrier, etc.)
     */
    private static Ship[] initializeShips(){
        Ship[] ships = new Ship[5];
        ships[0] = new Destroyer();
        ships[1] = new Battleship();
        ships[2] = new Submarine();
        ships[3] = new Carrier();
        ships[4] = new Cruiser();
        return ships;
    }

    /**
     * @param ships array of Ship class (destroyer,battleship,submarine,carrier,cruiser)
     * @param gameboard 2d charr array filled with water characters
     * @return gameboard (2d char array) filled with our ships
     */
    public static char[][] placeShips(Ship[] ships, char[][] gameboard, char water){
        int[] coordinates;

        for(Ship ship : ships){
            coordinates = generateShipLocalization(gameboard,ship,water);

            if(coordinates[2]==1) //if its horizontal(1) place ship in column, else in row
                for(int i=0;i<ship.getHealth();i++) {
                    gameboard[coordinates[0] + i][coordinates[1]] = ship.getId();
                }
            else
                for(int i=0;i<ship.getHealth();i++) {
                    gameboard[coordinates[0]][coordinates[1] + i] = ship.getId();
                }

        }

        return gameboard;
    }

    /**
     * Generating valid localization of our ship on gameboard
     * @param gameboard 2d charr array, our gameboard
     * @param ship class Ship element(not array),
     * @param water char for water
     * @return int[] with 3 elements, our row,column and direction
     */
    private static int[] generateShipLocalization(char[][] gameboard, Ship ship, char water){
        int[] coordinates = new int[3];
        Random random = new Random();
        int direction; //1-vertical, 2-horizontal
        boolean flag=true;

        while(flag){
            direction= random.nextInt(2)+1;
            coordinates[0]= random.nextInt(gameboard.length);
            coordinates[1]= random.nextInt(gameboard[0].length);

            if(direction==1){
                while(coordinates[0] + ship.getHealth()>gameboard.length) {
                    coordinates[0] = random.nextInt(gameboard.length);
                }

                for(int i=0;i<ship.getHealth();i++) {
                    flag = gameboard[coordinates[0] + i][coordinates[1]] != water;
                    if(flag) break;
                }

            }
            else{
                while(coordinates[1]+ship.getHealth()>gameboard[0].length) {
                    coordinates[1] = random.nextInt(gameboard[0].length);
                }
                for(int i=0;i<ship.getHealth();i++) {
                    flag = gameboard[coordinates[0]][coordinates[1]+i] != water;
                    if(flag) break;
                }
            }

            coordinates[2]=direction;
        }

        return coordinates;
    }

    private static void printGameboard(char[][] gameboard,char water){
        System.out.print("  ");
        for(int i=0;i<gameboard.length;i++)
            System.out.print(i + 1 + " ");
        System.out.println();

        for(int i=0;i<gameboard.length;i++) {
            System.out.print((char)('A'+i)+" ");
            for (int j = 0; j < gameboard[0].length; j++) {
                if(gameboard[i][j]>='B'&&gameboard[i][j]<='S')
                    System.out.print(water+" ");
                else
                    System.out.print(gameboard[i][j]+" ");
            }
            System.out.println();
        }
    }

    /**
     *  method to shot at enemy ships
     * @param gameboard 2d char array
     * @param ships class Ship array
     * @param coordinates coordinates which we got from getUserCord, int array with 2 elements
     * @param hit character for hit
     * @param miss character for miss
     * @return target, character that will be replaced, hit or miss
     */
    public static char Shot(char[][] gameboard, Ship[] ships, int[] coordinates, char hit, char miss){
        char target = gameboard[coordinates[0]][coordinates[1]];
        //target between letter B and S, because there are only ships between this range
        if(!(target>='B'&&target<='S')){
            target=miss;
            System.out.println("Miss!");
        }else {
            for (Ship ship : ships) {
                if (target == ship.getId()) {
                    System.out.println("Hit!");
                    ship.setHealth(ship.getHealth() - 1);
                    target = hit;
                    if (isSunk(ship)) {
                        System.out.println("You sunk "+ship.getName()+"!");
                    }
                    break;
                }
            }
        }
        return target;
    }


    private static int[] getUserCords(int height, int width){
        int row=0;
        int col=0;
        do{
            System.out.print("Give me row: ");
            row = new Scanner(System.in).next().charAt(0);  //don't need try catch block here, because we getting a char
        }while(row<'A'||row>'A'+width);
        row = (int)(row - 'A');

        do{
            System.out.print("Give me column: ");

            try{
                col = new Scanner(System.in).nextInt();
            }catch (Exception e){
                System.out.println("Give correct column!");
            }

        }while(col<1||col>height);
        col--;

        return new int[]{row,col};
    }

    /**
     * Check if our ship is sunk, if it is then setSunk=true
     * @param ship class Ship (not array)
     * @return true or false
     */
    public static boolean isSunk(Ship ship){
        if(ship.getHealth()==0) ship.setSunk(true);
        return ship.isSunk();
    }

    /**
     * check how many ships arent sunk, if there are 0 then the game will end
     * @param floatingShips integer
     * @return true if there are none, else false
     */
    public static boolean win(int floatingShips){
        return floatingShips==0;
    }

    private static char[][] updateGameboard(char[][] gameboard ,int[] coordinates, char target){
        gameboard[coordinates[0]][coordinates[1]]=target;
        return gameboard;
    }
}