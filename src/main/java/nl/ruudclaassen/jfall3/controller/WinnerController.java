package nl.ruudclaassen.jfall3.controller;

import javax.servlet.http.HttpSession;

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

		Metadata metadata = metadataService.getMetadataById(id);
		metadata.setWinner(null);
		metadataService.update(metadata);

		// TODO: CR use Yoda conditions here
		if (caller != null && caller.equals("edit")) {
			return "redirect:/promo/" + id + "/edit";
		}

		return "redirect:/promo/";
	}

	@RequestMapping(value = "/promo/{id}/winner/", method = RequestMethod.POST)
	public String loadPromotion(HttpSession session, @RequestParam String promoId, ModelMap modelMap) {

		Metadata metadata = (Metadata) session.getAttribute("metadata");

		if (metadata == null) {
			metadata = metadataService.getMetadataById(promoId);
			session.setAttribute("metadata", metadata);
		}

		modelMap.put("metadata", metadata);
		modelMap.put("winner", "");
		modelMap.put("winningCode", "");
		modelMap.put("spin", false);

		return "winner";
	}

	@RequestMapping(value = "/promo/{id}/winner/spin", method = RequestMethod.POST)
	public String loadCodes(@PathVariable String id, @RequestParam String activateSpinner, ModelMap modelMap) {

		Metadata metadata = metadataService.getMetadataById(id);

		if (metadata == null) {
			return "redirect:/";
		}

		// If spin button was not pressed, redirect back to winner page)
		if (Integer.parseInt(activateSpinner) != 1) {
			return "redirect:/promo/" + metadata.getId() + "/winner/";
		}

		Participant participant = participantService.pickWinner(metadata);

		// Update metadata with winning code
		metadata.setWinner(participant);
		metadataService.update(metadata);

		modelMap.put("metadata", metadata);
		modelMap.put("spin", true);
		modelMap.put("winner", participant.getName());
		modelMap.put("winningCode", participant.getCode());

		return "winner";
	}
}
