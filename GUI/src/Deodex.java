import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
public class Deodex
{
		static HashMap<String,String> files = new HashMap<String,String>();;
		static StringBuilder log = new StringBuilder("");
		static int number=0;
		
		public static void deodex(int apilevel,int compression)
		{
		    
			
		    File[] list = new File("source").listFiles(StaticConstants.filefilter);
			deleteUnnecessary(list);
			list = new File("source").listFiles(StaticConstants.filefilter);
			
			number=list.length;
			
			
			for(File ob:list)
				addToMap(ob);
			
			execute(apilevel,compression);
			
			writeLog();
			
			
		}
		
		private static void addToMap(File f)
		{
			String full = f.getName();
			int index = full.lastIndexOf('.');
			String name_noExt = full.substring(0,index);
			String ext = full.substring(index+1);
			
			files.put(name_noExt,ext);
		}
		
		@SuppressWarnings("rawtypes")
		private static void execute(int apilevel, int compression)
		{
			try
			{
				int k=0;
				Set set = files.entrySet();
				Scanner in;
				Iterator i = set.iterator();
				while(i.hasNext())
				{
					Map.Entry me = (Map.Entry)i.next();
					File t = new File("framework\\"+me.getKey()+"."+me.getValue());
					Process p = Runtime.getRuntime().exec("cmd /c start /W /B deodex.bat " + me.getKey() + " " + compression + " " + apilevel + " pause " + me.getValue() + " " + t.exists());//cmd /c start /wait
					in = new Scanner(p.getInputStream());
					while(in.hasNextLine())
					{
						String line = in.nextLine();
						System.out.println(line);
						log.append(line).append('\n');
					}			
					
					p.waitFor();
					log.append("\n\n================COMPLETED DEODEXING===============\n").
						append("===================DEODEXED " + (++k) + " of " + number+"===============");
					System.out.println("\n\n================COMPLETED DEODEXING===============");
					System.out.println("===================DEODEXED " + k + " of " + number+"===============");
				}
				
			}
			catch(Exception e)
			{ e.printStackTrace(); }
		}
		
		private static void writeLog()
		{
			System.out.println("\n\nWriting Log to file..");
			try
			{
			  File x = new File("working\\log.txt");
			  if(!x.exists())
			  	 x.createNewFile();
			  FileWriter out = new FileWriter(x);
			  out.write(log.toString());
			  out.flush();
			  out.close();
			}
			catch(IOException e)
			{	e.printStackTrace(); }
			
			System.out.println("DONE WRITING.");
		}
		
		private static void deleteUnnecessary(File[] l)
		{
			for(File ob:l)
			{
				String name=ob.getName().substring(0,ob.getName().lastIndexOf('.'));
				if(new File("source\\"+name+"."+"odex").exists())
				{}
				else
				{
					ob.delete();
				}
			}
		}
		
		public static void clearFramework(boolean showConfirm)
		{
			if(showConfirm==true)
			{
				int ch=JOptionPane.showConfirmDialog(
					GUI.frame,
					"Are you sure that you want\n to clear framework files?",
					"Clear framework files?",
					JOptionPane.YES_NO_OPTION);
			
				if(ch==1)
				{
					return;
				}
			}
			
			File[] list = new File("framework").listFiles();
			for(File ob:list)
			{
				String name = ob.getName();
				if(name.equals("smali.jar") || name.equals("baksmali.jar"))
					continue;
				else
					ob.delete();
					
			}
			
			
			if(showConfirm==true)
			{
				list = new File("framework").listFiles();
				if(list.length==2)
				{
				JOptionPane.showMessageDialog(GUI.frame,
					     "Cleared Framework files successfully.",
					     "Success!",
					     JOptionPane.PLAIN_MESSAGE);
				}
				else
				{
					JOptionPane.showMessageDialog(GUI.frame,
			             "Framework files not deleted successfully.",
			             "Error!",
			             JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		public static void adbpull()
		{
			try
			{
				Process p = Runtime.getRuntime().exec("cmd /c start /wait adbpull.bat");
				p.waitFor();
			}
			catch(Exception e)
			{	e.printStackTrace(); }
		}
		
		public static void reset()
		{
			int ch=JOptionPane.showConfirmDialog(
					GUI.frame,
					"Are you sure that you want reset the tool?\nWARNING: ALL YOUR DATA IN THE TOOL WILL BE DELETED",
					"Clear framework files?",
					JOptionPane.YES_NO_OPTION);
			if(ch==1)
				return;
			
			//source folder
			File apks[] = new File("source").listFiles();
			for(File ob : apks)
				ob.delete();
			
			//framework folder
			clearFramework(false);
			
			//done folder
			File done[] = new File("done").listFiles();
			for(File ob : done)
				ob.delete();
			
			//working folder
			File working[] = new File("working").listFiles();
			outer : for(File ob : working)
			{
				String name=ob.getName();
				switch(name)
				{
					case "build":
					case "7za.exe":
					case "adb.exe":
					case "AdbWinApi.dll":
					case "AdbWinUsbApi.dll":
						continue outer;
						
					default:
						ob.delete();
				}
			}
			
			boolean success = new File("source").listFiles().length==0 && new File("done").listFiles().length==0;
			ArrayList<String> missing = new ArrayList<String>(0);
			String files[] = {"framework\\smali.jar","framework\\baksmali.jar","working\\build","working\\7za.exe",
					"working\\adb.exe","working\\AdbWinApi.dll", "working\\AdbWinUsbApi.dll"
			};
			for(String x:files)
			{
				File f = new File(x);
				if(!f.exists())
				{
					missing.add(x);
				}
			}
			if(missing.size()==0 && success)
			{
				JOptionPane.showMessageDialog(GUI.frame,
					     "Reset tool successfully.",
					     "Success!",
					     JOptionPane.PLAIN_MESSAGE);
			}
			else
			{
				StringBuilder sb = new StringBuilder();
				for(String s:missing)
				{
					sb.append(s).append('\n');
				}
				JOptionPane.showMessageDialog(GUI.frame,
			             "Could not reset tool. Missing files - \n"+sb+"Kindly re-download the tool.",
			             "Error!",
			             JOptionPane.ERROR_MESSAGE);
			}
						
		}
		
}