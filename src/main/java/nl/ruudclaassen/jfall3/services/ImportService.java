package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.model.Metadata;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class ImportService {

//    @Override
//    public List<String[]> importFile(File file){
//        List<String[]> fileLines = new ArrayList<>();
//
//        System.out.println("Start reading lines from " + file.getAbsolutePath());
//        try (Stream<String> stream = Files.lines(file.getPath()) {
//            stream.skip(1).forEach(s -> textLineToArray(s, fileLines));
//        } catch (IOException e) {
//            System.out.println("Failed to read file from " + file.getAbsolutePath());
//            e.printStackTrace();
//            //throw new RuntimeException("File not found");
//        } finally{
//            return fileLines;
//        }
//    }

    private List<String[]> textLineToArray(String textLine, List<String[]> fileLines){
        String[] lineArray = textLine.split(",");
        fileLines.add(lineArray);

        return fileLines;
    }

}
