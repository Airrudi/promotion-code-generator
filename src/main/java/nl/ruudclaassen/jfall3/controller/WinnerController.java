package nl.ruudclaassen.jfall3.controller;

import javax.servlet.http.HttpSession;

import nl.ruudclaassen.jfall3.model.Promotion;
import nl.ruudclaassen.jfall3.model.Participant;
import nl.ruudclaassen.jfall3.services.CodeService;
import nl.ruudclaassen.jfall3.services.PromotionService;
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
	private PromotionService PromotionService;

	@Autowired
	private ParticipantService participantService;

	@RequestMapping("promo/{id}/winner/")
	public String winner() {
		return "redirect:/promo/";
	}

	@RequestMapping("promo/{id}/winner/spin")
	public String redirectWinner(@PathVariable int id) {
		return "redirect:/promo/";
	}

	@RequestMapping("promo/{id}/winner/reset")
	public String resetWinner(@PathVariable int id, @RequestParam(value = "caller", required = false) String caller,
	        ModelMap modelMap) {

		Promotion Promotion = PromotionService.getPromotionById(id);
		Promotion.setWinner(null);
		PromotionService.update(Promotion);

		// TODO: CR use Yoda conditions here (removes the need for a null-check)
		if ("edit".equals(caller)) {
			return Constants.REDIRECT_PROMOTIONS + id + "/edit";
		}

		return Constants.REDIRECT_PROMOTIONS;
	}

	@RequestMapping(value = "/promo/{id}/winner/", method = RequestMethod.POST)
	public String loadPromotion(HttpSession session, @RequestParam int promoId, ModelMap modelMap) {

		Promotion Promotion = (Promotion) session.getAttribute("Promotion");

		if (Promotion == null) {
			Promotion = PromotionService.getPromotionById(promoId);
			session.setAttribute("Promotion", Promotion);
		}

		modelMap.put("Promotion", Promotion);
		modelMap.put("winner", "");
		modelMap.put("winningCode", "");
		modelMap.put("spin", false);

		return Constants.WINNER;
	}

	@RequestMapping(value = "/promo/{id}/winner/spin", method = RequestMethod.POST)
	public String loadCodes(@PathVariable int id, @RequestParam String activateSpinner, ModelMap modelMap) {

		Promotion Promotion = PromotionService.getPromotionById(id);

		if (Promotion == null) {
			return Constants.REDIRECT_HOME;
		}

		// If spin button was not pressed, redirect back to winner page)
		if (Integer.parseInt(activateSpinner) != 1) {
			return Constants.REDIRECT_PROMOTIONS + Promotion.getId() + "/winner/";
		}

		Participant participant = participantService.pickWinner(Promotion);

		// Update Promotion with winning code
		Promotion.setWinner(participant);
		PromotionService.update(Promotion);

		modelMap.put("Promotion", Promotion);
		modelMap.put("spin", true);
		modelMap.put("winner", participant.getName());
		modelMap.put("winningCode", participant.getCode());

		return Constants.WINNER;
	}


}
