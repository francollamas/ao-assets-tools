package utils;

import java.util.ArrayList;

/**
 * Contiene la información de un solo GrhData
 *
 * fileNum: número de textura
 * rect: contiene las coordenadas de los vértices del GrhData en la textura
 * frames: colección de índices de GrhData correspondientes a una animación
 * speed: velocidad de la animación
 * tr: porcion de textura que lo representa (solo para Grhs no animados)
 */

public class GrhData {
    private int fileNum;
    private Rect rect;
    private boolean processed;

    public ArrayList<Short> getFrames() {
        return frames;
    }

    private ArrayList<Short> frames;
    private float speed;

    public GrhData() {
        frames = new ArrayList<>();
        rect = new Rect();
    }

    public int getFileNum() {
        return fileNum;
    }

    public void setFileNum(int fileNum) {
        if (fileNum >= 0)
            this.fileNum = fileNum;
    }

    public Rect getRect() {
        return rect;
    }

    public Short getFrame(short index) {
        if (index < frames.size())
            return frames.get(index);
        return -1;
    }

    public int getCantFrames() {
        return frames.size();
    }

    public void addFrame(short num) {
        if (num >= 0)
            frames.add(num);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        if (speed >= 0)
            this.speed = speed;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}
