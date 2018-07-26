# Captain Maze

Captain Maze is a maze-solving artificial intelligence. Captain Maze is a very simple AI, using a minimalistic custom genetic algorithm in order to find an exit to a maze.

## AI Technology
Captain Maze does **not** utilise a neural-network. Because of this, Captain Maze's learning process is not reflective of many other AI implementations. Essentially, Captain Maze learns through artificial Darwinism; it guesses multiple paths to take, and discards paths which lead it away from a goal node, resulting in the best paths being kept and reproduced with mutations.

## Background Technology
The maze game was hastily put together by me so that I could input an image file of a maze, and then that would be converted correspondingly into a maze that Captain Maze could play in. This underlying application is a Java Swing application.

## Purpose of this Project
It is common for genetic-algorithms to affect an existing neural-network to solve a problem using machine-learning. An example of this phenomenon is the Neural Evolution of Augmenting Topologies (NEAT). I was curious as to how effective a typical AI could be if it had only evolution at its side. No artificial learning, no weight adjustment, just pure evolution. Obviously, this is not nearly as effective as having a neural-network too, so this project was made purely to satisfy my curiosity as to whether they even function at all (which they do, poorly).