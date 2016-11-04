package nl.ruudclaassen.jfall3.exceptions;

import java.util.List;

public class InputValidationException extends Exception {

	private static final long serialVersionUID = 3270546865075222760L;
	
	public enum Field{
		TITLE, 
		NUMBER_OF_CODES;

		// TODO: Q: Best way to create camelCase word?
		private String capitalizeString(String word){
			return word = word.substring(0, 1) + word.substring(1).toLowerCase(); 
		}
		
		public String capitalize(){
			
			String modifiedString = capitalizeString(this.name());
			
			if(modifiedString.contains("_")){
			
				StringBuilder sb = new StringBuilder(modifiedString);				
				
				while(sb.indexOf("_") != -1){
					int i = sb.indexOf("_");
					sb.deleteCharAt(i);
					sb.setCharAt(i, Character.toUpperCase(sb.charAt(i)));
				}			
				
				modifiedString = sb.toString();				
			}
				
			return modifiedString;
		}
	}
	
	private List<Field> fields;

	
	public InputValidationException(List<Field> fields){
		this.fields = fields;
	}
	
	
	public List<Field> getFields() {
		return fields;
	}

}
