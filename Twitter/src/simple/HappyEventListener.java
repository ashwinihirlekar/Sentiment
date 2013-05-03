package simple;

import com.espertech.esper.client.EventBean;

import com.espertech.esper.client.UpdateListener;

public class HappyEventListener implements UpdateListener {
	public void update(EventBean[] newEvents, EventBean[] oldEvents) {
		try {
			if (newEvents == null) {

				return;
			}
			EventBean event = newEvents[0];
			System.out.println("exceeded the count, actual "
					+ event.get("sum(ctr)"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
