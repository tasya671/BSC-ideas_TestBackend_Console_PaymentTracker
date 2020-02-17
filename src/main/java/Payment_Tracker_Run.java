import data.MoneyRecord;
import data.RecordStore;
import data.RecordStoreImpl;
import parser.Parser;
import printer.PrintDataFactory;
import printer.PrintDataStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Payment_Tracker_Run {

    private final static  String QUIT = "quit";
    private final static  String BACK = "back";
    private static RecordStore<MoneyRecord> recordStore = new RecordStoreImpl();
    private static final Parser parser = new Parser(recordStore);
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static PrintDataStore printDataStore;
    private static BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
    private static List<Future> futureList = new ArrayList<>();


    private void initData() throws IOException {
        System.out.println("*****WELCOME*****\n"+"Choose a place to record data: Console or File");
        String action = scanner.readLine();
        if (action.equalsIgnoreCase("file")){
            System.out.println("Enter file name");
            action = scanner.readLine();
            this.printDataStore = PrintDataFactory.create("File", recordStore, action);
        } else
            this.printDataStore = PrintDataFactory.create(action, recordStore, null);
        futureList.add(executorService.submit(printDataStore));
    }

    private void paymentRun(String...args) throws IOException {
        String action;
        String current;
        while (true) {
            synchronized (System.out){
                System.out.println("\tChoose a data entry option:\n"+
                        "file - file input\n"+
                        "console - console input, enter back to return to options\n"+
                        "command - input from a file specified in command line parameters\n"+
                        "to EXIT the program, enter quit");
                action = scanner.readLine();}

            switch (action) {
                case "file":
                    System.out.println("Enter name of file:");
                    final String fileName = scanner.readLine();
                    executorService.execute(() -> parser.putAllFromFiles(fileName));
                    break;

                case "console":
                    while (true) {
                        synchronized (System.out) {
                            System.out.println("Enter the data:");
                            current = scanner.readLine();
                        }
                        if ((current.equalsIgnoreCase(BACK)))
                            break;
                        try { parser.putOneRecord(current); }
                        catch(IllegalArgumentException exp){ System.out.println("Invalid data format"); }
                    }
                    break;

                case "command":
                    if (args.length == 0) break;
                    executorService.execute(()-> parser.putAllFromFiles(args[0]));
                    break;

                case QUIT:
                    executorService.shutdownNow();
                    for (int i = 0; i < futureList.size(); i++) {
                        futureList.get(i).cancel(true); }
                    return;
            }
        }
    }

    public static void main(String[] args) {
        try {
            Payment_Tracker_Run tracker = new Payment_Tracker_Run();
            tracker.initData();
            tracker.paymentRun(args);
        } catch (IOException e) { e.getMessage(); }
    }
}
