package ca.ualberta.team7project.interfaces;

import android.location.LocationListener;

/* Note this is not called LocationListener since Android has a LocationListener. */
/**
 * Listener for the LocationController
 */
public interface PositionListener extends LocationListener
{
	public void forceLocationUpdate();
}
