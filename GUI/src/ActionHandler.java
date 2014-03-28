import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ActionHandler implements ActionListener
{
	@Override
	public void actionPerformed(ActionEvent e)
	{
		Object src = e.getSource();
		if(src==GUI.deodex)
		{
			Deodex.deodex(GUI.apilevel, GUI.compression);
		}
		else if(src==GUI.adbpull)
		{
			System.out.println("Pulling");
		}
		else if(src==GUI.clear)
		{
			System.out.println("Clearing framework");
		}
		else if(src==GUI.reset)
		{
			System.out.println("Resetting");
		}
		else if(src==GUI.exit)
		{
			System.exit(0);
		}
	}
}
