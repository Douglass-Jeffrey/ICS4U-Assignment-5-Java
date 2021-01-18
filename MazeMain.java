


import java.util.Scanner; //Import the scanner class
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors

class MazeMain {

  public static void main(String[] args) {
    
    // Creating the first maze
    File firstMazeFile = new File("Maze1.txt");
    char[][] firstMaze = createMaze(firstMazeFile, 6, 6);
    
    // create scanner object
    Scanner userInput = new Scanner(System.in);
    
    System.out.println("Enter the width of the maze: ");
    int totalRow = userInput.nextInt();
      
    System.out.println("Enter the height of the maze: ");
    int totalColumn = userInput.nextInt();
    char[][] mazeMatrix = createMaze(firstMazeFile, totalRow, totalColumn);
    
    int startX = 0;
    int startY = 0;
    int endX = 0;
    int endY = 0;
          
    //find start and end 
    for (int row = 0; row < totalRow; row++) {
      for (int column = 0; column < totalColumn; column++) {

        if (mazeMatrix[row][column] == 'S') {
          startX = column;
          startY = row;

        } else if (mazeMatrix[row][column] == 'G') {
          endX = column;
          endY = row;
        }
      }
    }
    
    System.out.println(startX + "," + startY + "," + endX + "," + endY);
    
    boolean[][] boolMatrix = boolConvert(mazeMatrix, totalRow, totalColumn);
    
    printMaze(mazeMatrix, totalRow, totalColumn);
    
    matrixSolve(boolMatrix, startX, startY, endX, endY, mazeMatrix);
    
  }
  
  public static String printMaze(char[][] mazeMatrix, int totalRow, int totalColumn) {
    String mazePrint = "";
    for (int row = 0; row < totalRow; row++) {
      for (int column = 0; column < totalColumn; column++) {
        if (column % 6 == 0) {
          mazePrint = mazePrint + "\n";
        }
        mazePrint = mazePrint + mazeMatrix[row][column];
      }
    }
    System.out.println(mazePrint);
    return(mazePrint);
  }
  
  public static char[][] redefMaze(char[][] mazeMatrix, boolean [][] boolMatrix, int totalRow, int totalColumn) {
    for (int row = 0; row < totalRow; row++) {
      for (int column = 0; column < totalColumn; column++) {
        if (boolMatrix[row][column] == true) {
          mazeMatrix[row][column] = '*';
        }
      }
    }
    return(mazeMatrix);
  }

  public static boolean [][] matrixSolve(boolean[][] boolMatrix, int startX, int startY, int endX, int endY, char[][] mazeMatrix) {
    int searchAppendageX = startX;
    int searchAppendageY = startY;
  
    while (true) {
      if (searchAppendageX == endX && searchAppendageY == endY) {
        
        System.out.println("Found end");
        
        for (int row = 0; row < 6; row++) {
          for (int column = 0; column < 6; column++) {
            if (boolMatrix[column][row] == true) {
              mazeMatrix[column][row] = '*';
            }
          }
        }
        printMaze(mazeMatrix, 6, 6);

        return(boolMatrix);
      }
      
      // move out of spawn
      if (searchAppendageX == startX && searchAppendageY == startY) {
        searchAppendageX += 1;
      } 
      
      if (searchAppendageX == 0 && searchAppendageY != startY && searchAppendageY != 5) {

        if ((boolMatrix[searchAppendageX][searchAppendageY + 1] == false)
            && (boolMatrix[searchAppendageX][searchAppendageY - 1] == false)) {
         
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          // recurse
          return(matrixSolve(boolMatrix, startX, startY, endX, endY, mazeMatrix));       
        }

      } else if (searchAppendageX == 5 && searchAppendageY != endY && searchAppendageY != 0 && searchAppendageY != 5) {
        if ((boolMatrix[searchAppendageX][searchAppendageY + 1] == false)
            && (boolMatrix[searchAppendageX][searchAppendageY - 1] == false)) {
        
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          // recurse
          return(matrixSolve(boolMatrix, startX, startY, endX, endY, mazeMatrix));   
        }
        
      } else if (searchAppendageY == 0 && searchAppendageX != startX && searchAppendageX != 5) {
        if ((boolMatrix[searchAppendageX + 1][searchAppendageY] == false)
            && (boolMatrix[searchAppendageX - 1][searchAppendageY] == false)) {
          
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          // recurse
          return(matrixSolve(boolMatrix, startX, startY, endX, endY, mazeMatrix));   
        }
        
      } else if (searchAppendageY == 5 && searchAppendageX != 0 && searchAppendageX != 5) {
        if ((boolMatrix[searchAppendageX + 1][searchAppendageY] == false)
            && (boolMatrix[searchAppendageX - 1][searchAppendageY] == false)) {
                    
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          // recurse
          return(matrixSolve(boolMatrix, startX, startY, endX, endY, mazeMatrix));
        }
      
      } else if (searchAppendageX == 5 && searchAppendageY == 0) {
        if ((boolMatrix[searchAppendageX - 1][searchAppendageY]) == false || (boolMatrix[searchAppendageX][searchAppendageY + 1]) == false) {
          
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          // recurse
          return(matrixSolve(boolMatrix, startX, startY, endX, endY, mazeMatrix));
        }
        
      } else if (searchAppendageX == 0 && searchAppendageY == 5) {
        if ((boolMatrix[searchAppendageX + 1][searchAppendageY]) == false || (boolMatrix[searchAppendageX][searchAppendageY - 1]) == false) {
          
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          // recurse
          return(matrixSolve(boolMatrix, startX, startY, endX, endY, mazeMatrix));
        }
        
      } else if (searchAppendageX == 5 && searchAppendageY == 5) {
        if ((boolMatrix[searchAppendageX - 1][searchAppendageY]) == false || (boolMatrix[searchAppendageX][searchAppendageY - 1]) == false) {
          
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          // recurse
          return(matrixSolve(boolMatrix, startX, startY, endX, endY, mazeMatrix));
        }
      
      // if dead end away from wall
      } else if (((boolMatrix[searchAppendageX][searchAppendageY + 1] == false)
               && (boolMatrix[searchAppendageX][searchAppendageY - 1] == false)
               && (boolMatrix[searchAppendageX + 1][searchAppendageY] == false))
                 
               || ((boolMatrix[searchAppendageX][searchAppendageY + 1] == false)
               && (boolMatrix[searchAppendageX][searchAppendageY - 1] == false)
               && (boolMatrix[searchAppendageX - 1][searchAppendageY] == false))
          
               || ((boolMatrix[searchAppendageX + 1][searchAppendageY] == false)
               && (boolMatrix[searchAppendageX - 1][searchAppendageY] == false)
               && (boolMatrix[searchAppendageX][searchAppendageY + 1] == false))
        
               || ((boolMatrix[searchAppendageX + 1][searchAppendageY] == false)
               && (boolMatrix[searchAppendageX - 1][searchAppendageY] == false)
               && (boolMatrix[searchAppendageX][searchAppendageY - 1] == false))) {
           
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          // recurse
          return(matrixSolve(boolMatrix, startX, startY, endX, endY, mazeMatrix));
      }
      
      // move appendage set
      if (boolMatrix[searchAppendageX][searchAppendageY + 1] == true) {
        searchAppendageY += 1;
      } else if (boolMatrix[searchAppendageX + 1][searchAppendageY] == true) {
        searchAppendageX += 1;
      } else if (boolMatrix[searchAppendageX - 1][searchAppendageY] == true) {
        searchAppendageX += -1;
      } else if (boolMatrix[searchAppendageX][searchAppendageY - 1] == true) {
        searchAppendageY += -1;
      }
    }
  }

  /**
   * This function takes elements from a txt file and adds them to a 2D array
   * to create a maze.
   */
  static char[][] createMaze(File mazeFile, int length, int width) {
    // Creating array for the function to return containing the maze info
    char[][] newMaze = new char[width][length];

    try {
      // Taking the elements of the txt file and adding them to a 2D array
      Scanner mazeReader = new Scanner(mazeFile);
      for (int row = 0; mazeReader.hasNextLine() && row < width; row++) {
        char[] tempArray = mazeReader.nextLine().toCharArray();
        for (int col = 0; col < length && col < tempArray.length; col++) {
          newMaze[row][col] = tempArray[col];
        }
      }

      // Catching and showing the user what error occurred
    } catch (FileNotFoundException e) {
      System.out.println("ERROR: Unable to read file");
      e.printStackTrace();
    } catch (Exception e) {
      System.out.println("ERROR: Unable to create maze");
    }

    // Returning the newly created maze
    return newMaze;
  }
  
  public static boolean [][] boolConvert(char[][] mazeMatrix, int totalRow, int totalColumn) {
    boolean [][] boolMatrix = new boolean[totalRow][totalColumn];
    for (int row = 0; row < totalRow; row++) {
      for (int column = 0; column < totalColumn; column++) {
        if (mazeMatrix[row][column] == '.' || mazeMatrix[row][column] == 'S' || mazeMatrix[row][column] == 'G') {
          boolMatrix[row][column] = true;
        } else {
          boolMatrix[row][column] = false;
        }
      }
    }
    return(boolMatrix);
  }
  
  
  
}