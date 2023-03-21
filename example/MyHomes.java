package dr.dru.gui.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;

import dr.dru.gui.GUI;
import dr.dru.gui.component.icon.Icon;
import dr.dru.gui.component.icon.StatefulIconList;
import dr.dru.gui.component.panel.BorderPanel;
import dr.dru.gui.component.panel.ButtonPanel;
import dr.dru.gui.component.panel.IconListPanel;
import dr.dru.gui.component.position.UILoc;
import dr.dru.gui.component.position.UIRegion;

public class MyHomes {
	static GUI homeUI;
	
	static {
		homeUI = new GUI("MyHomes", 54,g->{
			g.addPanel(new UIRegion(0, 0, 8, 5), new BorderPanel(Icon.of(Material.CYAN_STAINED_GLASS_PANE, " ", 1)));
			
			//homes list
			IconListPanel list = g.addPanel(new UIRegion(1, 1,7,2), 
					new IconListPanel(new StatefulIconList(p->loadHomes(p).stream().map(data->
					Icon.of(Material.RED_BED, data.name, 1,"loc: "+data.loc.toVector(), "click to teleport"))
							.collect(Collectors.toList())))
					.setClickListener(e->e.player.teleport(loadHomes(e.player).get(e.panel.getIndex(e.slot.loc)).loc)));

			g.addPanel(new UILoc(3,5) ,new ButtonPanel(Icon.of(Material.EMERALD_BLOCK,"Pervious"), e->{
				list.turnPage(e.gui, e.player, -1);
//				)
			}));
			g.addPanel(new UILoc(4,5) ,new ButtonPanel(Icon.of(Material.EMERALD_BLOCK,"Back"), e->Home.open(e.player)));
			g.addPanel(new UILoc(5,5) ,new ButtonPanel(Icon.of(Material.EMERALD_BLOCK,"Next"), e->{
//				list.tu
				list.turnPage(e.gui, e.player, 1);
			}));
		});
		
		//border

		
	}
	
	public static void open(Player p) {
		homeUI.open(p);
	}
	
	static ArrayList<HomeData> homes = new ArrayList<>();
	static World w = Bukkit.getWorlds().get(0);
	static Random r = new Random();
	
	static {
		for(int i=0;i<50;i++) {
			homes.add(new HomeData("home "+i, w.getHighestBlockAt(r.nextInt(500), r.nextInt(500))
					.getLocation().add(0.5,1,0.5)));
		}
	}
	
	public static List<HomeData> loadHomes(Player p) {
		//load player's home data somehow by your way
		return homes;
	}
	
	
	
	public static class HomeData {
		String name;
		Location loc;
		
		public HomeData(String name, Location loc) {
			this.name = name;
			this.loc = loc;
		}
	}
	
	
}
