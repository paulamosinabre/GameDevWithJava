package main;

import java.awt.Dimension;
import entity.Player;
import entity.Entity;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import main.KeyHandler;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable {
	//screen settings
    final int originalTileSize = 13; //32x32 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 12;
    public final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol; //768
    public final int screenHeight = tileSize * maxScreenRow; //576

    //world settings
    public final int maxWorldCol = 28;
    public final int maxWorldRow = 60;
    public final int worldWidth = tileSize * maxWorldCol; //
    public final int worldHeight = tileSize * maxWorldRow;
    

    
    //FPS
    int FPS = 60;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread; //para tuloy tuloy siyang mag-run
    public Player player = new Player(this, keyH);
    public TileManager tileM = new TileManager(this);
    public CollisionChecker colChecker = new CollisionChecker(this);
    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.requestFocusInWindow();
        startGameThread();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {

        double drawInterval = 1000000000 / FPS; // Time per frame in nanoseconds
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;
            
            if(delta >= 1) {
            	update();
            	repaint();
            	delta--;
            }
            
            if(timer >= 1000000000) {
            	
            	drawCount = 0;
            	timer = 0;
            }
        }

    }
    
    public void update() {
        player.update();
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        //tile first kasi kung player mauna, mahahide siya ng bg
        tileM.draw(g2);
        player.draw(g2);
        
        g2.dispose();
        
    }
}
