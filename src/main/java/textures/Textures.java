package textures;

import utils.Grhs;

/**
 * Programa que vuelve a rearmar las texturas de un AO en base a sus indexs, pero ya sin depender tanto de éstos
 */
public class Textures {

    public static void main(String[] args) {
        System.out.println("Loading...");
        var grhs = new Grhs();
        System.out.println("Done");


        var tg = new TexturesGenerator();
        var grhsData = grhs.getGrhsData();
        for (int i = 0; i < grhsData.length; i++) {
            /*if (grhsData[i] != null && grhsData[i].getCantFrames() > 1) {
                var maxWidth = grhsData[i].getFrames().stream().mapToInt(v -> {
                    if (grhsData[v -1] != null) {
                        return (int)(grhsData[v - 1].getRect().getWidth());
                    }
                    return 0;
                }).max().getAsInt();
                var maxHeight = grhsData[i].getFrames().stream().mapToInt(v -> {
                    if (grhsData[v - 1] != null) {
                        return (int)(grhsData[v - 1].getRect().getHeight());
                    }
                    return 0;
                }).max().getAsInt();
                grhsData[i].getFrames().stream().forEach(f -> {
                    if (grhsData[f - 1] != null) {
                        tg.generateTexture(f - 1, grhsData[f - 1], maxWidth, maxHeight);

                        grhsData[f - 1].setProcessed(true);
                    }
                });
            }*/

            if (grhsData[i] != null && grhsData[i].getCantFrames() == 1) {
                //System.out.println(i);
                tg.generateTexture(i, grhsData[i]);

            }

        }

    }
}
