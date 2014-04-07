import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class ActionHandler implements ActionListener
{
	static Thread t;
	@Override
	public void actionPerformed(ActionEvent e)
	{
		if(t!=null && t.isAlive())
		{
			JOptionPane.showMessageDialog(GUI.frame,
				     "There is already a process running.",
				     "Error!",
				     JOptionPane.ERROR_MESSAGE);
			return;
		}
		Object src = e.getSource();
		if (src == GUI.deodex)
		{
			t = new Thread(){
				@Override
				public void run()
				{
					Deodex.deodex(GUI.apilevel, GUI.compression);
					Verify.verify();
				}
			};
			t.start();
		}
		else if (src == GUI.adbpull)
		{
			t = new Thread(){
				@Override
				public void run()
				{
					Deodex.adbpull();
				}
			};
			t.start();
		} 
		else if (src == GUI.clear)
		{
			t = new Thread()
			{
				@Override
				public void run()
				{
					Deodex.clearFramework(true);
				}
			};
			t.start();
		} 
		else if (src == GUI.reset)
		{
			
			t = new Thread()
			{
				@Override
				public void run()
				{
					Deodex.reset();
				}
			};
			t.start();
		} 
		else if (src == GUI.exit)
		{
			System.exit(0);
		}
	}
}
