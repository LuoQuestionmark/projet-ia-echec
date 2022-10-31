# projet-ia-echec

This is another school AI project for my study in UQAC. This time I should program an AI which is capable of playing chess.
Wenhao Luo, start at the 18 oct 2022 (after the vacation).

## note: 18 oct

### general tasks

Firstly the installation is done; the game can be run at my end. I don't want to upload the program itself to the git, since it should be available on the test computer.

Now there are multiples tasks to be done:

- [ ] find a way to input data into the system
- [ ] find a way to get the output. i.e. what is the move from arena
- [ ] program in java to rebuild the scenery
- [ ] program an AI to find the best move in the scenery

### input

It seems that to make a move into the system I just need to type in the keyboard, i.e. stdin should be good enough. I wonder if I can launch the program from something like `exec` after dup2 the stdin. Maybe I need to learn how to start the game as well.

Due to unable to understand what is exactly needed, I will do this part later.

### chess board in Java

Let us start by building a chess board in Java, so that our agent can at least "see" the board, and do some legal moves.

I assume that I need at least two `class` to do this part :

- a `Board` of size $8 \times 8$ that conclude all the pieces
- a `Piece` which is a general class of a piece
  - (some sub-class of pieces, of course)

The board should be able to tell what are the legal moves. Maybe an *evaluation function* as well.

#### Convention & Notation

The white player will occupy the "bottom" of the board, and the index will be counted from left to right, from "bottom" to "top".

| 7, 0 |      |      |      |      |      |      |      |
| ---- | ---- | ---- | ---- | ---- | ---- | ---- | ---- |
| 6, 0 |      |      |      |      |      |      |      |
| 5, 0 |      |      |      |      |      |      |      |
| 4, 0 |      |      |      |      |      |      |      |
| 0, 3 |      |      |      |      |      |      |      |
| 0, 2 |      |      |      |      |      |      |      |
| 0, 1 |      |      |      |      |      |      |      |
| 0, 0 | 1, 0 | 2, 0 | 3, 0 | 4, 0 | 5, 0 | 6, 0 | 7, 0 |

## note: 26 oct

Last time I have basically finished the implementation of the chess pieces and the chess board. However, I have realized that with the current implementation it will consume a large amount of memory if I try to add a new board, e.g. make a decision tree.

So I have come to a new implementation of the chess board:

- the board itself contains an array of `Piece`;
- each reference `null` if the case is empty, or a certain `Piece` object if it isn't;
- all the `Piece` are created once in a general way, and it will no longer contain the information of its coordinnation.

## note: 28 oct

The main part of `Board` and `Piece` is now finish, which means now all the pieces can be placed on the board and I am now able to know how to move them.

Next step:

- [x] write a function `move` to actually move a piece. This should return a new `Board` object;
- [x] write the functions `en passant` and `castling`;
- [ ] write an evaluation function which evaluate the board.

## note: 31 oct

Now the `move` `en passant` and `castling` are done. Still need the `evaluate` to finish this part.

### evalutation function

The evaluation function should enable the `Board` to know if one of the players is more or less avanced compare to the other. So a function like the following:

```java
double evaluate(); // return number from -inf to inf
```

is what I want.

#### Convention of evaluate function

- if the white wins, then the note should be $\inf$, if the black wins, then $-\inf$;
- if the board is perfectly even, then the score should be $0$;
- if the game is ended as even, then the score should be $0$.
- the more pieces one side has, the more score it gains;
- the more available moves one side has, the more score it gains.

#### Implementation

I think the score mentioned [here](https://support.chess.com/article/656-what-do-the-computer-evaluation-numbers-mean-like-225) should be a good kick starter.

Basically:
> 1 = a pawn <br>
> 3 = a Knight or Bishop <br>
> 5 = a Rook <br>
> 9 = a Queen
