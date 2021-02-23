import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * An iterator type class to read values from a text file.  This is a subclass of
 * <code>Input</code> that hides the exceptions that can happen when opening a file.  As with
 * <code>Input</code> all errors during use of the iterator result in a message output to the
 * standard error and program termination.
 * <p/>
 * <p>This class is useful for people new to Java since it allows them to write programs using
 * input from a file without having to fully understand the read / parse / handle exceptions model
 * of the standard Java classes.  Once exceptions and object chaining are covered this class
 * ought not to be used, it is definitely just an "early stepping stone" utility class for initial
 * learning.</p>
 *
 * @author Russel Winder
 * @version 2005-08-10
 */
public class FileInput extends Input
{
    /**
     * Construct <code>FileInput</code> object given a file name.
     */
    public FileInput(final String fileName)
    {
        try
        {
            scanner = new Scanner(new FileInputStream(fileName));
        }
        catch (FileNotFoundException fnfe)
        {
            System.err.println("File " + fileName + " could not be found.");
            System.exit(1);
        }
    }

    /**
     * Construct <code>FileInput</code> object given a file name.
     */
    public FileInput(final FileInputStream fileStream)
    {
        super(fileStream);
    }
}