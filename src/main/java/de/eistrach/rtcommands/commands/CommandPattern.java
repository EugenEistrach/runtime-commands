package de.eistrach.rtcommands.commands;

import de.eistrach.rtcommands.commands.annotations.RuntimeCommand;
import de.eistrach.rtcommands.commands.localization.Texts;
import org.bukkit.Bukkit;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

public class CommandPattern {

    private static final Logger log = Bukkit.getLogger();

    private final List<CommandArgument> arguments = new ArrayList<>();

    private final CommandManager commandManager;

    private final Object methodInstance;
    private final Method method;

    private final String commandName;
    private final int priority;
    private final boolean ignoreUsage;
    private final String usage;

    private final String[] originalArguments;
    private String signature = "";

    private boolean hasPatternErrors = false;

    public CommandPattern(final CommandManager commandManager, final RuntimeCommand command, final Method method) {
        this.commandManager = commandManager;
        this.priority = command.priority();
        this.method = method;
        this.methodInstance = createInstance(method);
        this.originalArguments = command.arguments();
        this.commandName = command.name();
        this.ignoreUsage = command.ignoreUsage();
        this.usage = command.usage();
        createVariables(command.arguments());
    }

    public Object[] createParams(final String[] args) {
        if (args.length != getLength()) {
            return null;
        }

        final List<CommandArgument> commandArguments = getArguments();

        final List<Object> params = new ArrayList<>();

        boolean hasError = false;

        int argsIndex = 0;
        for (final CommandArgument argument : commandArguments) {
            if (argument.requireName() && argument.hasNoType()) {
                if (!argument.getName().equals(args[argsIndex])) {
                    hasError = true;
                    break;
                }
                argsIndex++;
                continue;
            }

            final int paramsIndex = argument.requireName() ? argsIndex + 1 : argsIndex;

            if (paramsIndex >= args.length ||
                    argument.requireName() && !args[argsIndex].equals(argument.getName()) ||
                    !argument.requireName() && args[argsIndex].equals(argument.getName())) {
                hasError = true;
                break;
            }

            final Optional param;

            try {
                param = commandManager.convert(argument.getType(), args[paramsIndex]);
            } catch (final Exception e) {
                hasError = true;
                break;
            }

            if (param == null) {
                hasError = true;
                break;
            }

            params.add(param.orElse(null));
            argsIndex = paramsIndex + 1;
        }

        if (hasError) {
            return null;
        }

        return params.toArray();
    }


    public List<CommandArgument> getArguments() {
        return arguments;
    }

    public Object getMethodInstance() {
        return methodInstance;
    }

    public Method getMethod() {
        return method;
    }

    public int getPriority() {
        return priority;
    }

    public String getSignature() {
        return signature;
    }

    public String[] getOriginalArguments() {
        return originalArguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public boolean isIgnoreUsage() {
        return ignoreUsage;
    }

    public String getUsage() {
        return usage;
    }

    public boolean hasErrors() {
        return hasPatternErrors;
    }

    public int getLength() {
        return arguments.stream().mapToInt(CommandArgument::getLength).sum();
    }

    private void createVariables(final String[] arguments) {
        Arrays.stream(arguments)
                .map(this::createPatternVariable)
                .forEach(this::addVariable);

        if (!hasPatternErrors) {
            signature = buildSignature();
        }
    }

    private CommandArgument createPatternVariable(final String pattern) {
        return new CommandArgument(pattern.trim());
    }

    private void addVariable(final CommandArgument variable) {
        if (!variable.isValidPattern()) {
            log.warning(() -> String.format(Texts.INVALID_COMMAND_VARIABLE,  variable.getPattern()));
            hasPatternErrors = true;
            return;
        }

        arguments.add(variable);
    }

    private String buildSignature() {
        final StringBuilder builder = new StringBuilder();
        for (final CommandArgument argument : arguments) {
            if (argument.requireName()) {
                builder.append(argument.getName());
                builder.append("|");
            }

            if (!argument.hasNoType()) {
                builder.append(argument.getType());
                builder.append("|");
            }
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }


    private Object createInstance(final Method m) {
        m.setAccessible(true);
        try {
            return m.getDeclaringClass().newInstance();
        } catch (final InstantiationException | IllegalAccessException e) {
            log.severe(e.getLocalizedMessage());
            return null;
        }
    }
}
