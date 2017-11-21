package sz.internal.common.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class HandlePicture {
	public static Map streamToByte(MultipartFile file) throws Exception {
		Map map = new HashMap();
		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int width = 0;
		int height = 0;
		byte[] bytes=new byte[2000000];
		try {
			is = file.getInputStream();
			BufferedImage bi = ImageIO.read(is);
			width = bi.getWidth();
			height = bi.getHeight();
			ImageIO.write(bi, "png", baos);
			bytes = baos.toByteArray();
		} finally {
			if (is != null) {
				is.close();
				baos.close();
			}
		}
		map.put("bytes", bytes);
		map.put("width", width);
		map.put("height", height);
		return map;
	}
}
