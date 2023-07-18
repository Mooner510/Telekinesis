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
    private Set<UUID> autoPickup;
    private Set<UUID> autoSmelt;

    @Override
    public void onEnable() {
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
                        p.sendMessage(chat("&6[ 자동 줍기 ] &c자동줍기 기능이 비활성화되었습니다."));
                        autoPickup.remove(p.getUniqueId());
                    } else {
                        p.sendMessage(chat("&6[ 자동 줍기 ] &a자동줍기 기능이 활성화되었습니다."));
                        autoPickup.add(p.getUniqueId());
                    }
                }
            }
            case "autosmelt" -> {
                if (sender instanceof Player p) {
                    if (autoSmelt.contains(p.getUniqueId())) {
                        p.sendMessage(chat("&6[ 자동 굽기 ] &c자동굽기 기능이 비활성화되었습니다."));
                        autoSmelt.remove(p.getUniqueId());
                    } else {
                        p.sendMessage(chat("&6[ 자동 굽기 ] &a자동굽기 기능이 활성화되었습니다."));
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

    private void smelt(ItemStack itemStack) {
        String str = itemStack.getType().toString();
        if (str.endsWith("_TERRACOTTA")) {
            try {
                itemStack.setType(Material.valueOf(str.substring(0, str.indexOf('_')) + "GLAZED_TERRACOTTA"));
                return;
            } catch (Exception ignore) {
            }
        }
        switch (itemStack.getType()) {
            case RAW_COPPER, COPPER_ORE -> itemStack.setType(Material.COPPER_INGOT);
            case RAW_IRON, IRON_ORE -> itemStack.setType(Material.IRON_INGOT);
            case RAW_GOLD, NETHER_GOLD_ORE, GOLD_ORE -> itemStack.setType(Material.GOLD_INGOT);
            case DIAMOND_ORE -> itemStack.setType(Material.DIAMOND);
            case LAPIS_ORE -> itemStack.setType(Material.LAPIS_LAZULI);
            case REDSTONE_ORE -> itemStack.setType(Material.REDSTONE);
            case COAL_ORE -> itemStack.setType(Material.COAL);
            case EMERALD_ORE -> itemStack.setType(Material.EMERALD);
            case NETHER_QUARTZ_ORE -> itemStack.setType(Material.QUARTZ);
//            case SAND, RED_SAND -> itemStack.setType(Material.GLASS);
//            case COBBLESTONE -> itemStack.setType(Material.SMOOTH_STONE);
//            case SANDSTONE -> itemStack.setType(Material.SMOOTH_SANDSTONE);
//            case RED_SANDSTONE -> itemStack.setType(Material.SMOOTH_RED_SANDSTONE);
//            case CLAY_BALL -> itemStack.setType(Material.BRICK);
//            case NETHERRACK -> itemStack.setType(Material.NETHER_BRICK);
//            case NETHER_BRICKS -> itemStack.setType(Material.CRACKED_NETHER_BRICKS);
//            case BASALT -> itemStack.setType(Material.SMOOTH_BASALT);
//            case CLAY -> itemStack.setType(Material.TERRACOTTA);
//            case STONE_BRICKS -> itemStack.setType(Material.CRACKED_STONE_BRICKS);
//            case POLISHED_BLACKSTONE_BRICKS -> itemStack.setType(Material.CRACKED_POLISHED_BLACKSTONE_BRICKS);
//            case COBBLED_DEEPSLATE -> itemStack.setType(Material.DEEPSLATE);
//            case DEEPSLATE_BRICKS -> itemStack.setType(Material.CRACKED_DEEPSLATE_BRICKS);
//            case DEEPSLATE_TILES -> itemStack.setType(Material.CRACKED_DEEPSLATE_TILES);
//            case CACTUS -> itemStack.setType(Material.GREEN_DYE);
//            case ACACIA_LOG, BIRCH_LOG, OAK_LOG, DARK_OAK_LOG, JUNGLE_LOG, SPRUCE_LOG, MANGROVE_LOG,
//                    STRIPPED_ACACIA_LOG, STRIPPED_OAK_LOG, STRIPPED_JUNGLE_LOG, STRIPPED_BIRCH_LOG, STRIPPED_DARK_OAK_LOG, STRIPPED_SPRUCE_LOG, STRIPPED_MANGROVE_LOG,
//                    ACACIA_WOOD, BIRCH_WOOD, DARK_OAK_WOOD, JUNGLE_WOOD, MANGROVE_WOOD, OAK_WOOD, SPRUCE_WOOD,
//                    STRIPPED_ACACIA_WOOD, STRIPPED_DARK_OAK_WOOD, STRIPPED_BIRCH_WOOD, STRIPPED_JUNGLE_WOOD, STRIPPED_OAK_WOOD, STRIPPED_MANGROVE_WOOD, STRIPPED_SPRUCE_WOOD ->
//                    itemStack.setType(Material.CHARCOAL);
//            case CHORUS_FRUIT -> itemStack.setType(Material.POPPED_CHORUS_FRUIT);
//            case WET_SPONGE -> itemStack.setType(Material.SPONGE);
//            case SEA_PICKLE -> itemStack.setType(Material.LIME_DYE);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        boolean smelt = autoSmelt.contains(uuid);
        boolean pickup = autoPickup.contains(uuid);
        if (!smelt && !pickup) return;

        Collection<ItemStack> drops = event.getBlock().getDrops(event.getPlayer().getInventory().getItemInMainHand());

        event.setDropItems(false);
        if (smelt) drops.forEach(this::smelt);
        if (pickup) {
            drops.forEach(item -> event.getPlayer().getInventory().addItem(item));
            return;
        }
        drops.forEach(item -> event.getPlayer().getWorld().dropItemNaturally(event.getBlock().getLocation(), item));
    }
}
