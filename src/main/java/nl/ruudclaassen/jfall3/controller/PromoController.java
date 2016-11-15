package nl.ruudclaassen.jfall3.controller;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;
import nl.ruudclaassen.jfall3.services.CodeService;
import nl.ruudclaassen.jfall3.services.MetadataService;
import nl.ruudclaassen.jfall3.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Controller
public class PromoController {

    @Autowired
    private MetadataService metadataService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private ParticipantService participantService;


    @RequestMapping("/promo/")
    public String load(ModelMap modelMap) {

        Map<String, Metadata> promotions = metadataService.load();
        modelMap.put("promotions", promotions);

        return "promotions";
    }

    @RequestMapping(value = "/promo/", method = RequestMethod.POST)
    public String delete(@RequestParam String promoId, ModelMap modelMap) {

        // Returns map with remaining promotions
        // TODO: Q: Get metadata first or put promoId directly in method below?


        Map<String, Metadata> promotions = metadataService.delete(promoId);
        modelMap.put("promotions", promotions);

        return "promotions";
    }

    @RequestMapping("/promo/new")
    public String newPromo(ModelMap modelMap) {
        modelMap.put("metadata", new Metadata());
        modelMap.put("newPromo", true);
        modelMap.put("edit", false);
        modelMap.put("numberOfParticipants", 0);
        modelMap.put("title", "Nieuwe promotie");

        return "promo-form";
    }

    @RequestMapping(value = "/promo/new", method = RequestMethod.POST)
    public String savePromo(Metadata metadata, ModelMap modelMap, @RequestParam(required = false, name="uploadCodes") MultipartFile codeFile, @RequestParam(required = false, name="uploadParticipants") MultipartFile uploadParticipants, @RequestParam String generateOrUpload, @RequestParam(required=false) String registeredParticipants) {

        try {

            metadataService.save(metadata);

            if (generateOrUpload.equals("upload") && validFile("txt", codeFile)) {
                codeService.save(metadata, codeFile.getInputStream());
            } else {
                codeService.save(metadata);
            }

            if (registeredParticipants != null && registeredParticipants.equals("1") && validFile("csv", uploadParticipants)) {
                participantService.save(metadata, uploadParticipants.getInputStream());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/promo/";
    }

    @RequestMapping("/promo/{id}/edit")
    public String getPromo(ModelMap modelMap, @PathVariable String id) {

        Metadata metadata = metadataService.getMetadataById(id);
        int numberOfParticipants = metadata.getNumberOfParticipants();
        Participant participant = metadata.getWinner();

        // TODO: Check how thymeleaf deals with missing variables (if participant is null and does not exist in the modelmap)
        if(participant != null) {
            modelMap.put("winner", participant.getName());
            modelMap.put("winningCode", participant.getCode());
        }

        modelMap.put("title", "Promotie aanpassen");
        modelMap.put("metadata", metadata);
        modelMap.put("edit", true);
        modelMap.put("numberOfParticipants", numberOfParticipants);

        return "promo-form";
    }

    @RequestMapping(value = "/promo/{id}/edit", method = RequestMethod.POST)
    public String editPromo(Metadata metadata, ModelMap modelMap, @RequestParam(required = false, name="uploadParticipants") MultipartFile participantFile, @RequestParam(required=false) String registeredParticipants) {

        try {
            metadataService.update(metadata);

            if (registeredParticipants != null && registeredParticipants.equals("1") && validFile("csv", participantFile)) {
                participantService.save(metadata, participantFile.getInputStream());
                //ParticipantService.VALID_PARTICIPANTS_LIST;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/promo/";
    }


    private boolean validFile(String fileType, MultipartFile file) throws Exception {
//        if (!file.getContentType().equals(fileType)) {
//            throw new Exception("Incorrect fileType");
//        }

        if ((file.getSize() / 1024 > 1000)) {
            throw new Exception("File is to big");
        }

        return true;
    }





}




