package textures;

import utils.GrhData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TexturesGenerator {

    public static final String EXT_INPUT = "bmp";
    public static final String EXT_OUTPUT = "png";
    public static final String DIR_INPUT = "input/Graficos/";
    public static final String DIR_OUTPUT = "output/Graficos/";

    private int collectedIncrementor = 0;
    private int currentTexture;
    private ArrayList<ArrayList<BufferedImage>> bufferedMatrix;

    public TexturesGenerator() {
        resetMatrix();
    }

    private void resetMatrix() {
        bufferedMatrix = new ArrayList<>();
        bufferedMatrix.add(new ArrayList<>());
    }

    private void addRow() {
        bufferedMatrix.add(new ArrayList<>());
    }

    public void generateTexture(int grhNum, GrhData grhData) {
        generateTexture(grhNum, grhData, 0, 0);
    }

    public void generateTexture(int grhNum, GrhData grhData, int width, int height) {
        if (width == 0) width = (int) (grhData.getRect().getWidth());
        if (height == 0) height = (int) (grhData.getRect().getHeight());

        var originalWidth = (int) (grhData.getRect().getWidth());
        var originalHeight = (int) (grhData.getRect().getHeight());


        var startX = (width - originalWidth) / 2;
        var startY = (height - originalHeight) / 2;

        File file = new File(DIR_INPUT + grhData.getFileNum() + "." + EXT_INPUT);
        if (!file.exists()) {
            System.out.println("El grafico " + grhData.getFileNum() + " no existe. ");
            return;
        }

        try {
            BufferedImage img = ImageIO.read(new File(DIR_INPUT + grhData.getFileNum() + "." + EXT_INPUT));

            BufferedImage img2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < img2.getWidth(); x++) {
                for (int y = 0; y < img2.getHeight(); y++) {
                    img2.setRGB(x, y, -16777216);
                }
            }
            for (int x = 0; x < grhData.getRect().getWidth(); x++) {
                for (int y = 0; y < grhData.getRect().getHeight(); y++) {
                    if (grhData.getRect().getX1() + x < img.getWidth() && grhData.getRect().getY1() + y < img.getHeight()) {
                        var rgb = img.getRGB((int) (grhData.getRect().getX1()) + x, (int) (grhData.getRect().getY1()) + y);
                        img2.setRGB(startX + x, startY + y, rgb);
                    }
                }
            }

            if (currentTexture == 0) {
                currentTexture = grhData.getFileNum();
                bufferedMatrix.get(bufferedMatrix.size() - 1).add(img2);
            } else if (grhData.getFileNum() != currentTexture) {
                saveCollectedTextures();
                resetMatrix();
                currentTexture = 0;
            } else {
                bufferedMatrix.get(bufferedMatrix.size() - 1).add(img2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveCollectedTextures() throws IOException {
        collectedIncrementor += 1;

        var widths = bufferedMatrix.stream().map(row -> {
            int width = 0;
            for (var column : row) {
                width += column.getWidth();
            }
            return width;
        }).collect(Collectors.toList());
        var totalWidth = widths.stream().mapToInt(v -> v).max().getAsInt();


        var columnas = bufferedMatrix.get(0).size();
        var heights = new ArrayList<Integer>();
        for (int i = 0; i < columnas; i++) {
            var height = 0;
            for (int j = 0; j < bufferedMatrix.size(); j++) {
                height += bufferedMatrix.get(j).get(i).getHeight();
            }
            heights.add(height);
        }
        var totalHeight = heights.stream().mapToInt(v -> v).max().getAsInt();

        BufferedImage img = new BufferedImage(totalWidth, totalHeight, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                img.setRGB(i, j, -16777216);
            }
        }

        for (int i = 0; i < bufferedMatrix.size(); i++) {
            for (int j = 0; j < bufferedMatrix.get(i).size(); j++) {
                var elementWidth = Math.round((float)totalWidth / bufferedMatrix.get(i).size());
                var elementHeight = Math.round((float)totalHeight / bufferedMatrix.size());

                addTexToBigImage(bufferedMatrix.get(i).get(j), img, elementWidth, elementHeight, j, i);
            }
        }

        ImageIO.write(img, EXT_OUTPUT, new File(DIR_OUTPUT + collectedIncrementor + "." + EXT_OUTPUT));
    }

    private void addTexToBigImage(BufferedImage tex, BufferedImage img, int width, int height, int numX, int numY) {

        var startX = (width - tex.getWidth()) / 2;
        var startY = (height - tex.getHeight()) / 2;
        if (startX < 0) startX = 0;
        if (startY < 0) startY = 0;

        for (int x = 0; x < tex.getWidth(); x++) {
            for (int y = 0; y < tex.getHeight(); y++) {
                var x1 = numX * tex.getWidth();
                var y1 = numY * tex.getHeight();
                //try {
                //System.out.println(x1 + x + startX);
                //System.out.println(y1 + y + startY);
                if (x1 + x < img.getWidth() && y1 + y < img.getHeight()) {
                    var rgb = tex.getRGB(x, y);
                    img.setRGB(startX + x1 + x, startY + y1 + y, rgb);
                }
                //}
                //catch (Exception e) {

                //}

            }
        }
    }

}
