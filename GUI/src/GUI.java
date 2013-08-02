/*
 * This class handles the entire GUI using
 * various Swing and AWT components. 
 */

/**
 * @Author - Madhav Kanbur(abcdjdj)
 * @Version - V4
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
public class GUI implements ActionListener
{
	static JFrame frame;
	JPanel panel,west,east;
	ArrayList<Color> donecolors;
	JButton apks,jars,exit,adbpull,clear;
	JList list,l2;
	JLabel text;
	public static final int VERSION=4;
	public static int compression=5,apilevel=14;
	String[] values;public static String debugmode="exit";
	JCheckBox debug;
	public static void main(String args[])
	{
		GUI ob = new GUI();
		ob.makeGUI();
	} 
	
	/*
	 * makeGUI is the main function to set the GUI.
	 */
	public void makeGUI()
	{
		frame = new JFrame("Universal Deodexer - V"+VERSION); 
		frame.setSize(600,500);
		frame.setVisible(true);
		frame.getContentPane().setBackground(Color.GREEN);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		panel = new JPanel(){
			    protected void paintComponent(Graphics g)
			    {
	        super.paintComponent(g);
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	        int w = getWidth();
	        int h = getHeight();
	        Color color1 = Color.CYAN;
	        	       Color color2 = Color.magenta;
	        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
	        g2d.setPaint(gp);
	        g2d.fillRect(0, 0, w, h);
	    }};
		
		panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		
		
		setButtons();
		setTitle();
		
		
		addBlanks(panel);
		panel.add(apks);
		addBlanks(panel);
		
		
		panel.add(jars);
		addBlanks(panel);
		
		
		panel.add(adbpull);
		addBlanks(panel);
						
		
		panel.add(clear);
		addBlanks(panel);
		
		
		panel.add(exit);
		
		frame.add(BorderLayout.NORTH,text);
		frame.add(BorderLayout.CENTER,panel);
		
		setWest();
		setEast();
		
		
		
		panel.revalidate();
		panel.repaint();
		
		
	}
	
	/*
	 * This function initializes all the 
	 * various buttons used in the gui.
	 * Each button is randomly assigned a colour by
	 * the function randomColor()
	 */
	public void setButtons()
	{
		donecolors = new ArrayList<Color>();
		//An ArrayList of Colors to prevent returning any
		//repeated colour.
		
		apks = new JButton();
		apks.setText("1. Deodex All APKs");
		apks.setBackground(randomColor());
		apks.addActionListener(this);
		
		jars = new JButton();
		jars.setText("2. Deodex All JARs");
		jars.setBackground(randomColor());
		jars.addActionListener(this);
		
		adbpull = new JButton();
		adbpull.setText("3. Pull framework files from phone");
		
		adbpull.setBackground(randomColor());
		adbpull.addActionListener(this);
		
		clear = new JButton();
		clear.setText("4. Clear framework files");
		clear.setBackground(randomColor());
		clear.addActionListener(this);
		
		exit = new JButton();
		exit.setText("5. Exit");
		exit.setBackground(randomColor());
		exit.addActionListener(this);
	}
	
	/*
	 * This function sets the main title on the 
	 * screen.
	 */
	public void setTitle()
	{

		
		text = new JLabel("Universal Deodexer V" + VERSION +" –By abcdjdj",SwingConstants.CENTER);
		Font f = new Font("serif", Font.ITALIC, 28);
		text.setFont(f);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		ControlActions controller=null;
		try
		{
		  controller  = new ControlActions();
		  
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
			
		}
		Object src = e.getSource();
		if(src==apks)
		{
			System.out.println("Deodexing all apks");
			try
			{
				deleteUnnecessary();
			    controller.deodexApks(compression,apilevel,debugmode);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		
		}
		else if(src==jars)
		{
			System.out.println("Deodexing all jars");
			try
			{
				deleteUnnecessary();
			    controller.deodexJars(compression,apilevel,debugmode);
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else if(src==adbpull)
		{
			System.out.println("Pulling framework files.");
			try
			{
			controller.adbPull();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else if(src==clear)
		{
			System.out.println("Clearing framework files..");
			int ch=JOptionPane.showConfirmDialog(
				    frame,
				    "Are you sure that you want\n to clear framework files?",
				    "Clear framework files?",
				    JOptionPane.YES_NO_OPTION);
			if(ch==1)
			{
				return;
			}
			try
			{
			    controller.clearFiles();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		else if(src==exit)
		{
			System.exit(0);
		}
	}
	
	public void addBlanks(JPanel a)
	{
		JLabel[] blank = new JLabel[2]; 
		for(int i=0;i<blank.length;i++)
		{
			blank[i] = new JLabel();
			blank[i].setText(" ");
			a.add(blank[i]);
		}
	}
	
	public Color randomColor()
	{
		Color[] list = {Color.BLUE,Color.CYAN,
				Color.LIGHT_GRAY,Color.magenta,Color.PINK,Color.RED,
				Color.WHITE,Color.YELLOW};
		boolean flag=true;
		int ans;
		do
        {
			
            ans=(int)(Math.random()*10);
            
            if((ans>=0 && ans<list.length))
            {
            	break;
            }
            
        }while(true);
		for(Color ob:donecolors)
        {
        	if(ob.equals(list[ans]))
        	{
        		flag=false;
        		break;
        	}
        }
		if(flag==false)
		{
			return randomColor();
			/*If the colour has already been assigned
			to another button then a different color
			will be returned using recursion.
			*/
		}
		donecolors.add(list[ans]);
		return list[ans];
	}
	
	public void setWest()
	{

		west = new JPanel(){
			 protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        Graphics2D g2d = (Graphics2D) g;
			        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			        int w = getWidth();
			        int h = getHeight();
			        Color color1 = Color.CYAN;
			        Color color2 = Color.MAGENTA;
			        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
			        g2d.setPaint(gp);
			        g2d.fillRect(0, 0, w, h);
			        }
		};
		JLabel cp = new JLabel("\0                           ");
		west.setBackground(Color.ORANGE);
		west.add(cp);//Fill up some blank spaces horizontally
		frame.add(BorderLayout.WEST,west);
	}
	
	public void setEast()
	{
		east = new JPanel(){
			 protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        Graphics2D g2d = (Graphics2D) g;
			        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			        int w = getWidth();
			        int h = getHeight();
			        Color color1 = Color.CYAN;
			        Color color2 = Color.MAGENTA;
			        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
			        g2d.setPaint(gp);
			        g2d.fillRect(0, 0, w, h);
			        }
		};
		JLabel ver = new JLabel("Android Version",SwingConstants.CENTER);
		
		BoxLayout ly = new BoxLayout(east,BoxLayout.Y_AXIS);
		east.setLayout(ly);
		east.add(ver);
		values = new String[]{"Default", "4.3", "4.2","4.1", "4.0.3, 4.0.4", "4.0, 4.0.1, 4.0.2",
				"3.2","3.1.x","3.0.x","2.3.4, 2.3.3", "2.3.2, 2.3.1, 2.3", "2.2.x", "2.1.x",
				"2.0.1", "2.0", "1.6", "1.5", "1.1", "1.0"
		};
		list = new JList(values);
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
				    	apilevel=14;
				    }
				    else
				    {
				        apilevel=1;
				       for(int i=values.length-1;i>0;i--)
				          {
				            if(selection.equalsIgnoreCase(values[i]))
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
		
	    String[] values2 = {"Compression=0","Compression=1","Compression=2","Compression=3",
	    		"Compression=4","Compression=5","Compression=6","Compression=7",
	    		"Compression=8","Compression=9"};
		l2 = new JList(values2);
		l2.setSelectedIndex(compression);
		JScrollPane scroller2 = new JScrollPane(l2);
		scroller2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		l2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		l2.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent lse) 
			{
				if( !lse.getValueIsAdjusting()) {
				String selection = (String) l2.getSelectedValue();
				compression = selection.charAt(selection.length()-1)-48;
				
			}
		}});
		
		JLabel comp = new JLabel("Compression Level");
		east.add(comp);
		east.add(scroller2);
		
		debug = new JCheckBox("Debug mode");
		debug.setSelected(false);
		debug.addItemListener(new ItemListener(){

			@Override
			public void itemStateChanged(ItemEvent arg0)
			{
				debugmode=debug.isSelected()?"pause":"exit";
			
			}
			
		});
		east.add(debug);
		frame.add(BorderLayout.EAST,east);
	}
	
	public void deleteUnnecessary()throws IOException
	{
		
		File az = new File(".");boolean flag=false;String name;
        String path=az.getCanonicalPath()+"\\apks";
        File[] list = new File(path).listFiles();
        int i,j;
        for(i=0;i<list.length;i++)
        {
            flag=false;
            name=list[i].getName();
            if(name.endsWith(".odex"))
            {
                continue; 
            }
            if(name.endsWith(".apk")||name.endsWith(".jar"))
            {
                name=name.substring(0,name.length()-4);
                for(j=0;j<list.length;j++)
                {
                    if(list[j].getName().contains(name)&&list[j].getName().endsWith(".odex"))
                    {
                        flag=true;
                        break;
                    }
                }
            }
            if(flag==false)
            {
                list[i].delete();
            }
        }
    }
		
}



