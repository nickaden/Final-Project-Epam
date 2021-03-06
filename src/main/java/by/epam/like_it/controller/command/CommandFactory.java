package by.epam.like_it.controller.command;

import by.epam.like_it.controller.util.CommandLoader;

import java.io.IOException;
import java.util.Map;

public class CommandFactory {

    private static CommandFactory ourInstance = new CommandFactory();
    private Map<String,Command> commandMap;

    public static CommandFactory getInstance() {
        return ourInstance;
    }

    public Command getCommand(String commandType){
        return commandMap.get(commandType);
    }

    private CommandFactory() {

    }

    public void loadCommands() throws IOException{
        commandMap= CommandLoader.getInstance().getCommands();
    }
}
