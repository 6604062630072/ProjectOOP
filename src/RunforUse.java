//import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;



public class RunforUse extends JFrame implements ActionListener{

    HomeMenu Home = new HomeMenu();
    RunGame R = new RunGame();

    public RunforUse(){
        this.setSize(1800, 800);
        this.add(Home);
        Home.BPlay.addActionListener(this);
        Home.BExit.addActionListener(this);
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == Home.BPlay){
            this.setLocationRelativeTo(null);
            this.remove(Home);
            this.setSize(1800, 800);
            this.add(R);
            R.requestFocusInWindow();
            R.timestart = false;
        }else if(e.getSource() == Home.BExit){
            System.exit(0);
        }
        this.validate();
        this.repaint();
    }
    public static void main(String[] args) {
        JFrame j = new RunforUse();
        j.setSize(1800, 800);
        j.setTitle("Animation Example");
        j.setDefaultCloseOperation(EXIT_ON_CLOSE);
        j.setVisible(true);
        j.setLocationRelativeTo(null);
    }
}
