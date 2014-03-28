import java.util.*;
import java.util.zip.*;
import java.io.*;

public class Verify
{

	public static void verify()
	{
		File list[] = new File("done").listFiles();
		int l = list.length, i = 0;
		for (i = 0; i < l; i++)
		{
			System.out.println("Done checking "
					+ (int) ((double) (i + 1) / l * 100) + "%");
			checkDex(list[i]);
		}
	}

	public static void checkDex(File f)
	{
		try
		{
			ZipInputStream z = new ZipInputStream(new FileInputStream(f));
			ZipEntry e;
			boolean flag = false;
			String name;
			while ((e = z.getNextEntry()) != null)
			{
				name = e.getName();
				if (name.equals("classes.dex"))
				{
					flag = true;
					break;
				}
			}
			if (!flag)
			{
				System.out.println("FAILED : " + f.getName());
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
