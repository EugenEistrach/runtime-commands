package de.eistrach.rtcommands.commands;

import de.eistrach.rtcommands.commands.annotations.RuntimeCommand;
import de.eistrach.rtcommands.commands.converters.ICustomTypeConverter;
import de.eistrach.rtcommands.commands.converters.impl.DoubleConverter;
import de.eistrach.rtcommands.commands.converters.impl.FloatConverter;
import de.eistrach.rtcommands.commands.converters.impl.IntegerConverter;
import de.eistrach.rtcommands.commands.converters.impl.MaterialConverter;
import de.eistrach.rtcommands.commands.converters.impl.PlayerConverter;
import de.eistrach.rtcommands.commands.converters.impl.StringConverter;
import de.eistrach.rtcommands.commands.localization.Texts;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

public class CommandManager {

    private static final Logger log = Bukkit.getLogger();

    private final Plugin plugin;

    private final Map<String, CustomCommand> commandMap = new HashMap<>();
    private final Map<String, ICustomTypeConverter> converterMap = new HashMap<>();

    public CommandManager(final Plugin plugin) {
        this.plugin = plugin;
    }

    public void registerAllRuntimeCommands() {
        addDefaultConverters();

        final List<Method> methods = getRuntimeCommandMethods();
        methods.sort(Comparator.comparing(method -> ((Method) method).getAnnotation(RuntimeCommand.class).priority())
                .reversed());
        methods.forEach(this::addDefinitionToCommandMap);

        commandMap.values()
                .forEach(this::registerCommand);
    }

    public void addConverter(final String typeName, final ICustomTypeConverter converter) {
        converterMap.put(typeName, converter);
    }

    public Optional convert(final String typeName, final String value) {
        if (!converterMap.containsKey(typeName)) {
            log.severe(() -> String.format(Texts.CONVERTER_NOT_FOUND, typeName));
            return Optional.empty();
        }
        return converterMap.get(typeName).convert(value);
    }

    private void addDefaultConverters() {
        addConverter("String", new StringConverter());
        addConverter("Integer", new IntegerConverter());
        addConverter("Float", new FloatConverter());
        addConverter("Double", new DoubleConverter());
        addConverter("Player", new PlayerConverter(plugin));
        addConverter("Material", new MaterialConverter());
    }

    private void addDefinitionToCommandMap(final Method m)  {
        final RuntimeCommand runtimeCommand = m.getAnnotation(RuntimeCommand.class);
        final CustomCommand customCommand = CustomCommand.fromAnnotation(runtimeCommand);

        if(!commandMap.containsKey(customCommand.getName()))  {
            commandMap.put(customCommand.getName(), customCommand);
        }

        final CustomCommand baseCommand = commandMap.get(customCommand.getName());
        baseCommand.merge(customCommand);
        baseCommand.addPattern(new CommandPattern(this, runtimeCommand, m));
    }


    private void registerCommand(final CustomCommand customCommand) {
        final CommandMap map = getCommandMap();

        if (map == null)
            return;

        final PluginCommand command = createPluginCommand(customCommand.getName(), plugin);

        if (command == null)
            return;

        customCommand.sortPatterns();
        command.setAliases(customCommand.getAliases());
        command.setDescription(customCommand.getDescription());
        command.setPermission(customCommand.getPermission());
        command.setExecutor(customCommand);
        command.setUsage(customCommand.getUsage());

        map.register(plugin.getDescription().getName(), command);
    }

    private PluginCommand createPluginCommand(final String name, final Plugin plugin) {
        try {
            final Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class,
                    Plugin.class);
            constructor.setAccessible(true);
            return constructor.newInstance(name, plugin);
        } catch (final Exception e) {
            log.severe(e.getLocalizedMessage());
        }
        return null;
    }

    private CommandMap getCommandMap() {
        if (Bukkit.getPluginManager() instanceof SimplePluginManager) {
            final Field f;
            try {
                f = SimplePluginManager.class.getDeclaredField("commandMap");
                f.setAccessible(true);
                return (CommandMap) f.get(Bukkit.getPluginManager());
            } catch (final Exception e) {
                log.severe(e.getLocalizedMessage());
            }
        }
        return null;
    }

    private List<Method> getRuntimeCommandMethods() {
        final Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(plugin.getClass().getPackage().getName()))
                        .setScanners(new MethodAnnotationsScanner())
        );
        return new ArrayList<>(reflections.getMethodsAnnotatedWith(RuntimeCommand.class));
    }
}
