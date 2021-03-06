package pv.dotai.datai.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pv.dotai.datai.ReplayListener;
import pv.dotai.datai.replay.Entity;

/**
 * Swing frame for the analyzer's example
 * @author Thomas Ibanez
 * @since  1.0
 */
public class ReplayRunner extends JFrame implements ReplayListener {
	private static final long serialVersionUID = 1L;
	private DrawPane contentPane;
	private Collection<Entity> entities;
	private final int WIN_WIDTH = 720;
	private final int WIN_HEIGHT = 720;
	private final int OFFSET = 40;

	public ReplayRunner() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, WIN_WIDTH, WIN_HEIGHT);
		contentPane = new DrawPane();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		setBackground(Color.black);
	}

	@Override
	public void update(int tick, Collection<Entity> entities) {
		this.entities = entities;
		this.setTitle("Tick: " + tick);
		contentPane.repaint();
		try {
			Thread.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int getMapX(Entity e) {
		return (int) ((e.getWorldX() / 32768.0) * WIN_WIDTH * 2.0) - (WIN_WIDTH / 2);
	}

	public int getMapY(Entity e) {
		return (int) ((e.getWorldY() / 32768.0) * WIN_HEIGHT * 2.0) - (WIN_HEIGHT / 2);
	}

	class DrawPane extends JPanel {

		private BufferedImage bg;

		public DrawPane() {
			try {
				this.bg = ImageIO.read(getClass().getResource("minimap.jpg"));
				setBackground(Color.black);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void paint(Graphics g1) {
			
			Graphics2D g = (Graphics2D)g1.create();
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			g.drawImage(bg, OFFSET, OFFSET, WIN_WIDTH - OFFSET * 2, WIN_HEIGHT - OFFSET * 2, null);
			if (entities == null)
				return;
			for (Entity entity : entities) {
				String name = entity.getClassName();
				if (entity.fetchProperty("CBodyComponentBaseAnimatingOverlay.m_cellX") != null) {
					if(name.startsWith("CDOTA_Unit_Hero_")) {
						g.setColor((long)entity.fetchProperty("m_iTeamNum") == 3 ? Color.RED : Color.green);
						if((int)entity.fetchProperty("m_iHealth") > 0) {
        						g.fillArc(getMapX(entity) - 6, getMapY(entity) - 6, 12, 12, 0, 360);
        						g.setColor(color(name));
        						g.fillArc(getMapX(entity) - 5, getMapY(entity) - 5, 10, 10, 0, 360);
						} else {
							g.drawChars(new char[] {'X'}, 0, 1, getMapX(entity), getMapY(entity));
						}
					} else if(name.equals("CDOTA_BaseNPC_Creep_Lane") || name.equals("CDOTA_BaseNPC_Creep_Siege")) {
						g.setColor((long)entity.fetchProperty("m_iTeamNum") == 3 ? Color.RED : Color.green);
						g.fillArc(getMapX(entity) - 3, getMapY(entity) - 3, 6, 6, 0, 360);
					} else if(name.equals("CDOTA_BaseNPC_Creep_Neutral")) {
						g.setColor(Color.cyan);
						g.fillArc(getMapX(entity) - 4, getMapY(entity) - 4, 8, 8, 0, 360);
					} else if(name.equals("CDOTA_BaseNPC_Tower") || name.equals("CDOTA_BaseNPC_Barracks")) {
						g.setColor((long)entity.fetchProperty("m_iTeamNum") == 3 ? Color.RED : Color.green);
						g.fillRect(getMapX(entity) - 4, getMapY(entity) - 4, 8, 8);
					} else if(name.equals("CDOTA_BaseNPC_Fort")) {
						g.setColor((long)entity.fetchProperty("m_iTeamNum") == 3 ? Color.RED : Color.green);
						g.fillPolygon(new int[] {getMapX(entity) - 8, getMapX(entity), getMapX(entity) + 8}, new int[] {getMapY(entity) + 8, getMapY(entity) - 8, getMapY(entity) + 8}, 3);					
					} else if(name.equals("CDOTA_Unit_Roshan")){
						g.setColor(Color.ORANGE);
						g.fillArc(getMapX(entity) - 5, getMapY(entity) - 5, 10, 10, 0, 360);
					}
				}
			}
		}
		
		public Color color(String name) {
			return new Color(name.length() * 10 % 255, Math.abs(name.hashCode()) % 255, name.charAt(name.length()-1) * 20 % 255);
		}
	}
}
