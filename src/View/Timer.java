package View;

public class Timer implements Runnable{
    Thread t;
    SolitaireView vi;
    public Timer (SolitaireView vi) {
        t = new Thread(this,"timerThread");
        this.vi = vi;
        t.start();
    }

    @Override
    public void run() {
        try{
            for (int i = 0; i < 60; i++) {
                for (int j = 0; j < 60; j++) {
                    Thread.sleep(1000);
                    if(!vi.myController.gameOver())
                        if (j < 10) {
                            if(i < 10) {
                                vi.setTime("                    0" + i + ":" + 0 + j);
                            } else {
                                vi.setTime("                    " + i + ":" + 0 + j);
                            }

                        } else {
                            if(i < 10) {
                                vi.setTime("                    0" + i + ":" + j);
                            } else {
                                vi.setTime("                    " + i + ":" + j);
                            }
                        }
                }
            }
        } catch(Exception ignored) {
        }
    }
}
