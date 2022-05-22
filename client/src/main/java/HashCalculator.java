import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.*;
import java.util.*;

public class HashCalculator implements Hasher{
    private String hash = "";

    public  String computeHash(File directory){

        if(!directory.isDirectory()){
            System.out.println(directory.getName() + " is not a directory");
            return hash;
        } else if (directory.listFiles().length == 0){
            System.out.println("Directory is empty");
            return hash;
        }
        Vector<FileInputStream> streams = new Vector<>();
        System.out.println("Found files for hashing");
        collectStreams(streams, directory);
        SequenceInputStream stream = new SequenceInputStream(streams.elements());
        try {
            hash = DigestUtils.sha1Hex(stream);

        } catch (IOException e) {
            System.out.println("An error occurred while hashing dir = "+ directory.getAbsolutePath());
        }
        return hash;

    }

    private  void collectStreams(List<FileInputStream> streams, File directory){
        File[] files = directory.listFiles();
        // order the files to reproduce the same hash
        Arrays.sort(files, Comparator.comparing(File::getName));
        for(File file: files){
            if(file.isDirectory()){
                collectStreams(streams, file);
            }
            try {
                streams.add(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                System.out.println(file.getName() + " Not found");
            }
        }
    }

    public String getHash() {
        return hash;
    }
}
