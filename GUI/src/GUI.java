
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI
{
	static JFrame frame; 
	static JPanel panel,west,north,east;
	static JButton deodex,adbpull,clear,exit,reset;
	static JLabel title;
	static ActionHandler onClick = new ActionHandler();
	static JList<String> list,l2;
	static int apilevel=StaticConstants.default_apilevel,compression=StaticConstants.default_compression;
	
	
	public static void main(String[] args)
	{
		setButtons();
		setNorth();
		setWest();
		setEast();
		makeGUI();

	}
	
	public static void makeGUI()
	{
		frame = new JFrame(StaticConstants.TITLE);
		frame.setSize(600,500);
		frame.getContentPane().setBackground(StaticConstants.BACKGROUND);
		frame.setVisible(true);
		
		panel = new JPanel();
		panel.setBackground(StaticConstants.BACKGROUND);
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		addBlanks(panel);
		panel.add(deodex);
		addBlanks(panel);
		panel.add(adbpull);
		addBlanks(panel);
		panel.add(clear);
		addBlanks(panel);
		panel.add(reset);
		addBlanks(panel);
		panel.add(exit);
		addBlanks(panel);
		
		
				
		frame.add(BorderLayout.CENTER,panel);
		frame.add(BorderLayout.WEST,west);
		frame.add(BorderLayout.NORTH,north);
		frame.add(BorderLayout.EAST,east);
		
	}
	
	public static void setButtons()
	{
				
		deodex = new JButton();
		deodex.setText("1. Deodex all files");
		deodex.addActionListener(onClick);
		
		adbpull = new JButton();
		adbpull.setText("2. Pull framework files from phone");
		adbpull.addActionListener(onClick);
		
		clear = new JButton();
		clear.setText("3. Clear framework files");
		clear.addActionListener(onClick);
		
		reset = new JButton();
		reset.setText("4. Reset tool");
		reset.addActionListener(onClick);
				
		exit = new JButton();
		exit.setText("5. Exit");
		exit.addActionListener(onClick);
		
	}
	
	public static void setWest()
	{

		west = new JPanel();
		JLabel cp = new JLabel("\0                           ");
		west.setBackground(StaticConstants.BACKGROUND);
		west.add(cp);//Fill up some blank spaces horizontally
		
	}
	
	public static void setNorth()
	{
		north = new JPanel();
		title = new JLabel(new StringBuilder(StaticConstants.TITLE).append(" - by ").append(StaticConstants.AUTHOR).toString());
		Font f = new Font("serif", Font.ITALIC, 28);
		title.setFont(f);
		title.setForeground(Color.WHITE);
		north.add(BorderLayout.CENTER,title);
		north.setBackground(StaticConstants.BACKGROUND);
	}
	
	public static void addBlanks(JPanel a)
	{
		JLabel[] blank = new JLabel[2]; 
		for(int i=0;i<blank.length;i++)
		{
			blank[i] = new JLabel();
			blank[i].setText(" ");
			a.add(blank[i]);
		}
	}
	
	public static void setEast()
	{
		east = new JPanel();
		east.setBackground(StaticConstants.BACKGROUND);
								
		JLabel ver = new JLabel("Android Version",SwingConstants.CENTER);
		ver.setForeground(Color.WHITE);
		
		BoxLayout ly = new BoxLayout(east,BoxLayout.Y_AXIS);
		east.setLayout(ly);
		east.add(ver);
		
		list = new JList<String>(StaticConstants.api_versions);
		list.setSelectedIndex(0);
		JScrollPane scroller = new JScrollPane(list);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		east.add(scroller);
		
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent lse) 
			{
				
				
				if(!lse.getValueIsAdjusting())
				{
					//Set the api correctly according to the selection.
				    String selection = (String) list.getSelectedValue();
				    if(selection.equals("Default"))
				    {
				    	apilevel=StaticConstants.default_apilevel;
				    }
				    else
				    {
				        apilevel=1;
				       for(int i=StaticConstants.api_versions.length-1;i>0;i--)
				          {
				            if(selection.equalsIgnoreCase(StaticConstants.api_versions[i]))
						     {
					           break;
						      }
				          apilevel++;
				          }
				    }
				   System.out.println(apilevel);
				}
				
				
			
		}});
		
		addBlanks(east);
		
	   
		l2 = new JList<String>(StaticConstants.compressions);
		l2.setSelectedIndex(StaticConstants.default_compression);
		JScrollPane scroller2 = new JScrollPane(l2);
		scroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		l2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		l2.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent lse) 
			{
				if( !lse.getValueIsAdjusting())
				{
				String selection = (String) l2.getSelectedValue();
				compression = selection.charAt(selection.length()-1)-48;
				System.out.println(compression);
				
			}
		}});
		
		JLabel comp = new JLabel("Compression Level");
		comp.setForeground(Color.WHITE);
		east.add(comp);
		east.add(scroller2);
		
				
		
	}
	
	
	

}
