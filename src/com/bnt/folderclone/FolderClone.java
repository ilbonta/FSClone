package com.bnt.folderclone;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class FolderClone {

	static final String HELP = "" +
            "Uso: java FolderClone" +
            " \"input path\" " +
            " \"output path\" " +
            " [arguments...]\r\n"
			+ "" +
            "Arg:\r\n" + "    " +
            "-h	       help\r\n" +
            "-v	       verbose: verbose mode\r\n" +
            "-d		   force delete output before clone\r\n" +
            "";
                                   
	                                                                            
	public static void main(String[] args) throws IOException {
        FolderClone fc = new FolderClone();

		boolean delete = false;
		boolean verbose = false;

		for (int i = 0; i < args.length; i++) {
			if (args[i].equalsIgnoreCase("-h") || args.length < 2) {
				System.out.println(HELP);
				System.exit(0);
			} else if (args[i].equals("-v")) {
				verbose = true;
			} else if (args[i].equals("-d")) {
				delete = true;
			}
		}

		fc.cloneFS(args[0], args[1], verbose, delete);
	}

    public void cloneFS(String input, String output, boolean verbose, boolean delete) throws IOException {
        long time = 0;
        System.out.println("\n----START----\n");

        if(verbose) {
            time= System.currentTimeMillis();
            System.out.println("\nCOPY from: " + input + "\n TO: " + output+"\n");
        }

        // Check input
        if (!new File(input).isDirectory()) {
            System.out.println("\nDIR \"" + input + "\" DOESN'T EXIST \n---STOPPED---");
        }

        // Check output
        File f =new File(output);
        if(!f.exists()) {
            System.out.println(output);
            f.mkdirs();
        }
        else if(delete) {
            Path path = Paths.get(output);
            Files.walk(path)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }

        System.out.println("...\\" +output.substring(output.lastIndexOf("\\")+1, output.length()));
        System.out.println("      " +Duplicate.CHAR_PIPE);

        Duplicate dup = new Duplicate();
        dup.walkin(input, output, verbose, "      ");
        System.out.println("\n----END without error----\n");

        if(verbose) {
            System.out.println("Copied "+Duplicate.countF+" File and "+Duplicate.countD+" Dir in "+(System.currentTimeMillis()-time)+" mls");
        }
    }
}                                                                        
                                                                         