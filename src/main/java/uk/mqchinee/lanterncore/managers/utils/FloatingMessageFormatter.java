package uk.mqchinee.lanterncore.managers.utils;

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
