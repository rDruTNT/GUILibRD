package dr.gui.org;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import dr.gui.org.GUILib.GUI;
import dr.gui.org.GUILib.ItemListPanel;
import dr.gui.org.GUILib.BorderPanel;
import dr.gui.org.GUILib.OnClickListener;
import dr.gui.org.GUILib.OnPanelClickListener;
import dr.gui.org.GUILib.Panel;
import dr.gui.org.GUILib.UILoc;
import dr.gui.org.GUILib.UIRegion;

//example class
public class Main extends JavaPlugin implements Listener {

	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new GUILib(), this);
	}

	
	
	@EventHandler
	public void test(PlayerToggleSneakEvent e) {
	
		for(int t=0; t<50; t++) {
			GUI g = new GUI("Test", 54);
			ItemListPanel ipl = new ItemListPanel("ListPanel", new UIRegion(2, 1, 6, 4));
			ipl.backgroundMat = new ItemStack(Material.WHITE_STAINED_GLASS_PANE);
			ipl.itemList.add(new ItemStack(Material.GRASS_BLOCK));
			ipl.itemList.add(new ItemStack(Material.STONE_AXE));
			
			ItemStack addBtn = new ItemStack(Material.GREEN_WOOL);
			ItemMeta bm = addBtn.getItemMeta();
			bm.setDisplayName("加入列表");
			addBtn.setItemMeta(bm);
			ipl.itemList.add(addBtn);
			
			g.addPanel(new BorderPanel("Border",new UIRegion(0, 0, 8, 5), new ItemStack(Material.BLACK_STAINED_GLASS_PANE)));
			
			g.addPanel(ipl);
			g.setItem(45, new ItemStack(Material.ARROW));
			g.setItem(53, new ItemStack(Material.ARROW));
			g.open(e.getPlayer());
			
			g.clickListener = new OnClickListener() {
				
				@Override
				public void run(GUI handler, InventoryClickEvent e) {
					// TODO Auto-generated method stub
//					ItemListPanel ipl = ((ItemListPanel)handler.getPanel("ListPanel"));
					if(e.getSlot()==53) {
						ipl.page++;
						ipl.update(g);
						Bukkit.broadcastMessage("next page");
					}
					if(e.getSlot()==45) {
						ipl.page--;
						ipl.update(g);
					}	
				}
			};
			ipl.clickListener = new OnPanelClickListener() {
				
				@Override
				public void run(GUI handler, InventoryClickEvent e, UILoc loc) {
//					ItemListPanel ipl = (ItemListPanel) handler.getPanel("ListPanel");
					if(e.getCurrentItem()==null||e.getCursor()==null) return;
					if(e.getCurrentItem().getItemMeta().getDisplayName().equals("加入列表")) {
						ipl.itemList.add(ipl.itemList.size()-1, e.getCursor().clone());
						ipl.update(g);
						
					}
					
				}
			};
		}
		
	}
	
	public GUI itemListPreview() {
		GUI g = GUILib.createGUI("itemsPreview", 54);
		
		g.clickListener = new OnClickListener() {
			@Override
			public void run(GUI gui, InventoryClickEvent e) {
				// TODO Auto-generated method stub
		
				
			}
		};
		return g;
	}
	
	public void onDisable() {

	}
}
	