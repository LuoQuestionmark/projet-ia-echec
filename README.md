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
