import java.util.Scanner;
import java.util.Random;

public class Battleship {
    public static void printBoard(char[][] newBoard){
        System.out.println("   0123456789  ");
        for(int i=0; i <newBoard.length; i++){
            System.out.print(i + " |");
            for(int j=0; j < newBoard[i].length; j++){
                //if statement checks if the computer has a ship in this spot (marked as a 2) and hides it from the player
                //Also hides previous computer guesses (indicated with T) from the player
                if(newBoard[i][j]=='2'){
                    System.out.print(" ");
                } else if(newBoard[i][j]=='T'){
                    System.out.print(" ");
                } else {
                    System.out.print(newBoard[i][j]);
                }

            }
            System.out.print("| "+i+"\n");
        }
        System.out.println("   0123456789");
    }

    public static void userCoords(char[][] sea){
        Scanner input = new Scanner(System.in);
        int shipCount = 0;
        int shipsTotal = 5;
        System.out.println("Deploy your ships:");
        while (shipCount < shipsTotal){
            System.out.print("Enter X coordinate for ship " + (shipCount+1) + ": ");
            int x = input.nextInt();
            System.out.print("Enter Y coordinate for ship " + (shipCount+1) + ": ");
            int y = input.nextInt();
            if((x > 9 || x < 0) ||(y > 9 || y < 0)){
                System.out.println("Please enter valid coordinates!");
            } else if (sea[x][y] == '@'){
                System.out.println("Please enter valid coordinates!");
            } else {
                sea[x][y] = '@';
                shipCount++;
            }
        }
    }

    public static void computerCoords(char[][] sea){
        Random rand = new Random();
        int compShips = 0;
        int totalShip = 5;

        System.out.println("Computer is deploying ships");

        while(compShips < totalShip) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            if (sea[x][y] != '@' && sea[x][y] != '2'){
                sea[x][y] = '2';
                compShips++;
                System.out.println("Ship "+compShips+" DEPLOYED");
            }
        }
        System.out.println("---------------------------");
    }

    public static boolean validGuess(char[][] sea, int x, int y){
        boolean valid = false;

        if((x < 10 && x > -1) && (y < 10 && y > -1)){
            if(sea[x][y] != '!'){
                if(sea[x][y] != 'x'){
                    if(sea[x][y] != '-'){
                        valid = true;
                    }
                }
            }
        }
        return valid;
    }

    public static int playerTurn(char[][] sea){
        Scanner input = new Scanner(System.in);
        System.out.println("YOUR TURN");
        boolean guess = false;
        int key = -1;
        while (!guess){
            System.out.print("Enter X coordinate: ");
            int x=input.nextInt();
            System.out.print("Enter Y coordinate: ");
            int y=input.nextInt();
            if (validGuess(sea, x, y)) {
                if (sea[x][y] == '2') {
                    System.out.println("Boom! You sunk the ship!");
                    sea[x][y] = '!';
                    guess = true;
                    key = 1;
                } else if (sea[x][y] == '@') {
                    System.out.println("Oh no, you sunk your own ship :(");
                    sea[x][y] = 'x';
                    guess = true;
                    key = 0;
                } else {
                    System.out.println("Sorry, you missed");
                    sea[x][y] = '-';
                    guess = true;
                }
            } else {
                System.out.println("Please enter valid guess (cannot repeat guesses or guess out of bounds)");
            }
        }
        return key;
    }

    public static int computerTurn(char[][] sea){
        System.out.println("COMPUTER'S TURN");
        Random rand = new Random();
        boolean guess = false;
        int key = -1;
        while(!guess) {
            int x = rand.nextInt(10);
            int y = rand.nextInt(10);
            if (validGuess(sea, x, y) && sea[x][y] != 'T') {
                if (sea[x][y] == '@') {
                    System.out.println("The Computer sunk one of your ships!");
                    sea[x][y] = 'x';
                    guess = true;
                    key = 0;
                } else if (sea[x][y] == '2') {
                    System.out.println("The Computer sunk of its own ships");
                    sea[x][y] = '!';
                    guess = true;
                    key = 1;
                } else {
                    System.out.println("Computer missed");
                    sea[x][y] = 'T';
                    guess = true;
                }
            }
        }
        return key;
    }

    public static void main(String[] args) {
      //Welcome message
      System.out.println("**** Welcome to Battle Ships game ****\n");
      System.out.println("Right now, the sea is empty.\n");

      //Initialize board
      char[][] sea = new char[10][10];

      //Print initial board state, empty
      printBoard(sea);

      //Prompt user for coordinates to ship and update the board
      userCoords(sea);

      //Re-display board showing user-placed ships
      printBoard(sea);

      //Computer randomly places ships, avoiding overlap with players and boundaries
      computerCoords(sea);

      //Initialize ship count and run loop, taking player turns then computer turns, until 1 player runs out of ships
      int playerShips = 5;
      int compShips = 5;
      while (playerShips > 0 && compShips > 0){
          int key = playerTurn(sea);
          if (key == 0){
              playerShips--;
          } else if (key == 1){
              compShips--;
          }
          key = computerTurn(sea);
          if (key == 0){
              playerShips--;
          } else if (key == 1){
              compShips--;
          }
          printBoard(sea);
          System.out.println("Your ships: " + playerShips + " | Computer ships: "+compShips);
          System.out.println("-----------------------");
      }

        //Calculate winner and print final message to player
        System.out.println("Your ships: " + playerShips + " | Computer ships: "+compShips);
        if(playerShips == 0){
            System.out.println("Sorry you lose");
        } else {
            System.out.println("Hooray! You win the battle :)");
        }



    }
}
