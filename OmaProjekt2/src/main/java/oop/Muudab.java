package oop;

import javafx.scene.text.Text;

public class Muudab implements Runnable{
    private Text tekstRaha;
    private Text tekstTarkus;
    private Text tekstRahulolu;
    private Text tekstEnergia;

    private int tarkus;
    private int rahulolu;
    private int raha;
    private int energia;

    private boolean joobBool = false;
    private boolean tööBool = false;
    private boolean tarkusBool = false;
    private boolean energiaBool = false;

    //stack overflow
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

    public Muudab(Text tekstRaha, Text tekstTarkus, Text tekstRahulolu, Text tekstEnergia, int tarkus, int rahulolu, int raha, int energia) {
        this.tekstRaha = tekstRaha;
        this.tekstTarkus = tekstTarkus;
        this.tekstRahulolu = tekstRahulolu;
        this.tekstEnergia = tekstEnergia;
        this.tarkus = tarkus;
        this.rahulolu = rahulolu;
        this.raha = raha;
        this.energia = energia;
    }

    public int getTarkus() {
        return tarkus;
    }

    public void setTarkus(int tarkus) {
        this.tarkus = tarkus;
    }

    public int getRahulolu() {
        return rahulolu;
    }

    public void setRahulolu(int rahulolu) {
        this.rahulolu = rahulolu;
    }

    public int getRaha() {
        return raha;
    }

    public void setRaha(int raha) {
        this.raha = raha;
    }

    public int getEnergia() {
        return energia;
    }

    public void setEnergia(int energia) {
        this.energia = energia;
    }


    //statide muutmise boolid
    public void setJoobBool(boolean joobBool) {
        this.tööBool = false;
        this.tarkusBool = false;
        this.energiaBool = false;
        this.joobBool = joobBool;
    }

    public void setTööBool(boolean tööBool) {
        this.tarkusBool = false;
        this.energiaBool = false;
        this.joobBool = false;
        this.tööBool = tööBool;
    }

    public void setTarkusBool(boolean tarkusBool) {
        this.energiaBool = false;
        this.joobBool = false;
        this.tööBool = false;
        this.tarkusBool = tarkusBool;
    }

    public void setEnergiaBool(boolean energiaBool) {
        this.joobBool = false;
        this.tööBool = false;
        this.tarkusBool = false;
        this.energiaBool = energiaBool;
    }

    // meetodid, et peatada, pausile panna ,ning jätkata
    public void stop() {
        running = false;
        // you might also want to interrupt() the Thread that is
        // running this Runnable, too, or perhaps call:
        resume();
        // to unblock
    }

    public void pause() {
        // you may want to throw an IllegalStateException if !running
        paused = true;
    }

    public boolean isPaused() {
        return paused;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll(); // Unblocks thread
        }
    }



    @Override
    public void run() {
        /**
         * https://stackoverflow.com/questions/16758346/how-pause-and-then-resume-a-thread
         *
         * klass töötab nii kaua, kuni vähemalt 1 atribuutidest nulli jookseb või me selle ise peatame
         * kui nupulevajutus muudab vastavat booleani true'ks, hakkavad stat'id vastavalt muutuma
         * saab pausi peale panna ning saab peatada.
         */

        while (running) {
            synchronized (pauseLock) {
                if (!running) { // may have changed while waiting to
                    // synchronize on pauseLock
                    System.out.println("Nägemist!");
                    break;
                }
                if (paused) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait(); // will cause this Thread to block until
                            // another thread calls pauseLock.notifyAll()
                            // Note that calling wait() will
                            // relinquish the synchronized lock that this
                            // thread holds on pauseLock so another thread
                            // can acquire the lock to call notifyAll()
                            // (link with explanation below this code)
                            //https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html
                        }
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) { // running might have changed since we paused
                        System.out.println("Nägemist!");
                        break;
                    }
                }
            }

            // Your code here

            System.out.println(this);
            if (joobBool) {
                this.setRahulolu(getRahulolu() + 8);
                this.setRaha(getRaha() - 4);
                this.setEnergia(getEnergia() - 2);
                this.setTarkus(getTarkus() - 2);
            } else if (tööBool) {
                this.setRahulolu(getRahulolu() - 2);
                this.setRaha(getRaha() + 8);
                this.setEnergia(getEnergia() - 4);
                this.setTarkus(getTarkus() - 2);
            }
            else if (tarkusBool) {
                this.setRahulolu(getRahulolu() - 4);
                this.setRaha(getRaha() - 2);
                this.setEnergia(getEnergia() - 2);
                this.setTarkus(getTarkus() + 8);
            }
            else if (energiaBool) {
                this.setRahulolu(getRahulolu() - 2);
                this.setRaha(getRaha() - 2);
                this.setEnergia(getEnergia() + 8);
                this.setTarkus(getTarkus() - 4);
            } else {
                this.setRahulolu(getRahulolu() - 1);
                this.setRaha(getRaha() - 1);
                this.setEnergia(getEnergia() - 1);
                this.setTarkus(getTarkus() - 1);
            }

            tekstRaha.setText("Raha: "+ getRaha());
            tekstTarkus.setText("Tarkus: "+ getTarkus());
            tekstRahulolu.setText("Rahulolu: " + getRahulolu());
            tekstEnergia.setText("Energia: "+ getEnergia());

            if (getEnergia() < 0 || getRaha() < 0 || getRahulolu() < 0 || getTarkus() < 0) {
                System.out.println("Mäng läbi");
                this.stop();
            }


            //ootame pool sekundit iga muutuse vahel
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }


    }

    public String toString() {
        return "Mängija seis:[" + "tarkus: " + tarkus + ", rahulolu: " + rahulolu + ", raha: " + raha + ", energia: " + energia + ']';
    }
}
