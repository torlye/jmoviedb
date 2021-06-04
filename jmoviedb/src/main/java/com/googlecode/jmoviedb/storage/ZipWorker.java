/*
 * This file is part of JMoviedb.
 * 
 * Copyright (C) Tor Arne Lye torarnelye@gmail.com
 * 
 * JMoviedb is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMoviedb is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.googlecode.jmoviedb.storage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * A worker class with methods for compressing and uncompressing zip files.
 * Used for handling of the *.jmdb file type, which is a zip file containing
 * Derby's database files.
 * @author Tor Arne Lye
 *
 */
public class ZipWorker {
	
	private String zipFileName;
	private String extractPath;
	private static final int BUFFER = 2048;
	
	private static final String fileSeparator = System.getProperty("file.separator");
	
	/**
	 * Default constructor.
	 * @param zipFileName file name to save to
	 * @param extractPath path to extract files to
	 */
	public ZipWorker(String zipFileName, String extractPath) {
		this.zipFileName = zipFileName;
		this.extractPath = extractPath;
	}
	
	/**
	 * Compresses files in  extractPath to zipFileName
	 * @throws IOException
	 */
	public void compress() throws IOException {
		String[] files = getFileTree("");
		
		byte[] buffer = new byte[BUFFER];
		ZipOutputStream outputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
		
		for(String file : files) {
			FileInputStream inputStream = new FileInputStream(extractPath + fileSeparator + file);
			
			outputStream.putNextEntry(new ZipEntry(file.replaceAll("\\\\", "/")));
			
			outputStream.write(buffer, 0, inputStream.read(buffer));
			
			int len;
			while ((len = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, len);
			}
			
			outputStream.closeEntry();
			inputStream.close();
		}
		outputStream.close();
	}
	
	/**
	 * Extracts files from zipFileName to extractPath
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	public void extract() throws IOException {
		File destinationPath = new File(extractPath);
		File source = new File(zipFileName);
		
		ZipFile zip = new ZipFile(source, ZipFile.OPEN_READ);
		Enumeration entries = zip.entries();
		
		while(entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry)(entries.nextElement());
			
			File destinationFile = new File(destinationPath, entry.getName());
			File destinationParent = destinationFile.getParentFile();
			
			destinationParent.mkdirs();
			
			if(!entry.isDirectory()) {
				BufferedInputStream inputStream = new BufferedInputStream(zip.getInputStream(entry));
				
				int currentByte;
				byte buffer[] = new byte[BUFFER];
				
				FileOutputStream fileOutputStream = new FileOutputStream(destinationFile);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, BUFFER);
				
				while ((currentByte = inputStream.read(buffer, 0, BUFFER)) != -1) {
					bufferedOutputStream.write(buffer, 0, currentByte);
				}
				
				bufferedOutputStream.flush();
				bufferedOutputStream.close();
				inputStream.close();
			}
		}
		zip.close();
	}
	
	/**
	 * Builds an array of all files in the specified folder and its subfolders.
	 * Will call itself recursively.
	 * @param file the folder to start from, relative to the local variable extractPath
	 * @return an array of all filenames found
	 */
	private String[] getFileTree(String file) {
		File rootFolder = new File(extractPath + fileSeparator + file);
		
		//if this is a file, return it
		if(!rootFolder.isDirectory()) {
			return new String[]{file};
		}
		
		ArrayList<String> fileArrayList = new ArrayList<String>();
		if(!file.equals("")) {
			file = file+fileSeparator;
		}
		for(String subFolder : rootFolder.list()) {
			String[] subSubFolders = getFileTree(file + subFolder);
			for(String subSubFolder : subSubFolders) {
				fileArrayList.add(subSubFolder);
			}
		}
		
		//finally, return an array of file/folder paths
		return fileArrayList.toArray(new String[0]);
	}
}
