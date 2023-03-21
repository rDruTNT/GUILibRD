# GUILibRD
an light weight API.

# Installation
Add following content to your pom.xml:
```
      <repositories>  
        <repository>
          <id>rDruTNT-DruRespositories</id>
          <url>https://packagecloud.io/rDruTNT/DruRespositories/maven2</url>
          <releases>
            <enabled>true</enabled>
          </releases>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      <repositories>
      
<dependency>
  <groupId>GUILibRD</groupId>
  <artifactId>guilibrd</artifactId>
  <version>2.0-SNAPSHOT</version>
</dependency>
```
# Example
[![GUILib 範例影片]](https://youtu.be/6A59R68TPNc)
效果如下：
# Usage

First thing first you have to register your plugin by GUILib.Register(JavaPlugin)
```java
  @Override
  public void onEnable() {
    GUILib.register(this);
  }
```

To create a GUI use new a GUI with title, size and <init>
```java
  new GUI("A GUI Title", 54);
  new GUI("A GUI Title", (ActiveGUI gui)->{
    //init your variable like icon or panel to point to address correctly 
    //if the GUI is reference to a static var, 
    //init your component here or the component will not be reset on open.
    });
      
 // if you wish to play sound on click use
 gui.setGlobalClickListener((PanelClickEvent event)->{event.player.playSound(sound);});
```

To set component on GUI, use GUI#setIcon(slot, icon) or GUI#addPanel(region, panel)

To set a single Item, you can Icon or StatefulIcon and assignation the slot either by 0~53 or UILoc(x,y), the origin starts from left top to right bottom.
```java
  //Icon is a stateless component it cannot update once instantiate.
  gui.setIcon(4, Icon of(Material display, String name, int amount, String... lore));
  //StatefulIcon is a icon with player scope, use this if you want it to be difference with different player.  
  gui.setIcon(UILoc.of(4,0), new StatefulIcon((Player player)->{return getPlayerHead(player);}));
```

To set a group of Items, you can use UIPanel(Icon).
Use UIRegion(x1, y1, x2, y2) or UIRegion(UILoc, UILoc)  to set the region.
```java
  gui.addPanel(new UIRegion.single(2,2),new UIPanel(Icon.of(itemstack)));
      
  //if you wish to do some custom event, use
  panel.setClickListener((PanelClickEvent event)->event.player.sendMessage("you clicked this panel"));
```   
If you want some other event, theres:
      - PanelClickEvent
      - ShowcaseChangeEvent
      - GUICloseEvent
      - GUIDisposeEvent
      
Extension of UIPanel can help you do some trick easily like:
      - BorderPanel
      - ButtonPanel
      - IconListPanel
      - IntegerLeverPanel
      - ProgressBarPanel
      - ShowcasePanel
      - TogglePanel
      - ValuePanel  

In the end you will need to open the GUI to player 
Simply use GUI#open(Player) to open a gui to this player and get its instantiated acitve gui
```java
      ActiveGUI actives = GUI.open(player);
```
      
Please contact me if you want to know how the details work.
