package de.eistrach.rtcommands;

import de.eistrach.rtcommands.commands.CommandInfo;
import de.eistrach.rtcommands.commands.annotations.RuntimeCommand;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class CommandExamples {

    @RuntimeCommand(name = "gift", arguments = {"target?:[Player]", "material?:[Material]"})
    private boolean giveItem(final CommandInfo info, final Player target, final Material material) {
        System.out.print(target);
        System.out.println(material);
        return true;
    }

    @RuntimeCommand(name = "blacklist", arguments = "add:[Player]")
    private boolean addToBlacklist(final CommandInfo info, final Player player) {
        System.out.println(player);
        return true;
    }

    @RuntimeCommand(name="blacklist", arguments = "remove:[Player]")
    private boolean removeFromBlacklist(final CommandInfo info, final Player player) {
        System.out.println(player);
        return true;
    }

    @RuntimeCommand(name="blacklist", arguments = "show")
    private boolean showBlacklist(final CommandInfo info) {
        System.out.println("show");
        return true;
    }

    @RuntimeCommand(name="empty")
    private boolean empty(final CommandInfo info) {
        System.out.println("empty");
        return true;
    }

    @RuntimeCommand(name="test", arguments = {"arg1", "arg:[Player]", "arg2?:[String]", "arg4", "p:[Double]"})
    private boolean test(final CommandInfo info, final Player arg, final String arg2, final Double p) {
        System.out.println(arg);
        System.out.println(arg2);
        System.out.println(p);
        return true;
    }

    @RuntimeCommand(name = "perm", arguments = {"p:[Permission]"})
    private boolean permissionTest(final CommandInfo info, final String permission) {

        return true;
    }
}

// \cmd1 source Eugen target Flip