package de.eistrach.rtcommands.commands.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RuntimeCommand {
    String name();
    String description() default "";
    String[] aliases() default {};
    String usage() default "";
    String permission() default "";
    String[] arguments() default {};
    boolean ignoreUsage() default false;
    int priority() default 0;
}
