package Misc;

// Acknowledgment: http://www.cse.chalmers.se/edu/year/2015/course/TDA602/Eraserlab/pwdmasking.html

/**
 * A Class that masks the password
 */
class EraserThread implements Runnable {
    /** A boolean to indicate whether to start or stop masking */
    private boolean stop;

    /**
     * Prints out the prompt
     * 
     * @param prompt The prompt displayed to the user
     */
    public EraserThread(String prompt) {
        System.out.print(prompt);
    }

    /**
     * Begin masking...display asterisks (*)
     */
    public void run() {
        stop = true;
        while (stop) {
            System.out.print("\010*");
            try {
                Thread.currentThread();
                Thread.sleep(1);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

    /**
     * Instruct the thread to stop masking
     */
    public void stopMasking() {
        this.stop = false;
    }
}
