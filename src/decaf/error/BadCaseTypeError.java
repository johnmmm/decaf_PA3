package decaf.error;

import decaf.Location;

/**
 * example：type: complex is different with other expr's type int
 * PA2
 */
public class BadCaseTypeError extends DecafError {

    private String firsttype;
    
    private String secondtype;

    public BadCaseTypeError(Location location, String firsttype, String secondtype) 
    {
		super(location);
        this.firsttype = firsttype;
        this.secondtype = secondtype;
	}

	@Override
	protected String getErrMsg() {
		return "type: " + firsttype + " is different with other expr's type " + secondtype;
	}

}
