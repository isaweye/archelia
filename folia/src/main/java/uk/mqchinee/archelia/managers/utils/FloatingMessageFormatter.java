package uk.mqchinee.archelia.managers.utils;

import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FloatingMessageFormatter {

    private final int maxLinesWidth;
    private final int maxLinesAmount;

    /**
     * Constructs a new FloatingMessageFormatter.
     *
     * @param maxLinesWidth   The maximum width of each line in characters.
     * @param maxLinesAmount  The maximum number of lines to display.
     */
    public FloatingMessageFormatter(int maxLinesWidth, int maxLinesAmount) {
        this.maxLinesWidth = maxLinesWidth;
        this.maxLinesAmount = maxLinesAmount;
    }

    /**
     * Formats the chat message and wraps it into multiple lines if needed.
     *
     * @param chatMessage The chat message to format.
     * @return A list of strings representing the formatted lines.
     */
    public List<String> format(String chatMessage) {
        // Wrap the chat message to fit the specified width
        String wrapped = WordUtils.wrap(chatMessage, maxLinesWidth, null, true);

        // Split the wrapped message into individual lines
        String[] wrappedLines = wrapped.split(System.lineSeparator());

        // If the number of lines exceeds the maximum allowed, truncate the array and append "..." to the last line
        if (wrappedLines.length > maxLinesAmount) {
            wrappedLines = Arrays.copyOfRange(wrappedLines, 0, maxLinesAmount);
            wrappedLines[maxLinesAmount - 1] += " ...";
        }

        // Convert the array of lines to an ArrayList and return
        return new ArrayList<>(Arrays.asList(wrappedLines));
    }

}
