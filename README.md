# GPA Calculator Application

A simple Java application for GPA calculation.

## Features

- Add multiple courses with details (course name, code, credit, grade, teacher information)
- Calculates GPA based on entered courses and their grades
- JavaFX GUI: allows interactive course entry, editing, and deletion
- Displays total credits and calculated GPA
- Scene navigation for setup and award/certificate display

## Technologies Used

- Java (core language)
- JavaFX (for GUI)
- ControlsFX, FormsFX, ValidatorFX, Ikonli, BootstrapFX, TilesFX, FXGL (JavaFX modules and UI helpers)

## Installation

1. **Prerequisites:**
    - Java JDK 11 or newer
    - Maven or Gradle (for dependency management, if used)
    - JavaFX libraries and third-party modules (controlsfx, formsfx, validatorfx, etc.)

2. **Clone the Repository:**
   ```sh
   git clone https://github.com/ZainaRahman/Rahman_2207021_GPACalc.git
   cd Rahman_2207021_GPACalc
   ```

3. **Build and Run:**
    - Open the project in your IDE (e.g. IntelliJ IDEA, Eclipse)
    - Make sure JavaFX libraries are properly configured
    - Run the `Launcher` class

## Usage

- Start the application (run `Launcher.java`)
- Add course details using the table and input fields
- Enter required total credits
- After entering all courses, press the GPA button to calculate your GPA
- Navigate between scenes for setup and final results/awards

## Code Analysis

### Main Components

- **Launcher.java:**  
  Application entry point; launches the JavaFX `HelloApplication` class.

- **HelloApplication.java:**  
  Sets up the initial JavaFX scene window.

- **HelloController.java / Scene2Controller.java / AwardController.java:**  
  Controllers for scenes managing user interaction, course entry, GPA calculation, and award display.

- **CourseDetails.java:**  
  Model class for storing course info, credits, grades, codes, and teacher names. Uses JavaFX properties for data binding.

### GPA Calculation Logic

In `Scene2Controller`:
- Users input courses; each course has name, code, credit, grade, teacher1, teacher2.
- GPA is calculated as a weighted average:  
  `GPA = (Sum of course credits × grade points) / total credits`
  Grade points mapping from “A+” to “F” as per usual GPA rules.
- GPA button is enabled when entered credits match required credits.

#### UI Interactivity

- Users can add, edit, delete courses.
- Courses are displayed in a table and are editable.
- Award page displays all entered courses, credits, teachers, and calculated GPA.
