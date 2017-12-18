package decaf.error;

import decaf.Location;

/**
 * example：For copy expr, the source class : student and the destination class : people are not same
 * PA2
 */
public class BadCopyMatchError extends DecafError {
    
    private String firstType;

    private String secondType;

	public BadCopyMatchError(Location location, String firstType, String secondType) {
		super(location);
        this.firstType = firstType;
        this.secondType = secondType;
	}

	@Override
	protected String getErrMsg() {
		return "For copy expr, the source " + secondType + " and the destination " + firstType + " are not same";
	}
}
