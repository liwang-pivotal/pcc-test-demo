package io.pivotal;

import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;

public class SimpleListener extends CacheListenerAdapter {
	
	@Override
	public void afterCreate (EntryEvent e) {
		System.out.println(e);
	}
	
	@Override
	public void afterUpdate (EntryEvent e) {
		System.out.println(e);
	}

}
