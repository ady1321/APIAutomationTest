package vending; 

public class NotSufficientChangeException extends RuntimeException { 
	
	private String message; 
	
	public NotSufficientChangeException(String string) { 
		
		super(string);
		this.message = string;
		System.out.println(string + "\n"); } 

}

