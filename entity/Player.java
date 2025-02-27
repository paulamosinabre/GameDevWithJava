package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import tile.TileManager;

public class Player extends Entity {

    //Instantiate the class para accessible siya sa player
    GamePanel gp;
    KeyHandler keyH;
    TileManager tileM;
    
    //indicates where we draw player on the screen
    public final int screenX;
    public final int screenY;
    
    private boolean jumping = false;
    private int velocityY = 0, velocityX = 0;
    private final int jumpStrength = -9; // Initial jump force (negative to move up)
    private final int moveSpeed = 3; // Controls horizontal movement speed during jump
    private final int gravity = 1; // Gravity to pull down
    private int jumpTargetY, jumpTargetX; // The target tile position
    
    
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        
        //x, y, width, height of the player's solid area
        solidArea = new Rectangle(10, 20, gp.tileSize - 20, gp.tileSize - 20);
        
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
    	
    	//player position
        worldX = gp.tileSize * 15;
        worldY = gp.tileSize * 51;
        speed = 2;
        direction = "up";
    }
    
    public void getPlayerImage() {
        try {
            System.out.println("i am trying");
            
            up = ImageIO.read(getClass().getResourceAsStream("/player/cat-front-removebg-preview.png"));
            down = ImageIO.read(getClass().getResourceAsStream("/player/cat-up-and-down.png"));
            left = ImageIO.read(getClass().getResourceAsStream("/player/cat-left-removebg-preview.png"));
            right = ImageIO.read(getClass().getResourceAsStream("/player/cat-right-removebg-preview.png"));
            
            if (up == null) {
                System.out.println("file not found");
            } else {
                System.out.println("file found!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void update() {
    	if (jumping) {
    	    worldX += velocityX;
    	    worldY += velocityY;
    	    velocityY += gravity;

    	    // Check collision during jump
    	    collisionOn = false;
    	    gp.colChecker.checkTile(this);
    	    
    	    if (collisionOn) {
                velocityY = 0; // Stop vertical movement
                jumping = false; // Stop jumping if hitting something
            }

    	    if (velocityY > 0 && worldY >= jumpTargetY) {
    	        worldY = jumpTargetY;
    	        worldX = jumpTargetX;
    	        jumping = false;
    	        velocityX = 0;
    	        velocityY = 0;
    	    }
    	}
    	 
    	    // Handle jump input when not already jumping
    	    if (!jumping) {
    	        if (keyH.upPressed && worldY - gp.tileSize >= 0) {
    	            startJump(worldX, worldY - gp.tileSize, 0, jumpStrength, "up");
    	        }
    	        if (keyH.downPressed && worldY + gp.tileSize < gp.worldHeight - gp.tileSize) {
    	            startJump(worldX, worldY + gp.tileSize, 0, jumpStrength, "down");
    	        }
    	        if (keyH.leftPressed && worldX - gp.tileSize >= 0) {
    	            startJump(worldX - gp.tileSize, worldY, -speed, jumpStrength, "left");
    	        }
    	        if (keyH.rightPressed && worldX + gp.tileSize < gp.worldWidth - gp.tileSize) {
    	            startJump(worldX + gp.tileSize, worldY, speed, jumpStrength, "right");
    	        }
    	    }
    }
    
 // Start a jump with smooth left/right movement
    private void startJump(int targetX, int targetY, int horizontalVelocity, int verticalVelocity, String newDirection) {
    
    	if (gp.tileM == null) {
            System.out.println("Error: TileManager is not initialized!");
            return;
        }
    	
    	int tileCol = targetX / gp.tileSize;
        int tileRow = targetY / gp.tileSize;
       
        // Check if the target tile is tile 5 (brick)
        if (gp.tileM.tile[gp.tileM.mapTileNum[tileCol][tileRow]].collision) {
            return; // Prevent jumping onto any solid tile
        }
        
        jumping = true;
        velocityX = horizontalVelocity;
        velocityY = verticalVelocity;
        jumpTargetX = targetX;
        jumpTargetY = targetY;
        direction = newDirection;
    }
    
    public void draw(Graphics2D g2) {
        BufferedImage image = null;
       
        switch(direction){
            case "up":
                image = up;
                break;
            case "down":
            	image = down;
            	break;
            case "left":
            	image = left;
            	break;
            case "right":
            	image = right;
            	break;
        }
        //yung g2 para siya magpakita ng mga graphics
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
