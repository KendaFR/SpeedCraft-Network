package fr.kenda.speedcraft.core.command.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {
    String name();
    String permission() default "";
    String[] aliases() default {};
    boolean consoleExecute() default true;
}