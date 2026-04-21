package cn.sijay.owl.common.utils;

import cn.sijay.owl.auth.entity.Captcha;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * CaptchaUtil
 *
 * @author sijay
 * @since 2026-04-21
 */
public class CaptchaUtil {
    private static final int WIDTH = 120;
    private static final int HEIGHT = 40;
    private static final String CHAR_SET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    /**
     * 生成验证码对象，包含图片 Base64 和明文文本
     */
    public static Captcha generateCaptcha() {
        String code = generateRandomCode();
        BufferedImage image = createImage(code);
        String base64 = encodeToBase64(image);
        return new Captcha(UUIDUtil.simpleUuid(), code, base64);
    }

    private static String generateRandomCode() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(CHAR_SET.charAt(random.nextInt(CHAR_SET.length())));
        }
        return sb.toString();
    }

    private static BufferedImage createImage(String code) {
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();

        // 1. 背景色（浅色）
        g.setColor(new Color(240, 240, 240));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        Random random = new Random();

        // 2. 画干扰线
        g.setColor(Color.GRAY);
        for (int i = 0; i < 8; i++) {
            int x1 = random.nextInt(WIDTH);
            int y1 = random.nextInt(HEIGHT);
            int x2 = random.nextInt(WIDTH);
            int y2 = random.nextInt(HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }

        // 3. 画噪点
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(WIDTH);
            int y = random.nextInt(HEIGHT);
            g.drawRect(x, y, 1, 1);
        }

        // 4. 逐个绘制字符（不同颜色、旋转角度）
        int fontSize = HEIGHT - 10;
        Font font = new Font("Arial", Font.BOLD, fontSize);
        g.setFont(font);

        for (int i = 0; i < code.length(); i++) {
            String ch = String.valueOf(code.charAt(i));
            // 随机颜色（深色）
            g.setColor(new Color(
                random.nextInt(100),
                random.nextInt(100),
                random.nextInt(100)
            ));

            // 计算字符位置，留出边距
            int x = 10 + i * (WIDTH - 20) / code.length();
            int y = HEIGHT - 8;

            // 轻微旋转（-20° 到 +20°）
            double rotation = Math.toRadians(random.nextInt(41) - 20);
            g.rotate(rotation, x, y);
            g.drawString(ch, x, y);
            g.rotate(-rotation, x, y);  // 复位旋转
        }
        g.dispose();
        return image;
    }

    private static String encodeToBase64(BufferedImage image) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, "png", baos);
            byte[] bytes = baos.toByteArray();
            String base64 = EncodeUtil.base64Encode(bytes);
            // 返回可直接嵌入 img 标签的 Data URL
            return "data:image/png;base64," + base64;
        } catch (IOException e) {
            throw new RuntimeException("图片编码失败", e);
        }
    }


}
