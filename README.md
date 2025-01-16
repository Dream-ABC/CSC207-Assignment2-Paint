# CSC207 Assignment 2: Paint Application

## Overview
This project is a **Paint Application** developed as part of the CSC207 Assignment 2. It is built using **JavaFX** for a modern and interactive graphical user interface (GUI). The application supports a wide range of drawing and editing features and emphasises object-oriented principles with the implementation of **Command**, **Factory**, and **Strategy** design patterns.

---

## Features
The Paint Application provides the following functionalities:

### **Drawing and Editing**
- **Drawing Multiple Shapes**: Supports various shapes (e.g., circles, rectangles, polylines, etc.).
- **Copy and Paste**: Duplicate and place existing shapes.
- **Select Tool**: Select and drag any shape(s) on the canvas to reposition it.
- **Erase Entire Strokes or Parts**: Erase either entire strokes or precise parts of a shape.
- **Text Input**: Add and edit text on the canvas.
- **Adjust Colours**: Customise the colour of shapes or text.
- **Change Line Thickness**: Modify the thickness of shape outlines.
- **Toggle Fill**: Choose between filled or outlined shapes.

### **Canvas and Layer Management**
- **Layer Management**: Organise and manage shapes in layers for complex designs.
- **Adjust Canvas Size**: Dynamically resize the canvas to fit your needs during a session.
- **Set `.png` as Background**: Load a PNG file from your system and set it as the background layer for the current layer of your canvas.

### **Undo/Redo**
- **Unlimited Undo/Redo**: Revert or reapply any number of changes made during the session.

### **File Operations**
- **Save as `.paint` File**: Save the entire canvas, including all shapes, layers, canvas size, and undo/redo history, for future editing.
- **Open `.paint` File**: Load previously saved files and continue editing, including undo/redo operations and canvas resizing.
- **Export as `.png`**: Save the canvas as a high-quality PNG image for sharing or publication.

---

## Design Patterns Used
### 1. **Command Pattern**
   - Enables actions like undo/redo by encapsulating requests as objects.
### 2. **Factory Pattern**
   - Simplifies the creation of various shape objects.
### 3. **Strategy Pattern**
   - Allows dynamic selection of behaviours such as erasing or moving shapes.

---

## How to Run the Application
1. Clone or download this repository to your local machine.
2. Open the `Assignment2` folder.
3. Navigate to the `src/main/java/ca/utoronto/utm/assignment2/paint/` directory.
4. Locate the `Paint.java` file.
5. Run the application by executing `Paint.java` using your preferred IDE or command line.

### **JavaFX Requirements**
This application uses **JavaFX** for its GUI:
- Ensure that **JavaFX SDK** is installed on your system.
- Add the JavaFX library to your project's dependencies. If using an IDE like IntelliJ IDEA or Eclipse, you can configure the JavaFX runtime arguments.

---

## Acknowledgements
This project was developed as part of the CSC207 Software Design course at the University of Toronto in Mississauga. It demonstrates the application of software design principles and design patterns in building robust and extensible applications.
