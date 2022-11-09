# projet-ia-echec

This is another school AI project for my study in UQAC. This time I should program an AI which is capable of playing chess.
Wenhao Luo, start at the 18 oct 2022 (after the vacation).

## note: 18 oct

### general tasks

Firstly the installation is done; the game can be run at my end. I don't want to upload the program itself to the git, since it should be available on the test computer.

Now there are multiples tasks to be done:

- [ ] find a way to input data into the system
- [ ] find a way to get the output. i.e. what is the move from arena
- [x] program in java to rebuild the scenery
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
- all the `Piece` are created once in a general way, and it will no longer contain the information of its coordination.

## note: 28 oct

The main part of `Board` and `Piece` is now finish, which means now all the pieces can be placed on the board and I am now able to know how to move them.

Next step:

- [x] write a function `move` to actually move a piece. This should return a new `Board` object;
- [x] write the functions `en passant` and `castling`;
- [ ] write an evaluation function which evaluate the board.

## note: 31 oct

Now the `move` `en passant` and `castling` are done. Still need the `evaluate` to finish this part.

### evaluation function

The evaluation function should enable the `Board` to know if one of the players is more or less advanced compare to the other. So a function like the following:

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
> 1 = a pawn
>
> 3 = a Knight or Bishop
>
> 5 = a Rook
>
> 9 = a Queen

## note: 1 nov

Since the part with rebuilding the scenario is done, I now search and add the [standard](./engine-interface.txt) of the engine interface to implement[^1].

[^1]: I found it [here](https://www.shredderchess.com/download.html).

According to the document,

> all communication is done via standard input and output with text commands

... input and output are done with `print`.

> the engine must always be able to process input from stdin, even while thinking

... multithread is needed.

>all command strings the engine receives will end with '\n',  also all commands the GUI receives should end with '\n',
>
> arbitrary white space between tokens is allowed
>
> if the engine or the GUI receives an unknown command or token it should just ignore it and try to parse the rest of the string in this line.
>
> if the engine receives a command which is not supposed to come, for example "stop" when the engine is not calculating, it should also just ignore it

... a simple parser is needed.

> The engine will always be in forced mode which means it should never start calculating or pondering without receiving a "go" command first
>
> The move format is in long algebraic notation.

... some other conventions.

### the interface to be done

#### input (to the engine)

```text
uci
debug [on | off]
isready
setoption name <id> [value <x>]
register
ucinewgame
position [fen <fenstring> | startpos ]  moves <move1> .... <movei>
go
stop
ponderhit
quit
```

#### output

```text
(not finish yet)
```

Considering it's a long program to be done, I will do it later.

## note: 8 nov

Time to start the AI. The thing is how to design the structure. Specially knowing that it should do it on multithread with instantaneous respond.

The main class should be something like this:

```java
class ChessAnalyser implements Runnable (Board b);
```

and a `run` function which enable the analyse. Once it's running, there should be a `getNext` function which give the best next move.

Because the function should always give a "not so bad" answer, search should be done in a more "flat" way. Maybe a `Queue` that do the analyse FIFO. For example, we check the move(s) in the following order:

```pseudocode
m1
m2
m3
...
m1-m2
m1-m3
m1-m4
...
// m2 can be ignored if minmax is done properly
...
m3-m1
...
```

for the actual tree structure, I can do it like this:

```java
class Node {
    Board currentBoard;
    TreeMap<Move, Node> moves;
    float score, min, max;
    void updateMin(float val);
    void updateMax(float val);
}
```

### wrapping

After that I still need an interface to actually do the work of communication. Which will also create the instance of `ChessAnalyser` for each new move. In the other word, each `ChessAnalyser` will only calculate one legal move.

## note: 09 nov

I tested the code that I wrote yesterday. While it seems to be working, it doesn't give a satisfaite result: there is 0 everywhere, due to the lack of a *heuristic function*. In the other word, since there is not much happening at the start of the game - let's say, for the first four moves there is rarely any piece is taken - then their score are all zeros. That's about $10^4$ zeros floating around, before the algorithme can merely do anything useful.

That's why I think it's necessary to add a function $h$, to calculate a hidden score showing the advantage/disadvantage of one player. Besides, I am not sure if the $\alpha - \beta$ works as I want. More tests are needing.
