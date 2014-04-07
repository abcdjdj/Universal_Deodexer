/**
Universal Deodexer
Copyright (C) 2014 Madhav Kanbur

This file is part of Universal Deodexer.

Universal Deodexer is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or
(at your option) any later version.

Universal Deodexer is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Universal Deodexer. If not, see <http://www.gnu.org/licenses/>.*/

/**
 * This class contains the on-click event handling 
 * implementation for the various buttons of the GUI.
 * 
 * @Author - Madhav Kanbur
 * @Version - V5
 */

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
