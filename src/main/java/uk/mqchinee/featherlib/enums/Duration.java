package uk.mqchinee.featherlib.enums;

public enum Duration {
    infinity(1000000),
    second(20);

    public final int value;

    Duration(int value) {
        this.value = value;
    }


}
