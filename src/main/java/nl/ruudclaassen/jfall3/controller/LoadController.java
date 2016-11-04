package nl.ruudclaassen.jfall3.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.services.CodeService;
import nl.ruudclaassen.jfall3.services.MetadataService;

@Controller
public class LoadController {
	
	@Autowired
	CodeService codeService;
	
	@Autowired
	MetadataService metadataService;
		
	@RequestMapping("/load/")		
	public String load(ModelMap modelMap){
		
		Map<String, Metadata> promotions = metadataService.load();
		modelMap.put("promotions", promotions);
		
		return "load";
	}
	
	@RequestMapping(value = "/load/", method = RequestMethod.POST)			
	public String delete(@RequestParam String promoId, ModelMap modelMap){
		
		// Returns map with remaining promotions 
		Map<String, Metadata> promotions = codeService.remove(promoId);		 		
		modelMap.put("promotions", promotions);
		
		return "load";
	}
	
	@RequestMapping(value = "/load/promotion/", method = RequestMethod.POST)			
	public String loadPromotion(HttpSession session, @RequestParam String promoId, ModelMap modelMap){
		
		Metadata metadata = (Metadata)session.getAttribute("metadata");

		if(metadata == null){
			metadata = codeService.getMetadataById(promoId);			
			session.setAttribute("metadata", metadata);
		}		
		
		return "redirect:/winner/";
	}		
}