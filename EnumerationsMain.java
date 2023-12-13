public class EnumerationsMain
{
    /**
     * Another <b>main</b> application.
     * This one will generate a list of games
     * and describe the outputs
     *
     * @param args command lines parameters
     */
    public static void main(String[] args)
    {

        int numRows    = validateInt(args, 0);
        int numColumns = validateInt(args, 1);
        int wins       = validateInt(args, 2);

        if (args.length > 3)
        {
            System.out.println("Too many arguments. Only the first 3 are used.");
        }

        TicTacToeEnumerations generator = new TicTacToeEnumerations(numRows, numColumns, wins);
        generator.generateAllGames();
        System.out.println(generator);
    }

    /**
     * Extract an integer from the provided argument
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