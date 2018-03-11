package pv.dotai.datai;

import java.util.Collection;

import pv.dotai.datai.replay.Entity;

/**
 * Interface to implement when creating a listener
 * @author Thomas Ibanez
 * @since  1.0
 */
public interface ReplayListener {
	
	/**
	 * Function to implement when creating a listener,
	 * This function will be called by the replaybuilder each time there's a change in the game's state
	 * @param tick current tick
	 * @param entities all the entities currently in the game
	 */
	void update(int tick, Collection<Entity> entities);
}
