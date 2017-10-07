public class Cell {
    int x, y;
    boolean visited;
    boolean[] walls = {true, true, true, true};

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.visited = false;
        this.walls = walls;
    }
}
