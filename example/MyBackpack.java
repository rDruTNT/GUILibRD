package dr.dru.gui.example;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import dr.dru.gui.GUI;
import dr.dru.gui.component.icon.IIcon;
import dr.dru.gui.component.icon.IconList;
import dr.dru.gui.component.icon.StatefulIconList;
import dr.dru.gui.component.panel.ShowcasePanel;
import dr.dru.gui.component.position.UIRegion;

public class MyBackpack {
	static GUI myBackpack;
	static {
		myBackpack = new GUI("My backpack", 54,gui->{
			ShowcasePanel backpack = new ShowcasePanel(new StatefulIconList(p->load(p).toList()),e->{
				save(e.changer, e.value.toItemArray());
			});
			gui.addPanel(new UIRegion(0, 0,8,5), backpack);
				
		});
	}
	
	public static void open(Player p) {
		myBackpack.open(p);
	}

	private static HashMap<UUID, ItemStack[]> cachDatas = new HashMap<>();
	
	public static IconList load(Player p) {
		//load data somehow like this
		return new IconList(cachDatas.getOrDefault(p.getUniqueId(), new ItemStack[54]));
	}
	
	public static void save(Player p,ItemStack[] data) {
		cachDatas.put(p.getUniqueId(), data);
	}
	
}
