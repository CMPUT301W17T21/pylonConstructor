package team21.pylonconstructor;

/**
 * Created by joshuarobertson on 2017-03-17.
 */

abstract class Command {
    abstract public Boolean execute();
    abstract public Boolean unexecute();
    abstract public Boolean isReversable();
}

