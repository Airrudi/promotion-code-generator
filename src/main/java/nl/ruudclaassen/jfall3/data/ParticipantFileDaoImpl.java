package nl.ruudclaassen.jfall3.data;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Component
public class ParticipantFileDaoImpl implements ParticipantDao {

    public static final String HEADERS = "name,code,email";

    @Override
    public Map<String, Participant> delete() {
        return null;
    }

    @Override
    public void save(Metadata metadata, Set<Participant> participantList) {
        // TODO: Check if file already exists?
        String fileName = buildFileName(metadata.getId());
        File file = new File(fileName);

        // Overwrites existing file
        try (PrintWriter writer = new PrintWriter(file, "UTF-8")){
            writer.println(HEADERS);

            // Print the entries
            participantList.forEach((participant)->writer.println(participant));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Schrijven mislukt");

        }
    }

    public String buildFileName(String id){
        return "participant-" + id + ".csv";
    }

    private void buildParticipantList(String line, Set<Participant> participants){
        String[] lineArray = line.split(",");
        Participant participant = new Participant(lineArray[0], lineArray[1], lineArray[2]);
        participants.add(participant);
    }

    @Override
    public Participant getParticipantByCode(Metadata metadata) {
        Set<Participant> participants = this.load(metadata.getId());
        String code = metadata.getWinningCode();

        for(Participant participant : participants){
            if(participant.getCode().equals(code)){
                return participant;
            }
        }

        return null;
    }

    @Override
    public Set<Participant> load(String promoId) {
        Set<Participant> participants = new HashSet<>();
        String fileName = buildFileName(promoId);

        System.out.println("Start reading ParticipantFile from " + Paths.get(fileName).toAbsolutePath());
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.skip(1).forEach(s -> buildParticipantList(s, participants));
        } catch (IOException e) {
            System.out.println("Failed to read ParticipantFile from " + Paths.get(fileName).toAbsolutePath());
            System.out.println("Returning empty list of participants");
            //e.printStackTrace();
        } finally{
            return participants;
        }
    }
}
