package de.eistrach.rtcommands.commands;

import de.eistrach.rtcommands.commands.annotations.RuntimeCommand;
import de.eistrach.rtcommands.commands.localization.Texts;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class CustomCommand implements CommandExecutor {

    private static final Logger log = Bukkit.getLogger();

    private final String name;
    private List<String> aliases;
    private String description;
    private String permission;
    private final List<String> usages = new ArrayList<>();

    private final List<CommandPattern> patterns = new ArrayList<>();

    public static CustomCommand fromAnnotation(final RuntimeCommand runtimeCommand) {
        return new CustomCommand(
                runtimeCommand.name(),
                Arrays.asList(runtimeCommand.aliases()),
                runtimeCommand.description(),
                runtimeCommand.permission());
    }

    public CustomCommand(final String name, final List<String> aliases, final String description,
                         final String permission) {
        this.name = name;
        this.aliases = aliases;
        this.description = description;
        this.permission = permission;
    }

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label,
                             final String[] args) {
        final CommandMethod method = getMethod(args);
        if (method == null) {
            return false;
        }
        return method.invoke(new CommandInfo(label, command, sender, args));
    }

    public void merge(final CustomCommand other) {
        if (aliases == null || aliases.isEmpty()) {
            aliases = other.getAliases();
        }

        if (StringUtils.isBlank(description)) {
            description = other.getDescription();
        }

        if (StringUtils.isBlank(permission)) {
            permission = other.getPermission();
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }


    public String getPermission() {
        return permission;
    }

    public String getUsage() {
        final StringBuilder usage = new StringBuilder();
        usage.append(Texts.COMMAND_NOT_FOUND);
        usages.stream()
                .filter(StringUtils::isNotBlank)
                .forEach(s -> usage.append(String.format("%s\n", s)));
        return usage.toString();
    }

    public void addPattern(final CommandPattern pattern) {

        if (pattern.hasErrors()) {
            log.warning(() -> String.format(Texts.INVALID_COMMAND_PATTERN, pattern.getMethod().getName()));
            return;
        }

        if (patterns.stream().anyMatch(commandPattern -> commandPattern.getSignature().equals(pattern.getSignature()))) {
            log.warning(() -> String.format(Texts.COLLIDING_COMMAND_PATTERN, pattern.getMethod().getName()));
            return;
        }

        patterns.add(pattern);
    }

    public void sortPatterns() {
        usages.clear();
        patterns.sort(Comparator.comparing(CommandPattern::getPriority).reversed());
        patterns.forEach(commandPattern -> usages.add(createUsage(commandPattern)));

    }

    private String createUsage(final CommandPattern pattern) {
        if (pattern.getUsage().isEmpty() && pattern.isIgnoreUsage()) {
            return "";
        }

        if(!pattern.getUsage().isEmpty()) {
            return pattern.getUsage();
        }

        final StringBuilder usage = new StringBuilder();

        usage.append("/").append(pattern.getCommandName()).append(" ");

        for (final CommandArgument argument : pattern.getArguments()) {
            if (argument.requireName() && argument.hasNoType()) {
                usage.append(argument.getName());
            } else if (argument.requireName()) {
                usage.append(String.format(Texts.ARGUMENT_USAGE_WITH_NAME, argument.getName(), argument.getType()));
            } else  {
                usage.append(String.format(Texts.ARGUMENT_USAGE_WITHOUT_NAME, argument.getType()));
            }
            usage.append(" ");
        }

        return usage.toString();
    }

    private CommandMethod getMethod(final String[] args) {
        for (final CommandPattern pattern : patterns) {
            final Object[] params = pattern.createParams(args);
            if (params != null) {
                return new CommandMethod(pattern.getMethodInstance(), pattern.getMethod(), params);
            }
        }
        return null;
    }
}
