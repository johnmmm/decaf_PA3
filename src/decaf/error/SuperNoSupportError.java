package decaf.error;

import decaf.Location;

/**
 * example：super.member_var is not supported
 * PA2
 */
public class SuperNoSupportError extends DecafError {

	public SuperNoSupportError(Location location) {
		super(location);
	}

	@Override
	protected String getErrMsg() {
        return "super.member_var is not supported";
	}
}