package nl.ruudclaassen.jfall3.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.services.CodeServiceImpl;


@Controller
public class HomeController {
	
	@Autowired
    CodeServiceImpl codeService;
	
	
	@RequestMapping("/")
	public String home(HttpSession session, ModelMap modelMap){
		//Metadata metadata = (Metadata)session.getAttribute("metadata");
		
//		if(metadata != null){
//			modelMap.put("title", metadata.getTitle());
//			modelMap.put("winningCode", metadata.getWinningCode());
//		}	
		
		
		return "home";
	}
	
//	@RequestMapping(value = "/", method = RequestMethod.POST)
//	public String choosePromo(HttpSession session, @RequestParam String promoId, ModelMap modelMap){
//
//		// Get the chosen metadata object
//		Metadata metadata = codeService.getMetadataById(promoId);
//
//		// Show if winningCode already exists (front end effects)
//		modelMap.put("winningCode", metadata.getWinner().getCode(););
//		modelMap.put("title", metadata.getTitle());
//
//		return "home";
//	}
	
	
	
	// session.setAttribute("promoId" , selectedPromo);
}
