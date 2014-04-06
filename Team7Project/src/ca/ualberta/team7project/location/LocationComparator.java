package ca.ualberta.team7project.location;

import java.util.Comparator;

import ca.ualberta.team7project.models.ThreadModel;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.DegreeCoordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;

/**
 * Compares ThreadModels by geolocation distance
 * @author michael
 * 
 */
public class LocationComparator implements Comparator<ThreadModel> {
	
	/**
	 * Gets the distance between two ThreadModel's LocationModels
	 * @return distance between two points as a double
	 */
	
	private Coordinate lat;
	private Coordinate lon;
	
	public LocationComparator(double lat, double lon){
		this.lat = new DegreeCoordinate(lat);
		this.lon = new DegreeCoordinate(lon);
	}
	
	//calculate the distance from the base point and the point of indicated thread
	public double distanceFromBase(ThreadModel thread){
		Coordinate latitude = new DegreeCoordinate(thread.getLocation().getLatitude());
		Coordinate longitude = new DegreeCoordinate(thread.getLocation().getLongitude());
		
		Point threadPoint = new Point(latitude, longitude);
		
		Point basePoint = new Point(lat, lon);
		
		return EarthCalc.getDistance(threadPoint, basePoint);
	}
	
	//I don't know how to use this
	public double distanceDelta(ThreadModel lhs, ThreadModel rhs)
	{
		Coordinate latitude = new DegreeCoordinate(lhs.getLocation().getLatitude());
		Coordinate longitude = new DegreeCoordinate(lhs.getLocation().getLongitude());
		
		Point lhsPoint = new Point(latitude, longitude);

		latitude = new DegreeCoordinate(rhs.getLocation().getLatitude());
		longitude = new DegreeCoordinate(rhs.getLocation().getLongitude());

		Point rhsPoint = new Point(latitude, longitude);

		return EarthCalc.getDistance(lhsPoint, rhsPoint);		
	}
	
	/**
	 * For sorting collections, compares two ThreadModels by location.
	 * @return int greater than 1 if lhs is further away than the rhs.
	 */
	@Override
	public int compare(ThreadModel lhs, ThreadModel rhs) {

		double distancelhs = distanceFromBase(lhs);
		double distancerhs = distanceFromBase(rhs);
		
		if (distancelhs-distancerhs>0)
			return 1;
		if (distancelhs-distancerhs < 0)
			return -1;
		return 0;
	}

}
