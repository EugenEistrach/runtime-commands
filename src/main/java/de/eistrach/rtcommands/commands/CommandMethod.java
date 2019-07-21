package de.eistrach.rtcommands.commands;

import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class CommandMethod {

    private static final Logger log = Bukkit.getLogger();

    private final Object instance;
    private final Method method;
    private final Object[] params;

    public CommandMethod(final Object instance, final Method method, final Object[] params) {
        this.instance = instance;
        this.method = method;
        this.params = params;
    }

    public boolean invoke(final CommandInfo info) {
        try {

            final ArrayList<Object> objects = new ArrayList<>();
            objects.add(info);
            objects.addAll(Arrays.asList(params));
            return (boolean) method.invoke(instance, objects.toArray());
        } catch (final IllegalAccessException | InvocationTargetException e) {
           log.severe(e.getLocalizedMessage());
        }
        return false;
    }
}
