package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import logic.WindModel;

/**
 * The GUI Runner for the Shibumi Setup Application
 * 
 * @author J2579
 */
@SuppressWarnings("serial")
public class ShibumiSetup extends JFrame implements ActionListener{

	private PositionalWindow window;
	
	private JLabel beachLabel = new JLabel("Beach is facing what direction?");
	private String[] beachStringDirections = new String[] {"Select Direction...","North","East","South","West"};
	private JComboBox<String> beachDirections = new JComboBox<String>(beachStringDirections);

	private JLabel windLabel = new JLabel("Wind is blowing from what direction?");
	public static String[] directions = new String[] {"Select Direction...","N","NNE","NE","ENE","E","ESE","SE","SSE","S","SSW","SW","WSW","W","WNW","NW","NNW"};
	private JComboBox<String> windDirections = new JComboBox<String>(directions); 
	
	
	private WindModel model;
	
	public void run() {
		
		setTitle("Shibumi Positional Setup");
		setSize(900,500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new GridLayout(1, 2)); //Selection on left, visual on right
		
		JPanel selectorPanel = new JPanel();
		selectorPanel.setLayout(new GridLayout(1, 2)); //beach on left, wind on right
		
		JPanel beachDirectionPanel = new JPanel();
		beachDirectionPanel.setLayout(new GridLayout(2,1));
		beachDirectionPanel.setBorder(new TitledBorder("Beach Direction"));
		
		beachLabel.setHorizontalAlignment(JLabel.CENTER);
		beachLabel.setVerticalAlignment(JLabel.CENTER);
		beachDirectionPanel.add(beachLabel);
		
		beachDirectionPanel.add(beachDirections);
		beachDirections.addActionListener(this);
		
		
		selectorPanel.add(beachDirectionPanel);
		
		JPanel windDirectionPanel = new JPanel();
		windDirectionPanel.setLayout(new GridLayout(2,1));
		windDirectionPanel.setBorder(new TitledBorder("Wind direction"));
		
		windLabel.setHorizontalAlignment(JLabel.CENTER);
		windLabel.setVerticalAlignment(JLabel.CENTER);
		windDirectionPanel.add(windLabel);
		
		windDirectionPanel.add(windDirections);
		windDirections.addActionListener(this);
		
		selectorPanel.add(windDirectionPanel);
		

		add(selectorPanel);
		
		window = new PositionalWindow(400,500);
		add(window);
		
		model = new WindModel();
		
		setVisible(true);
		
		
		window.createAndSetBuffer();
		window.update();
		
		//Timer starts here
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource().equals(windDirections)) 
			model.setWindDirection((String)windDirections.getSelectedItem());
		else if(ae.getSource().equals(beachDirections))
			model.setBeachDirection((String)beachDirections.getSelectedItem());

		window.update();
	}
	
	public static void main(String[] args) {
		ShibumiSetup s = new ShibumiSetup();
		s.run();
	}

	
	private class PositionalWindow extends DoubleBufferedCanvas {

		public PositionalWindow(int width, int height) {
			super(width, height);
		}

		@Override
		public void draw(Graphics g) {
			
			g.fillRect(0, 0, 500, 400);
			
			String direction = model.getBeachDirection();
			if(direction.equals("East")) {
				g.setColor(Color.YELLOW);
				g.fillRect(0, 0, 300, 500);
				g.setColor(Color.BLUE);
				g.fillRect(300, 0, 100, 500);
			}
			
			else if(direction.equals("North")) {
				g.setColor(Color.YELLOW);
				g.fillRect(0, 100, 400, 400);
				g.setColor(Color.BLUE);
				g.fillRect(0, 0, 500, 100);
			}
			
			else if(direction.equals("South")) {
				g.setColor(Color.YELLOW);
				g.fillRect(0, 0, 400, 350);
				g.setColor(Color.BLUE);
				g.fillRect(0, 350, 500, 150);
			}
			
			else if(direction.equals("West")) {
				g.setColor(Color.YELLOW);
				g.fillRect(100, 0, 300, 500);
				g.setColor(Color.BLUE);
				g.fillRect(0, 0, 100, 500);
			}
			
			int[][] points = model.getDrawPoints();

			g.setColor(Color.LIGHT_GRAY);
			drawCircle(g, points[0][0], points[0][1], 25);
			drawCircle(g, points[1][0], points[1][1], 25);
			
			g.setColor(Color.GREEN);
			g.fillPolygon(new int[] {points[0][0],points[1][0],points[2][0]}, new int[] {points[0][1],points[1][1],points[2][1]},3);			
		}
		
		private void drawCircle(Graphics g, int x, int y, int radius) {

			int diameter = radius * 2;
			g.fillOval(x - radius, y - radius, diameter, diameter); 
		}
		
	}
}