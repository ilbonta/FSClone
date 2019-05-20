package com.bnt.folderclone;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Duplicate {
	static int countD = 0;
	static int countF = 0;
	// ─
	static String CHAR_MIN = "" + (char) Integer.parseInt("2500", 16);
	// ├
	static String CHAR_T = "" + (char) Integer.parseInt("251C", 16);
	// │
	static String CHAR_PIPE = "" + (char) Integer.parseInt("2502", 16);
	// └
	static String CHAR_L = "" + (char) Integer.parseInt("2514", 16);

	/**
	 * Search recursively all file and folder and by adding the name of the file in
	 * the output string, recreates them in the new location
	 * 
	 * @param inputPath where to copy
	 * @param output    where to paste
	 * @param verbose   toggle the comment
	 */
	/**
	 * @param inputPath
	 * @param output
	 * @param verbose
	 * @param graphicTree
	 * @throws IOException
	 */
	public void walkin(String inputPath, String output, boolean verbose, String graphicTree) throws IOException {

//		System.out.println("input: " +inputPath);
//		System.out.println("output: " +output);
//		System.out.println("graphicTree: " +graphicTree);

		File dir = new File(inputPath);
		File listFile[] = dir.listFiles();
		int count = listFile.length;

		if (listFile != null) {
			for (int i = 0; i < count; i++) {

				String outputPath = output + "\\" + listFile[i].getName();

				// Determino ultimo elemento figlio
				String closure = CHAR_T;
				if (i + 1 >= count) {
					closure = CHAR_L;
				}

				// Check element type
				if (listFile[i].isDirectory()) {
					countD++;
					if (verbose) {
						// If a folder contain a folder or last element is a folder)
						if (closure.equals(CHAR_L)) {
							closure = CHAR_T;
						}
						System.out.println(graphicTree + closure + CHAR_MIN + listFile[i].getName());
					}

					Files.createDirectories(Paths.get(outputPath));
					walkin(inputPath + "\\" + listFile[i].getName(), outputPath, verbose,
							graphicTree.replace(CHAR_T, CHAR_PIPE) + CHAR_PIPE + " ");

				} else {
					if (listFile[i].isFile()) {
						countF++;
						if (verbose) {
							System.out.println(graphicTree + closure + CHAR_MIN + listFile[i].getName());
						}

						new FileWriter(outputPath);
					}
				}
			}
		}
	}
}
