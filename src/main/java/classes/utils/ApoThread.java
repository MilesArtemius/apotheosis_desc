package classes.utils;

import javafx.application.Platform;

public class ApoThread extends Thread {
    private boolean background;

    public ApoThread(boolean isBackground) {
        this.background = isBackground;
    }

    @Override
    public void run() {
        try {
            onRun();
        } finally {
            if (background) {
                onComplete();
            } else {
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                        ApoThread.this.onComplete();
                    }
                });
            }
        }
    }

    public void onComplete(){}
    public void onRun(){}
}
