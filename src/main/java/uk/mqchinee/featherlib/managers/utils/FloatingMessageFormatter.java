package uk.mqchinee.featherlib.managers.utils;

//    # Maximum amount of letters in one line in message
//    max-lines-width: 30
//    # Maximum amount of lines in one message
//    max-lines-amount: 4
//    # If limits were reached the end of the message will be shown as ellipsis (...)
//
//    # Minimum duration of message
//    min-duration: 100
//    # Maximum duration of message
//    max-duration: 300
//
//    # duration = amountOfLetters * readSpeed
//    # measures in ticks (1 second = 20 ticks)
//    read-speed: 5

import org.apache.commons.lang.WordUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FloatingMessageFormatter {

        private final int maxLinesWidth;
        private final int maxLinesAmount;

        public FloatingMessageFormatter(int maxLinesWidth, int maxLinesAmount) {
            this.maxLinesWidth = maxLinesWidth;
            this.maxLinesAmount = maxLinesAmount;
        }

        public List<String> format(String chatMessage) {
            String wrapped = WordUtils.wrap(chatMessage, maxLinesWidth, null, true);
            String[] wrappedLines = wrapped.split(System.lineSeparator());
            if (wrappedLines.length > maxLinesAmount) {
                wrappedLines = Arrays.copyOfRange(wrappedLines, 0, maxLinesAmount);
                wrappedLines[maxLinesAmount - 1] += " ...";
            }

            return new ArrayList<>(Arrays.asList(wrappedLines));
        }

}
