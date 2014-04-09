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
 * This class contains the functions for verifying if
 * a apk/jar has been deodexed successfully.
 * 
 * @Author - Madhav Kanbur
 * @Version - V5
 */

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
		File list[] = new File("done").listFiles(StaticConstants.filefilter);
		int l = list.length, i = 0;
		for (i = 0; i < l; i++)
		{
			System.out.println("Done checking "
					+ (int) ((double) (i + 1) / l * 100) + "%");
			checkDex(list[i]);
		}
		if(sb.toString().contains("FAILED"))
			System.out.println(sb);
		else
			System.out.println("Successfully deodexed all files!");
	}

	private static void checkDex(File f)
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
				sb.append("FAILED : ").append(f.getName()).append('\n');
			}
			z.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
