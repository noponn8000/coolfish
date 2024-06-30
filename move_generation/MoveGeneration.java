package coolfish.move_generation;

import coolfish.board.BitBoard;
import coolfish.board.Color;
import coolfish.board.Direction;

public class MoveGeneration {
    // 64 (squares) x 2 (colors) array containing bitboards of pawn attacks; 0 =
    // white, 1 = black
    public long[][] pawnAttacks;
    // 64 (squares) array containing bitboards of knight attacks
    public long[] knightAttacks;
    // 64 (squares) array containing bitboards of king attacks
    public long[] kingAttacks;
    // 64 (squares) array containing bitboards of bishop relevant occupancy
    public long[] bishopRelevantOccupancies;
    // 64 (squares) array containing bitboards of rook relevant occupancy
    public long[] rookRelevantOccupancies;

    public MoveGeneration() {
        pawnAttacks = new long[64][2];
        knightAttacks = new long[64];
        kingAttacks = new long[64];
        bishopRelevantOccupancies = new long[64];
        rookRelevantOccupancies = new long[64];

        initiateLeaperAttacks();
    }

    private void initiateLeaperAttacks() {
        for (int i = 0; i < 64; i++) {
            pawnAttacks[i][0] = getPawnAttackMask(i, Color.White);
            pawnAttacks[i][1] = getPawnAttackMask(i, Color.Black);

            knightAttacks[i] = getKnightAttackMask(i);

            kingAttacks[i] = getKingAttackMask(i);

            bishopRelevantOccupancies[i] = getBishopRelevantOccupancyMask(i);
            rookRelevantOccupancies[i] = getRookRelevantOccupancyMask(i);
        }
    }

    public long getPawnAttackMask(int square, Color color) {
        long pawnBitboard = BitBoard.setBitRaw(0L, square, true);
        long mask = 0L;

        if (color == Color.White) {
            mask |= BitBoard.shiftBits(pawnBitboard, Direction.NE);
            mask |= BitBoard.shiftBits(pawnBitboard, Direction.NW);
        } else {
            mask |= BitBoard.shiftBits(pawnBitboard, Direction.SE);
            mask |= BitBoard.shiftBits(pawnBitboard, Direction.SW);
        }

        return mask;
    }

    public long getKnightAttackMask(int square) {
        long knightBitboard = BitBoard.setBitRaw(0L, square, true);
        long mask = 0L;

        mask |= BitBoard.shiftBits(knightBitboard, Direction.NNE);
        mask |= BitBoard.shiftBits(knightBitboard, Direction.NEE);
        mask |= BitBoard.shiftBits(knightBitboard, Direction.NNW);
        mask |= BitBoard.shiftBits(knightBitboard, Direction.NWW);
        mask |= BitBoard.shiftBits(knightBitboard, Direction.SSE);
        mask |= BitBoard.shiftBits(knightBitboard, Direction.SEE);
        mask |= BitBoard.shiftBits(knightBitboard, Direction.SSW);
        mask |= BitBoard.shiftBits(knightBitboard, Direction.SWW);

        return mask;
    }

    public long getKingAttackMask(int square) {
        long kingBitboard = BitBoard.setBitRaw(0L, square, true);
        long mask = 0L;

        mask |= BitBoard.shiftBits(kingBitboard, Direction.N);
        mask |= BitBoard.shiftBits(kingBitboard, Direction.S);
        mask |= BitBoard.shiftBits(kingBitboard, Direction.W);
        mask |= BitBoard.shiftBits(kingBitboard, Direction.E);
        mask |= BitBoard.shiftBits(kingBitboard, Direction.NW);
        mask |= BitBoard.shiftBits(kingBitboard, Direction.NE);
        mask |= BitBoard.shiftBits(kingBitboard, Direction.SW);
        mask |= BitBoard.shiftBits(kingBitboard, Direction.SE);

        return mask;
    }

    public long getBishopRelevantOccupancyMask(int square) {
        long mask = 0L;

        mask = BitBoard.maskRay(mask, square, Direction.NW, 1, 0L);
        mask = BitBoard.maskRay(mask, square, Direction.NE, 1, 0L);
        mask = BitBoard.maskRay(mask, square, Direction.SW, 1, 0L);
        mask = BitBoard.maskRay(mask, square, Direction.SE, 1, 0L);

        return mask;
    }

    public long getRookRelevantOccupancyMask(int square) {
        long mask = 0L;

        mask = BitBoard.maskRay(mask, square, Direction.N, 1, 0L);
        mask = BitBoard.maskRay(mask, square, Direction.S, 1, 0L);
        mask = BitBoard.maskRay(mask, square, Direction.W, 1, 0L);
        mask = BitBoard.maskRay(mask, square, Direction.E, 1, 0L);

        return mask;
    }
}
