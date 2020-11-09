---
layout: page
title: User guide
---

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **1. Introduction**

![Serenity Logo](images/logo.png)

Welcome to the User Guide of **Serenity**!

Are you a tutor teaching CS2101 in NUS? Do you struggle to keep track of students' attendance and class participation
on many different excel sheets for different lessons? Do you also tend to forget to address questions students asked
during lessons? Fret not, our application, **Serenity**, will help keep you sane when managing tutorial groups.

**Serenity** is a desktop application that helps CS2101 tutors manage their lessons. This application is optimized
for use through a <i>Command Line Interface (CLI)</i>, meaning that you operate the application by typing commands into a
command box. This application also has a <i>Graphical User Interface (GUI)</i>, 
where you can interact with the application through graphical icons such as buttons.

This user guide serves to provide you with an in-depth documentation on how to set up and use our application.
With that said, let's get [started](#2-quick-start)!

--------------------------------------------------------------------------------------------------------------------

## **2. Quick start**

To get started with using **Serenity**, you can follow these steps:

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `Serenity.jar` from [here](https://github.com/AY2021S1-CS2103T-W12-4/tp/releases).

3. Copy the file to the folder you want to use as the home folder for your **Serenity** application.

4. Double-click the file to start the app. The _GUI_ similar to the image below should appear in a few seconds.

   <p align="center">

   <img src="images/userGuide/Gui.png" alt="Layout of GUI" border="1px solid black">
   <i>Figure 2.1 Welcome screen.</i>

   </p>

5. Type the command in the _Command Box_ and press `Enter` to execute it. 

6. Refer to the [Features](#4-features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## **3. About**

### 3.1. Structure of this document

This User Guide have been structured such that you can easily find what you need.

In [Section 3.2](#32-reading-this-document), you can find useful tips on reading this document.

In [Section 4](#4-features), you can find details of the main features of **Serenity**:

* Setting up
* Attendance taking
* Participation marking
* Question addressing
* Utility viewing

### 3.2. Reading this document 

In this subsection, you can find the explanation of the technical terms, symbols and syntax that are used in this
User Guide.

#### 3.2.1. Terminology related to the _GUI_

The following figure shows the _GUI_ of **Serenity**. It is annotated with the name of the _GUI_ components.

<p align="center">

<img src="images/userGuide/GuiAnnotated.png" alt="Layout of GUI">
<i>Figure 3.2.1.1 Annotated GUI of <b>Serenity</b>.</i>
    
</p>

What the 6 main components of the _GUI_ do:

* _Menu Bar_: This is where you can access our User Guide and exit the application.

* _Side Bar_: This is where you can easily access flagged attendance records, pending questions and 
existing tutorial groups.

* _Title Display_: This is where **Serenity** displays the title of the page that you are viewing.

* _Data Display_: This is where **Serenity** displays the information of the page that you are viewing.

* _Result Display_: This is where **Serenity** displays guiding messages.

* _Command Box_: This is where you type your commands.

#### 3.2.2. Layout of the _GUI_

**Serenity** is divided into these 3 pages:

* Home page
* Tutorial group page
* Tutorial lesson page

##### Home page

This is the default page shown when you start **Serenity**. This page consists of:

* **Flagged attendance tab**: this tab contains the names of students whose attendance have been flagged with
the `flagatt` command. Flagging a student's attendance serves as a reminder on the first page of the app
for you to check on the student and perform any follow-up actions, if necessary. 

* **Pending questions tab**: this tab shows the questions asked by students across all your tutorial groups,
providing a convenient place for you to keep track of all the questions asked by students
across your tutorial groups and lessons.

<p align="center">

<img src="images/userGuide/pages/HomePage.png" alt="Home page of Serenity" border="1px solid black">
<i>Figure 3.2.2.1 Home page of <b>Serenity</b>.</i>

</p>

##### Tutorial group page

This page shows the information related to a tutorial group you are teaching. This page consists of: 

* **Lessons tab**: this tab allows you to view the list of lessons scheduled for this group.

* **Students tab**: this tab displays the list of students belonging in this group. You may modify this list,
especially at the start of each semester where there will be students entering the module,
appealing into the module, switching between tutorial groups, and even dropping the module.

* **Attendance tab**: this tab shows the attendance sheet of the students. You may view the attendance records
of all the students in the tutorial group across all the tutorial lessons.

* **Participation tab**: this tab shows the participation score sheet of the students. Similarly, you may view
the class participation grades of all the students in the tutorial group across all the tutorial lessons.

<p align="center">

<img src="images/userGuide/pages/GroupPage.png" alt="Group page of Serenity" border="1px solid black">
<i>Figure 3.2.2.3 Group page of <b>Serenity</b>.</i>

</p>

##### Tutorial lesson page

This page shows the records for a specific lesson. This page consists of:

* **Students tab**: this tab contains the list of students in the tutorial group. For each student, you may
view and change the student's [attendance](#42-attendance-taking) and [participation](#43-participation-marking) grades.
You may also flag the student's attendance if necessary.

* **Questions tab**: this tab contains the list of questions that were asked during this tutorial lesson. In this tab,
you may add, edit, find or delete a [question](#44-question-addressing) that was asked during this tutorial lesson.

<p align="center">

<img src="images/userGuide/pages/LessonPage.png" alt="Lesson page of Serenity" border="1px solid black">
<i>Figure 3.2.2.4 Lesson Page of <b>Serenity</b>.</i>

</p>

#### 3.2.3. General symbols and syntax

The table below explains the general symbols and syntax used throughout the document.

Symbol/syntax | Meaning
--------|------------------
`command` | This indicates a technical term, which you can find out more in [Command syntax and usage](#323-command-syntax-and-usage).
_GUI_ | This indicates a graphical component, such as a button, layout format and text input box.
:information_source: | This indicates a note.
:bulb: | This indicates a tip.
:warning: | This indicates a warning.

#### 3.2.4. Command syntax and usage

The table below explains some important technical terms.

Technical term | Meaning
--------|------------------
Command | The instruction that the user types into the command box for **Serenity** to perform a specific task.
Command word | The first word of the command that tells **Serenity** which task should be performed.
Parameter | The word or phrase following the command word that provides further details of the task (if necessary).
Prefix | The letter that is placed at the start of each parameter to distinguish one parameter from another.
XLSX | The excel file format that **Serenity** handles for imports and exports.

#### 3.2.5. Command format

You can use commands to tell **Serenity** to perform a specific task.

**Format**:

`COMMAND_WORD prefix/PARAMETER`

<div markdown="block" class="alert alert-info">

:information_source: **Notes regarding the command format:**

* Words in upper case are the parameters to be supplied by you.<br>
  e.g. in `viewgrp grp/GROUP_NAME`, `GROUP_NAME` is a parameter which can be used as `viewgrp grp/G01`
* Parameters have to be in the specified order.<br>
  e.g. if the command specifies `grp/GROUP_NAME lsn/LESSON_NAME`, `lsn/LESSON_NAME grp/GROUP_NAME` is not acceptable.

</div>

**Example:**

If you need to add a new tutorial lesson, you can type the following command.

`addlsn grp/GROUP_NAME lsn/LESSON_NAME`

Analysis:

* Command: Add a tutorial lesson

* Command Word: `addlsn`

* Parameter: `GROUP_NAME`, `LESSON_NAME`

* Prefix: `grp/`, `lsn/`

--------------------------------------------------------------------------------------------------------------------

## **4. Features**

This section contains all the information you need to know about the features of **Serenity**.
To use each feature or sub-feature, you will need to enter the command into the _Command Box_.
Below is an overview of the features available in **Serenity** :

1. [Setting up](#41-setting-up) feature provides you the tools to set up your tutorial groups and lessons.
2. [Attendance taking](#42-attendance-taking) feature provides you the tools to mark attendance for your lessons.
3. [Participation marking](#43-participation-marking) feature provides you the tools to grade student participation. 
4. [Question addressing](#44-question-addressing) feature provides you the tools to keep track of questions asked by students.
5. [Utility viewing](#45-utility-viewing) feature provides you the tools to view the information of the above features.

### 4.1. Setting Up

It is the start of the semester! Brand-new classes, brand-new students and a whole new experience.
Follow the instructions below to set up your new classes, and watch **Serenity** do the magic for you.

#### 4.1.1. Add a new tutorial group from XLSX file: `addgrp`

You can use this command to add a new tutorial group automatically from XLSX data.

<div markdown="block" class="alert alert-info">

:information_source: **Notes regarding the `addgrp` command:**

* A sample XLSX file can be found [here](https://github.com/AY2021S1-CS2103T-W12-4/tp/blob/master/CS2101_G04.xlsx)
* The XLSX file should store a list of students.
* The list of students you are importing cannot exist in another tutorial group.
* The format of the XLSX file should be the same as the XLSX file that you may download from LUMINUS. 
Examples of valid and invalid file formats can be found in the [XLSX file format documentaion](/XlsxFileFormat.md)
* To obtain the PATH_TO_XLSX, either:<br>
   * Get the location of the XLSX file in your computer.<br>
     e.g. `addgrp grp/G04 path/C:\Users\serene\CS2101_G04.xlsx`<br>
   * Copy and paste the XLSX file into the same folder as your **Serenity** application, then type the name of the XLSX file.<br>
     e.g. `addgrp grp/G04 path/CS2101_G04.xlsx`

</div>

<div markdown="block" class="alert alert-primary">

:bulb: **Tip for the `addgrp` command:**

After adding a tutorial group, a button will be created. It allows you to easily navigate to the group in the future,
as pressing the button equals to typing the `viewgrp grp/GROUP_NAME` command.

</div>

**Format:**

`addgrp grp/GROUP_NAME path/PATH_TO_XLSX`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:**
GROUP_NAME follows the format 'XYY' where X is an alphabetical character and Y is a digit from 0 to 9. 

</div>

**Example:**

You are adding a new tutorial group named `G04` to prepare for a new semester.
The XLSX data of the tutorial group is stored in the same folder with the Excel file named `CS2101_G04.xlsx`.

Adding a new tutorial group:

1. Type `addgrp grp/G04 path/CS2101_G04.xlsx` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. At the same time, a new button named `G04` will be created in the _Side Bar_.
3. **Serenity** will switch to tutorial group `G04` page.
4. You can now see the list of students and a default list of tutorial lessons for tutorial group `G04`.

<p align="center">

<img src="images/userGuide/settingUp/addgrp_command.png" alt="Command box for addgrp command">
<i>Figure 4.1.1.1 Command for <code>addgrp</code> command.</i>

</p>

<p align="center">

<img src="images/userGuide/settingUp/addgrp_result.png" 
     alt="Result upon successful execution of command" border="1px solid black">
<i>Figure 4.1.1.2 Screen displayed when the <code>addgrp</code> command is successfully executed.</i>

</p>

####  4.1.2. Delete an existing tutorial group: `delgrp`

You can use this command to delete an existing tutorial group.

<div markdown="block" class="alert alert-info">

:information_source: **Notes regarding the `delgrp` command:**

After deleting a tutorial group, the button named after the tutorial group will be deleted as well.

</div>

**Format:**

`delgrp grp/GROUP_NAME`

**Example:**

You accidentally added the wrong tutorial group `G01` and you decided to remove the tutorial group.

Deleting an existing tutorial group:

1. Type `delgrp grp/G01` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. The button named `G01` will be removed from the _Side_Bar_.
2. **Serenity** will exit tutorial group `G01` page.
3. You can now see that tutorial group `G01` no longer exists.

#### 4.1.3. Add a new tutorial lesson: `addlsn`

You can use this command to add a new tutorial lesson for a specified tutorial group.

**Format:**

`addlsn grp/GROUP_NAME lsn/LESSON_NAME`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:**
LESSON_NAME follows the format 'XX-Y' where X is the week number, and Y is the lesson number. 

</div>

**Example:**

You want to create a new tutorial lesson called `1-3` (week 1, lesson 3) for tutorial group `G01`.

Adding a tutorial lesson to a tutorial group:

1. Type `addlsn grp/G01 lsn/1-3` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. **Serenity** will display tutorial lesson `1-3` page.
3. You can now see that tutorial lesson `1-3` has been added to tutorial group `G01`.

<p align="center">

<img src="images/userGuide/settingUp/addlsn_result.png" 
    alt="Result upon successful execution of command" border="1px solid black">
<i>Figure 4.1.3.1 Screen displayed when the <code>addlsn</code> command is successfully executed.</i>

</p>

#### 4.1.4. Delete an existing tutorial lesson: `dellsn`

You can use this command to delete an existing lesson from a specified tutorial group.

**Format:**

`dellsn grp/GROUP_NAME lsn/LESSON_NAME`

**Example:**

You accidentally added the wrong tutorial lesson `1-1` for tutorial group `G01` and you decided to remove it.

Deleting an existing lesson from a tutorial group:

1. Type `dellsn grp/G01 lsn/1-1` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message. 
2. **Serenity** will exit tutorial lesson `1-1` page and enter tutorial group `G01` page.
3. You can now see that tutorial lesson `1-1` no longer exists.

#### 4.1.5. Add a new student: `addstudent`

You can use this command to add a new student to a tutorial group.

**Format:**

`addstudent grp/GROUP_NAME name/STUDENT_NAME matric/STUDENT_NUMBER`

**Example:**

A new student named `David Chong` with student number `A0123456D` entered your tutorial group,
and you want to add him to tutorial group `G01`.

Adding a new student to a tutorial group:

1. Type `addstudent grp/G01 name/David Chong matric/A0123456D` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. `David Chong` will be added to the list of students in tutorial group `G01`.

#### 4.1.6. Delete an existing student: `delstudent`

You can use this command to delete an existing student from a tutorial group.

**Format:**
<br>`delstudent grp/GROUP_NAME name/STUDENT_NAME matric/STUDENT_NUMBER`
<br>`delstudent INDEX grp/GROUP_NAME`

**Example:**

A student named `Aaron Tan` with student number `A0123456A` dropped the CS2101 module,
and you need to remove him from the tutorial group `G01`. `Aaron Tan` is the first person on the student list
so his `index` number is 1.

<ins>Method 1</ins> <br>
Deleting an existing student from a tutorial group by `name` and `student number`:

1. Type `delstudent grp/G01 name/Aaron Tan matric/A0123456A` into the _Command Box_.
2. Press `Enter` to execute.

<ins>Method 2</ins> <br>
Deleting an existing student from a tutorial group by `index` number:

1. Type `delstudent 1 grp/G01` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. `Aaron Tan` will no longer be in the list of students in tutorial group `G01`.

<p align="center">

<img src="images/userGuide/settingUp/delstudent_result.png" 
    alt="Result upon successful execution of command" border="1px solid black">
<i>Figure 4.1.6.1 Screen displayed when the <code>delstudent</code> command is successfully executed.</i>

</p>

### 4.2. Attendance taking

Need to mark attendance of your students at the start of each lesson quickly?
This feature allows you to mark attendance of students easily.

#### 4.2.1. Mark all students as present: `markpresent all`

You can use this command to mark all students in a tutorial group as present for a tutorial lesson.

<div markdown="block" class="alert alert-primary">

:bulb: **Tip for the `markpresent all` command:**

You can conveniently mark all students as present at once in the beginning of the tutorial lesson,
then mark some students as absent afterwards. You can then start your tutorial lesson without much hassle.

</div>

**Format:** 

`markpresent all`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:** 
You must navigate to the tutorial lesson page through `viewlsn` before this command can be used.

</div>

**Example:**

All students in group `G01` are present for your tutorial lesson `1-2` of tutorial group `G01`. 

Marking all students in a tutorial group as present for a tutorial lesson:

To mark all of them present at once:
1. Navigate to view group `G01` lesson `1-2` via [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `markpresent all` into the *Command Box*.
3. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. You can now see that all the students' attendance have been updated on the _Student Information Panel_.

#### 4.2.2. Mark a student as present: `markpresent`

You can use this command to mark a specific student as present for a tutorial lesson.

**Format:**
<br>`markpresent name/STUDENT_NAME matric/STUDENT_NUMBER`
<br>`markpresent INDEX`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:** You must navigate to the tutorial lesson page through `viewlsn` before this command can be used.

</div>

**Example:**

A student named `Aaron Tan` with student number `A0123456A` is present for your tutorial lesson `1-2` of tutorial group `G01`.
`1-2` of tutorial group `G01`. `Aaron Tan` is the first person on the student list so his `index` number is 1.

<ins>Method 1</ins> <br>
Marking a student as present for a tutorial lesson by `name` and `student number`:

To mark him present:
1. Navigate to view group `G01` lesson `1-2` via [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `markpresent name/Aaron Tan matric/A0123456A` into the *Command Box*.
3. Press `Enter` to execute.

<ins>Method 2</ins> <br>
Marking a student as present for a tutorial lesson by `index` number:

To mark him present:
1. Navigate to view group `G01` lesson `1-2` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `markpresent 1` into the *Command Box*.
3. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. You can now see that his attendance has been updated on the _Student Information Panel_.

<p align="center">

<img src="images/userGuide/attendanceTaking/markpresent_result.png" 
     alt="Result upon successful execution of command" border="1px solid black">
<i>Figure 4.2.2.1 Screen displayed when the command is successfully executed.</i>

</p>

#### 4.2.3. Mark a student as absent: `markabsent`

You can use this command to mark a specific student as absent for a tutorial lesson.

**Format:**
<br>`markabsent name/STUDENT_NAME matric/STUDENT_NUMBER`
<br>`markabsent INDEX`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:** You must navigate to the tutorial lesson page through `viewlsn` before this command can be used.

</div>

**Example:**

A student named `Aaron Tan` with student number `A0123456A` is absent for your tutorial lesson `1-2` of tutorial group `G01`.
`Aaron Tan` is the first person on the student list so his `index` number is 1. 

<ins>Method 1</ins> <br>
Marking a student as absent for a tutorial lesson by `name` and `student number`:

1. Navigate to view group `G01` lesson `1-2` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `markabsent name/Aaron Tan matric/A0123456A` into the *Command Box*.
3. Press `Enter` to execute.

<ins>Method 2</ins> <br>
Marking a student as absent for a tutorial lesson by `index` number:

1. Navigate to view group `G01` lesson `1-2` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `markabsent 1` into the *Command Box*.
3. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. You can now see that his attendance has been updated on the _Student Information Panel_.

<p align="center">

<img src="images/userGuide/attendanceTaking/markabsent_result.png" 
     alt="Result upon successful execution of command" border="1px solid black">
<i>Figure 4.2.3.1 Screen displayed when the command is successfully executed.</i>

</p>

#### 4.2.4. Mark all students as absent: `markabsent all`

Accidentally marked all the students as present and want to make them back absent? You can use this command to mark 
all students in a tutorial group back as absent for a tutorial lesson.

**Format:** 
`markabsent all`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:** You must navigate to the tutorial lesson page through `viewlsn` before this command can be used.

</div>

**Example:**

All students in group `G01` are absent for your tutorial lesson `1-2`. 

Marking all students in a tutorial group as absent for a tutorial lesson:

1. Navigate to view group `G01` lesson `1-2` via [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `markabsent all` into the *Command Box*.
3. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. You can now see that all the students' attendance have been updated on the _Student Information Panel_.

#### 4.2.5. Flag attendance of a student: `flagatt`

You can use this command to flag attendance of a specific student so you can remember to check the student's
attendance at the end of class.

**Format:**
<br>`flagatt name/STUDENT_NAME matric/STUDENT_NUMBER`
<br>`flagatt INDEX`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:** You must navigate to the tutorial lesson page through `viewlsn` before this command can be used.

</div>

**Example:**

A student named `Aaron Tan` with student number `A0123456A` from your tutorial group `G01` decided to join
another group for tutorial lesson `1-2`. You want to flag his attendance so that you can remember to check with the 
other tutor later on. `Aaron Tan` is the first person on the student list so his `index` number is 1. 

<ins>Method 1</ins> <br>
Flagging a student's attendance for a tutorial lesson by his `name` and `student number`:

1. Navigate to view group `G01` lesson `1-2` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `flagatt name/Aaron Tan matric/A0123456A` into the _Command Box_.
3. Press `Enter` to execute.

<ins>Method 2</ins><br>
Flagging a student's attendance for a tutorial lesson by his `index` number:

1. Navigate to view group `G01` lesson `1-2` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `flagatt 1` into the _Command Box_.
3. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. His attendance will now be flagged for future follow-up actions.

#### 4.2.6. Unflag attendance of a student: `unflagatt`

You can use this command to unflag attendance of a specific student that was flagged after you have checked whether the 
student attended a replacement class. If you accidentally flagged the wrong student, the action can also be easily undone 
using `unflagatt` command.

**Format:**
<br>`unflagatt name/STUDENT_NAME matric/STUDENT_NUMBER`
<br>`unflagatt INDEX`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:** You must navigate to the tutorial lesson page through `viewlsn` before this command can be used.

</div>

**Example:**

A student named `Aaron Tan` with student number `A0123456A` from your tutorial group `G01` decided to go for 
another tutorial class for lesson `1-2`. You previously flagged his attendance and you now want to unflag it
after checking that he has attended the replacement class. `Aaron Tan` is the first person on the student list 
so his `index` number is 1. 

<ins>Method 1</ins> <br>
Unflagging a student's attendance for a tutorial lesson by his `name` and `student number`:

1. Navigate to view group `G01` lesson `1-2` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `unflagatt name/Aaron Tan matric/A0123456A` into the _Command Box_.
3. Press `Enter` to execute.

<ins>Method 2</ins><br>
Unflagging a student's attendance for a tutorial lesson by his `index` number:

1. Navigate to view group `G01` lesson `1-2` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `unflagatt 1` into the _Command Box_.
3. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. His attendance will now be unflagged.

#### 4.2.7. Export attendance as XLSX file: `exportatt`

You can use this command to export the attendance sheet of a specific tutorial group as a new Excel file.

<div markdown="block" class="alert alert-primary">

:bulb: **Tip for the `exportatt` command:**

You can conveniently obtain the soft copy version of the attendance sheet for
attendance list submission as part of NUS Centre for English Language Communication requirements.

</div>

<div markdown="block" class="alert alert-info">

:information_source: **Notes regarding the `exportatt` command:**

The attendance sheet will be saved as a XLSX file named after the tutorial group.
The file will be saved at the same folder as your **Serenity** application.

</div>

**Format**:

`exportatt grp/GROUP_NAME`

**Example**:

You want to export the attendance sheet of tutorial group `G01`.

Exporting attendance sheet of a tutorial group:
1. Type `exportatt grp/G01` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:
1. The _Result Display_ will show a success message.
2. The attendance sheet of tutorial group `G01` will be saved as `G01_attendance.xlsx`.
You can find the XLSX file at the location where your **Serenity** application is located.

<p align="center"><img src="images/userGuide/attendanceTaking/exportatt_command.png" 
   alt="Command box for exportatt command"></p>
<p align="center"><i>Figure 4.2.7.1 Command for <code>exportatt</code> command.</i></p>

<p align="center"><img src="images/userGuide/attendanceTaking/exportatt_result.png" 
   alt="Result upon successful execution of command" border="1px solid black"></p>
<p align="center"><i>Figure 4.2.7.2 Contents of excel file generated.</i></p>

### 4.3. Participation marking

Need to keep track of all your students' participation during lessons quickly? This feature allows you to key in a 
student's participation score easily with a scale for reference.

Participation score scale from 1 to 5:

 | Score   | 1 | 2 | 3 | 4 | 5 |
 |---------|---|---|---|---|---|
 | **Remarks** |Very Poor|Poor|Sufficient|Good|Commendable|

#### 4.3.1. Add participation score of a student: `addscore`

You can use this command to increase the participation score of a specific student for a tutorial lesson.

<div markdown="block" class="alert alert-danger">

:warning: **Warning regarding the `addscore` command:**

You cannot increase the participation score of a student such that the final score is more than 5.
The maximum score is 5.

</div>

**Format:**
<br>`addscore name/STUDENT_NAME matric/STUDENT_NUMBER score/SCORE_TO_ADD`
<br>`addscore INDEX score/SCORE_TO_ADD`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:** You must navigate to the tutorial lesson page through `viewlsn` before this command can be used.

</div>

**Example:**

In tutorial lesson `1-2` of tutorial group `G01`,
you felt that your student named `Aaron Tan` with student number `A0123456A` provided some good insights during the lesson. 
You decided to add `1` mark to his participation score for this tutorial lesson. `Aaron Tan` 
is the first person on the student list so his `index` number is 1.

<ins>Method 1</ins> <br>
Adding participation score of a student in a tutorial lesson by `name` and `student number`:

1. Navigate to view tutorial lesson `1-2` of tutorial group `G01` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `addscore name/Aaron Tan matric/A0123456A score/1` into the _Command Box_.
3. Press `Enter` to execute.

<ins>Method 2</ins> <br>
Adding participation score of a student in a tutorial lesson by `index`number:

1. Navigate to view tutorial lesson `1-2` of tutorial group `G01` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `addscore 1 score/1` into the _Command Box_.
3. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. You can now see that his score has been updated on the _Student Information Panel_.

<p align="center"><img src="images/userGuide/participationMarking/addscore_result.png" 
   alt="Result upon successful execution of command" border="1px solid black"></p>
<p align="center"><i>Figure 4.3.1.1 Screen displayed when the command is successfully executed.</i></p>

#### 4.3.2. Subtract participation score of a student: `subscore`

You can use this command to decrease the participation score of a specific student for a tutorial lesson.

<div markdown="block" class="alert alert-danger">

:warning: **Warning regarding the `subscore` command:**

You cannot decrease the participation score of a student such that the final score is less than 0.
The minimum score is 0.

</div>

**Format:**

<br>`subscore name/STUDENT_NAME matric/STUDENT_NUMBER score/SCORE`
<br>`subscore INDEX score/SCORE`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:** You must navigate to the tutorial lesson page through `viewlsn` before this command can be used.

</div>

**Example:**

In tutorial lesson `1-2` of tutorial group `G01`,
you felt that your student named `Aaron Tan` with student number `A0123456A` responses were actually not very insightful 
on second thoughts. You decided to subtract `1` mark from his participation score for this tutorial lesson. `Aaron Tan` 
is the first person on the student list so his `index` number is 1.

<ins>Method 1</ins> <br>
Subtracting participation score of a student in a tutorial lesson by `name` and `student number`:

1. Navigate to view tutorial lesson `1-2` of tutorial group `G01` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `subscore name/Aaron Tan matric/A0123456A score/1` into the _Command Box_.
3. Press `Enter` to execute.

<ins>Method 2</ins> <br>
Subtracting participation score of a student in a tutorial lesson:

1. Navigate to view tutorial lesson `1-2` of tutorial group `G01` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `subscore 1 score/1` into the _Command Box_.
3. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. You can now see that his score has been updated on the _Student Information Panel_.

#### 4.3.3. Edit participation score for a student: `editscore`

You can use this command to edit participation score of a specific student in a tutorial lesson.

**Format:**
<br>`editscore name/STUDENT_NAME matric/STUDENT_NUMBER score/SCORE`
<br>`editscore INDEX score/SCORE`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:** You must navigate to the tutorial lesson page through `viewlsn` before this command can be used.

</div>

**Example:**

In tutorial lesson `1-2` of tutorial group `G01`,
you noticed that your student named `Aaron Tan` with student number `A0123456A` participated actively in class 
discussions for the lesson. You decided to edit his current score to `3` marks for this lesson. 
`Aaron Tan` is the first person on the student list so his `index` number is 1.

<ins>Method 1</ins> <br>
Edit participation score of a student in a tutorial lesson by `name` and `student number`:

1. Navigate to view group `G01` lesson `1-2` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `editscore name/Aaron Tan matric/A0123456A score/3` into the *Command Box*.
3. Press `Enter` to execute.

<ins>Method 2</ins> <br>
Edit participation score of a student in a tutorial lesson by `index` number:

1. Navigate to view group `G01` lesson `1-2` via  [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
2. Type `editscore 1 score/3` into the *Command Box*.
3. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. You can now see that his score has been updated on the _Student Information Panel_.

#### 4.3.4. Export participation scores as XLSX file: `exportscore`

You can use this command to export the participation score sheet of a specific tutorial group as a new Excel file.

<div markdown="block" class="alert alert-info">

:information_source: **Notes regarding the `exportscore` command:**

The participation score sheet will be saved as a XLSX file named after the tutorial group.
The file will be saved at the same folder as your **Serenity** application.
    
</div>

**Format:**

`exportscore grp/GROUP_NAME`

**Example:**

You want to collate the participation scores of tutorial group `GO1` into a XLSX file for grading on a 
different platform (i.e. LumiNUS).

Exporting participation score sheet of a tutorial group:

1. Type `exportscore grp/G01` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. The participation score sheet of tutorial group `G01` will be saved as `G01_participation.xlsx`.
You can find the XLSX file at the location where your **Serenity** application is located.

### 4.4. Question addressing

Need to keep track of all your students' questions during lessons quickly? This feature allows you to key in questions
easily for future reference.

#### 4.4.1. Add a new question: `addqn`

You can use this command to add a question that was asked in a tutorial lesson, so that you can address the question
in the future.

**Format:**

`addqn qn/QUESTION`

<div markdown="block" class="alert alert-danger">

:warning: **Warning:** You must navigate to the tutorial lesson page through `viewlsn` before this command can be used.

</div>

**Example:**

A student asked you, "`What is the deadline for the report?`" during the lesson `1-1` in group `G01`.
As the deadline of the report has changed, you want to note this question down, check the new deadline and
address the question in the future. 

Adding a question:

1. Navigate to view group `G01` lesson `1-1` via [`viewlsn`](#452-view-a-tutorial-lesson-viewlsn) command.
1. Type `addqn qn/What is the deadline for the report?` into the _Command Box_.
1. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. The question will be added to the list of questions.

<p align="center"><img src="images/userGuide/questionAddressing/addqn_result.png" 
   alt="List of questions after command is executed." border="1px solid black"></p>
<p align="center"><i>Figure 4.4.1.1 List of questions after the command is successfully executed.</i></p>

#### 4.4.2. Delete an existing question: `delqn`

You can use this command to delete a question after addressing it.

**Format:**

`delqn INDEX`

**Example:**

You addressed one of the questions at the end of a tutorial lesson and you want to delete the question from the
list of questions.

Deleting a question:

1. Type `delqn 1` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. The question will be deleted from the list of questions.

#### 4.4.3. Edit an existing question: `editqn`

You can use this command to edit an existing question that was asked in a tutorial lesson.

**Format:**

`editqn INDEX [grp/GROUP_NAME] [lsn/LESSON_NAME] [qn/QUESTION]`

<div markdown="block" class="alert alert-info">

:information_source: **Notes regarding the `editqn` command:**

* You can edit the question's description, or the group/lesson the question was asked in.
* The existing values will be updated to the new values.
* If `grp/GROUP_NAME` or `lsn/LESSON_NAME` is supplied, ensure that the group or lesson exists.
  Otherwise, an error message will be displayed to alert you.
  
</div>

**Example:**

Suppose you have accidentally made a spelling mistake in the `First` question in the displayed list and 
wish to change it from "`What is the dedline for the report?`" to "`What is the deadline for the report?`". 
You can do so as follows.

Editing a question:

1. Type `editqn 1 qn/What is the deadline for the report?` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. The _Data Display_ will update and display the list of all your questions, including the edited question.

#### 4.4.4. Find questions by keywords: `findqn`

You can use this command to find a question that was asked in a tutorial lesson. 
Serenity will help you search for questions that contain the keywords that you are finding.

**Format:**

`findqn KEYWORD [MORE_KEYWORDS]...`

**Example:**

Let say your question list has a bunch of questions from all your tutorial lessons. 
You want to search for a specific one with the word `deadline` in the question. You can do so as follows.

Finding questions:

1. Type `findqn deadline` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. The _Data Display_ will update and display the list of questions with the keyword `deadline`.

### 4.5 Utility viewing

The following commands allow you to navigate between different views.

#### 4.5.1. View a tutorial group: `viewgrp`

You can use this command to view the list of students and tutorial lessons of a tutorial group.
You will also be able to execute tutorial group-related commands.

<div markdown="block" class="alert alert-primary">

:bulb: **Tip for the `viewgrp` command:**

You can easily navigate to the tutorial group by pressing its button in the Side_Bar as well!
Pressing the tutorial group's button equals to typing this command.
    
</div>

**Format:**

`viewgrp grp/GROUP_NAME`

**Example:**

You want to view the details of tutorial group `G01`.

<ins>Method 1</ins> <br>

Viewing a tutorial group by typing the following command:

1. Type `viewgrp grp/G01` into the _Command Box_.
2. Press `Enter` to execute.

<ins>Method 2</ins> <br>

Viewing a tutorial group by pressing the group button:

1. Press the button named `G01` in the Side_Bar.

Outcome:

1. The _Result Display_ will show a success message.
2. The _Data Display_ will update and display the information for tutorial group `G01`.

<p align="center"><img src="images/userGuide/utilityViewing/viewgrp.png" 
   alt="User interface for viewing tutorial group" border="1px solid black"></p>
<p align="center"><i>Figure 4.5.1.1 User interface for viewing tutorial group.</i></p>

#### 4.5.2. View a tutorial lesson: `viewlsn`

You can use this command to view and modify the attendance and participation scores of 
a tutorial lesson. You will also be able to execute tutorial lesson-related commands.

**Format:**

`viewlsn grp/GROUP_NAME lsn/LESSON_NAME`

**Example:**

You want to view the details of tutorial lesson `1-1` of tutorial group `G01`.

Viewing a tutorial lesson:

1. Type `viewlsn grp/G01 lsn/1-1` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. The _Data Display_ will update and display the students' information for tutorial lesson `1-1` of tutorial group `G01`.

<p align="center"><img src="images/userGuide/utilityViewing/viewlsn.png" 
   alt="User interface for viewing tutorial lesson" border="1px solid black"></p>
<p align="center"><i>Figure 4.5.2.1 User interface for viewing tutorial lesson.</i></p>

#### 4.5.3. View attendance sheet of a tutorial group: `viewatt`

You can use this command to view the attendance sheet of the students in a tutorial group across all tutorial lessons.
The attendance sheet will be displayed in table form.

<div markdown="block" class="alert alert-info">

:information_source: **Notes regarding the `viewatt` command:**

* You can view the attendance of all your students in the tutorial group at one glance.
* You can also preview the XLSX file that you can generate with the `exportatt` command,
  before generating the actual XLSX file.
    
</div>

**Format:**

`viewatt grp/GROUP_NAME`

**Example:**

You want to view the attendance sheet of the students in tutorial group `G01`.

Viewing the attendance sheet of a tutorial lesson:

1. Type `viewatt grp/G01` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. The _Data Display_ will update and display the attendance sheet of tutorial group `G01`.

<p align="center"><img src="images/userGuide/utilityViewing/viewatt.png" 
   alt="User interface for viewing tutorial attendance" border="1px solid black"></p>
<p align="center"><i>Figure 4.5.3.1 User interface for viewing tutorial attendance.</i></p>

#### 4.5.4. View participation score sheet of a tutorial group: `viewscore`

You can use this command to view the participation score sheet of the students in a tutorial group
across all tutorial lessons. The participation score sheet will be displayed in table form.

<div markdown="block" class="alert alert-info">

:information_source: **Notes regarding the `viewscore` command:**

* You can view the participation score of all your students in the tutorial group at one glance.
* You can also preview the XLSX file that you can generate with the `exportscore` command,
  before generating the actual XLSX file.
    
</div>

**Format:**

`viewscore grp/GROUP_NAME`

**Example:**

You want to view the participation score sheet of the students in tutorial group `G01`.

Viewing the participation score sheet of a tutorial lesson:

1. Type `viewscore grp/G01` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. The _Data Display_ will update and display the participation score sheet of tutorial group `G01`.

<p align="center"><img src="images/userGuide/utilityViewing/viewscore.png" 
   alt="User interface for viewing tutorial participation score sheet" border="1px solid black"></p>
<p align="center"><i>Figure 4.5.4.1 User interface for viewing tutorial participation score sheet.</i></p>

#### 4.5.5. View flagged attendance: `viewflag`

You can use this command to view students with their attendance marked as flagged across all tutorial lessons.

<div markdown="block" class="alert alert-primary">
    
:bulb: **Tip for the `viewflag` command:**

You can easily navigate to the flagged attendance list by pressing the "Flag" button in the Side_Bar as well!
Pressing the button equals to typing this command.
    
</div>

<div markdown="block" class="alert alert-primary">

:bulb: **Tip for the `viewflag` command:**

Instead of viewing flagged attendance across all tutorial lessons,
you can view flagged attendance for a specific tutorial lesson by
entering the tutorial lesson page using `viewlsn` and scrolling through the "Lesson" tab in the page.

</div>

**Format:**

`viewflag`

**Example:**

You want to view all flagged attendance.

Viewing flagged attendance:

1. Type `viewflag` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. The _Data Display_ will update and display the list of students with flagged attendance across all tutorial lessons.

<p align="center"><img src="images/userGuide/utilityViewing/viewflagatt.png"
   alt="User interface for viewing flagged attendance." border="1px solid black"></p>
<p align="center"><i>Figure 4.5.5.1 User interface showing the layout of the flagged attendance tab.</i></p>

#### 4.5.6. View pending questions: `viewqn`

You can use this command to view questions that were asked across all tutorial lessons
and have yet to be addressed.

<div markdown="block" class="alert alert-primary">
    
:bulb: **Tip for the `viewqn` command:**

You can easily navigate to the pending questions list by pressing the "Qns" button in the Side_Bar as well!
Pressing the button equals to typing this command.
    
</div>

<div markdown="block" class="alert alert-primary">
    
:bulb: **Tip for the `viewqn` command:**

Instead of viewing pending questions across all tutorial lessons,
You can view questions for a specific tutorial lesson by
entering the tutorial lesson page using `viewlsn` and pressing the "Questions" tab in the page.
    
</div>

**Format:**

`viewqn`

**Example:**

You want to view all pending questions.

Viewing pending questions:

1. Type `viewqn` into the _Command Box_.
2. Press `Enter` to execute.

Outcome:

1. The _Result Display_ will show a success message.
2. The _Data Display_ will update and display the list of pending questions asked across all tutorial lessons.

<p align="center"><img src="images/userGuide/utilityViewing/viewqn.png" 
   alt="User interface for viewing questions" border="1px solid black"></p>
<p align="center"><i>Figure 4.5.6.1 User interface for viewing questions.</i></p>

--------------------------------------------------------------------------------------------------------------------

## **5. FAQ**

**Q:** How many tutorial groups can I manage in **Serenity**?

**A:** You can manage more than 1 tutorial groups in **Serenity**.

**Q:** How do I transfer my data to another computer?

**A:** By default, **Serenity** saves all your data in a folder named `data`. This folder can be found in the
home folder for your **Serenity** application. You can copy and transfer the data folder into the home folder of
your **Serenity** application on your other computer. **Serenity** will automatically load your data upon launching.

--------------------------------------------------------------------------------------------------------------------

## **6. Command summary**

### 6.1. Setup commands 

Command | Example
------------ | -------------
**Add tutorial group** <br>`addgrp grp/GROUP_NAME path/PATH_TO_XLSX`| <br>`addgrp grp/G04 path/CS2101_G04.xlsx`
**Delete tutorial group** <br> `delgrp grp/GROUP_NAME`| <br>`delgrp grp/G01`
**Add tutorial lesson** <br> `addlsn grp/GROUP_NAME lsn/LESSON_NAME` | <br>`addlsn grp/G01 lsn/1-3`
**Delete tutorial lesson** <br> `dellsn grp/GROUP_NAME lsn/LESSON_NAME` | <br>`dellsn grp/G01 lsn/1-1`
**Add student** <br> `addstudent grp/GROUP_NAME name/STUDENT_NAME matric/STUDENT_NUMBER` | <br>`addstudent grp/G01 name/David Chong matric/A0123456D`
**Delete student** <br> - `delstudent grp/GROUP_NAME name/STUDENT_NAME matric/STUDENT_NUMBER`<br> - `delstudent INDEX grp/GROUP` | <br> - `delstudent grp/G01 name/Aaron Tan matric/A0123456A` <br> - `delstudent 1 grp/G01`

### 6.2. Attendance taking commands

Command | Example
------------ | -------------
**Mark a student as present** <br> - `markpresent name/STUDENT_NAME matric/STUDENT_NUMBER`<br> - `markpresent INDEX` | <br> - `markpresent name/Aaron Tan matric/A0123456A` <br> - `markpresent 1`
**Mark a student as absent** <br> - `markabsent name/STUDENT_NAME matric/STUDENT_NUMBER`<br>-`markabsent INDEX` | <br> - `markabsent name/Aaron Tan matric/A0123456A`<br> - `markabsent 1`
**Mark all students as present** <br> `markpresent all` | <br>`markpresent all`
**Mark all students as absent** <br> `markabsent all` | <br>`markabsent all`
**Flag attendance** <br> - `flagatt name/STUDENT_NAME matric/STUDENT_NUMBER` <br> - `flagatt INDEX` | <br> - `flagatt name/Aaron Tan matric/A0123456A` <br> - `flagatt 1`
**Unflag attendance** <br> - `unflagatt name/STUDENT_NAME matric/STUDENT_NUMBER` <br> - `unflagatt INDEX` | <br> - `unflagatt name/Aaron Tan matric/A0123456A`<br> - `unflagatt 1` 
**Export attendance** <br> `exportatt grp/GROUP_NAME` | `exportatt grp/G01`

### 6.3. Participation marking commands

Command | Example
------------ | -------------
**Add participation score** <br> - `addscore name/STUDENT_NAME matric/STUDENT_NUMBER score/SCORE`<br> - `addscore INDEX score/SCORE` | <br>- `addscore name/Aaron Tan matric/A0123456A score/2` <br> - `addscore 1 score/2`
**Edit participation score** <br> - `editscore name/STUDENT_NAME matric/STUDENT_NUMBER score/SCORE` <br> - `editscore INDEX score/SCORE` | <br>- `editscore name/Aaron Tan matric/A0123456A score/2` <br> - `editscore 1 score/2`
**Subtract participation score** <br> - `subscore name/STUDENT_NAME matric/STUDENT_NUMBER score/SCORE` <br> - `subscore INDEX score/SCORE` | <br>- `subscore name/Aaron Tan matric/A0123456A score/2` <br> - `subscore 1 score/2`
**Export participation scores** <br> `exportscore grp/GROUP_NAME` | <br>`exportscore grp/G01`

### 6.4. Question addressing commands 

Command | Example
------------ | -------------
**Add question** <br> `addqn qn/QUESTION` | `addqn qn/What is the deadline for the report?`
**Delete question** <br> `delqn INDEX` | `delqn 1`
**Edit question** <br> `editqn INDEX [grp/GROUP_NAME] [lsn/LESSON_NAME] [qn/QUESTION]` | `editqn 1 qn/Can you repeat the deadlines for all submissions?`
**Find question** <br> `findqn KEYWORD [MORE_KEYWORDS]...` | `findqn deadline`

### 6.5. Utility viewing commands 

Command | Example
------------ | -------------
**View tutorial group** <br> `viewgrp grp/GROUP_NAME` | `viewgrp grp/G01`
**View tutorial lesson** <br> `viewlsn grp/GROUP_NAME lsn/LESSON_NAME` | `viewlsn grp/G01 lsn/1-1`
**View attendance** <br> `viewatt grp/GROUP_NAME` | `viewatt grp/G01`
**View participation score** <br> `viewscore grp/GROUP_NAME` | `viewscore grp/G01`
**View flag** <br> `viewflag` | `viewflag`
**View questions** <br> `viewqn` | `viewqn`

