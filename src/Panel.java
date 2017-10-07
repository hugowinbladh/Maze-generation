import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Panel extends JPanel implements ActionListener{
    Timer timer = new Timer(2,this);
    int WIDTH = 500, HEIGHT = 500;
    int CELLSIZE = 10;
    Cell[][] cells;
    Cell current;
    Stack<Cell> cellStack = new Stack();


    public Panel() {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        cells = new Cell[(int) Math.floor(HEIGHT/CELLSIZE)][(int) Math.floor(WIDTH/CELLSIZE)];
        for (int i = 0; i < HEIGHT/CELLSIZE; i++) {
            for (int j = 0; j < WIDTH/CELLSIZE; j++){
                cells[i][j] = new Cell(j*CELLSIZE,i*CELLSIZE);
            }
        }
        current = cells[0][0];
        cells[0][0].walls[3] = false;
        cells[(int) Math.floor(HEIGHT/CELLSIZE)-1][(int) Math.floor(WIDTH/CELLSIZE)-1].walls[1] = false;
        timer.start();
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0,0,WIDTH,HEIGHT);
        g.setColor(Color.WHITE);
        for (Cell cellArr[] : cells) {
            for (Cell c : cellArr) {
                if (c.walls[0])
                    g.drawLine(c.x,c.y,c.x+CELLSIZE,c.y);
                if (c.walls[1])
                    g.drawLine(c.x+CELLSIZE,c.y,c.x+CELLSIZE,c.y+CELLSIZE);
                if (c.walls[2])
                    g.drawLine(c.x+CELLSIZE,c.y+CELLSIZE,c.x,c.y+CELLSIZE);
                if (c.walls[3])
                    g.drawLine(c.x,c.y+CELLSIZE,c.x,c.y);
                if (c == current){
                    g.setColor(Color.GREEN);
                    g.fillRect(c.x,c.y,CELLSIZE,CELLSIZE);
                    g.setColor(Color.WHITE);
                }
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        current.visited = true;
        boolean[] adjacentVisited = {true, true, true, true};
        int[] currentIndex = {current.x/CELLSIZE, current.y/CELLSIZE};
        // [0] = top, [1] = right, [2] = bottom, [3] = left
        boolean[] border = {(current.y < 1), (current.x > WIDTH - (CELLSIZE+1)), (current.y > HEIGHT - (CELLSIZE+1)), (current.x < 1)};
        if (!border[0] && !cells[currentIndex[1]-1][currentIndex[0]].visited)
            adjacentVisited[0] = false;
        if (!border[1] && !cells[currentIndex[1]][currentIndex[0]+1].visited)
            adjacentVisited[1] = false;
        if (!border[2] && !cells[currentIndex[1]+1][currentIndex[0]].visited)
            adjacentVisited[2] = false;
        if (!border[3] && !cells[currentIndex[1]][currentIndex[0]-1].visited)
            adjacentVisited[3] = false;
        boolean stuck = true;
        for (boolean b : adjacentVisited)
            if(!b)
                stuck = false;

        if (!stuck) {
            cellStack.push(current);
            int direction = (int) Math.floor(Math.random() * 4);
            while (adjacentVisited[direction]) {
                direction = (int) Math.floor(Math.random() * 4);
            }

            current.walls[direction] = false;

            if (direction == 0)
                current = cells[currentIndex[1] - 1][currentIndex[0]];
            if (direction == 1)
                current = cells[currentIndex[1]][currentIndex[0] + 1];
            if (direction == 2)
                current = cells[currentIndex[1] + 1][currentIndex[0]];
            if (direction == 3)
                current = cells[currentIndex[1]][currentIndex[0] - 1];

            current.walls[(direction+2)%4] = false;
        }
        else {
            cellStack.pop();
            current = cellStack.peek();
        }
        repaint();
        for(Cell[] cellArr : cells){
            for (Cell c : cellArr){
                if (!c.visited)
                    return;
            }
        }
        timer.stop();

    }
}
