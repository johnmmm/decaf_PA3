package decaf.error;

import decaf.Location;

/**
 * example：expected class type for copy expr but int given
 * PA2
 */
public class BadCopyClassError extends DecafError {
    
    private String type;

	public BadCopyClassError(Location location, String type) {
		super(location);
        this.type = type;
	}

	@Override
	protected String getErrMsg() {
		return "expected class type for copy expr but " + type + " given";
	}
}
