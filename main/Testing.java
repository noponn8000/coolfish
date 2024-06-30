package coolfish.main;

import coolfish.board.BitBoard;
import coolfish.board.Direction;
import coolfish.board.Square;
import coolfish.move_generation.MoveGeneration;

public class Testing {
    public static void testPawnAttackTables()
    {
        var gen = new MoveGeneration();
        for (int i = 0; i < 64; i++)
        {
            System.out.println("Position:");
            BitBoard.printBitboardRaw(BitBoard.setBitRaw(0L, i, true));
            System.out.println("White Pawn Attacks:");
            BitBoard.printBitboardRaw(gen.pawnAttacks[i][0]);
            System.out.println("Black Pawn Attacks:");
            BitBoard.printBitboardRaw(gen.pawnAttacks[i][1]);
        }
    }

    public static void testKnightAttackTables()
    {
        var gen = new MoveGeneration();
        for (int i = 0; i < 64; i++)
        {
            System.out.println("Position:");
            BitBoard.printBitboardRaw(BitBoard.setBitRaw(0L, i, true));
            System.out.println("Knight Attacks:");
            BitBoard.printBitboardRaw(gen.knightAttacks[i]);
        }
    }

    public static void testBlockerMasking()
    {
        var gen = new MoveGeneration();

        var bitboard = 0L;

        bitboard = BitBoard.setBitRaw(bitboard, Square.b6.ordinal(), true);
        BitBoard.printBitboardRaw(bitboard);

        bitboard = BitBoard.maskRay(0L, Square.f2.ordinal(), Direction.NW, 0, bitboard);
        bitboard = BitBoard.setBitRaw(bitboard, Square.b2.ordinal(), true);
        bitboard = BitBoard.maskRay(bitboard, Square.f2.ordinal(), Direction.W, 0, bitboard);

        BitBoard.printBitboardRaw(bitboard);
    }

    public static void testBishopOccupancyTables()
    {
        var gen = new MoveGeneration();

        for (int i = 0; i < 64; i++)
        {
            System.out.println("Position:");
            BitBoard.printBitboardRaw(BitBoard.setBitRaw(0L, i, true));
            System.out.println("Bishop Relevant Occupancy: ");
            BitBoard.printBitboardRaw(gen.bishopRelevantOccupancies[i]);
        }
    }

    public static void testRookOccupancyTables()
    {
        var gen = new MoveGeneration();

        for (int i = 0; i < 64; i++)
        {
            System.out.println("Position:");
            BitBoard.printBitboardRaw(BitBoard.setBitRaw(0L, i, true));
            System.out.println("Rook Relevant Occupancy: ");
            BitBoard.printBitboardRaw(gen.rookRelevantOccupancies[i]);
        }
    }

    public static void testKingAttackTables()
    {
        var gen = new MoveGeneration();
        for (int i = 0; i < 64; i++)
        {
            System.out.println("Position:");
            BitBoard.printBitboardRaw(BitBoard.setBitRaw(0L, i, true));
            System.out.println("King Attacks:");
            BitBoard.printBitboardRaw(gen.kingAttacks[i]);
        }
    }

    public static void testRankAndFileBitmasks()
    {
        System.out.println("File A");
        BitBoard.printBitboardRaw(BitBoard.FILE_A);
        System.out.println("File A and B");
        BitBoard.printBitboardRaw(BitBoard.FILE_AB);
        System.out.println("File H");
        BitBoard.printBitboardRaw(BitBoard.FILE_H);
        System.out.println("File H and G");
        BitBoard.printBitboardRaw(BitBoard.FILE_GH);

        System.out.println("Rank 1");
        BitBoard.printBitboardRaw(BitBoard.RANK_1);
        System.out.println("Rank 1 and 2");
        BitBoard.printBitboardRaw(BitBoard.RANK_12);
        System.out.println("Rank 8");
        BitBoard.printBitboardRaw(BitBoard.RANK_8);
        System.out.println("Rank 8 and 7");
        BitBoard.printBitboardRaw(BitBoard.RANK_78);
    }
}
