package dr.gui.org;

//injector version 0.2, last update on 2021/6/5
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class GUILib implements Listener{
	
	private static HashMap<Inventory, GUI> activeGUI = new HashMap<>();
	
	public static GUI createGUI(String title, int size) {
		return new GUI(title, size);
	}
	
	@EventHandler
	public static void onInventoryItemMove(InventoryMoveItemEvent e) {
		if(!activeGUI.containsKey(e.getDestination())) return;
		if(!e.getDestination().equals(e.getSource())) e.setCancelled(true);
	}
	
	@EventHandler
	public static void onInventory(InventoryDragEvent e) {
		if(!activeGUI.containsKey(e.getInventory())||e.getInventory().equals(e.getWhoClicked().getInventory())) return;
		e.setCancelled(true);
	}
	@EventHandler
	private static void onInventoryClick(InventoryClickEvent e) {
		GUI g = activeGUI.get(e.getInventory());
		if(g==null) return;
		Bukkit.broadcastMessage(e.getAction().toString());
		if(!(e.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)||e.getAction().name().contains("PICKUP")||e.getAction().name().contains("PLACE"))||
				e.getClickedInventory().equals(e.getInventory()))
			e.setCancelled(true);
		//panel event first
		if(g.clickListener!=null)
		g.clickListener.run(g, e);
		g.panels.values().forEach(p->
		{
			UILoc slot = new UILoc(e.getSlot());
			if(p.clickListener!=null&&p.loc.isWithin(slot))
				p.clickListener.run(g, e, slot.substract(p.loc.firstL));
		});
	}
	
//	@EventHandler
//	public void onInventoryClose(InventoryCloseEvent e) {
//		GUI g = activeGUI.get(e.getInventory());
//		if(g==null) return;
//		g.close();
//		//finalize it
//	}

	public static class UILoc {
		int x, y;
		
		public UILoc(int x,int y) {
			this.x = x;
			this.y = y;
		}
		
		public UILoc(int slot) {
			x = slot%9;
			y = (slot-x)/9;
		}
		
		public int toInvLoc() {
			return x+y*9;
		}
		
		public UILoc add(UILoc l) {
			x += l.x;
			y += l.y;
			return this;
		}
		public UILoc substract(UILoc l) {
			x -= l.x;
			y -= l.y;
			return this;
		}
		
		public static Iterator<UILoc> getLocs(UILoc loc, UILoc offest) {
			ArrayList<UILoc> list = new ArrayList<>();
			for(int y = 0; y < offest.y; y++)
				for(int x = 0; x < offest.x; x++) {
					list.add(new UILoc(loc.x + x,loc.y + y));
				}
			return list.iterator();
		}
		
		@Override
		public boolean equals(Object obj) {
			if(!(obj instanceof UILoc)) return false;
			UILoc l = (UILoc) obj;
			return (x==l.x && y==l.y);
		}
		
		@Override
		public int hashCode() {
			return x*10+y;
		}
	}
	
	public static class UIRegion {
		public UILoc firstL, secondL;
		
		public UIRegion(UILoc first, UILoc second) {
			firstL = first;
			secondL = second;
		}
		
		public UIRegion(int minX,int minY,int maxX,int maxY) {
			firstL = new UILoc(Math.min(minX, maxX), Math.min(minY, maxY));
			secondL = new UILoc(Math.max(minX,maxX),Math.max(minY, maxY));
		}
		
		public int getSize() {
			return Math.abs(secondL.x - firstL.x+1)*Math.abs(secondL.y - firstL.y+1);
		}
		
		public Iterator<UILoc> getLocs() {
			ArrayList<UILoc> list = new ArrayList<>();
			for(int y = firstL.y; y <= secondL.y; y++)
				for(int x = firstL.x; x <= secondL.x; x++) {
					list.add(new UILoc(x, y));
				}
			return list.iterator();
		}
		
		public boolean isWithin(UILoc loc) {
			if(loc.x>=firstL.x && loc.x<=secondL.x && loc.y >= firstL.y && loc.y <= secondL.y) 
				return true;
			return false;
		}
	}
	
	public static class GUI {
		private Inventory inv;
		HashMap<String, Panel> panels = new HashMap<>();
		public OnClickListener clickListener;
		
		public GUI(String title, int size) {
			inv = Bukkit.createInventory(null, size, title);
			GUILib.activeGUI.put(inv, this);
		}
		
		public void setItem(int slot, ItemStack item) {
			inv.setItem(slot, item);
		}
		public void open(Player player) {
			player.openInventory(inv);
		}
		public List<HumanEntity> getViewers() {
			return inv.getViewers();
		}
		
		public void close() {
			activeGUI.remove(inv);
			inv.clear();
			inv =null;
		}
		
		public void update() {
			panels.values().forEach(p->p.update(this));
		}
		
		public void addPanel(Panel panel) {
			panels.put(panel.id, panel);
			panel.update(this);
		}
		
		public Panel getPanel(String id) {
			return panels.get(id);
		}
	}
	
	public static abstract class OnClickListener {
		public OnClickListener() {
			
		}
		public abstract void run(GUI handler, InventoryClickEvent e);
	}
	
	public static abstract class OnPanelClickListener {
		public OnPanelClickListener() {
			
		}
		public abstract void run(GUI handler, InventoryClickEvent e, UILoc loc);
	}
	
//	public static abstract class OnItemListPanelClickListener {
//		public OnItemListPanelClickListener() {
//			
//		}
//		
//		public abstract void event(GUI handler, InventoryClickEvent e, UILoc loc);
//	}
	
	
	
	public static class Panel {
		private final String id;
		public UIRegion loc;
		public ItemStack backgroundMat;
		public OnPanelClickListener clickListener;
		public Panel(String id, UIRegion loc) {
			this.id = id;
			this.loc = loc;
		}
		
		public Panel(String id, UIRegion loc, ItemStack bdmat) {
			this.id = id;
			this.loc = loc;
			backgroundMat = bdmat;
		}
		
		public String getId() {
			return id;
		}
		public void update(GUI gui) {
			Iterator<UILoc> ul = loc.getLocs();
			while(ul.hasNext()) {
				UILoc loc = ul.next();
				gui.inv.setItem(loc.toInvLoc(), backgroundMat);
			}
		}
		//public onClickListener clickListener;
	}
	public static class BorderPanel extends Panel {

		public BorderPanel(String id, UIRegion loc, ItemStack borderMat) {
			super(id, loc, borderMat);
		}
		@Override
		public void update(GUI gui) {
			for(int x=loc.firstL.x;x<=loc.secondL.x;x++) {
				gui.setItem(new UILoc(x,loc.firstL.y).toInvLoc(), backgroundMat);
				gui.setItem(new UILoc(x,loc.secondL.y).toInvLoc(), backgroundMat);
			}
			for(int y = loc.firstL.y+1; y<loc.secondL.y; y++) {
				gui.setItem(new UILoc(loc.firstL.x,y).toInvLoc(), backgroundMat);
				gui.setItem(new UILoc(loc.secondL.x,y).toInvLoc(), backgroundMat);
			}
			super.update(gui);
		}
		
	}
	
	public static class ItemListPanel extends Panel {
		public ArrayList<ItemStack> itemList = new ArrayList<>();
		public int page = 1;
		public ItemListPanel(String id, UIRegion loc) {
			super(id, loc);
		}
		
		@Override
		public void update(GUI gui) { 
			int maxSize = loc.getSize(), maxPage = (int) Math.ceil((float)itemList.size()/(float)maxSize);;
			if(page==0) page = maxPage;
			else if(page>maxPage) page = 1;
			
			int index =maxSize*(page-1);
		
			Iterator<UILoc> ul = loc.getLocs();
			while(ul.hasNext()) {
				UILoc loc = ul.next();
				if(itemList.size()>index&&itemList.get(index)!=null) {
					gui.inv.setItem(loc.toInvLoc(), itemList.get(index));
				}
					
				else if(backgroundMat!= null) gui.inv.setItem(loc.toInvLoc(), backgroundMat);
				index++;
			}
		}
		
	}
}
