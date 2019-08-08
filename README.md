# NOTES
> Refer to __src/math/README.md__ for info on math library

> Some methods do not include Javadoc-styled description,
since I deemed it to be more confusing than helpful. For
example, Bounce::add(Ball), does not have one, since it
is incredibly descriptive on its own

> Actions accept a Triplet<Block, Paddle, Ball>, which is
a unified interface for any action, which you can assign
to ActionPaddle or ActionBlock (or ActionBall, if one existed).
The unused (or undefined) parameters, such as Block in a 
Ball-Paddle collision, can be passed as null, as long as
the action does not require it. This allowed me to create
a versatile Bounce game platform and easily create new
collision-activated power-ups

# README
* By Ramil Shaymardanov, rs380
* Sep. 2 - Sep.8
* ~10h
* Level description files, included in ./data. Plain text
file, where each line represents rows from top. Each line
contains arbitrary number of blocks, represented by a single
digit 0-6 (refer to BLOCK FILE SPECIFICATION). If more
blocks are added, than there is space on screen, the player
will never be able to complete a level. Name is a digit,
representing level number.
* The messages do not always show up consistently, after 
a level is finished
* Every time you miss the ball, all balls advance
* I enjoyed this assignment, especially freedom in
implementation.
  * I would love to use JavaFX event mechanics,
  in order to make action triggering more flexible, however,
  it was not necessary for this project, so I decided to skip
  it.
  * I would also like to have implemented support for
  collision between a ball and multiple blocks within
  one iteration, however, this happens so rarely, it was
  not worth it.
  * I could have also spent time trying to create message
  interface, to display splash, status, endgame screens. Its
  actually abominable, but I did not think it was worth it

#### BLOCK FILE SPECIFICATION

|id|description|hits|
|---|---|---|
|0|empty space|0|
|1|simple block|1|
|2|simple block|2|
|3|simple block|3|
|4|spawns 5 balls from paddle in random directions|1|
|5|ball destroys everything in it's path for 5 secs|1|
|6|speeds up the ball 2 times|1|