package org.mooner510.telekinesis;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

import static org.mooner510.telekinesis.MoonerUtils.chat;

public final class Telekinesis extends JavaPlugin implements Listener {
    private static Telekinesis telekinesis;

    public static Telekinesis getInstance() {
        return telekinesis;
    }

    private Set<UUID> autoPickup;
    private Set<UUID> autoSmelt;

    @Override
    public void onEnable() {
        telekinesis = this;
        autoPickup = new HashSet<>();
        autoSmelt = new HashSet<>();
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info(chat("&aTelekinesis Plugin Enabled!"));
    }

    @Override
    public void onDisable() {
        getLogger().info(chat("&aTelekinesis Plugin Enabled!"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        switch (command.getName()) {
            case "autopickup" -> {
                if (sender instanceof Player p) {
                    if (autoPickup.contains(p.getUniqueId())) {
                        p.sendMessage(chat("&6[ 자동 줍기 ] &c껐다 어쩔래"));
                        autoPickup.remove(p.getUniqueId());
                    } else {
                        p.sendMessage(chat("&6[ 자동 줍기 ] &a아마 켜졌을꺼임 해보셈"));
                        autoPickup.add(p.getUniqueId());
                    }
                }
            }
            case "autosmelt" -> {
                if (sender instanceof Player p) {
                    if (autoSmelt.contains(p.getUniqueId())) {
                        p.sendMessage(chat("&6[ 자동 굽기 ] &c껐다 어쩔래"));
                        autoSmelt.remove(p.getUniqueId());
                    } else {
                        p.sendMessage(chat("&6[ 자동 굽기 ] &a아마 켜졌을꺼임 해보셈"));
                        autoSmelt.add(p.getUniqueId());
                    }
                }
            }
            default -> {
                return false;
            }
        }
        return true;
    }

    private ItemStack smelt(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case RAW_COPPER -> itemStack.setType(Material.COPPER_INGOT);
            case RAW_IRON -> itemStack.setType(Material.IRON_INGOT);
            case RAW_GOLD -> itemStack.setType(Material.GOLD_INGOT);
            case NETHER_GOLD_ORE -> itemStack.setType(Material.GOLD_INGOT);
            case GOLD_ORE -> itemStack.setType(Material.GOLD_INGOT);
            case COPPER_ORE -> itemStack.setType(Material.GOLD_INGOT);
            case DIAMOND_ORE -> itemStack.setType(Material.GOLD_INGOT);
            case IRON_ORE -> itemStack.setType(Material.GOLD_INGOT);
            case LAPIS_ORE -> itemStack.setType(Material.GOLD_INGOT);
            case RAW_GOLD -> itemStack.setType(Material.GOLD_INGOT);
            case RAW_GOLD -> itemStack.setType(Material.GOLD_INGOT);
            case RAW_GOLD -> itemStack.setType(Material.GOLD_INGOT);
            case RAW_GOLD -> itemStack.setType(Material.GOLD_INGOT);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        boolean smelt = autoSmelt.contains(uuid);
        boolean pickup = autoPickup.contains(uuid);
        if (!smelt && !pickup) return;

        Collection<ItemStack> drops = event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand());

        if (smelt) {
            drops.forEach(item -> );
        }
        if (pickup) {
            event.setDropItems(false);
            if (!drops.isEmpty()) drops.forEach(item -> event.getPlayer().getInventory().addItem(item));
        }
    }
}
