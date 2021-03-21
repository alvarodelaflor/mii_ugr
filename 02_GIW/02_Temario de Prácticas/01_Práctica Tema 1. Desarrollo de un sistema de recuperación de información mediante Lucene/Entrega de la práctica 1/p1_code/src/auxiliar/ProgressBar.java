package auxiliar;

public class ProgressBar {

    Thread thread;

    public void processing(Integer index, Integer total) {

        Integer percent = (index * 100) / total;
        thread = new Thread(){

            public void run() {
                System.out.print("\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b");
                System.out.print("Processing---------");
                System.out.print(percent+"%");

                try {
                    thread.sleep(50);
                } catch(Exception e){

                }
            }
        };
        thread.start();
    }
}
