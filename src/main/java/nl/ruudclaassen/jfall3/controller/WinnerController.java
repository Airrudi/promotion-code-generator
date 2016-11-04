package nl.ruudclaassen.jfall3.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.services.CodeService;

@Controller
public class WinnerController {
	
	@Autowired
	CodeService codeService;
	
	@RequestMapping("/winner/")	
	public String winner(HttpSession session, ModelMap modelMap){
		
		Metadata metadata = (Metadata)session.getAttribute("metadata");
		
		if(metadata == null){
			return "redirect:/";
		}		
		
		modelMap.put("metadata", metadata);		
		
		return "winner";		
	}
	
//	@RequestMapping(value = "/winner/", method = RequestMethod.POST)		
//	public String loadCodes(@RequestParam String promoId, HttpSession session, ModelMap modelMap){
//		
//		// TODO: Q: Any dangers in having an empty post? 
//		Metadata metadata = (Metadata)session.getAttribute("metadata");
//		
//		if(metadata == null){
//			metadata = codeService.getMetadataById(promoId);			
//			session.setAttribute("metadata", metadata);
//		}		
//
//		metadata = codeService.pickWinner(metadata);
//		session.setAttribute("metadata", metadata);	
//		modelMap.put("metadata", metadata);		
//		
//		return "winner";
//	}
	
	@RequestMapping("/winner/spin/")
	public String redirectWinner(){
		System.out.println("Redirected user home");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/winner/", method = RequestMethod.POST)		
	public String loadCodes(@RequestParam String activateSpinner, HttpSession session, ModelMap modelMap){
		
		Metadata metadata = (Metadata)session.getAttribute("metadata");
		
		if(metadata == null){
			return "redirect:/";
		}
		
		if(Integer.parseInt(activateSpinner) != 1){
			return "redirect:/winner/";
		}
		
		metadata = codeService.pickWinner(metadata);
		session.setAttribute("metadata", metadata);
		modelMap.put("metadata", metadata);
		
		return "winner";
	}
}
