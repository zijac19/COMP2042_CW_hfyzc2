package handlers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageLoader {
	private BufferedImage img;
	public static String ingamebackground = "/resources/Image/ingame.jpg";

	
	public ImageLoader(String path) {
		try {
			img = ImageIO.read(ImageLoader.class.getResourceAsStream(path));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public BufferedImage getImage() {
		return img;
	}
	public BufferedImage getSubImage(int section) {
		return img.getSubimage(0, section*25, 50, 25);
	}
}
