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
```

To set component on GUI, use GUI#setIcon(slot, icon) or GUI#addPanel(region, panel)

To set a single Item, you can Icon or StatefulIcon.
```java
  //Icon is a stateless component it cannot update once instantiate.
  gui.setIcon(4, Icon of(Material display, String name, int amount, String... lore));
  //StatefulIcon is a icon with player scope, use this if you want it to be difference with different player.  
  gui.setIcon(UILoc.of(4,0), new StatefulIcon((Player player)->{return getPlayerHead(player);}));
```


GUI gui = GUILib.createGUI(name, size, autoRemove?);

in the GUI you can simply set Item by setItem(), its convinent that we can make it a line to finish setting with (Material display, String name, int amount, String... lore)
or make some event with OnClickListener and OnOpenListener.

the more complex is Panel. It's the compements of GUI.


The GUI is not personal as its designed, so you have to do the things on OpenListener event to make diffrence appaerance on different player.

And the most important things is that autoRemove is false as default, if means than you have to release its memory by manually.

## GUI

the GUI of create, so do GUILib.createGUI() and directly new GUI() is possible due to there's no matter now (maybe in future new GUI() will be private)

Method
* clone(newTitle, newSize, autoRemove)
* create  

#Class UML:
![bLLDR-8m4BtdLupeXIwXshqZohPKMwHM8A2zcsIKMdNio7PeQPV_lcCxX8JGIdi1pyppytndOYpJjin555bk1SAVfza3toodC8HRmOo1AMUPPdaKHOcN2G2V4S3WdfcN5ThHNDuAdhm-S6DHeXv3qXJRo7c1yWXK3ObmmvdSehHAxqXOMSrb1juf4Xt58oj6isaC2oPHZA5mVyRnRnLF](https://user-images.githubusercontent.com/70189787/123243094-45fa6780-d515-11eb-8973-112b2ec369e7.png)
