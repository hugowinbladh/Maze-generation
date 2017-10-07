import javax.swing.*;

public class Main extends JFrame{

    public Main() {
        Panel panel = new Panel();
        setBounds(0,0,500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("random maze generator");
        add(panel);
    }

    public static void main(String[] args) {
        new Main();
    }
}
