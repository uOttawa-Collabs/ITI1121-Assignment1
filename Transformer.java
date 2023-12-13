public class Transformer
{
    /**
     * The list of all allowable symmetries for a n (numRows) x m (numColumns) board
     * by applying the following transformations
     *
     * @param numRows    The number of rows in your board
     * @param numColumns The number of columns in your board
     *
     * @return All rotations for a symmetric board
     */
    public static Type[] symmetricTransformations(int numRows, int numColumns)
    {
        Type[] types;

        if (numRows == 0 || numColumns == 0)
        {
            return null;
        }

        if (numRows != numColumns)
        {
            types    = new Type[4];
            types[0] = Type.IDENTITY;
            types[1] = Type.HORIZONAL_SYMMETRY;
            types[2] = Type.VERTICAL_SYMMETRY;
            types[3] = Type.HORIZONAL_SYMMETRY;
        }
        else
        {
            types    = new Type[8];
            types[0] = Type.IDENTITY;
            types[1] = Type.ROTATION;
            types[2] = Type.ROTATION;
            types[3] = Type.ROTATION;
            types[4] = Type.HORIZONAL_SYMMETRY;
            types[5] = Type.ROTATION;
            types[6] = Type.ROTATION;
            types[7] = Type.ROTATION;
        }

        return types;
    }

    /**
     * Applies the transformation specified as parameter
     * to transformedBoard
     * <p>
     * If the transformation was successful return true, if not return false;
     */
    public static boolean transform(Type transformation, int numRows, int numColumns, int[] board)
    {
        switch (transformation)
        {
            case IDENTITY:
                return identity(numRows, numColumns, board);
            case ROTATION:
                return rotate90(numRows, numColumns, board);
            case VERTICAL_SYMMETRY:
                return verticalFlip(numRows, numColumns, board);
            case HORIZONAL_SYMMETRY:
                return horizontalFlip(numRows, numColumns, board);
            default:
                return false;
        }
    }

    /**
     * Create the identity board, which means do not flip the board at all.
     * Here we ignore the current values within the provided board and
     * populate it with its index value.
     * <p>
     * If we consider a 3x3 board, the identity board would be
     * <p>
     * 0 | 1 | 2
     * ----------
     * 3 | 4 | 5
     * ----------
     * 6 | 7 | 8
     * <p>
     * <p>
     * If the transformation was successful return true, if not return false;
     */
    public static boolean identity(int numRows, int numColumns, int[] board)
    {
        if (numRows == 0 || numColumns == 0 || board == null || numRows * numColumns != board.length)
        {
            return false;
        }

        try
        {
            for (int i = 0; i < numRows * numColumns; ++i)
            {
                board[i] = i;
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return false;
        }
        return true;
    }

    /**
     * Flip a board horizontally based on the n (numRows) x m (numColumns) grid
     * editing the provided board in place.
     * <p>
     * If we consider a 3x3 board
     * <p>
     * 1 | 2 | 3
     * ----------
     * 4 | 5 | 6
     * ----------
     * 7 | 8 | 9
     * <p>
     * The updated horizontally flipped board would be
     * <p>
     * 7 | 8 | 9
     * ----------
     * 4 | 5 | 6
     * ----------
     * 1 | 2 | 3
     * <p>
     * If the transformation was successful return true, if not return false;
     */
    public static boolean horizontalFlip(int numRows, int numColumns, int[] board)
    {
        if (numRows == 0 || numColumns == 0 || board == null || numRows * numColumns != board.length)
        {
            return false;
        }

        try
        {
            for (int i = 0; i < numRows / 2; i++)
            {
                for (int j = 0; j < numColumns; j++)
                {
                    int temp = board[i * numColumns + j];
                    int k    = (numRows - 1 - i) * numColumns + j;
                    board[i * numColumns + j] = board[k];
                    board[k]                  = temp;
                }
            }
        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    /**
     * Flip a board vertically based on the n (numRows) x m (numColumns) grid
     * editing the provided board in place.
     * <p>
     * If we consider a 3x3 board
     * <p>
     * 1 | 2 | 3
     * ----------
     * 4 | 5 | 6
     * ----------
     * 7 | 8 | 9
     * <p>
     * The updated vertically flipped board would be
     * <p>
     * 3 | 2 | 1
     * ----------
     * 6 | 5 | 4
     * ----------
     * 9 | 8 | 7
     * <p>
     * If the transformation was successful return true, if not return false;
     */
    public static boolean verticalFlip(int numRows, int numColumns, int[] board)
    {
        if (numRows == 0 || numColumns == 0 || board == null || numRows * numColumns != board.length)
        {
            return false;
        }

        try
        {
            for (int i = 0; i < numColumns / 2; i++)
            {
                for (int j = 0; j < numRows; j++)
                {
                    int temp = board[numColumns * j + i];
                    int k    = (numColumns - 1 - i) + j * numColumns;
                    board[numColumns * j + i] = board[k];
                    board[k]                  = temp;
                }
            }

        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }

    /**
     * Rotate a board 90 degrees based on the n x (numRows) x m (numColumns) grid
     * editing the provided board in place.
     * <p>
     * If we consider a 3x3 board
     * <p>
     * 1 | 2 | 3
     * ----------
     * 4 | 5 | 6
     * ----------
     * 7 | 8 | 9
     * <p>
     * The updated 90 rotated board would be
     * <p>
     * 7 | 4 | 1
     * ----------
     * 8 | 5 | 2
     * ----------
     * 9 | 6 | 3
     * <p>
     * You can only rotate n x n boards.
     * <p>
     * If the transformation was successful return true, if not return false;
     */
    public static boolean rotate90(int numRows, int numColumns, int[] board)
    {
        if (numRows == 0 || numRows != numColumns || board == null ||
            numRows * numColumns != board.length)
        {
            return false;
        }
        else if (numRows == 1)
        {
            return true;
        }
        else
        {
            try
            {
                for (int i = 0; i < numRows; ++i)
                {
                    for (int j = i + 1; j < numColumns; ++j)
                    {
                        // Transpose
                        int coord1 = i * numColumns + j;
                        int coord2 = j * numColumns + i;
                        // Swap
                        board[coord1] ^= board[coord2];
                        board[coord2] ^= board[coord1];
                        board[coord1] ^= board[coord2];
                    }
                }
                return verticalFlip(numRows, numColumns, board);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                return false;
            }
        }
    }

    private static void test(int numRows, int numColumns)
    {
        int[] test;
        test = new int[numRows * numColumns];

        System.out.println("testing " + numRows + " numRows and " + numColumns + " numColumns.");

        identity(numRows, numColumns, test);
        System.out.println(java.util.Arrays.toString(test));

        horizontalFlip(numRows, numColumns, test);
        System.out.println("HF => " + java.util.Arrays.toString(test));

        horizontalFlip(numRows, numColumns, test);
        System.out.println("HF => " + java.util.Arrays.toString(test));

        verticalFlip(numRows, numColumns, test);
        System.out.println("VF => " + java.util.Arrays.toString(test));

        verticalFlip(numRows, numColumns, test);
        System.out.println("VF => " + java.util.Arrays.toString(test));

        for (int i = 0; i < 4; i++)
        {
            boolean didTransform = rotate90(numRows, numColumns, test);
            if (didTransform)
            {
                System.out.println("ROT => " + java.util.Arrays.toString(test));
            }
        }
    }

    public static void main(String[] args)
    {
        int[] test;
        int   numRows, numColumns;

        test(2, 2);
        test(2, 3);
        test(3, 3);
        test(4, 3);
        test(4, 4);
    }

    /**
     * An static enum for the types of
     * allowable transformations
     */
    public enum Type
    {
        UNKNOWN,
        IDENTITY,
        ROTATION,
        VERTICAL_SYMMETRY,
        HORIZONAL_SYMMETRY,
    }
}
