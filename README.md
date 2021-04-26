# midiTurtle
Turtle graphics project for CS349 using MIDI audio files.

In order to run the application, simply double click on the executable jar and you will be presented with a screen with multiple buttons
and a text field. Start first by entering the file path of any midi file you would like to run the application on. Click load. After
loading the file you can run and watch the animation by clicking play. Clicking pause will pause the animation and the song. Clicking
restart will rest everything as if you just clicked load. Finally, if you would like to save the created image, simply click export and
a .png file of the image will be saved in the same folder that the program is contained in.

Known bugs in the program are associated with the export button. Currently it exports the file to the bin folder within the jar when it is 
exported which is not accessible from the executable jar. Another bug is how the white line is traced over the black. It is supposed to be more smooth and drawn
out and not so choppy. We believe its becuase tweening doesnt look as smooth when its just drawing lines from point to point between key times.
