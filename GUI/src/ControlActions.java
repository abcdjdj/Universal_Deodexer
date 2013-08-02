/*
 * This class contains all the functions which
 * actually deodex all the apks/jars. The execute()
 * function accepts the name of the apk, compression level,
 * api level and debug mode. Debug mode prevents the deodexing
 * window to close automatically after deodexing a jar/apk. Hence
 * it will take either "exit" or "pause" as it's value.
 */
/**
 * @Author - Madhav Kanbur (abcdjdj)
 * @Version - V4
 */

    import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
    public class ControlActions
    {
    	String path,files;File file,folder;File[]listOfFiles;
    	int i;
    	ControlActions()throws IOException
    	{
    		 file = new File(".");
             path = ".";
             path=""+ file.getCanonicalPath() + "\\apks";//Path to the apks folder
             File folder = new File(path);
             listOfFiles = folder.listFiles();
    	}
            
        public void execute(String name, String type,int compression,int api,String debug)throws IOException, InterruptedException
        {
            int i; char b;
            for(i=0;i<name.length();i++)
            {
                b=name.charAt(i);
                if(b=='.' &&(name.substring(i+1).equals("jar") || name.substring(i+1).equals("apk")))
                {
                    break;
                }
            }
     
            name=name.substring(0,i);
            Process p =Runtime.getRuntime().exec("cmd /c start /wait deodex_" + type + ".bat " + name+" "+compression+" "+api +" "+debug);
            p.waitFor();
        }
        
        public void deodexApks(int compression,int api,String debug)throws InterruptedException,IOException
        {
        	listOfFiles = new File(path).listFiles();
        	int k=0,k2=0;
        	for (i=0; i < listOfFiles.length; i++)
            {
                if (listOfFiles[i].isFile())
                {
                    files = listOfFiles[i].getName();
                    if (files.endsWith(".apk"))
                    {
                        execute(files, "apk",compression,api,debug);
                        k++;
                    }
                }
            }
        	for(File a:listOfFiles)
        	{
        		if(a.getName().endsWith(".apk"))
        		{
        			k2++;
        		}
        	}
        	
        	if(k==k2)
        	{
        		JOptionPane.showMessageDialog(GUI.frame,
    				    "Deodexed "+k+" apk file(s)!",
    				    "Success!",
    				    JOptionPane.PLAIN_MESSAGE);
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(GUI.frame,
            		    "Could not deodex all apks.",
            		    "Error!",
            		    JOptionPane.ERROR_MESSAGE);
        	}
        
        
        	
        }
        
        public void deodexJars(int compression,int api,String debug)throws InterruptedException,IOException
        {
        	listOfFiles = new File(path).listFiles();
        	int k=0,k2=0;
        	for (i=0; i < listOfFiles.length; i++)
            {
                if (listOfFiles[i].isFile())
                {
                    files = listOfFiles[i].getName();
                    if (files.endsWith(".jar"))
                    {
                        execute(files, "jar",compression,api,debug);
                        k++;
                    }
                }
            }
        	for(File a:listOfFiles)//There may be other files in the apks folder too.
        	{
        		if(a.getName().endsWith(".jar"))
        		{
        			k2++;
        		}
        	}
        	
        	
        	if(k==k2)
        	{
        		JOptionPane.showMessageDialog(GUI.frame,
    				    "Deodexed "+k+" jar file(s)!",
    				    "Success!",
    				    JOptionPane.PLAIN_MESSAGE);
        	}
        	else
        	{
        		JOptionPane.showMessageDialog(GUI.frame,
            		    "Could not deodex all jar files.",
            		    "Error!",
            		    JOptionPane.ERROR_MESSAGE);
        	}
        	
        }
        
        public void adbPull()throws IOException,InterruptedException
        {
        	Process p =Runtime.getRuntime().exec("cmd /c start /wait adbpull.bat");
            p.waitFor();
        }
        
        public void clearFiles()throws IOException,InterruptedException
        {
        	File[] list = new File("framework").listFiles();
            String name;
            for(File a:list)
            {
                name=a.getName();
                if(name.equalsIgnoreCase("baksmali.jar") || name.equalsIgnoreCase("smali.jar"))
                {
                    continue;
                }
                a.delete();       
            }
            File list2[] = new File("working").listFiles();
            for(File a:list2)
            {
                name=a.getName();
                if(name.equalsIgnoreCase("LOG-APK.txt") || name.equalsIgnoreCase("Log-Jar.txt"))
                {
                    a.delete();
                }
            }
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

