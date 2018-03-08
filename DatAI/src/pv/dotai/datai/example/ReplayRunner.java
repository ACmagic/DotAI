package pv.dotai.datai.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pv.dotai.datai.ReplayListener;
import pv.dotai.datai.replay.Entity;

public class ReplayRunner extends JFrame implements ReplayListener {
	private static final long serialVersionUID = 1L;
	private DrawPane contentPane;
	private Collection<Entity> entities;
	private final int WIDTH = 720;
	private final int HEIGHT = 720;

	public ReplayRunner() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, WIDTH, HEIGHT);
		contentPane = new DrawPane();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	@Override
	public void update(int tick, Collection<Entity> entities) {
		this.entities = entities;
		this.setTitle("Tick: " + tick);
		contentPane.repaint();
		try {
			Thread.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getMapX(Entity e) {
		return (int) ((e.getWorldX() - 16384) / 15000 * 720);
	}

	public int getMapY(Entity e) {
		return (int) ((e.getWorldY() - 16384) / 15000 * 720);
	}

	class DrawPane extends JPanel {

		private BufferedImage bg;

		public DrawPane() {
			try {
				this.bg = ImageIO.read(getClass().getResource("minimap.jpg"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void paint(Graphics g) {
			g.drawImage(bg, 0, 0, 720, 720, null);
			if (entities == null)
				return;
			for (Entity entity : entities) {
				String name = entity.getClassName();
				if (entity.fetchProperty("CBodyComponentBaseAnimatingOverlay.m_cellX") != null) {
					g.setColor(Color.red);
					g.fillArc(getMapX(entity), getMapY(entity), 10, 10, 0, 360);
				}
			}
		}
	}

}
