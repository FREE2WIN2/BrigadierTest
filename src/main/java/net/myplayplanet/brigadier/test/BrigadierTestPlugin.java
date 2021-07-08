package net.myplayplanet.brigadier.test;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static com.mojang.brigadier.arguments.IntegerArgumentType.integer;
import static com.mojang.brigadier.builder.LiteralArgumentBuilder.literal;
import static com.mojang.brigadier.builder.RequiredArgumentBuilder.argument;

public class BrigadierTestPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        CommandDispatcher<Player> commandDispatcher = new CommandDispatcher<>();
        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        CommandDispatcher<net.minecraft.server.v1_15_R1.CommandListenerWrapper> dispatcher = craftServer.getServer().getCommandDispatcher().a();
        LiteralArgumentBuilder<net.minecraft.server.v1_15_R1.CommandListenerWrapper> lbuilder = literal("brigadiertest");
        ArgumentBuilder<net.minecraft.server.v1_15_R1.CommandListenerWrapper, ?> largument = argument("zahl", integer());
        dispatcher.register(
              lbuilder
                        .then(
                                largument
                                        .executes(c -> {
                                            System.out.println("Bar is " + getInteger(c, "bar"));
                                            return 1;
                                        })
                        )
                        .executes(c -> {
                            System.out.println("Called foo with no arguments");
                            return 1;
                        })
        );
    }

    private Integer getInteger(CommandContext<?> c, String bar) {
        return c.getArgument(bar,Integer.class);
    }
}
