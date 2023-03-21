package dr.dru.gui.example;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dr.dru.gui.GUI;
import dr.dru.gui.GUILib;
import dr.dru.gui.component.icon.Icon;
import dr.dru.gui.component.icon.StatefulIcon;
import dr.dru.gui.component.panel.BorderPanel;
import dr.dru.gui.component.panel.ButtonPanel;
import dr.dru.gui.component.panel.IconListPanel;
import dr.dru.gui.component.panel.IntegerLeverPanel;
import dr.dru.gui.component.panel.TogglePanel;
import dr.dru.gui.component.panel.UIPanel;
import dr.dru.gui.component.position.UILoc;
import dr.dru.gui.component.position.UIRegion;

public class MyStats {
	static GUI myStats;
	static GUI potionList;

	static {
		myStats = new GUI("MyStats", 54,g->{
			g.addPanel(new UIRegion(0, 0, 8,5), new BorderPanel(Icon.of(Material.LIGHT_BLUE_STAINED_GLASS_PANE," ",1)));
			g.setIcon(UILoc.of(4,1), new StatefulIcon(p->getHead(p)));
//			
			g.addPanel(UILoc.of(20), 
					new ButtonPanel(new StatefulIcon(p->
						GUILib.getItem(Material.IRON_CHESTPLATE,"Status: ",1,"Health: "+p.getHealth()+"/"+p.getMaxHealth()
						,"Food: "+p.getFoodLevel()+"/20")),
					e->{
						e.player.setHealth(e.player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
						e.player.setFoodLevel(20);
						e.gui.render(e.player,e.slot);
						}));
//			
			DecimalFormat formatter = new DecimalFormat("0");
			g.addPanel(UILoc.of(22), new ButtonPanel(
					new StatefulIcon(p->GUILib.getItem(Material.EXPERIENCE_BOTTLE,
						"Levels",1,"level: "+p.getLevel(),"exp: "+formatter.format(p.getExpToLevel()*p.getExp())+"/"
					+p.getExpToLevel() ,"click to get 10 exps")), 
					e->{
						e.player.giveExp(10);
						e.gui.render(e.player,e.slot);
						}));
//			
			g.addPanel(UILoc.of(24), 
					new ButtonPanel(new StatefulIcon(p-> {
						List<String> efs = p.getActivePotionEffects().stream().map(eff->eff.toString()+":"+(eff.getDuration()/20)
							+":"+eff.getAmplifier()).collect(Collectors.toList());
						ArrayList<String> lore = new ArrayList<>();
						lore.addAll(efs);
						lore.add("click to add effects");
					return GUILib.getItem(Material.CAULDRON,"Active Effects",1,lore);
					}), e->{
						potionList.open(e.player);
					}));
			
			g.addPanel(UIRegion.single(4,5) ,new ButtonPanel(Icon.of(Material.EMERALD_BLOCK,"Back"), e->{
				Home.open(e.player);
			}));
		});

		potionList = new GUI("Select Potion Type...", 54,g->{
			g.addPanel(new UIRegion(0, 0,8,5), new BorderPanel(Icon.of(Material.PURPLE_STAINED_GLASS_PANE, " ")));
			
			IconListPanel plist = g.addPanel(new UIRegion(1,1,7,4), new IconListPanel(Arrays.stream(
					PotionEffectType.values()).sorted(Comparator.comparing(p->p.toString()))
					.map(e->Icon.of(Material.DRAGON_BREATH, e.getName(),1,
							"§6§lClick to select "+e.getName())).collect(Collectors.toList())));
			
			plist.setClickListener(e->{
				ItemStack clicked = e.inventoryClickEvent.getCurrentItem();
				if(clicked==null||!clicked.getItemMeta().hasDisplayName())
					return;
				PotionEffectType eff = PotionEffectType.getByName(
						ChatColor.stripColor(clicked.getItemMeta().getDisplayName()));
				if(eff!=null)
					effectAddConfirm(e.player, eff);
			});
			
			g.addPanel(UIRegion.single(4,5) ,new ButtonPanel(Icon.of(Material.EMERALD_BLOCK,"Back"), e->{
				open(e.player);
			}));
			
		});
		

		
	}
	public static ItemStack getHead(Player p) {
		ItemStack head = GUILib.getItem(Material.PLAYER_HEAD, ChatColor.AQUA+p.getName(), 1);
		SkullMeta hm = (SkullMeta) head.getItemMeta();
		hm.setOwningPlayer(p);
		head.setItemMeta(hm);
		return head;
	}
	
	public static void open(Player p) {
		myStats.open(p);
	
	}

	
	//may be update to placeholder static in future
	public static void effectAddConfirm(Player p, PotionEffectType type) {
		GUI g = new GUI("Effect add confirm", 27);
		//Border
		g.addPanel(new UIRegion(0,0,8,2), new BorderPanel(Icon.of(Material.PURPLE_STAINED_GLASS_PANE," ")));
		//Background
		g.addPanel(new UIRegion(1,1,7,1), new UIPanel(Icon.of(Material.CYAN_STAINED_GLASS_PANE," ")));

		IntegerLeverPanel lvl = new IntegerLeverPanel(new StatefulIcon(a->GUILib.getItem(Material.GOLDEN_CARROT, 
				"Amplifer level: {0}", 1,"Right click: +1","Left click: -1","Shift click: 5"))
				,0,0,255, 5);
		IntegerLeverPanel time = new IntegerLeverPanel(new StatefulIcon(a->GUILib.getItem(Material.CLOCK, 
				"Duration: {0} secs", 1,"Right click: +1sec","Left click: -1sec","Shift click: 30secs"))
				,60,0,99999, 30);
		TogglePanel hide = new TogglePanel(Icon.of(Material.TRIPWIRE_HOOK,"Hide particles: on",1,"click to turn off"),
				Icon.of(Material.TRIPWIRE_HOOK,"Hide particles: off",1,"click to turn on"));
		
		g.addPanel(UILoc.of(11), lvl);
		g.addPanel(UILoc.of(13), time);
		g.addPanel(UILoc.of(15), hide);
		
		//Confirm button
		g.addPanel(UIRegion.single(3,2) ,new ButtonPanel(Icon.of(Material.EMERALD_BLOCK,"Confirm"), e->{
			e.player.addPotionEffect(new PotionEffect(type, time.getValue()*20, lvl.getValue(),hide.isCheck));
			e.player.sendMessage("Successfully add "+ type +" for "+time.getValue() + "seconds");
			e.gui.dispose();
		}));
		
		g.addPanel(UIRegion.single(5,2) ,new ButtonPanel(Icon.of(Material.REDSTONE_BLOCK,"Back"), e->potionList.open(p)));
		g.open(p);
	}
	
}
