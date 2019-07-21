package de.eistrach.rtcommands.commands.localization;

public class Texts {
    private Texts() {}

    public static final String COMMAND_NOT_FOUND = "Unknown Command. Check out these commands: \n";
    public static final String INVALID_COMMAND_PATTERN = "Ignoring command '%s' because some argument is not valid.";
    public static final String COLLIDING_COMMAND_PATTERN = "Ignoring command '%s' because another command with same " +
            "arguments already exists";

    public static final String INVALID_COMMAND_VARIABLE = "Fix: %s";
    public static final String CONVERTER_NOT_FOUND = "Converter for type '%s' does not exist";

    public static final String ARGUMENT_USAGE_WITHOUT_NAME = "<%s>";
    public static final String ARGUMENT_USAGE_WITH_NAME = "%s <%s>";
}
