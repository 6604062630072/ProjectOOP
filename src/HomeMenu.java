import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.Image;
//import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class HomeMenu extends JPanel {
	/*URL HomeBgURL = getClass().getResource("2.Homepic.jpg");
    Image HomeBg = new ImageIcon(HomeBgURL).getImage();*/
	private ImageIcon HomeBg = new ImageIcon(this.getClass().getResource("2.Homepic.jpg"));
	private ImageIcon play = new ImageIcon(this.getClass().getResource("1play.png"));
	private ImageIcon exit = new ImageIcon(this.getClass().getResource("1exit.png"));
	public JButton BPlay = new JButton(play);
	public JButton BExit  = new JButton(exit);

	public HomeMenu() {
		setLayout(null);
			BExit.setBounds(715,500,350,150);
            add(BExit);
            add(BPlay);
            BPlay.setBounds(750, 300, 280,100);
            add(BPlay);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(HomeBg.getImage() , 0, 0, 1800, 800, this);
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(10)); // กำหนดความหนาเป็น 5 หน่วย
		g2.drawRect(300, 100, 1200, 150);
        g.setColor(Color.YELLOW);
		g.fillRect(300, 100, 1200, 150);
		g.setColor(Color.BLACK);
        g.setFont(new Font("Dosis",Font.CENTER_BASELINE,85));		
        g.drawString("Run away from the Math Demon",350,200);	
	}
}




