package ru.dariayo;

public abstract class Command {
    private Object argument;

    public abstract void execute();

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
