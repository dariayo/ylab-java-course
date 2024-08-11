package ru.dariayo;

import java.sql.SQLException;

public abstract class Command {
    private Object argument;

    public abstract void execute() throws SQLException;

    public Command() {
    }

    public Object getArgument() {
        return argument;
    }

    public void setArgument(Object argument) {
        this.argument = argument;
    }

    public abstract String getName();

}
