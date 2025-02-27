package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public int worldX, worldY;
    public int speed;
    
    //Use BufferedImage to access the image. It stores image files 
    public BufferedImage up, down, left, right;
    public String direction;
    public String obstaclesImg;
    public Rectangle solidArea;
    public boolean collisionOn = false;
}
