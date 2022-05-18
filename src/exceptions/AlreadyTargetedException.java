package exceptions;

@SuppressWarnings("serial")
public class AlreadyTargetedException extends Exception{

	public AlreadyTargetedException() {
		
	}

	public AlreadyTargetedException(String s) {
		super(s);
		
	}

}