import java.awt.*;
import javax.swing.*;

class Renderer extends JFrame {    
    private int length;
    private int scale = 4;
    private Automaton automaton;
    public Renderer(Automaton automaton) {
        this.automaton = automaton;
        length = automaton.getColorGrid().length;
        setBounds(50, 50, length*scale, length*scale);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(new Painter());
        setVisible(true);        
    }

    private class Painter extends Canvas {
        //@Override
        public void paint(Graphics g) {
            for (int i = 0; i < length; i++) {
                for (int j = 0; j < length; j++) {
                    g.setColor(automaton.getColorGrid()[i][j]);
                    g.fillRect(i*scale, j*scale, scale, scale);
                }
            }
        }
    }
}