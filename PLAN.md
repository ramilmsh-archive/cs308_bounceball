# Plan

[SuperBreakout by Atatri](https://www.atari.com/arcade#!/arcade/superbreakout/play)
I found it interesting, because it had three modes, one of
which had two balls, trapped inside the blocks, therefore,
by destroying first layer, you could get up to three balls.
Another had the blocks move down, every time you missed the
ball, and the third one had two paddles. Also, the angle of
reflection of the ball did not depend on the angle of
incidence, but rather on contact location.

I plan to add more blocks and increase ratio of blocks
with superpowers (some of which can have adverse effects,
such as ball speeding up and requiring several hits to destroy),
as well as increasing the ball speed and positioning the
blocks in uneven shape, so that it is harder to predict the
balls' movement

The levels will be stored in the text files, the file
specification is provided below
#### BLOCK FILE SPECIFICATION

|id|description|hits|
|---|---|---|
|0|empty space|0|
|1|simple block|1|
|2|enhanced block|2|
|3|enhanced block|3|
|4|spawns 5 balls from paddle in random directions|1|
|5|ball destroys everything in it's path for 5 secs|1|
|6|speeds up the ball 2 times|1|

Cheat Keys:
* R - reset
* L - add lives
* 0-2 - select level
* M (for madness) - spawn 10000 balls (because why not?)


## Additional
Make the blocks advance every time you miss the ball