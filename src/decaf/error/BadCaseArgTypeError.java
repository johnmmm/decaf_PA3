package decaf.error;

import decaf.Location;

/**
 * example：incompatible case expr: bool given, but int expected
 * PA2
 */
public class BadCaseArgTypeError extends DecafError {

	private String given;

    public BadCaseArgTypeError(Location location, String given) 
    {
		super(location);
		this.given = given;
	}

	@Override
	protected String getErrMsg() {
		return "incompatible case expr: " + given + " given, but int expected";
	}

}
