package entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {

    //Instantiate the class para accessible siya sa player
    GamePanel gp;
    KeyHandler keyH;
    
    //indicates where we draw player on the screen
    public final int screenX;
    public final int screenY;
    
    
    
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        
        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);
        setDefaultValues();
        getPlayerImage();
        
        
    }

    public void setDefaultValues() {
    	
    	//player position
        worldX = gp.tileSize * 8;
        worldY = gp.tileSize * 31;
        speed = 2;
        direction = "up";
        
    }
    
    
    public void getPlayerImage() {
       
        try {
            System.out.println("i am trying");
            System.out.println("Resource path: " + getClass().getResourceAsStream("C:/Users/paula/Downloads/cat_-_avatar1-removebg-preview.png"));
            
            
            up = ImageIO.read(getClass().getResourceAsStream("/player/cat-up-and-down.png"));
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
        if (keyH.upPressed == true) {
            direction = "up";
            worldY -= 10;
        } else if (keyH.downPressed == true) {
            direction = "down";
            worldY += 10;
        } else if (keyH.leftPressed == true) {
            direction = "left";
            worldX -= 10;
        } else if (keyH.rightPressed == true) {
            direction = "right";
            worldX += 10;
        }
        
     
     
    }

    public void draw(Graphics2D g2) {
        /*
        g2.setColor(Color.white);
        //PANOO ma-get yung variable from another package???
        //do this: field.variable
        g2.fillRect(x, y, gp.tileSize, gp.tileSize); di na to kailangan sice we are going to use image
         */
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
