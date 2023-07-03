import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) throws IOException {
        photoFrameDesign();
    }

    private static void photoFrameDesign() throws IOException {
        String sourceFolderPath = "input";
        String destinationFolderPath = "output";
        File sourceFolder = new File(sourceFolderPath);
        File[] imageFiles = sourceFolder.listFiles((dir, name) -> isSupportedImageFormat(name.toLowerCase()));
        if (imageFiles != null) {
            for (File imageFile : imageFiles) {
                BufferedImage originalImage = ImageIO.read(imageFile);
                BufferedImage processedImage = processImage(originalImage);
                String destinationFilePath = destinationFolderPath + File.separator + imageFile.getName();
                File destinationFile = new File(destinationFilePath);
                ImageIO.write(processedImage, getFileExtension(String.valueOf(imageFile)), destinationFile);
            }
        }
    }

    private static boolean isSupportedImageFormat(String fileName) {
        List<String> supportedFormats = Arrays.asList("jpg", "png", "jpeg");
        String extension = getFileExtension(fileName);
        return supportedFormats.contains(extension);
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    private static BufferedImage processImage(BufferedImage originalImage) throws IOException {
        // 实现对照片的处理逻辑，可以添加边框、调整大小等
        // 返回处理后的照片
        int borderWidth = 200; // 边框大小
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        int newWidth = originalWidth + 2 * borderWidth;
        int newHeight = originalHeight + 2 * borderWidth;
        int squareWidth = Math.max(newWidth, newHeight);
        BufferedImage borderedImage = new BufferedImage(squareWidth, squareWidth, originalImage.getType());

        Graphics2D g2d = borderedImage.createGraphics();
        // 设置边框颜色为白色
        g2d.setColor(Color.WHITE);

        g2d.fillRect(0, 0, squareWidth, squareWidth);

        // 绘制原始图片&保持居中
        g2d.drawImage(originalImage, squareWidth == newWidth ? borderWidth : (squareWidth - originalWidth) / 2, squareWidth == newWidth ? (squareWidth - originalHeight) / 2 : borderWidth, null);

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.PLAIN, 100));

        // 绘制文本
        String text = "";
        int textX = borderWidth; // 文本的 x 坐标
        int textY = squareWidth - borderWidth - 10; // 文本的 y 坐标（留出一些空间）

        g2d.drawString(text, textX, textY);

        g2d.dispose();
        return borderedImage;
    }
}