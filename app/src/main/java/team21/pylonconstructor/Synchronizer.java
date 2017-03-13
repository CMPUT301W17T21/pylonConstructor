package team21.pylonconstructor;

/**
 * Created by joshuarobertson on 2017-03-04.
 */

/**
 * This class is an abstract superclass for all of the synchronizers.
 * The synchronizers will be implemented when we handle switching between online and offline.
 */
abstract class Synchronizer {
    public Synchronizer() {}

    public abstract boolean sync();
}
