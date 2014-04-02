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

		double distance = distanceDelta(lhs, rhs);
		
		if (distance > 0)
			return 1;

		if (distance < 0)
			return -1;

		return 0;
	}

}
