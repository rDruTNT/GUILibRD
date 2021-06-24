# GUILibRD
an light weight API.

# Usage

first thing you have to get a GUI by this:
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
