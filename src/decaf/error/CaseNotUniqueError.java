package decaf.error;

import decaf.Location;

/**
 * example：condition is not unique
 * PA2
 */
public class CaseNotUniqueError extends DecafError {

    public CaseNotUniqueError(Location location) 
    {
		super(location);
	}

	@Override
	protected String getErrMsg() {
		return "condition is not unique";
	}

}
