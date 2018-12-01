import java.util.Arrays;
import java.util.Random;

public class Game {

  Random rng = new Random();
  public static final int ROWS = 20;
  public static final int COLUMNS = 20;

  // 2D orthogonal universe
  // given an initial generation called a seed

  // Arrays - Grid based
  // Cell - Object based


  public static void main(String[] args) throws InterruptedException {
    Game game = new Game();
    Cell[][] grid = new Cell[ROWS][COLUMNS];
    game.populateAll(grid);
    printGrid(grid);
    while (true) {
      System.out.println();
      grid = game.updateGrid(grid);
      printGrid(grid);
      Thread.sleep(1000);
    }

  }

  public static Cell[][] updateGrid(Cell[][] grid) {
    Cell[][] newGrid = new Cell[ROWS][COLUMNS];
    for (int rows = 0; rows < ROWS; rows++) {
      for (int columns = 0; columns < COLUMNS; columns++) {
        newGrid[rows][columns] = grid[rows][columns].phaseOfLife(grid);
      }
    }
    return newGrid;
  }

  public static void printGrid(Cell[][] grid) {
    for (Cell[] column : grid) {
      System.out.println(Arrays.toString(column));
    }
  }

  public void populateAll(Cell[][] grid) {
    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid.length; y++) {
        grid[x][y] = new Cell(x, y, rng.nextBoolean());
      }
    }
  }

  public class Cell {

    public int getRow() {
      return row;
    }

    public void setRow(int row) {
      this.row = row;
    }

    public int getColumn() {
      return column;
    }

    public void setColumn(int column) {
      this.column = column;
    }

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
      else return " ";
    }

    Cell[] getNeighbors(Cell[][] grid) {
      Cell[] neighbors = new Cell[8];
      neighbors[0] = normalize(this, new Cell(-1, -1, true), grid); //northwest
      neighbors[1] = normalize(this, new Cell(-1,  0, true), grid); //north
      neighbors[2] = normalize(this, new Cell(-1,  1, true), grid);//northeast
      neighbors[3] = normalize(this, new Cell( 0,  1, true), grid); //east
      neighbors[4] = normalize(this, new Cell( 1,  1, true), grid); //southeast
      neighbors[5] = normalize(this, new Cell( 1,  0, true), grid); //south
      neighbors[6] = normalize(this, new Cell( 1, -1, true), grid);; //southwest
      neighbors[7] = normalize(this, new Cell( 0, -1, true), grid);; //west
      //FIXME corners
      return neighbors;
    }

    private Cell normalize(Cell base, Cell offset, Cell[][] grid) {
      //assumes parameters are never smaller than -grid.length
      int row = (base.getRow() + offset.getRow() + grid.length) % grid.length;
      int column = (base.getColumn() + offset.getColumn() + grid.length) % grid.length;
      return new Cell(row, column, grid[row][column].alive);
    }

    Cell phaseOfLife(Cell[][] grid) {
      Cell newCell = new Cell(this.getRow(), this.getColumn(), this.alive);
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
            newCell.alive = false;
          }
        // A live cell having 2 or 3 live neighbors lives on
          else if (numberOfLiveNeighbors == 2 || numberOfLiveNeighbors == 3) {
            newCell.alive = true;
          }
        // A live cell having more than three live neighbors dies
        else if (numberOfLiveNeighbors > 3) {
           newCell.alive = false;
          }
      } else {
        // A dead cell having 3 live neighbors becomes alive
        if (numberOfLiveNeighbors == 3) {
          newCell.alive = true;
        }
      }
      return newCell;
    }
  }


  // RULES


  // CONSTRAINTS
  // Four lines of codes per method
  // No primitives
  // No loops

}
