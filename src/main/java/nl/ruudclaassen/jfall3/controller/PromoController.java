package nl.ruudclaassen.jfall3.controller;

import nl.ruudclaassen.jfall3.exceptions.InputValidationException;
import nl.ruudclaassen.jfall3.services._MetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.ruudclaassen.jfall3.model.Metadata;
import nl.ruudclaassen.jfall3.services.CodeService;

@Controller
public class PromoController {
	
	@Autowired
	private CodeService codeService;

	@Autowired
	private _MetadataService metadataService;
	
	@RequestMapping("/promo/new")
	public String loadPage(ModelMap modelMap){

		modelMap.put("metadata", new Metadata());
		modelMap.put("newPromo", true);

		return "promo-form";
	}
	
	@RequestMapping(value = "/promo/new", method = RequestMethod.POST)
	public String createPromo(Metadata metadata, ModelMap modelMap){
		
		try{
			codeService.save(metadata);
			return "redirect:/promo/";

		} catch(InputValidationException ive){
			for(InputValidationException.Field field : ive.getFields()){
				modelMap.put("error" + field.capitalize() , true);
			}

			modelMap.put("metadata", metadata);
			modelMap.put("newPromo", true);

			return "promo-form";
		}
	}

	@RequestMapping(value = "/promo/{id}/edit", method = RequestMethod.GET)
	public String editPromo(ModelMap modelMap, @PathVariable String id){

		// TODO: Q: How to work with the object in the form, instead of separate fields?
		Metadata metadata = metadataService.getMetadataById(id);

		modelMap.put("metadata", metadata);
		//modelMap.put("new", false);

		return "promo-form";
	}
}




