package com.juan.adx.common.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.lang3.StringUtils;


public class ImageUtils {

	public static Map<String,Integer> getWidthAndHeight(String url){
		Map<String,Integer> map=new HashMap<String, Integer>();
		InputStream is = null;
		try {
			is = new URL(url).openStream();
			BufferedImage sourceImg = ImageIO.read(is);
			if(sourceImg!=null){
				map.put("width",sourceImg.getWidth());
				map.put("height",sourceImg.getHeight());
				return map;
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	@SuppressWarnings("resource")
	public static Map<String,Integer> getZipImageWidthAndHeight(String path,String fileName){
		Map<String,Integer> map=new HashMap<String, Integer>();
		InputStream is = null;
		try {
			ZipFile zf = new ZipFile(path);
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			ZipInputStream zin = new ZipInputStream(in);
			ZipEntry ze;
			while ((ze = zin.getNextEntry()) != null) {
				if (ze.getName().startsWith(fileName)){
					break;
				}
			}
			is = zf.getInputStream(ze);
			BufferedImage sourceImg = ImageIO.read(is);
			if(sourceImg!=null){
				map.put("width",sourceImg.getWidth());
				map.put("height",sourceImg.getHeight());
				return map;
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	public static void cancelAlpha( File f, int height, int width, boolean bb, boolean wipeOffAlpha ) throws IOException {
		BufferedImage bi = ImageIO.read( f );
		width = bi.getWidth();
		height = bi.getHeight();
		Image itemp = bi.getScaledInstance( width, height, Image.SCALE_SMOOTH );
		if( bb ) {
			BufferedImage image = new BufferedImage( width, height,
					BufferedImage.TYPE_INT_RGB );
			Graphics2D g = image.createGraphics();
			g.setColor( Color.white );
			g.fillRect( 0, 0, width, height );
			if( width == itemp.getWidth( null ) )
				g.drawImage( itemp, 0, ( height - itemp.getHeight( null ) ) / 2,
						itemp.getWidth( null ), itemp.getHeight( null ),
						Color.white, null );
			else
				g.drawImage( itemp, ( width - itemp.getWidth( null ) ) / 2, 0, itemp
						.getWidth( null ), itemp.getHeight( null ), Color.white,
						null );
			g.dispose();
			itemp = image;
		}
		if( !( itemp instanceof BufferedImage ) ) {
			itemp = createBufferedImage( itemp, width, height, wipeOffAlpha );
		}
		String type = "jpg";
		if( !wipeOffAlpha && hasAlpha( itemp ) ) {
			type = "png";
		}
		ImageIO.write( ( BufferedImage )itemp, type, f );
	}

	private static BufferedImage createBufferedImage( Image image, int w, int h, boolean wipeOffAlpha ) {
		int type;
		if( wipeOffAlpha ) {
			type = BufferedImage.TYPE_INT_RGB;
		} else if( hasAlpha( image ) ) {
			type = BufferedImage.TYPE_INT_ARGB;
		}
		else {
			type = BufferedImage.TYPE_INT_RGB;
		}

		BufferedImage bi = new BufferedImage( w, h, type );

		Graphics g = bi.createGraphics();
		g.drawImage( image, 0, 0, w, h, null );
		g.dispose();

		return bi;
	}

	private static boolean hasAlpha( Image image ) {
		try {
			PixelGrabber pg = new PixelGrabber( image, 0, 0, 1, 1, false );
			pg.grabPixels();

			return pg.getColorModel().hasAlpha();
		} catch( InterruptedException e ) {
			return false;
		}
	}

	/*
     * 将图片裁剪成固定比例的图片
     * scale=height/width
     */
	public static void cutCenterImage(InputStream in, File dest, float scale, String extension) throws IOException{
		Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(extension);
		ImageReader reader = (ImageReader)iterator.next();
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		int imageIndex = 0;
		int width = reader.getWidth(imageIndex);
		int height = reader.getHeight(imageIndex);
		int targetWidth = width;
		int targetHeight = height;
		if (scale > 0) {
			targetHeight = (int) (width*scale);
			if (targetHeight > height) {
				targetHeight = height;
				targetWidth = (int) (height/scale);
			}
		}
		Rectangle rect = new Rectangle((width-targetWidth)/2, (height-targetHeight)/2, targetWidth, targetHeight);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0,param);
		ImageIO.write(bi, extension, dest);
	}

	public static boolean isDownload(String fileName) {
		if (!StringUtils.isEmpty(fileName)) {
			int  split = fileName.lastIndexOf(".");
			if (split > 0) {
				String subfix = fileName.substring(split+1);
				return subfix.equalsIgnoreCase("jpg")
						|| subfix.equalsIgnoreCase("png")
						|| subfix.equalsIgnoreCase("jpeg")
						|| subfix.equalsIgnoreCase("bmp")
						|| subfix.equalsIgnoreCase("gif");
			}
		}

		return false;
	}
	
	/**
	 * 从文章内容中获取图片信息
	 * @param content
	 * @return
	 */
	public static Set<String> getImgsFromText( String content ) {
		Set<String> imgs = new HashSet<String>();
		Pattern PATTERN = Pattern.compile( ".*?(<img.*?src=\"(.*?)\").*?" );
		Matcher matcher = PATTERN.matcher( content );
		while( matcher.find() ) {
			System.out.println( matcher.group( 2 ) );
			imgs.add( matcher.group( 2 ) );
		}

		return imgs;
	}

	public static void main( String[] args ) throws IOException {
		Map<String, Integer> zipImageWidthAndHeight = getZipImageWidthAndHeight("d:\\Res.zip", "pre_splash");
		System.out.println(zipImageWidthAndHeight);
	}
}
