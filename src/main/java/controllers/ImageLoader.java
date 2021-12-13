package controllers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class load the image from the resources folder
 *
 * Refactor by
 * @author Chang Zi Jac
 */
public class ImageLoader {
	//initialize the variables and set up the image directory
	private BufferedImage img;
	public static String ingamebackground = "/resources/Image/ingame.jpg";

	/**
	 *This method load the image from the directory given by above
	 *
	 * @param path
	 */
	public ImageLoader(String path) {
		try {
			img = ImageIO.read(ImageLoader.class.getResourceAsStream(path));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method get and return the image
	 */
	public BufferedImage getImage() {
		return img;
	}

	/**
	 * This method get and return the sub image from the image
	 * @param section
	 * @return img.getSubimage
	 */
	public BufferedImage getSubImage(int section) {
		return img.getSubimage(0, section*25, 50, 25);
	}
}
