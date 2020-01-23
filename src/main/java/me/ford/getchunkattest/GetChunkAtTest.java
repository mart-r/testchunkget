package me.ford.getchunkattest;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import io.netty.util.internal.ThreadLocalRandom;

/**
 * GetChunkAtTest
 */
public class GetChunkAtTest extends JavaPlugin {
    private final int timeTotry = 2;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        int loops = 1;
        World world = getServer().getWorlds().get(0);
        if (args.length > 0) {
            try {
                loops = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                // loops = 1
            }
        }
        for (int i = 0; i < loops; i++) {
            runTest(world, sender);
        }
        return true;
    }

    private void runTest(World world, CommandSender sender) {
        int x = ThreadLocalRandom.current().nextInt(-100000, 100000);
        int z = ThreadLocalRandom.current().nextInt(-100000, 100000);
        sender.sendMessage("Starting... chunk " + x + " ," + z + " in " + world.getName() + " is loaded?" + world.isChunkGenerated(x, z));
        world.getChunkAt(x, z); // generate chunk

        BukkitTask task = getServer().getScheduler().runTaskTimer(this, () -> checkTask(sender, world, x, z), 20L, 20L);
        getServer().getScheduler().runTaskLater(this, () -> task.cancel(), 20L * timeTotry);
    }

    private void checkTask(CommandSender sender, World world, int x, int z) {
        boolean generated = world.isChunkGenerated(x, z);
        sender.sendMessage(x + " ," + z + " in " + world.getName() + " loade now?" + generated);
    }

    
}