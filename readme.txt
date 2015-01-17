================ Snake Forfattere ================
Anna Mølgaard Nielsen (s144437)
Christian Søholm Andersen (s103080)
Mathias Enggrob Boon (s144484)
Van Anh Tri Trinh (s144449)


================ Start af spillet ================
For at starte spillet kan man enten åbne jar-filen, snake.jar, eller åbne driveren, driver.java, inde i snake.control-pakken. 
Når spillet startes bliver man sendt direkte ind i menuen, hvor man kan vælge at spille one-player, two-player, se controls-menuen, eller lukke spillet.


================ Control ================
One-player: styrer slangen med piletasterne.
Two-player: player1 styrer slangen med piletasterne og player2 styrer slangen med A (venstre), D (højre), W (op) og S (ned).


================ Generelle genveje ================
Escape-tast: genvej til menu-siden.
BackSpace-tast: genvej til forrige menuside (virker kun når man er i menuen).
Space-tast eller Enter-tast: genvej til at starte et spil med de indstillinger der er valgt, selvom det kun er default-indstillinger (virker i One-player eller Two-player menuerne).
M-tast: genvej til at mute lyden (kan også opnås ved at trykke på højttaleren oppe i højre hjørne af vinduet).
P-tast: satter spillet på pause (virker kun i et påbegyndt spil).


================ Game Over/Won ================
- One-player: For at vinde spillet skal slangen blive så lang, at den fylder hele banen, så der ikke kan produceres flere æbler pa tomme felter. Man taber hvis hovedet rammer sin egen krop.
- Two-player: Man vinder spillet, hvis modstanderens hovede rammer spillerens krop. Omvendt taber man spillet ved at ramme modstanderen krop med hovedet. Hvis begge spillers hovedet rammer ind i hinanden er spillet uafgjort.


================ Fil manifest ================
- snake.control:
	- Driver.java
	- BoardMultiplayerListener.java
	- BoardSingleplayerListener.java
	- ControlsListener.java
	- HeaderListener.java
	- MenuListener.java
	- OptionsListener.java
	- OptionsMultiplayerListener.java
	- OptionsSingleplayerListener.java
	- ViewFrameListener.java
- snake.model:
	- Board.java
	- Direction.java
	- Event.java
	- Field.java	
	- Food.java
	- Game.java
	- GameMultiplayer.java
	- GameSingleplayer.java
	- Player.java
	- Snake.java
- snake.view:
	- Audio.java
	- BoardBasePanel.java
	- BoardMultiplayerPanel.java
	- BoardSingleplayerPanel.java
	- ControlsPanel.java
	- HeaderMultiplayerPanel.java
	- HeaderSingleplayerPanel.java
	- MenuPanel.java
	- OptionsBasePanel.java
	- OptionsMultiplayerPanel.java
	- OptionsSingleplayerPanel.java
	- ResourceColors.java
	- ResourceImages.java
	- ViewFrame.java
