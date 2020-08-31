package com.gcstudios.entities;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.World;


public class Player extends Entity{
	
	public boolean right, left;
	
	public int dir = 1;
	private double gravity = 2;
	
	//sistema de pulo
	public boolean jump = false;
	
	public boolean isJumping = false;
	public int jumpHeight = 42; //altura do pulo
	public int jumpFrames = 0;
	
	//Animação dos spritesheets array
	private int framesAnimation = 0;
	private int maxFrames = 15;
	private int maxSprite = 2; //números de spritesheet 
	private int curSprite = 0;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
	}
	
	public void tick(){
		depth = 2;
		//gravidade, Colisão com os tiles| Sist.Pulo isJumping gravidade não interferir no pulo
		if(World.isFree((int)x,(int)(y+gravity)) && isJumping == false) {
			y+=gravity;
		}
		if(right && World.isFree((int)(x+speed), (int)y)) {
			x+=gravity;
			dir = 1;
		}
		else if(left && World.isFree((int)(x-speed), (int)y)) {
			x-=gravity;
			dir = -1;
		}
		
		//Sistema de pulo
		if(jump) {
			if(!World.isFree(this.getX(), this.getY()+1)) {
				isJumping = true;
			}else {
				jump = false;
			}
		}
		
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY()-2)) {
				y-=2;
				jumpFrames+=2;
				if(jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			}else {
				//Resetando os valores
				isJumping = false;
				jump=false;
				jumpFrames = 0;
			}
		}
	}
	
	//Renderizando Animação do personagem indo pros dois lados
	
	//Renderização dos spritesheets array []
	public void render(Graphics g) {
		framesAnimation++;
		if(framesAnimation == maxFrames) {
			curSprite++;
			framesAnimation = 0;
			if(curSprite == maxSprite) {
				curSprite = 0;
			}
		}
		
		if(dir == 1) {
			sprite = Entity.PLAYER_SPRITE_RIGHT[curSprite];
		}else if(dir == -1) {
			sprite = Entity.PLAYER_SPRITE_LEFT[curSprite];
		}
		super.render(g);
	}
	
}
