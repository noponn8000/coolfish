package coolfish.board;

import java.text.ParseException;

public class Board {
    // White pieces
    private BitBoard wP;
    private BitBoard wN;
    private BitBoard wB;
    private BitBoard wR;
    private BitBoard wQ;
    private BitBoard wK;
    // Black pieces
    private BitBoard bP;
    private BitBoard bN;
    private BitBoard bB;
    private BitBoard bR;
    private BitBoard bQ;
    private BitBoard bK;

    // Castling rights for white and black
    private boolean wCastleKing = false;
    private boolean wCastleQueen = false;
    private boolean bCastleKing = false;
    private boolean bCastleQueen = false;

    // Active en passant square - null if none
    private Square enPassantSquare;

    private int halfMoveCounter = 0;
    private int fullMoveCounter = 0;

    private boolean whiteToMove = true;

    public void printBoard(boolean includeMetadata)
    {
        if (includeMetadata)
        {
            System.out.print("White castling privileges: ");
            if (wCastleKing) System.out.print("Kingside ");
            if (wCastleQueen) System.out.print("Queenside");
            System.out.println();

            System.out.print("Black castling privileges: ");
            if (bCastleKing) System.out.print("Kingside ");
            if (bCastleQueen) System.out.print("Queenside");
            System.out.println();

            if (whiteToMove) System.out.println("White to move");
            else System.out.println("Black to move");

            if (enPassantSquare != null) System.out.println("En passant square: " + enPassantSquare);

            System.out.println("Full moves: " + fullMoveCounter + ", half moves: " + halfMoveCounter);
        }
        for (int i = 0; i < 64; i++)
        {
            if (i % 8 == 0)
            {
                System.out.println();
                System.out.print((64 - i) / 8 + " ");
            }

               if (bP.getBit(i))
                    System.out.print("♙");
               else if (bN.getBit(i))
                    System.out.print("♘");
               else if (bB.getBit(i))
                    System.out.print("♗");
               else if (bR.getBit(i))
                    System.out.print("♖");
               else if (bQ.getBit(i))
                    System.out.print("♕");
               else if (bK.getBit(i))
                    System.out.print("♔");
               else if (wP.getBit(i))
                    System.out.print("♟︎");
               else if (wN.getBit(i))
                    System.out.print("♞");
               else if (wB.getBit(i))
                    System.out.print("♝");
               else if (wR.getBit(i))
                    System.out.print("♜");
               else if (wQ.getBit(i))
                    System.out.print("♛");
               else if (wK.getBit(i))
                    System.out.print("♚");
               else
                    System.out.print("□");
                
                System.out.print(" ");
        } 

        System.out.println();
        System.out.println("  a b c d e f g h");
    }

    public Board(String boardString)
    {
        // Initialise bitboards
        wP = new BitBoard();
        wN = new BitBoard();
        wB = new BitBoard();
        wR = new BitBoard();
        wQ = new BitBoard();
        wK = new BitBoard();
        bP = new BitBoard();
        bN = new BitBoard();
        bB = new BitBoard();
        bR = new BitBoard();
        bQ = new BitBoard();
        bK = new BitBoard();

        int i = 0;
        for (char c : boardString.toCharArray())
        {
            switch(c)
            {
                case 'P':
                    wP.setBit(i, true);
                    break;
                case 'N':
                    wN.setBit(i, true);
                    break;
                case 'B':
                    wB.setBit(i, true);
                    break;
                case 'R':
                    wR.setBit(i, true);
                    break;
                case 'Q':
                    wQ.setBit(i, true);
                    break;
                case 'K':
                    wK.setBit(i, true);
                    break;
                case 'p':
                    bP.setBit(i, true);
                    break;
                case 'n':
                    bN.setBit(i, true);
                    break;
                case 'b':
                    bB.setBit(i, true);
                    break;
                case 'r':
                    bR.setBit(i, true);
                    break;
                case 'q':
                    bQ.setBit(i, true);
                    break;
                case 'k':
                    bK.setBit(i, true);
                    break;
            }

            i++;
        }
    }    
    
    public static Square parseSquare(String squareString) throws ParseException
    {
        int file = -1;
        int rank = -1;
        if (squareString.length() != 2) throw new ParseException(squareString, 0);
            else {
                switch (squareString.toCharArray()[0])
                {
                    case 'a':
                        file = 0;
                        break;
                    case 'b':
                        file = 1;
                        break;
                    case 'c':
                        file = 2;
                        break;
                    case 'd':
                        file = 3;
                        break;
                    case 'e':
                        file = 4;
                        break;
                    case 'f':
                        file = 5;
                        break;
                    case 'g':
                        file = 6;
                        break;
                    case 'h':
                        file = 7;
                        break;
                    default:
                        throw new ParseException(squareString, 0);
                }

                try {
                    int rankIndex = Integer.parseInt(squareString.substring(1, 2));
                    if (rankIndex < 1 || rankIndex > 8) throw new ParseException(squareString, 1);

                    rank = rankIndex;
                } catch (ParseException e) {
                    throw e;
                }
            }

            return Square.values()[8 * rank + file];
    }

    public static Square stringToSquare(String squareString)
    {
        try {
            return parseSquare(squareString);

        } catch (ParseException e) {
            return null;
        }
    }

    public void setWhiteToMove(boolean value) {
        this.whiteToMove = value;
    }
    public boolean getWhiteToMove() {
        return whiteToMove;
    }
    public void setWhiteCastleKingside(boolean value) {
        wCastleKing = value;
    }
    public void setWhiteCastleQueenside(boolean value) {
        wCastleQueen = value;
    }
    public void setBlackCastleKingside(boolean value) {
        bCastleKing = value;
    }
    public void setBlackCastleQueenside(boolean value) {
        bCastleQueen = value;
    }

    public void setFullMoves(int n)
    {
        if (n >= 0) fullMoveCounter = n;
    }

    public void setHalfMoves(int n)
    {
        if (n >= 1) halfMoveCounter = n;
    }
    public void setEnPassantSquare(Square square)
    {
        this.enPassantSquare = square;
    }
}
