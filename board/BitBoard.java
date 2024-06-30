package coolfish.board;

public class BitBoard {
    long bits;

    // Rank 8
    public static final long RANK_8 = 0b1111111100000000000000000000000000000000000000000000000000000000L;
    // Ranks 8 and 7
    public static final long RANK_78 = 0b1111111111111111000000000000000000000000000000000000000000000000L;
    // Rank 1
    public static final long RANK_1 = 0b0000000000000000000000000000000000000000000000000000000011111111L;
    // Ranks 1 and 2
    public static final long RANK_12 = 0b0000000000000000000000000000000000000000000000001111111111111111L;
    public static final long FILE_A = 0b1000000010000000100000001000000010000000100000001000000010000000L;
    public static final long FILE_AB = 0b1100000011000000110000001100000011000000110000001100000011000000L;
    public static final long FILE_H = 0b0000000100000001000000010000000100000001000000010000000100000001L;
    public static final long FILE_GH = 0b0000001100000011000000110000001100000011000000110000001100000011L;

    public BitBoard(String str, char pieceChar) {
        bits = 0L;
        int i = 0;
        for (char c : str.toCharArray()) {
            if (c == pieceChar) {
                setBit(i, true);
            }

            i++;
        }
    }

    public BitBoard(long bits) {
        this.bits = bits;
    }

    public BitBoard(int fromBit) {
        this.bits = 0L;
        setBit(fromBit, true);
    }

    public BitBoard() {
        bits = 0L;
    }

    public static boolean getBitRaw(long bits, int index) {

        if (index >= 64 || index < 0)
            return false;

        return (1L & (bits >>> (index))) == 1;
    }

    public static long setBitRaw(long bits, int index, boolean value) {
        if (index >= 64 || index < 0)
            return bits;

        if (value)
            bits |= (1L << (index));
        else
            bits &= ~(1L << (index));

        return bits;
    }

    public boolean getBit(int index) {
        if (index >= 64 || index < 0)
            return false;

        return (1L & (bits >>> (index))) == 1;
    }

    public void setBit(int index, boolean value) {
        if (index >= 64 || index < 0)
            return;

        if (value)
            bits |= (1L << (index));
        else
            bits &= ~(1L << (index));
    }

    public long getBitsRaw() {
        return bits;
    }

    // Prints the bitboard instance
    public void print() {
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0) {
                System.out.println();
                System.out.print((64 - i) / 8 + " ");
            }

            if (getBit(i))
                System.out.print(" 1 ");
            else
                System.out.print(" 0 ");
        }

        System.out.println();
        System.out.println("   a  b  c  d  e  f  g  h");
    }

    public static void printBitboardRaw(long bits) {
        for (int i = 0; i < 64; i++) {
            if (i % 8 == 0) {
                System.out.println();
                System.out.print((64 - i) / 8 + " ");
            }

            if (getBitRaw(bits, i))
                System.out.print(" 1 ");
            else
                System.out.print(" 0 ");
        }

        System.out.println();
        System.out.println("   a  b  c  d  e  f  g  h");
    }

    // Returns a shifted version of the input bitboard
    public static long shiftBits(long bits, Direction direction) {
        switch (direction) {
            case N:
                bits = (bits & ~(RANK_8)) << 8;
                break;
            case S:
                bits = (bits & ~(RANK_1)) >>> 8;
                break;
            case E:
                bits = (bits & ~(FILE_H)) >>> 1;
                break;
            case W:
                bits = (bits & ~(FILE_A)) << 1;
                break;
            case NE:
                bits = (bits & ~(RANK_8 | FILE_H)) << 7;
                break;
            case NW:
                bits = (bits & ~(RANK_8 | FILE_A)) << 9;
                break;
            case SE:
                bits = (bits & ~(RANK_1 | FILE_H)) >>> 9;
                break;
            case SW:
                bits = (bits & ~(RANK_1 | FILE_A)) >>> 7;
                break;
            case NN:
                bits = bits << 16;
                break;
            case SS:
                bits = bits >>> 16;
                break;
            case NEE:
                bits = (bits & ~(RANK_8 | FILE_GH)) << 6;
                break;
            case NNE:
                bits = (bits & ~(RANK_78 | FILE_H)) << 15;
                break;
            case NNW:
                bits = (bits & ~(RANK_78 | FILE_A)) << 17;
                break;
            case NWW:
                bits = (bits & ~(RANK_8 | FILE_AB)) << 10;
                break;
            case SEE:
                bits = (bits & ~(RANK_1 | FILE_GH)) >>> 10;
                break;
            case SSE:
                bits = (bits & ~(RANK_12 | FILE_H)) >>> 17;
                break;
            case SSW:
                bits = (bits & ~(RANK_12 | FILE_A)) >>> 15;
                break;
            case SWW:
                bits = (bits & ~(RANK_1 | FILE_AB)) >>> 6;
                break;
        }

        return bits;
    }

    // Activates the bits between the given position and the end of the board (minus the margin) in the given direction.
    // Used for calculating sliding piece moves.
    public static long maskRay(long bits, int index, Direction direction, int margin, long blockerBitboard) {
        int rank;
        int file;
        int targetRank = (index) / 8;
        int targetFile = (index) % 8;

        switch (direction) {
            case W:
                // West
                for (rank = targetRank, file = targetFile - 1; file >= margin; file--) {
                    bits = setBitRaw(bits, (8 * rank + file), true);
                    if (getBitRaw(blockerBitboard, (8 * rank + file)))
                        break;
                }
                break;
            case E:
                // East
                for (rank = targetRank, file = targetFile + 1; file < 8 - margin; file++) {
                    bits = setBitRaw(bits, (8 * rank + file), true);
                    if (getBitRaw(blockerBitboard, (8 * rank + file)))
                        break;
                }
            case N:
                // North
                for (rank = targetRank - 1, file = targetFile; rank >= margin; rank--) {
                    bits = setBitRaw(bits, (8 * rank + file), true);
                    if (getBitRaw(blockerBitboard, (8 * rank + file)))
                        break;
                }
                break;
            case S:
                // South
                for (rank = targetRank + 1, file = targetFile; rank < 8 - margin; rank++) {
                    bits = setBitRaw(bits, (8 * rank + file), true);
                    if (getBitRaw(blockerBitboard, (8 * rank + file)))
                        break;
                }
                break;
            case NE:
                // Ray in north-east direction
                for (rank = targetRank - 1, file = targetFile + 1; rank >= margin && file < 8 - margin; rank--, file++) {
                    bits = setBitRaw(bits, (8 * rank + file), true);
                    if (getBitRaw(blockerBitboard, (8 * rank + file)))
                        break;
                }
                break;
            case NW:
                // Ray in north-west direction
                for (rank = targetRank - 1, file = targetFile - 1; rank >= margin && file >= margin; rank--, file--) {
                    bits = setBitRaw(bits, (8 * rank + file), true);
                    if (getBitRaw(blockerBitboard, (8 * rank + file)))
                        break;
                }
                break;
            case SE:
                // Ray in south-east direction
                for (rank = targetRank + 1, file = targetFile + 1; rank < 8 - margin && file < 8 - margin; rank++, file++) {
                    bits = setBitRaw(bits, (8 * rank + file), true);
                    if (getBitRaw(blockerBitboard, (8 * rank + file)))
                        break;
                }
                break;
            case SW:
                // Ray in south-west direction
                for (rank = targetRank + 1, file = targetFile - 1; rank < 8 - margin && file >= margin; rank++, file--) {
                    bits = setBitRaw(bits, (8 * rank + file), true);
                    if (getBitRaw(blockerBitboard, (8 * rank + file)))
                        break;
                }
                break;
            default:
                break;
        }

        return bits;
    }

}
