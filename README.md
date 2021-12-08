# Practical 4: Vector Drawing

University of St. Andrews CS5001 Object Oriented Design, Modeling & Programming Course.
This project features Vector Drawing System using a Java Swing GUI. It uses a Model-View-Controller architectural pattern. 

### Implemeted Requirements:

[BASIC]
- Drawing straight lines.
- Drawing rectangles.
- Drawing ellipses.
- Drawing diagonal crosses.
- Undo/redo.
- Different colours.
- Create JUNIT TEST for Model functionalities.

[ADVANCED]
- Support for drawing squares and circles. One way of implementing this feature would be
  using a key (say the Shift key) to lock aspect ratio during the drawing of rectangles and
  ellipses. 
- Load and save vector drawings in a format that permits them to be manipulated as vector
  drawings after loading.
- Add Murray polygons.


### Run Code

~~~
$ cd CS5001-p4-vector-drawing/src/
$ javac *.java
$ java VectorDrawingMain
~~~

### To run tests (Using St Andrews stacscheck)

~~~
$ cd CS5001-p4-vector-drawing/
$ stacscheck ./Tests
~~~

