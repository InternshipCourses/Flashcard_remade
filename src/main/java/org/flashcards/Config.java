package org.flashcards;

import org.flashcards.flashcard_util.FlashcardInterface;
import org.flashcards.flashcard_util.FlashcardUtil;
import org.flashcards.input_output.IO;
import org.flashcards.input_output.Input;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;

public class Config implements ImportExportFileLocation{

    private static final Map<String, String> DEFAULT;
    private static Map<String,String> importAndExportFileName;
    static {
        importAndExportFileName = new HashMap<>();
        final var map = new HashMap<String, String>();
        map.put("-import","");
        map.put("-export","");
        DEFAULT = unmodifiableMap(map);

    }

    public Config(String[] arguments) {
        assignKeyToValue(arguments);
    }

    private void assignKeyToValue(String[] args) {
        int i = 0;

        while (i < args.length) {
            if (DEFAULT.containsKey(args[i]) && (i+2) <= args.length  ) {
                if (args[i + 1].charAt(0) != '-') {
                    importAndExportFileName.put(args[i], args[i + 1]);
                    i += 2;
                } else {
                    importAndExportFileName.put(args[i], DEFAULT.get(args[i]));
                    i++;
                }
            } else {
                i++;
            }
        }
        for (Map.Entry<String, String> defaultMap: DEFAULT.entrySet()) {
            importAndExportFileName.putIfAbsent(defaultMap.getKey(), defaultMap.getValue());
        }
    }

    @Override
    public Input getInput() {
        return () -> importAndExportFileName.get("-import");
    }

    @Override
    public FlashcardInterface getFlashcardGame(IO ioConfig) {
        return new FlashcardUtil(ioConfig); // A Factory can be used to select the type of Flash
    }

}
