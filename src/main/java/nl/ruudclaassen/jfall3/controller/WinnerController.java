package nl.ruudclaassen.jfall3.controller;

import javax.servlet.http.HttpSession;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.model.Participant;
import nl.ruudclaassen.jfall3.services.CodeService;
import nl.ruudclaassen.jfall3.services.MetadataService;
import nl.ruudclaassen.jfall3.services.ParticipantService;

import nl.ruudclaassen.jfall3.general.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

// TODO: CR add comments to make it clear which method does what
// TODO: CR introduce constants when you have to repeat a string variable a number of times.
@Controller
public class WinnerController {

	@Autowired
	private CodeService codeService;

	@Autowired
	private MetadataService metadataService;

	@Autowired
	private ParticipantService participantService;

	@RequestMapping("promo/{id}/winner/")
	public String winner() {
		return "redirect:/promo/";
	}

	@RequestMapping("promo/{id}/winner/spin")
	public String redirectWinner(@PathVariable String id) {
		return "redirect:/promo/";
	}

	@RequestMapping("promo/{id}/winner/reset")
	public String resetWinner(@PathVariable String id, @RequestParam(value = "caller", required = false) String caller,
	        ModelMap modelMap) {

		Metadata metadata = metadataService.getPromotionById(id);
		metadata.setWinner(null);
		metadataService.update(metadata);

		// TODO: CR use Yoda conditions here (removes the need for a null-check)
		if ("edit".equals(caller)) {
			return Constants.REDIRECT_PROMOTIONS + id + "/edit";
		}

		return Constants.REDIRECT_PROMOTIONS;
	}

	@RequestMapping(value = "/promo/{id}/winner/", method = RequestMethod.POST)
	public String loadPromotion(HttpSession session, @RequestParam String promoId, ModelMap modelMap) {

		Metadata metadata = (Metadata) session.getAttribute("metadata");

		if (metadata == null) {
			metadata = metadataService.getPromotionById(promoId);
			session.setAttribute("metadata", metadata);
		}

		modelMap.put("metadata", metadata);
		modelMap.put("winner", "");
		modelMap.put("winningCode", "");
		modelMap.put("spin", false);

		return Constants.WINNER;
	}

	@RequestMapping(value = "/promo/{id}/winner/spin", method = RequestMethod.POST)
	public String loadCodes(@PathVariable String id, @RequestParam String activateSpinner, ModelMap modelMap) {

		Metadata metadata = metadataService.getPromotionById(id);

		if (metadata == null) {
			return Constants.REDIRECT_HOME;
		}

		// If spin button was not pressed, redirect back to winner page)
		if (Integer.parseInt(activateSpinner) != 1) {
			return Constants.REDIRECT_PROMOTIONS + metadata.getId() + "/winner/";
		}

		Participant participant = participantService.pickWinner(metadata);

		// Update metadata with winning code
		metadata.setWinner(participant);
		metadataService.update(metadata);

		modelMap.put("metadata", metadata);
		modelMap.put("spin", true);
		modelMap.put("winner", participant.getName());
		modelMap.put("winningCode", participant.getCode());

		return Constants.WINNER;
	}


}
