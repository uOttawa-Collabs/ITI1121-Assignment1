import java.util.Random;
import java.util.Scanner;

public class Utils
{
    public static final Random generator = new Random();
    public static final String NEW_LINE  = System.getProperty("line.separator");

    public static final String readLine()
    {
        Scanner reader = new Scanner(System.in);
        return reader.nextLine();
    }

    public static final int readInt()
    {
        Scanner reader = new Scanner(System.in);
        return reader.nextInt();
    }

}
