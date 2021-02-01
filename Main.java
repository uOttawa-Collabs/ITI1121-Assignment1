public class Main
{
    /**
     * <b>main</b> of the application. Creates the instance of  TicTacToe
     * and starts the game. If two parameters lines  and columns
     * are passed, they are used. If the paramters lines, columns
     * and win are passed, they are used.
     * Otherwise, a default value is used. Defaults values (3) are also
     * used if the paramters are too small (less than 2).
     * Here, we assume that the command lines arguments are indeed integers
     *
     * @param args command lines parameters
     */
    public static void main(String[] args)
    {
        int lines   = validateInt(args, 0);
        int columns = validateInt(args, 1);
        int wins    = validateInt(args, 2);

        if (args.length > 3)
        {
            System.out.println("Too many arguments. Only the first 3 are used.");
        }

        TicTacToe game = new TicTacToe(lines, columns, wins);

        while (true)
        {
            printToScreen(game.show());
            String input = System.console().readLine();

            if ("restart".equals(input))
            {
                printToScreen("Restarting game.");
                game = new TicTacToe(lines, columns, wins);
                continue;
            }
            else if ("debug".equals(input))
            {
                printToScreen(game.toDebug());
                continue;
            }
            else if ("exit".equals(input))
            {
                printToScreen("bye!");
                break;
            }
            else
            {
                int position = Integer.parseInt(input);
                printToScreen(game.play(position));
            }
        }
    }

    /**
     * Print all the messages to the console
     */
    private static void printToScreen(String[] messages)
    {
        for (int i = 0; i < messages.length; i++)
        {
            System.out.println("\n");
            System.out.print(messages[i]);
        }
    }

    /**
     * Print a single message to the console
     */
    private static void printToScreen(String message)
    {
        if (message != null)
        {
            System.out.println(message);
        }
    }

    /**
     * Extract an integrate from the provided argument
     * it must be 2 or more.
     *
     * @param args  The command lines parameters
     * @param index Which index to parse
     */
    private static int validateInt(String[] args, int index)
    {
        if (index >= 0 && index < args.length)
        {
            int num = Integer.parseInt(args[index]);
            if (num >= 2)
            {
                return num;
            }
            else
            {
                System.out.println("Invalid argument, using default...");
            }
        }
        return 3;
    }
}
