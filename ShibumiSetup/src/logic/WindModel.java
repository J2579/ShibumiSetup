package logic;

import ui.ShibumiSetup;

/**
 * Holds the information for beach direction, as well as wind degree angle
 * from the view-controller in the model.
 * 
 * @author J2579
 */
public class WindModel {

	private String windDirection = "";

	private String beachDirection = "";
	
	private static final double[] windDegree = new double[16];
	
	public WindModel() {
		WindModel.setUp();
	}
	
	private static void setUp() {
		for(int i = 0; i < 16; ++i) {
			windDegree[i] =  i * 22.5;
		}
	}

	public void setBeachDirection(String beachDirection) {
		this.beachDirection = beachDirection;
	}
	
	public String getBeachDirection() {
		return beachDirection;
	}
	
	public void setWindDirection(String direction) {
		this.windDirection = direction;
	}
	
	/**
	 * Somehow, the trig works. Somehow.
	 */
	public int[][] getDrawPoints() {

		double degreeOfOncomingWind = -1;
		
		for(int i = 1; i < ShibumiSetup.directions.length; ++i) {
			if(ShibumiSetup.directions[i].equals(windDirection)) {
				degreeOfOncomingWind = 22.5 * (i - 1);
				break;
			}
		}
		
		if(degreeOfOncomingWind == -1) //Force the points to be drawn offscreen, if no valid direction was selected
			return new int[][] {{1000,1000},{1000,1000},{1000,1000}};
		
			 
		int pole1X, pole1Y, pole2X, pole2Y, arrowX, arrowY;

		double radianOfOncomingWind = Math.toRadians(degreeOfOncomingWind);
				
		arrowX = (int) (200 + (75 * Math.sin(radianOfOncomingWind + Math.PI)));
		arrowY = (int) (200 + (75 * Math.cos(radianOfOncomingWind)));

		int horizComponent = 200-arrowX;
		int vertiComponent = 200-arrowY;
		
		pole1X = 200 + (-vertiComponent);
		pole1Y = 200 + horizComponent;
		
		pole2X = 200 + vertiComponent;
		pole2Y = 200 + (-horizComponent);
		
		return new int[][] {{pole1X,pole1Y},{pole2X,pole2Y},{arrowX,arrowY}};
	}
}
