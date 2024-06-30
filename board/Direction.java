package coolfish.board;

public enum Direction {
    N,
    S,
    W,
    E,
    NE,
    NW,
    SE,
    SW,
    // Double north direction - for pawn moves on second rank
    NN,
    // Double south direction - for pawn moves on seventh rank
    SS,
    // Knight movement directions
    NNW,
    NWW,
    NNE,
    NEE,
    SSW,
    SWW,
    SSE,
    SEE
}
