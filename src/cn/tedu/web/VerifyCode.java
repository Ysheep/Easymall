package cn.tedu.web;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

public class VerifyCode {
	//����װ����֤���ı�
	private StringBuffer sb = new StringBuffer();
	private int base = 30;
	private int width = base * 4;
	private int height = base;
	private String codes = "23456789abcdefghjkmnopqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ";
	private static String[] fontNames = { "����", "���Ŀ���", "����", "΢���ź�",  "����_GB2312" };
	
	public void drawImage(OutputStream output){
		//1.����һ��ͼ�񻺳�������
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		//2.�õ����ƻ���
		Graphics2D g2 = (Graphics2D) bi.getGraphics();
		
		//3.��ʼ��ͼ
		//���ñ�����ɫ
		g2.fillRect(0, 0, width, height);
		
		for (int i = 0; i < 4; i++) {
			//��������
			g2.setFont(new Font(fontNames[getRandom(0, fontNames.length)], Font.BOLD, 23));
			
			//���û�����ɫ
			g2.setColor(new Color(getRandom(0,150), getRandom(0,150), getRandom(0,150)));
			
			String code = codes.charAt(this.getRandom(0, codes.length()))+"";
			int theta = getRandom(-45, 45);
			
			g2.rotate(theta * Math.PI / 180, 7+i*base, height-7);
			
			g2.drawString(code, 7+i*base, height-7);
			g2.rotate(-theta * Math.PI / 180, 7+i*base, height-7);
			
			sb.append(code);
		}
		
		for (int i = 0; i < 4; i++) {
			//���û�����ɫ
			g2.setColor(new Color(getRandom(0,150), getRandom(0,150), getRandom(0,150)));
			g2.drawLine(getRandom(0, width), getRandom(0, height), getRandom(0, width), getRandom(0, height));
		}
		g2.setColor(Color.GRAY);
		g2.drawRect(0, 0, width-1, height-1);
		
		//4.��ͼƬ���浽ָ������
		try {
			ImageIO.write(bi, "JPEG", output);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			g2.dispose();
		}
	}
	/**
	 * ��ȡָ����Χ�ڵ������
	 * @param start
	 * @param end
	 * @return
	 */
	private static int getRandom(int start, int end){
		Random random = new Random();
		return random.nextInt(end-start)+start;
	}
	
	/**
	 * ������֤���ı�
	 * @return
	 */
	public String getCode(){
		return sb.toString();
	}
}
