package v5.gidd.entities;

/**
 *  Inspectable objects should conform to the following contract and behavior:
 *
 *     1. Define validation check for each field which may be instantiated in an incorrect state.
 *     2. If any validation evaluated the corresponding field as incorrect,
 *        then throw a GiddExpception, and describe the cause in proper detail. Do not catch the exception.
 *        Service layer will do that instead.
 *     3. If no invalid data is detected, then exit while mutating nothing.
 *
 *  Note: It's better to capture user error and provide a error report. Do not attempt to correct user
 *        by mutating fields in this method.
 */

public interface Inspectable {

    /**
     * Runs inspect on some instance of this class. If invalid data is detects,
     * then throw new GiddException with descriptive error message.
     * Do not mutate anything, only inspect it.
     */

    void inspect();
}
