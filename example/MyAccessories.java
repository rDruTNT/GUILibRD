package dr.dru.gui.example;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dr.dru.gui.GUI;
import dr.dru.gui.GUILib;
import dr.dru.gui.component.icon.Icon;
import dr.dru.gui.component.panel.BorderPanel;
import dr.dru.gui.component.panel.ButtonPanel;
import dr.dru.gui.component.panel.ShowcasePanel;
import dr.dru.gui.component.panel.UIPanel;
import dr.dru.gui.component.position.UILoc;
import dr.dru.gui.component.position.UIRegion;

public class MyAccessories {
	
	public static void open(Player p) {
		GUI g = new GUI("My accessories", 45);
		//border
		g.addPanel(new UIRegion(0, 0,8,4), new BorderPanel(Icon.of(Material.GREEN_STAINED_GLASS_PANE, " ")));
		//background
		g.addPanel(new UIRegion(1, 1,7,3), new UIPanel(Icon.of(Material.LIME_STAINED_GLASS_PANE, " ")));

		g.setIcon(11, Material.LEAD,"Necklace");
		g.setIcon(15, Material.FERMENTED_SPIDER_EYE,"Bracelet");

		ShowcasePanel neck = new ShowcasePanel(Icon.of(loadNecklace()));
		ShowcasePanel brace = new ShowcasePanel(Icon.of(loadBracelet()));
		neck.setClickListener(e->saveData(p, neck.showcase.get(0).getItemStack(p), brace.showcase.get(0).getItemStack(p)));
		brace.setClickListener(e->saveData(p, neck.showcase.get(0).getItemStack(p), brace.showcase.get(0).getItemStack(p)));
		g.addPanel(UILoc.of(2, 2), neck);
		g.addPanel(UILoc.of(6, 2), brace);
		
		
		g.addPanel(UIRegion.single(4,4) ,new ButtonPanel(Icon.of(Material.EMERALD_BLOCK,"Back"), e->Home.open(p)));
		g.open(p);
	}
	
	public static void saveData(Player player, ItemStack necklace, ItemStack bracelet) {
		//save your data and active effect here
	}
	
	public static ItemStack loadNecklace() {
		//load your data somehow like this
		return GUILib.getItem(Material.STRING,"Common Necklace",1,"+10% Speed");
	}
	public static ItemStack loadBracelet() {
		//load your data somehow like this
		return GUILib.getItem(Material.STRING,"Cool Bracelet",1,"+3 Magic");
	}
}
