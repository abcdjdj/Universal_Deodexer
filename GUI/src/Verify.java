import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Verify
{
	static StringBuilder sb=new StringBuilder();
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
		
		System.out.println(sb);
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
				sb.append("FAILED : " + f.getName()+"\n");
			}
			z.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
