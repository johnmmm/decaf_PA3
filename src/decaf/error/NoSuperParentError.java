package decaf.error;

import decaf.Location;

/**
 * example：no parent class exist for class : people
 * PA2
 */
public class NoSuperParentError extends DecafError {
    
    private String type;

	public NoSuperParentError(Location location, String type) {
		super(location);
        this.type = type;
	}

	@Override
	protected String getErrMsg() {
        return "no parent class exist for " + type;
	}
}
