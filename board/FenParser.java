package coolfish.board;

import java.text.ParseException;

public class FenParser {
    public static Board parseFEN(String fen) throws ParseException
    {
        int index = 0;
        int strIndex = 0;
        String currentString;
        String boardString = "";

        while (index < 64)
        {
            currentString = fen.substring(strIndex, strIndex + 1);
            if (currentString.equals("/")) {
                strIndex += 1;
                continue;
            }

            try {
                int charToInt = Integer.parseInt(currentString);

                index += charToInt;
                strIndex++;
                boardString += "0".repeat(charToInt);
            } catch (NumberFormatException e) {
                boardString += currentString;
                strIndex++;
                index++;
            }
            
        }

        Board board = new Board(boardString);

        // Extra data containing info about move priority, castling, en passant and move count
        String extra[]  = fen.substring(strIndex, fen.length()).strip().split(" ");
        // The extra data is not complete, so an exception is thrown
        if (extra.length < 5) throw new ParseException("Incomplete metadata", strIndex);

        // Get move priority
        if (extra[0].equals("w"))
        {
            board.setWhiteToMove(true); 
        }
        else if (extra[0].equals("b"))
        {
            board.setWhiteToMove(false); 
        }

        // Get castling rights
        for (char currentChar : extra[1].toCharArray())
        {
            if (currentChar == 'K') board.setWhiteCastleKingside(true);
            else if (currentChar == 'Q') board.setWhiteCastleQueenside(true);
            else if (currentChar == 'k') board.setBlackCastleKingside(true);
            else if (currentChar == 'q') board.setBlackCastleQueenside(true);
        }

        if (!extra[2].equals("-"))
        {
            try {
                board.setEnPassantSquare(Board.parseSquare(extra[2]));
            } catch (ParseException e) {
                throw e;
            } 
        }

        // Try to parse an integer containing the number of full moves
        try {
            board.setFullMoves(Integer.parseInt(extra[3]));
        } catch (NumberFormatException e) {
            throw e;
        } 

        // Try to parse an integer containing the number of full moves
        try {
            board.setHalfMoves(Integer.parseInt(extra[4]));
        } catch (NumberFormatException e) {
            throw e;
        } 

        return board;
    }
}
