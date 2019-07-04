package it.gangemi.concurrent.increment;

class SimpleProgress {

    private final int size;
    private static final String ANIM = "|/-\\";

    SimpleProgress(int size) {
        this.size = size;
    }

    String step(final int count, final String message) {
        int percent = count * 100 / size;
        return "\r" + ANIM.charAt(percent % ANIM.length()) + " " + percent + "% - "+message;
    }


}
