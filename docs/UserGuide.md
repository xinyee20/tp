### Mark attendance for every student: `markAll`
Marks all students of a particular class for a single tutorial 
session as present.

Format:  `markAll TUTORIAL_GROUP LESSON_NUMBER`

Examples:
* `markAll G04 1-1`
* `markAll G09 7-2`

### Mark attendance for a single student as absent: `mark`

Marks a student of a particular class for a 
single tutorial session as absent.

Format: `mark TUTORIAL_GROUP LESSON_NUMBER NAME`

*TIP: Use `markAll` to mark all students as present first, then use
`mark` to mark just the students who are absent for a quick
and easy way to record attendance.

Examples:

* `mark G04 1-1 john`
* `mark G09 7-2 ryan`


### Flag attendance for a single student: `flag`

Flag attendance so you will be reminded to check again 
at the end of class.

Format: `flag TUTORIAL_GROUP LESSON_NUMBER NAME`

Examples:
 * flag G04 1-1 john
 * flag G09 7-2 ryan

### View attendance for each class: `attendance`

Provides a graphical view of all the student's 
attendance for a particular lesson.

Format: `attendance TUTORIAL_GROUP LESSON_NUMBER`

Examples:
 * attendance G04 1-1
 * attendance G09 7-2

### Exporting of attendance to CSV: `exportAtt`

Export the attendance for a particular tutorial group 
in a CSV file.

Format: `exportAtt TUTORIAL_GROUP`

File will be saved where your JAR file is located, with the file name
`TUTORIAL_GROUP.csv`.

Examples:
 * `exportAtt G04` saves the file as `G04.csv`
 * `exportAtt G09` saves the file as `G09.csv`

