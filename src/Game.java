import java.util.Arrays;
import java.util.Random;

public class Game {

  Random rng = new Random();

  // 2D orthogonal universe
  // given an initial generation called a seed

  // Arrays - Grid based
  // Cell - Object based


  public static void main(String[] args) {
    Game game = new Game();
    VonNeumann neighbors = new VonNeumann();
    Cell[][] grid = new Cell[5][5];
    game.populateAll(grid);
    for (Cell[] column : grid) {
      System.out.println(Arrays.toString(column));
    }
    System.out.println();
    System.out.println(Arrays.toString(grid[3][3].getNeighbors(grid)));
  }

  public void populateAll(Cell[][] grid) {
    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid.length; y++) {
        grid[x][y] = new Cell(x, y, rng.nextBoolean());
      }
    }
  }

  public class Cell {

    int row;
    int column;
    boolean alive = false;

    Cell(int row, int column, boolean alive) {
      this.row = row;
      this.column = column;
      this.alive = alive;
    }

    @Override
    public String toString() {
      if (alive) return "O";
      else return "X";
    }

    Cell[] getNeighbors(Cell[][] grid) {
      Cell[] neighbors = new Cell[]{
          grid[row-1][column], //north
          grid[row][column+1], //east
          grid[row][column-1], //west
          grid[row+1][column] //south
      };
      //FIXME corners
      return neighbors;
    }

    void phaseOfLife(Cell[][] grid) {
      Cell[] neighbors = this.getNeighbors(grid);
      int numberOfLiveNeighbors = 0;
      for (Cell neighbor : neighbors) {
        if (neighbor.alive) {
          numberOfLiveNeighbors++;
        }
      }
      if (this.alive) {
        // A live cell having less than 2 live neighbors dies
          if (numberOfLiveNeighbors < 2) {
            this.alive = false;
          }
        // A live cell having 2 or 3 live neighbors lives on
          else if (numberOfLiveNeighbors == 2 || numberOfLiveNeighbors == 3) {
            this.alive = true;
          }
        // A live cell having more than three live neighbors dies
        else if (numberOfLiveNeighbors > 3) {
           this.alive = false;
          }
      } else {
        // A dead cell having 3 live neighbors becomes alive
        if (numberOfLiveNeighbors == 3) {
          this.alive = true;
        }
      }
    }



  }

  public static class Location {
    private final int row;
    private final int column;

    public Location(int row, int column) {
      this.row = row;
      this.column = column;
    }

    public int getRow() {
      return row;
    }

    public int getColumn() {
      return column;
    }

  }

  public static class VonNeumann {

    private final Location[] neighbors = {
                          new Location(-1, 0),
        new Location(0, -1),                   new Location(0, 1),
                          new Location(1,  0)
    };

    public Location[] getNeighbors() {
      return neighbors;
    }

  }


  // RULES


  // CONSTRAINTS
  // Four lines of codes per method
  // No primitives
  // No loops

}
