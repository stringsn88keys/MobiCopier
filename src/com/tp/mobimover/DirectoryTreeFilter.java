package com.tp.mobimover;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author thomaspowell
 * 
 * class based on http://stackoverflow.com/questions/1384947/java-find-txt-files-in-specified-folder 
 * 
 */
public class DirectoryTreeFilter {
	static public File[] finderByExtension(String dirName, final String extension) {
		File dir = new File(dirName);
		File[] directories = null;
		File[] matchedFiles = null;
		
		if(dir.isDirectory()) {
			matchedFiles = dir.listFiles(new FilenameFilter()
			{
				public boolean accept(File dir, String filename) {
					return filename.endsWith(extension);
				}
			});
			directories = dir.listFiles(new FilenameFilter()
			{
				public boolean accept(File dir, String filename) {
					return dir.isDirectory();
				}	
			});
		}
		if(matchedFiles != null)
		{	
			List<File> tempMatchFiles = new ArrayList<File>();
			tempMatchFiles.addAll(Arrays.asList(matchedFiles));
			for(File directory : directories) {
				File[] lowerPathMatches = finderByExtension(directory.getPath(), extension);
				if(lowerPathMatches != null) {
					tempMatchFiles.addAll(Arrays.asList(lowerPathMatches));
				}
			}
			matchedFiles = tempMatchFiles.toArray(matchedFiles);
		}
		return matchedFiles;
	}
	// For testing purposes only.
	static public void main(String args[]) {
		File[] matches = finderByExtension(args[0], args[1]); 
		
		for(File match : matches) {
			System.out.println(match.getPath() + File.separator + match.getName());
		}
	}
}
