package nl.ruudclaassen.jfall3.data;

import nl.ruudclaassen.jfall3.exceptions.PromotionNotFoundException;
import nl.ruudclaassen.jfall3.model.Promotion;
import nl.ruudclaassen.jfall3.model.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PromotionDaoImpl implements PromotionDao {

    private static final String METAFILE = "metafile.csv";
    private static final String HEADERS = "id,title,note,creationDate,numberOfCodes,numberOfParticipants,winnerId";

    @Autowired
    ParticipantDao participantDao;

    @PersistenceContext
    private EntityManager em;

    // TODO: CR - do not use system.out.println; do not return something from a finally block; do
    // not use printStackTrace
    @Override
    public List<Promotion> getPromotions() {
        List<Promotion> PromotionList = em.createQuery("SELECT m FROM Promotion m").getResultList();

        //List<Participant> participantList = em.createQuery("SELECT p FROM Participant p").getResultList();

//		System.out.println("Start reading metafile from " + Paths.get(METAFILE).toAbsolutePath());
//		try (Stream<String> stream = Files.lines(Paths.get(METAFILE))) {
//			stream.skip(1).forEach(s -> convertToMap(s, PromotionMap));
//		} catch (IOException e) {
//			System.out.println("Failed to read metafile from " + Paths.get(METAFILE).toAbsolutePath()
//			        + ", creating new one");
//			this.createMetaFile();
//			e.printStackTrace();
//			// throw new RuntimeException("File not found");
//		}
        return PromotionList;
    }

    private void createMetaFile() {
        try (FileWriter fw = new FileWriter(METAFILE, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw);) {
            out.println(HEADERS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: CR magic numbers
    private void convertToMap(String PromotionLine, Map<String, Promotion> PromotionMap) {
        // TODO: Fix if text contains ,
//        String[] PromotionArray = PromotionLine.split(",");
//        Participant participant = null;
//
//        if (PromotionArray.length < 7) {
//            PromotionArray = Arrays.copyOf(PromotionArray, 7);
//        }
//
//        // Create new Promotion object which is added to the PromotionList
//        Promotion Promotion = new Promotion(PromotionArray[0], PromotionArray[1], PromotionArray[2], PromotionArray[3],
//                Integer.parseInt(PromotionArray[4]), Integer.parseInt(PromotionArray[5]), participant);
//
//        // TODO: Q: Before or after Promotion was created? Before you can only pass the Promotion
//        // id...
//        if (PromotionArray[6] != null) {
//            participant = participantDao.getParticipantById(Promotion, PromotionArray[6]);
//            Promotion.setWinner(participant);
//        }

        //PromotionMap.put(Promotion.getId(), Promotion);
    }

    @Override
    public Promotion getPromotionById(int id) {
        Promotion Promotion = em.find(Promotion.class, id);

        if (Promotion == null) {
            throw new PromotionNotFoundException("Promotion could not be found");
        }

        return Promotion;
    }

    @Override
    @Transactional
    public void delete(int id) {
        Promotion Promotion = em.find(Promotion.class, id);
        em.remove(Promotion);
    }

    @Override
    @Transactional
    public Promotion save(Promotion Promotion) {
        em.persist(Promotion);

        return Promotion;
    }

    @Override
    @Transactional
    public Promotion update(Promotion Promotion) {

        // Keep some of the old values
        Promotion oldPromotion = em.find(Promotion.class, Promotion.getId());
        oldPromotion = Promotion;

//        if (Promotion.getNumberOfParticipants() > 0) {
//            oldPromotion.setNumberOfParticipants(Promotion.getNumberOfParticipants());
//        }
//
//        if (Promotion.getNumberOfCodes() > 0) {
//            oldPromotion.setNumberOfCodes(oldPromotion.getNumberOfCodes());
//        }



        return Promotion;
    }

//    public boolean save(List<Promotion> PromotionList) {
//
//        // Overwrites existing file
////        try (PrintWriter writer = new PrintWriter(METAFILE, "UTF-8")) {
////            writer.println(HEADERS);
////
////            // Print the entries
////            PromotionList.forEach((id, Promotion) -> writer.println(Promotion));
////
////            return true;
////
////        } catch (IOException e) {
////            // TODO Auto-generated catch block
////            System.out.println("Schrijven mislukt");
////            return false;
////        }
//    }

//    public boolean save(int promoId, Promotion Promotion) {
//        List<Promotion> PromotionMap = this.getPromotions();
//
//        PromotionMap.put(promoId, Promotion);
//        this.save(PromotionMap);
//
//        return true;
//    }

}
