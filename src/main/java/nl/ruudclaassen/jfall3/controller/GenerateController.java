package nl.ruudclaassen.jfall3.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import nl.ruudclaassen.jfall3.exceptions.InputValidationException;
import nl.ruudclaassen.jfall3.exceptions.InputValidationException.Field;
import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.services.CodeService;

@Controller
public class GenerateController {
	
	@Autowired
	private CodeService codeService;
	
	@RequestMapping("/generate/")		
	public String loadPage(){
		return "generate";
	}
	
	@RequestMapping(value = "/generate/", method = RequestMethod.POST)		
	public String createPromo(HttpSession session,  @RequestParam String title, @RequestParam String numberOfCodes, @RequestParam String note, ModelMap modelMap){
		
		try{			
			Metadata metadata = codeService.save(title, numberOfCodes, note);			
			session.setAttribute("metadata", metadata);
			
			return "redirect:/load/";
			
		} catch(InputValidationException ive){			
			for(Field field : ive.getFields()){				
				modelMap.put("error" + field.capitalize() , true);			
			}
			
			modelMap.put("title", title);
			modelMap.put("numberOfCodes", numberOfCodes);
			modelMap.put("note", note);
			
			return "generate";
		}
	}
}




