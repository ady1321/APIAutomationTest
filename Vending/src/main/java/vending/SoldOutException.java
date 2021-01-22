package vending; 

public class SoldOutException extends RuntimeException { 
	
	private String message; 
	public SoldOutException(String string) { 
		super(string);
		this.message = string;
		System.out.println(string + "\n");
		} 
		
}

