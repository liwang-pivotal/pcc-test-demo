package io.pivotal;

import org.apache.geode.cache.query.CqEvent;
import org.apache.geode.cache.query.CqListener;

public class SimpleCQListener implements CqListener {

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(CqEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEvent(CqEvent e) {
		System.out.println("####### Catched a event! " + e);
	}

}
