package nl.ruudclaassen.jfall3.services;

import nl.ruudclaassen.jfall3.data.CodeDao;
import nl.ruudclaassen.jfall3.model.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.FileAlreadyExistsException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class CodeServiceImpl implements CodeService {

    @Autowired
    GeneratorServiceImpl generatorService;

    @Autowired
    CodeDao codeDao;

    @Autowired
    MetadataService metadataService;

    @Override
    public void save(Metadata metadata, InputStream codeStream) {
        Set<String> codes = new HashSet<>();

        try (InputStreamReader inputStreamReader = new InputStreamReader(codeStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {

            String line;
            String code;

            while ((line = bufferedReader.readLine()) != null) {
                code = line.trim();
                codes.add(code);
            }

            if (codes.size() > 0) {
                metadata.setNumberOfCodes(codes.size());
                metadataService.update(metadata);
                codeDao.save(metadata, codes);
            }

        } catch (FileAlreadyExistsException e) {
            e.getFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void save(Metadata metadata) {
        Set<String> codes = generatorService.generateCodes(metadata.getNumberOfCodes());
        codeDao.save(metadata, codes);
    }

    @Override
    public Set<String> load(Metadata metadata) {
        return codeDao.load(metadata);
    }
}
