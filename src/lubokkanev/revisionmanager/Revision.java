package lubokkanev.revisionmanager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Revision {
    private String revisionWord;
    private int revisionNumber;

    public Revision(String revisionString) {
        Matcher matcher = Pattern.compile("([^\\d]*)([\\d]+)").matcher(revisionString);
        if (!matcher.find()) {
            throw new RuntimeException("Incorrect revision format: '" + revisionString + "'. It should be of the type '<word><number>', 'rev5' for example.");
        }

        revisionWord = matcher.group(1);
        revisionNumber = Integer.parseInt(matcher.group(2));
    }

    public int getNumber() {
        return revisionNumber;
    }

    public String toString() {
        return revisionWord + revisionNumber;
    }
}
