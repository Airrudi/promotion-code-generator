package nl.ruudclaassen.jfall3.services;

import java.util.List;

import org.springframework.stereotype.Service;

// TODO: CR: this class doesn't do anything at the moment
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
