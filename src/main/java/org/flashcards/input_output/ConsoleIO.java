package org.flashcards.input_output;

import org.flashcards.FlashCard;
import org.flashcards.log.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class ConsoleIO implements IO, Closeable {

    private final BufferedReader reader;
    private Logger logger = Logger.getLogger(ConsoleIO.class.getName());
    public ConsoleIO(){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.reader = bufferedReader;
    }
    @Override
    public String read() {
        try {
            String consoleInput = reader.readLine();
            Log.appendLog(consoleInput);
                    /*.log
                    .append(consoleInput)
                    .append("\n");*/
            return consoleInput;
        } catch (IOException e) {
            throw new ConsoleIOException("Error reading input",e);
        }
    }

    @Override
    public void write(String inputData) {
        System.out.println(inputData);
        Log.appendLog(inputData);
//        logger.info(inputData);
//        FlashCard.log
//                .append(inputData)
//                .append("\n");
    }

    @Override
    public void close()  {
        try {
            reader.close();
        } catch (IOException e) {
            throw new ConsoleIOException("Error closing reader stream",e);
        }
    }
}
