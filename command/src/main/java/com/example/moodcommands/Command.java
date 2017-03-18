package com.example.moodcommands;

/**
 * Created by joshuarobertson on 2017-03-17.
 */

interface Command {
    Boolean execute();
    Boolean unexecute();
    boolean isReversable();
}

