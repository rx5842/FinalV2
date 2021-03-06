import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;



import java.awt.*;

public class Main extends JFrame {

	private JPanel cardPanel;

	//private GamePanel gamePanel;
	private SettingsPanel settings;
	//private Game game;
	private int resetCounter;
	//private MenuPanel menu;
	//private InstructionPanel instructions;
	private CardLayout cl;
	//test

	/**
	 * Plays the sound
	 */
	public static synchronized void playSound() {

		new Thread(new Runnable() {
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("TronMusic.wav"));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();

	}


	/**
	 * Creates a new Main object that runs the program.
	 * @param title The title of the window
	 */
	public Main(String title) {

		super(title);
		/*songs1 = new String[]{"menu.mp3"};
		songs2 = new String[]{"game.mp3"};
		songs3 = new String[]{"winner.mp3"};*/
		setBounds(100, 100, 800, 800);
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		resetCounter = 0;
		cardPanel = new JPanel();
		cl = new CardLayout();
		cardPanel.setLayout(cl);

		MenuPanel menu = new MenuPanel(this);
		InstructionPanel instructions = new InstructionPanel(this);

		Game game = new Game();
		GamePanel gamePanel = new GamePanel(this, game);
		settings = new SettingsPanel(this, game);

		addKeyListener(gamePanel.getKeyHandler());

		cardPanel.add(menu,"menu");
		cardPanel.add(instructions,"instructions");
		cardPanel.add(settings,"settings" + resetCounter);
		cardPanel.add(gamePanel,"game" + resetCounter);

		/*   sound1 = new JayLayer("audio/","audio/",false);
		sound1.addPlayList();
		sound1.addSongs(0,songs1);
		//sound.addSoundEffects(soundEffects);
		sound1.changePlayList(0);
		sound1.addJayLayerListener(this);
		sound1.nextSong();

		sound2 = new JayLayer("audio/","audio/",false);
		sound2.addPlayList();
		sound2.addSongs(0,songs2);
		//sound.addSoundEffects(soundEffects);
		sound2.changePlayList(0);
		sound2.addJayLayerListener(this);
		//sound1.nextSong();

		sound3 = new JayLayer("audio/","audio/",false);
		sound3.addPlayList();
		sound3.addSongs(0,songs3);
		//sound.addSoundEffects(soundEffects);
		sound3.changePlayList(0);
		sound3.addJayLayerListener(this);*/


		add(cardPanel);

		playSound();



		setVisible(true);
	}
	/**
	 * Another constructor that doesn't call the playSound method
	 * @param title
	 * @param i a placeholder
	 */
	public Main(String title,int i) {

		super(title);
		/*songs1 = new String[]{"menu.mp3"};
		songs2 = new String[]{"game.mp3"};
		songs3 = new String[]{"winner.mp3"};*/
		setBounds(100, 100, 800, 800);
		setResizable(false);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		resetCounter = 0;
		cardPanel = new JPanel();
		cl = new CardLayout();
		cardPanel.setLayout(cl);

		MenuPanel menu = new MenuPanel(this);
		InstructionPanel instructions = new InstructionPanel(this);

		Game game = new Game();
		GamePanel gamePanel = new GamePanel(this, game);
		settings = new SettingsPanel(this, game);

		addKeyListener(gamePanel.getKeyHandler());

		cardPanel.add(menu,"menu");
		cardPanel.add(instructions,"instructions");
		cardPanel.add(settings,"settings" + resetCounter);
		cardPanel.add(gamePanel,"game" + resetCounter);

		/*   sound1 = new JayLayer("audio/","audio/",false);
		sound1.addPlayList();
		sound1.addSongs(0,songs1);
		//sound.addSoundEffects(soundEffects);
		sound1.changePlayList(0);
		sound1.addJayLayerListener(this);
		sound1.nextSong();

		sound2 = new JayLayer("audio/","audio/",false);
		sound2.addPlayList();
		sound2.addSongs(0,songs2);
		//sound.addSoundEffects(soundEffects);
		sound2.changePlayList(0);
		sound2.addJayLayerListener(this);
		//sound1.nextSong();

		sound3 = new JayLayer("audio/","audio/",false);
		sound3.addPlayList();
		sound3.addSongs(0,songs3);
		//sound.addSoundEffects(soundEffects);
		sound3.changePlayList(0);
		sound3.addJayLayerListener(this);*/


		add(cardPanel);



		setVisible(true);
	}



	/**
	 * Starts the program by creating a new main object.
	 * @param args The standard parameter for the main method.
	 */
	public static void main(String[] args)
	{
		Main w = new Main("Connect 4.0");
	}

	/**
	 * Switches the current panel that is showing on the screen.
	 * @param name The name of the panel that should be switched to.
	 */
	public void changePanel(String name) {
		((CardLayout)cardPanel.getLayout()).show(cardPanel,name);
		requestFocus();
	}

	/**
	 * Switches the music to the next song when the players begin the game.
	 */
	public void switchSong() {
		/*sound1.stopSong();
		sound2.nextSong();*/
	}

	/**
	 * Plays the victory music when a player wins the game.
	 */
	public void winnerSong() {
		/*sound2.stopSong();
		sound3.nextSong();*/
	}

	/**
	 * Inherited abstract method from JayLayerListener.
	 */
	/*public void musicStarted() {
		// TODO Auto-generated method stub

	}

	@Override
	 *//**
	 * Inherited abstract method from JayLayerListener.
	 *//*
	public void musicStopped() {
		// TODO Auto-generated method stub

	}

	@Override
	  *//**
	  * Inherited abstract method from JayLayerListener.
	  *//*
	public void playlistEnded() {
		// TODO Auto-generated method stub

	}*/


	/**
	 * Inherited abstract method from JayLayerListener.
	 */
	public void songEnded() {
		// TODO Auto-generated method stub

	}


	/**
	 * Returns the value of the resetCounter
	 * @return the value of the resetCounter
	 */
	public int getResetCounter() {
		return resetCounter;
	}



}