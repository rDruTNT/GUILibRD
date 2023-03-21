package dr.dru.gui.example;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import dr.dru.gui.GUI;
import dr.dru.gui.GUILib;
import dr.dru.gui.component.icon.Icon;
import dr.dru.gui.component.panel.BorderPanel;
import dr.dru.gui.component.position.UIRegion;
public class Home extends JavaPlugin implements Listener {
	static GUI g;
	static {
		g = new GUI(ChatColor.GOLD+""+ChatColor.BOLD+"Home", 27);
		
		g.addPanel(new UIRegion(0, 0,8,2), new BorderPanel(Icon.of(Material.GRAY_STAINED_GLASS_PANE, " ", 1)));
		
		g.setIcon(10,Material.BEACON,"My stats",1,ChatColor.GRAY + "click to show your stats.")
			.setClickListener(e->MyStats.open(e.player));
		g.setIcon(12,Material.CHAINMAIL_CHESTPLATE,"My accessories",1,ChatColor.GRAY + "click to manage your accessories.")
			.setClickListener(e->MyAccessories.open(e.player));
		g.setIcon(14,Material.CHEST,"My backpack",1,ChatColor.GRAY + "click to open your backpack.")
			.setClickListener(e->MyBackpack.open(e.player));
		g.setIcon(16,Material.RED_BED,"My homes",1,ChatColor.GRAY + "click to manage your homes.")
			.setClickListener(e->MyHomes.open(e.player));
		g.setGlobalClickListener(e->e.player.playSound(e.player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 1));
	}
	
	@Override
	public void onEnable() {
		GUILib.register(this);
		Bukkit.getPluginManager().registerEvents(this, this);
		
		
	}
	
	@EventHandler
	public static void onSneak(PlayerToggleSneakEvent e) {
		open(e.getPlayer());
	}
	
	public static void open(Player p) {
		g.open(p);
	}
	
	
}
