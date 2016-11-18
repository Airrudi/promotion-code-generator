package nl.ruudclaassen.jfall3.controller;

import nl.ruudclaassen.jfall3.exceptions.EmptyFileException;
import nl.ruudclaassen.jfall3.exceptions.FileTooBigException;
import nl.ruudclaassen.jfall3.exceptions.PromotionNotFoundException;
import nl.ruudclaassen.jfall3.exceptions.WriteFailedException;
import nl.ruudclaassen.jfall3.model.Promotion;
import nl.ruudclaassen.jfall3.model.Participant;
import nl.ruudclaassen.jfall3.services.CodeService;
import nl.ruudclaassen.jfall3.services.PromotionService;
import nl.ruudclaassen.jfall3.services.ParticipantService;
import nl.ruudclaassen.jfall3.general.Constants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

// TODO: CR: add comments to describe what the methods do 
//TODO: CR introduce constants when you have to repeat a string variable a number of times.
@Controller
public class PromoController {

    private static Logger logger = Logger.getLogger(PromoController.class);

    @Autowired
    private PromotionService PromotionService;

    @Autowired
    private CodeService codeService;

    @Autowired
    private ParticipantService participantService;

    @RequestMapping("/promo")
    public String redirectPromo(ModelMap modelMap) {
        return Constants.REDIRECT_PROMOTIONS;
    }

    @RequestMapping("/promo/")
    public String load(Model model) {
        List<Promotion> promotions = PromotionService.getPromotions();
        model.addAttribute("promotions", promotions);

        return Constants.PROMOTIONS;
    }

    @RequestMapping(value = "/promo/", method = RequestMethod.POST)
    public String delete(@RequestParam int promoId, Model model) {
        PromotionService.delete(promoId);

        List<Promotion> promotions = PromotionService.getPromotions();
        model.addAttribute("promotions", promotions);

        return Constants.PROMOTIONS;
    }

    @RequestMapping("/promo/new")
    public String newPromo(ModelMap modelMap) {
        modelMap.put("Promotion", new Promotion());
        modelMap.put("newPromo", true);
        modelMap.put("edit", false);
        modelMap.put("numberOfParticipants", 0);
        modelMap.put("title", "Nieuwe promotie");

        return Constants.PROMOTION_EDIT;
    }

    @RequestMapping(value = "/promo/new", method = RequestMethod.POST)
    public String savePromo(Promotion Promotion, ModelMap modelMap,
                            @RequestParam(required = false, name = "uploadCodes") MultipartFile codeFile,
                            @RequestParam(required = false, name = "participantFile") MultipartFile participantFile,
                            @RequestParam String generateOrUpload) {

        try {

            Promotion = PromotionService.save(Promotion);

            // TODO: CR: use an enum rather than a string
            if (generateOrUpload.equals("upload") && validFile(codeFile)) {
                codeService.save(Promotion, codeFile.getInputStream());
            } else {
                codeService.save(Promotion);
            }

            if (participantFile != null && validFile(participantFile)) {
                participantService.save(Promotion, participantFile.getInputStream());
            }
        } catch (FileTooBigException e) {

        } catch (IOException e) {

        }

        return Constants.REDIRECT_PROMOTIONS;
    }

    @RequestMapping("/promo/{id}/edit")
    public String getPromo(ModelMap modelMap, @PathVariable int id) {

        Promotion Promotion = PromotionService.getPromotionById(id);
        int numberOfParticipants = Promotion.getNumberOfParticipants();
        Participant participant = Promotion.getWinner();

        // TODO: Check how thymeleaf deals with missing variables (if participant is null and does
        // not exist in the modelmap)
        if (participant != null) {
            modelMap.put("winner", participant.getName());
            modelMap.put("winningCode", participant.getCode());
        }

        modelMap.put("title", "Promotie aanpassen");
        modelMap.put("Promotion", Promotion);
        modelMap.put("edit", true);
        modelMap.put("numberOfParticipants", numberOfParticipants);

        return Constants.PROMOTION_EDIT;
    }

    @RequestMapping(value = "/promo/{id}/edit", method = RequestMethod.POST)
    public String editPromo(
            Promotion Promotion,
            @RequestParam(required = false, name = "uploadParticipants") MultipartFile participantFile
    ) throws IOException {

        PromotionService.update(Promotion);

        if (participantFile != null && validFile(participantFile)) {
            participantService.save(Promotion, participantFile.getInputStream());
        }

        return Constants.REDIRECT_PROMOTIONS;
    }

    private boolean validFile(MultipartFile file) {

        if (file.getSize() == 0) {
            throw new EmptyFileException("File is empty");
        }

        if ((file.getSize() / 1024 > 1000)) {
            throw new FileTooBigException("File is too big");
        }

        return true;
    }

    @ExceptionHandler(value = WriteFailedException.class)
    public String onWriteFailedException(WriteFailedException ex) {
        logger.error(ex.getMessage());

        return Constants.REDIRECT_PROMOTIONS;
    }

    @ExceptionHandler(value = IOException.class)
    public void onIOException(IOException ioe) {
        logger.error(ioe.getMessage());
    }

    @ExceptionHandler(PromotionNotFoundException.class)
    public String notFound(Model model, Exception ex){
        model.addAttribute("ex", ex);

        return Constants.PROMOTION_EDIT;
    }
}
