package test.java.battleships.game;

import main.java.battleships.game.*;
import static main.java.battleships.game.Game.*;

import main.java.battleships.ships.*;
import org.junit.Test;
import org.junit.Assert;

import static org.hamcrest.MatcherAssert.assertThat;



public class GameTests {

    @Test
    public void CanMakeGame(){
        new Game();
    }

    @Test
    public void CanCreateGameboard(){
        char[][] TestBoard = {
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'}
        };
        char[][] gameboard = Game.createGameboard(7,7, '-');
        for(int i=0;i<7;i++)
            Assert.assertArrayEquals(TestBoard[i],gameboard[i]);
    }

    @Test
    public void CanCreateGameboardSmallerThan7(){
        char[][] gameboard = Game.createGameboard(5,2,'-');
        Assert.assertTrue(gameboard==null);
    }

    @Test
    public void CanPlaceShips(){
        Ship[] ships = new Ship[5];
        for(int i=0;i<ships.length;i++) {
            ships[i] = new Destroyer();
        }

        char[][] gameboard = Game.createGameboard(7,7,'-');
        char[][] fullgameboard = Game.placeShips(ships, gameboard, '-');

        int count=0;
        for(int i=0;i<gameboard.length;i++) {
            for (int j = 0; j < gameboard[0].length; j++) {
                if (gameboard[i][j] == ships[0].getId())
                    count++;
            }
        }
        Assert.assertEquals(ships[0].getHealth()*5,count);
    }

    @Test
    public void CanHit(){
        Ship[] ship = new Ship[1];
        ship[0] = new Destroyer();
        int[] coordinates = {0,0};
        char[][] TestBoard = {
                {'D','D','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'}
        };
        Assert.assertEquals(Shot(TestBoard,ship,coordinates,'X','0'),'X');
    }

    @Test
    public void CanMiss(){
        Ship[] ship = new Ship[1];
        ship[0] = new Destroyer();
        char[][] TestBoard = {
                {'d','d','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'},
                {'-','-','-','-','-','-','-'}
        };
        int[] coordinates = {2,5};
        Assert.assertEquals(Shot(TestBoard,ship,coordinates,'X','0'),'0');
    }

    @Test
    public void CanSunk(){
        Ship[] ship = new Ship[1];
        ship[0] = new Destroyer();
        ship[0].setSunk(true);
        Assert.assertTrue(isSunk(ship[0]));
    }

    @Test
    public void CanWin(){
    Assert.assertTrue(win(0));
    }

}

