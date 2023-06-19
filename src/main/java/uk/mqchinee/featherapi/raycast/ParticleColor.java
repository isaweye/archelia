package uk.mqchinee.featherapi.raycast;

import java.util.Random;

public final class ParticleColor {

    private byte red;

    private byte green;

    private byte blue;

    public ParticleColor(int red, int green, int blue) {
        this.red = (byte)red;
        this.green = (byte)green;
        this.blue = (byte)blue;
    }

    public int getRed() {
        return 0xFF & this.red;
    }

    public int getGreen() {
        return 0xFF & this.green;
    }

    public int getBlue() {
        return 0xFF & this.blue;
    }

    public static ParticleColor fromRGB(int r, int g, int b) {
        return new ParticleColor(r, g, b);
    }

    public static ParticleColor fromRGB(int rgb) {
        if (rgb >> 24 != 0)
            return null;
        return fromRGB(rgb >> 16 & 0xFF, rgb >> 8 & 0xFF, rgb >> 0 & 0xFF);
    }

    public static ParticleColor randomColor() {
        Random r = new Random();
        return fromRGB(r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }
}