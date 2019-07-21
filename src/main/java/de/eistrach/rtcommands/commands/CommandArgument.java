package de.eistrach.rtcommands.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandArgument {
    private static final String REGEX = "^(?<name>\\w+)(?<typeInfo>(?<requireName>[?])?:\\[(?<type>\\w+)])?$";
    private static final Pattern COMPILED_REGEX = Pattern.compile(REGEX);

    private boolean isValidPattern = true;

    private String name;
    private String type;

    private boolean noType;
    private boolean requireName;

    private int length;

    private final String pattern;

    public CommandArgument(final String pattern) {
        this.pattern = pattern;

        final Matcher m = COMPILED_REGEX.matcher(pattern);

        if (!m.find()) {
            isValidPattern = false;
            return;
        }

        name = m.group("name");

        if (m.group("typeInfo") == null) {
            requireName = true;
            noType = true;
            length = 1;
            return;
        }

        requireName = m.group("requireName") == null;
        type = m.group("type");
        length = requireName ? 2 : 1;
    }

    public boolean isValidPattern() {
        return isValidPattern;
    }

    public String getName() {
        return name;
    }

    public String getPattern() {
        return pattern;
    }

    public boolean hasNoType() {
        return noType;
    }

    public boolean requireName() {
        return requireName;
    }

    public String getType() {
        return type;
    }

    public int getLength() {
        return length;
    }


}
