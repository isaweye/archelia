package uk.mqchinee.featherapi.raycast;

import org.bukkit.util.Vector;

public class RaycastMath {

    public double cos(double a) {
        return Math.cos(a);
    }

    public double sin(double a) {
        return Math.sin(a);
    }

    public double tan(double a) {
        return Math.tan(a);
    }

    public double arccos(double a) {
        return Math.acos(a);
    }

    public double arcsin(double a) {
        return Math.asin(a);
    }

    public double arctan(double a) {
        return Math.atan(a);
    }

    public double toRadians(double angdeg) {
        return Math.toRadians(angdeg);
    }

    public double toDeg(double angrad) {
        return Math.toDegrees(angrad);
    }

    public double getAngle(double width, double height) {
        if (width < 0.0D)
            width *= -1.0D;
        if (height < 0.0D)
            height *= -1.0D;
        if (width == 0.0D || height == 0.0D)
            return 0.0D;
        return arctan(height / width);
    }

    public Vector rotate(Vector vect, double yaw, double pitch) {
        yaw = toRadians(yaw);
        pitch = toRadians(pitch);
        vect = rotateX(vect, pitch);
        vect = rotateY(vect, -yaw);
        return vect;
    }

    public Vector rotateX(Vector vect, double a) {
        double y = cos(a) * vect.getY() - sin(a) * vect.getZ();
        double z = sin(a) * vect.getY() + cos(a) * vect.getZ();
        return vect.setY(y).setZ(z);
    }

    public Vector rotateY(Vector vect, double b) {
        double x = cos(b) * vect.getX() + sin(b) * vect.getZ();
        double z = -sin(b) * vect.getX() + cos(b) * vect.getZ();
        return vect.setX(x).setZ(z);
    }

    public Vector rotateZ(Vector vect, double c) {
        double x = cos(c) * vect.getX() - sin(c) * vect.getY();
        double y = sin(c) * vect.getX() + cos(c) * vect.getY();
        return vect.setX(x).setY(y);
    }
}

