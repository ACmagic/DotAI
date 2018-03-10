package pv.dotai.datai;

import java.util.Collection;

import pv.dotai.datai.replay.Entity;

public interface ReplayListener {
	void update(int tick, Collection<Entity> entities);
}
