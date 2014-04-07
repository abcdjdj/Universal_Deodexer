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
 * This class contains the various static final 
 * variables of the software.
 * 
 * @Author - Madhav Kanbur
 * @Version - V5
 */

import java.awt.Color;
import java.io.File;
import java.io.FileFilter;

public class StaticConstants
{
	public static final String TITLE = "Universal Deodex V5";
	public static final Color BACKGROUND = new Color(22, 132, 219);
	public static final String AUTHOR = "abcdjdj";
	public static final String[] api_versions = {
			"Default","4.4", "4.3", "4.2", "4.1", "4.0.3, 4.0.4",
			"4.0, 4.0.1, 4.0.2", "3.2", "3.1.x", "3.0.x", "2.3.4, 2.3.3",
			"2.3.2, 2.3.1, 2.3", "2.2.x", "2.1.x", "2.0.1", "2.0", "1.6",
			"1.5", "1.1", "1.0"
	};
	public static final String[] compressions = {
		"Compression=0","Compression=1","Compression=2","Compression=3",
		"Compression=4","Compression=5","Compression=6","Compression=7",
		"Compression=8","Compression=9"
		};
	public static final int default_compression=5;
	public static final int default_apilevel=14;
	public static final FileFilter filefilter = new FileFilter(){
		@Override
		public boolean accept(File f)
		{
			if(f.getName().endsWith(".apk") || f.getName().endsWith(".jar"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	};
}
