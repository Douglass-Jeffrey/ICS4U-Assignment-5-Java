/* 
* This program solves a maze using an algorithm
*
* @author  Douglass Jeffrey
* @version 1.0
* @since   2021-01-15
*/

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; //Import the scanner class

class MazeMain {

  public static void main(String[] args) {
    
    // Creating the first maze
    File firstMazeFile = new File("Maze1.txt");;

    // variables required to run the maze
    int startX = 0;
    int startY = 0;
    int endX = 0;
    int endY = 0;
    int totalRow = 6;
    int totalColumn = 6;
    
    // call createmaze
    char[][] mazeMatrix = createMaze(firstMazeFile, totalRow, totalColumn);
    
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
    

    // convert char maze to boolean
    boolean[][] boolMatrix = boolConvert(mazeMatrix, totalRow, totalColumn);
    
    // solve maze
    matrixSolve(boolMatrix, startY, startX, startX, startY, endX, endY, mazeMatrix);
    
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
    return (mazePrint);
  }


  public static boolean [][] matrixSolve(boolean[][] boolMatrix,
                                         int searchAppendageX,
                                         int searchAppendageY,
                                         int startX, int startY,
                                         int endX, int endY,
                                         char[][] mazeMatrix) {

    // prints current maze
    for (int row = 0; row < 6; row++) {
      for (int column = 0; column < 6; column++) {
        if (row == searchAppendageX && column == searchAppendageY) {
          mazeMatrix[row][column] = '+';
        } 
      }
    }
    printMaze(mazeMatrix, 6, 6);
    
    if (searchAppendageX == endY && searchAppendageY == endX) {
      
      System.out.println("End Found");

      return (boolMatrix);
    }
    
    // if a block away from goal
    if ((searchAppendageX + 1 == endY && searchAppendageY == endX) 
        || (searchAppendageX - 1 == endY && searchAppendageY == endX) 
        || (searchAppendageX == endY && searchAppendageY + 1 == endX) 
        || (searchAppendageX == endY && searchAppendageY - 1 == endX)) {
          
      searchAppendageX = endY;
      searchAppendageY = endX;
      return (matrixSolve(boolMatrix, searchAppendageX, searchAppendageY,
              startX, startY, endX, endY, mazeMatrix));
    }
    
    // if at start
    if (searchAppendageX == startX && searchAppendageY == startY) {
      
      if (boolMatrix[searchAppendageX][searchAppendageY + 1] == true) {
        
        return (matrixSolve(boolMatrix, searchAppendageX, searchAppendageY + 1,
                startX, startY, endX, endY, mazeMatrix));
        
      } else if (boolMatrix[searchAppendageX + 1][searchAppendageY] == true) {
       
        return (matrixSolve(boolMatrix, searchAppendageX + 1, searchAppendageY,
                startX, startY, endX, endY, mazeMatrix));
        
      } else if (boolMatrix[searchAppendageX][searchAppendageY - 1] == true) {
       
        return (matrixSolve(boolMatrix, searchAppendageX, searchAppendageY - 1,
                startX, startY, endX, endY, mazeMatrix));
        
      } else {
        
        throw null;
      } 
    }
    
    // if on roof
    if (searchAppendageX == 0 && searchAppendageY != 0 && searchAppendageY != 5) {

      if ((boolMatrix[searchAppendageX][searchAppendageY + 1] == false)
          && (boolMatrix[searchAppendageX][searchAppendageY - 1] == false)) {
       
        // set dead end to false to prevent it from going there again
        boolMatrix[searchAppendageX][searchAppendageY] = false;
        mazeMatrix[searchAppendageX][searchAppendageY] = 'X';
        // recurse
        return (matrixSolve(boolMatrix, searchAppendageX + 1, searchAppendageY,
                startX, startY, endX, endY, mazeMatrix));       
      }

    // if on ground
    } else if (searchAppendageX == 5 && searchAppendageY != endY 
               && searchAppendageY != 0 && searchAppendageY != 5) {
      
      if ((boolMatrix[searchAppendageX][searchAppendageY + 1] == false)
          && (boolMatrix[searchAppendageX][searchAppendageY - 1] == false)) {
      
        // set dead end to false to prevent it from going there again
        boolMatrix[searchAppendageX][searchAppendageY] = false;
        mazeMatrix[searchAppendageX][searchAppendageY] = 'X';
        // recurse
        return (matrixSolve(boolMatrix, searchAppendageX - 1, searchAppendageY,
                startX, startY, endX, endY, mazeMatrix));   
      }
    
    // if on left wall
    } else if (searchAppendageY == 0 && searchAppendageX != 0
               && searchAppendageX != 5) {
      if ((boolMatrix[searchAppendageX + 1][searchAppendageY] == false)
          && (boolMatrix[searchAppendageX - 1][searchAppendageY] == false)) {
        
        // set dead end to false to prevent it from going there again
        boolMatrix[searchAppendageX][searchAppendageY] = false;
        mazeMatrix[searchAppendageX][searchAppendageY] = 'X';
        // recurse
        return (matrixSolve(boolMatrix, searchAppendageX, searchAppendageY + 1,
                startX, startY, endX, endY, mazeMatrix));   
      }
    
    // if on right wall  
    } else if (searchAppendageY == 5 && searchAppendageX != 0 
               && searchAppendageX != 5) {
      if ((boolMatrix[searchAppendageX + 1][searchAppendageY] == false)
          && (boolMatrix[searchAppendageX - 1][searchAppendageY] == false)) {
                  
        // set dead end to false to prevent it from going there again
        boolMatrix[searchAppendageX][searchAppendageY] = false;
        mazeMatrix[searchAppendageX][searchAppendageY] = 'X';
        // recurse
        return (matrixSolve(boolMatrix, searchAppendageX, searchAppendageY - 1,
                startX, startY, endX, endY, mazeMatrix));
      }
    
    // if in corner 1
    } else if (searchAppendageX == 0 && searchAppendageY == 0) {
      if ((boolMatrix[searchAppendageX + 1][searchAppendageY]) == false
           || (boolMatrix[searchAppendageX][searchAppendageY + 1]) == false) {
        
        if ((boolMatrix[searchAppendageX][searchAppendageY + 1]) == false) {
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          mazeMatrix[searchAppendageX][searchAppendageY] = 'X';
          // recurse
          return (matrixSolve(boolMatrix, searchAppendageX + 1,
                  searchAppendageY, startX, startY, endX, endY, mazeMatrix));
          
        } else if ((boolMatrix[searchAppendageX + 1][searchAppendageY]) == false) {
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          mazeMatrix[searchAppendageX][searchAppendageY] = 'X';
          // recurse
          return (matrixSolve(boolMatrix, searchAppendageX,
                  searchAppendageY + 1, startX, startY, endX, endY,
                  mazeMatrix));
        }
      }

    // if in corner 2
    } else if (searchAppendageX == 5 && searchAppendageY == 0) {
      if ((boolMatrix[searchAppendageX - 1][searchAppendageY]) == false
           || (boolMatrix[searchAppendageX][searchAppendageY + 1]) == false) {
        
        // set dead end to false to prevent it from going there again
        boolMatrix[searchAppendageX][searchAppendageY] = false;
        mazeMatrix[searchAppendageX][searchAppendageY] = 'X';
        // recurse
        return (matrixSolve(boolMatrix, startX, startY, startX,
                startY, endX, endY, mazeMatrix));
      }
    
    // if in corner 3
    } else if (searchAppendageX == 0 && searchAppendageY == 5) {
      if ((boolMatrix[searchAppendageX + 1][searchAppendageY]) == false
           || (boolMatrix[searchAppendageX][searchAppendageY - 1]) == false) {
        
        // set dead end to false to prevent it from going there again
        boolMatrix[searchAppendageX][searchAppendageY] = false;
        mazeMatrix[searchAppendageX][searchAppendageY] = 'X';
        // recurse
        return (matrixSolve(boolMatrix, startX, startY, startX, startY,
                endX, endY, mazeMatrix));
      }
    
    // if in corner 4
    } else if (searchAppendageX == 5 && searchAppendageY == 5) {
      if ((boolMatrix[searchAppendageX - 1][searchAppendageY]) == false
           || (boolMatrix[searchAppendageX][searchAppendageY - 1]) == false) {
        
        if ((boolMatrix[searchAppendageX][searchAppendageY - 1]) == false) {
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          mazeMatrix[searchAppendageX][searchAppendageY - 1] = 'X';
          // recurse
          return (matrixSolve(boolMatrix, searchAppendageX,
                  searchAppendageY - 1, startX, startY,
                  endX, endY, mazeMatrix));
          
        } else if ((boolMatrix[searchAppendageX - 1][searchAppendageY]) == false) {
          // set dead end to false to prevent it from going there again
          boolMatrix[searchAppendageX][searchAppendageY] = false;
          mazeMatrix[searchAppendageX - 1][searchAppendageY] = 'X';
          // recurse
          return (matrixSolve(boolMatrix, searchAppendageX + 1,
                  searchAppendageY, startX, startY, endX, endY,
                  mazeMatrix));
        }
      }
    }
    
    if (searchAppendageX == 5 
        && boolMatrix[searchAppendageX][searchAppendageY + 1] == true) {
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX,
              searchAppendageY + 1, startX, startY, endX, endY, mazeMatrix));
    } else if (searchAppendageX == 5
               && boolMatrix[searchAppendageX][searchAppendageY - 1] == true) {
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX, searchAppendageY - 1,
              startX, startY, endX, endY, mazeMatrix));
    }
    
    if (searchAppendageX == 0 
        && boolMatrix[searchAppendageX][searchAppendageY + 1] == true 
        && searchAppendageY - 1 != 0) {
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX, searchAppendageY + 1,
              startX, startY, endX, endY, mazeMatrix));
    } else if (searchAppendageX == 0
               && boolMatrix[searchAppendageX][searchAppendageY - 1] == true
               && searchAppendageY - 1 != 0) {
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX, searchAppendageY - 1,
              startX, startY, endX, endY, mazeMatrix));
    } 
    
    if (searchAppendageY == 0
        && boolMatrix[searchAppendageX + 1][searchAppendageY] == true) {
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX + 1, searchAppendageY,
              startX, startY, endX, endY, mazeMatrix));
    } else if (searchAppendageY == 0 
               && boolMatrix[searchAppendageX - 1][searchAppendageY] == true) {
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX - 1, searchAppendageY,
              startX, startY, endX, endY, mazeMatrix));
    } 
    
    if (searchAppendageY == 5
        && boolMatrix[searchAppendageX + 1][searchAppendageY] == true) {
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX + 1, searchAppendageY,
              startX, startY, endX, endY, mazeMatrix));
    } else if (searchAppendageY == 5
               && boolMatrix[searchAppendageX - 1][searchAppendageY] == true) {
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX - 1, searchAppendageY,
              startX, startY, endX, endY, mazeMatrix));
    }
    
    if (((boolMatrix[searchAppendageX][searchAppendageY + 1] == false)
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
      mazeMatrix[searchAppendageX][searchAppendageY] = 'X';
      searchAppendageX = 0;
      searchAppendageY = 0;
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX, searchAppendageY,
              startX, startY, endX, endY, mazeMatrix));
    } 
    
    // move appendage set
    if ((boolMatrix[searchAppendageX + 1][searchAppendageY]) == true) {
      
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX + 1, searchAppendageY,
              startX, startY, endX, endY, mazeMatrix));
      
    } else if (boolMatrix[searchAppendageX][searchAppendageY + 1] == true) {
      
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX, searchAppendageY + 1,
              startX, startY, endX, endY, mazeMatrix));
      
    } else if (boolMatrix[searchAppendageX - 1][searchAppendageY] == true) {
    
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX - 1, searchAppendageY,
              startX, startY, endX, endY, mazeMatrix));
      
    } else if (boolMatrix[searchAppendageX][searchAppendageY - 1] == true) {
      
      // recurse
      return (matrixSolve(boolMatrix, searchAppendageX, searchAppendageY - 1,
              startX, startY, endX, endY, mazeMatrix));
    }
    return (boolMatrix);
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
  
  public static boolean [][] boolConvert(char[][] mazeMatrix,
                                         int totalRow, int totalColumn) {
    boolean [][] boolMatrix = new boolean[totalRow][totalColumn];
    for (int row = 0; row < totalRow; row++) {
      for (int column = 0; column < totalColumn; column++) {
        if (mazeMatrix[row][column] == '.' 
            || mazeMatrix[row][column] == 'S' 
            || mazeMatrix[row][column] == 'G') {
          boolMatrix[row][column] = true;
        } else {
          boolMatrix[row][column] = false;
        }
      }
    }
    return (boolMatrix);
  }
}
