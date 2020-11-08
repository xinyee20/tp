---
layout: page
title: Rui En's Project Portfolio Page
---

## Project: Serenity

Serenity is a desktop application that helps CS2101 tutors manage their tutorial groups and lessons. 
The user interacts with it using a CLI, and it has a GUI created with JavaFX. 
It is written in Java, and has about 25k LoC.

### About the project

My team of 4 software engineering students and I were tasked with enhancing a basic command line 
interface (CLI) desktop addressbook application for our Software Engineering project. We chose to 
morph it into a CS2101 tutor's lesson management application called Serenity. 

This enhanced application enables CS2101 tutors to:
 * Import and export student and lesson data,
 * Record students' attendance,
 * Grade students' class participation score, and 
 * Jot down questions asked by students directly without opening an external note-taking application.

### Summary of contributions

Given below are my contributions to the project.

* **New Feature 1**: Implemented UI parts and added functionality accordingly.

    * What it does:
    
       * Adds the home page, which contains the tab panes for flagged attendance and pending questions.
       This page acts as the basis of Serenity. It is the default page shown when the user first enters Serenity, and
       Serenity transitions to this page after functions like `delgrp` is executed.
       
       * Adds the side bar, which contains buttons for flagged attendance, pending questions and tutorial groups.
       Pressing the flagged attendance and pending questions button leads to the respective tabs in the home page.
       Pressing the tutorial group buttons is akin to executing the `viewgrp` command, allowing users to
       conveniently access tutorial groups that were created previously.
       
       * Modifies the tutorial group page UI, such that the data panel for lessons and students are placed in
       tab panes instead of split panes. Adds tab panes for attendance and participation score sheets as well as
       drafted the UI for TableView.
       
       * Modifies the tutorial lesson page UI by improving the layout and design of the lesson tab pane.
       Adds a tab pane for questions asked during the tutorial.
       
       * Adds a title for each page so that users can navigate between the home page, tutorial group page and
        tutorial lesson page with greater clarity.
       
       * `viewatt` and `viewscore` - Allows users to view the attendance sheet and participation score sheet
       of the specified tutorial group respectively.
       
       * `viewflag` - Allows users to view the flagged attendance records.
    
    * Justification: This enhancement allows users to easily navigate within Serenity and enhances the visuals of the
    information presented, improving the user experience. This enhancement also encapsulates more features within the
    UI, allowing users to view flagged attendance, pending questions, attendance sheet and participation score sheet.
    
    * Highlights: This enhancement requires an in-depth understanding of the Ui, Logic and Model components.
    The implementation was challenging as the information handled by the Model components have to be correctly
    processed to be displayed in the Ui. Some Ui components also involve execution of commands and they require
    modifications to be made to the Logic components.
    The implementation was time-consuming as well as it requires understanding of how JavaFX displays data.
    Time was also spent to analyse how the css files affect the display of JavaFX components.
    
* **New Feature 2**: Incorporated Excel XLSX file support

    * What it does:
    
        * Allows XLSX files to be read. This allows users to add a new tutorial group by importing a XLSX file
        containing a list of students and (optionally) tutorial lessons. The resulting tutorial group will automatically
        contain the list of students and tutorial lessons.
        
        * Allows XLSX files to be written. This allows users to export attendance and participation score sheets
        of a specified tutorial group as XLSX files. The resulting XLSX file will either contain attendance or
        participation score records of each student across all tutorial lessons in the tutorial group.
        
        * `exportatt` - Allows users to export attendance sheet of a specified tutorial group as a XLSX file.
        
        * `exportscore` - Allows users to export participation score sheet of a specified tutorial group as a XLSX file.
        
    * Justification: This implementation improves user experience and differentiates our app from others.
    The XLSX file support allows users to conveniently create a new tutorial group without having to manually add
    students into the tutorial group in the app. Furthermore, the XLSX file support helps users who work part-time
    (i.e. part-time CS2101 tutors) to easily export attendance sheet for submission to receive their salary claims.
    Being able to export attendance and participation score sheets also enable users to save the XLSX files for
    future reference.
    
    * Highlights: This implementation requires an in-depth understanding of the Logic and Model components,
    as well as the APACHE POI library for XLSX file support. Time was spent to think about what makes a XLSX file
    valid for Serenity, i.e. what are the information that must be present in the XLSX file for Serenity to successfully
    read and create a new tutorial group. Consequently, some time was spent to set up a series of error messages to be
    displayed to the user, should invalid XLSX files be supplied. The implementation of the exporting features was
    challenging as the information handled by the Model components
    (Group, Lesson, Student, StudentInfo, Attendance, Participation, etc.) have to be correctly processed and
    written to the XLSX output file.
    
* **New Feature 3**: Added the ability to add and delete tutorial groups through commands

    * What it does:
    
        * `addgrp` - Allows the users to add a new tutorial group from an Excel XLSX file containing a list of students.
                
        * `delgrp` - Allows the user to delete an existing tutorial group. 
    
    * Justification: This feature is integral to the core functionality of the product because
    it allows the user to keep track of all the tutorial groups that the user is handling. Subsequently, the user can
    keep track of the tutorial lessons and students inside each tutorial group.
    
    * Highlights: Moderate amount of effort was made as implementation of the commands was rather straightforward.
    Though some time was spent to fix the Ui and Storage bugs concerning the commands.

* **New Feature 4**: Established the initial setup of the Storage components

    * What it does: Stores the tutorial group data in the application as a JSON file.
    
    * Justification: This enhancement establishes the foundation of the Storage components of the application.
    The other group members subsequently added further details to the Storage components, where they allowed
    more Model components (StudentInfo, Attendance, Participation, etc.) to be stored inside the JSON file and
    allowed data to be loaded from the previous session.
    
    * Highlights: This enhancement requires the analysis of the Storage and Model component.
    Time was spent to convert the Model components (Group, Lesson, Student) into Jackson-friendly versions to store
    in a JSON file.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&tabOpen=true&tabType=authorship&tabAuthor=successs404&tabRepo=AY2021S1-CS2103T-W12-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code~other)

* **Project management**:
    * Modified and commented on parts of the code to improve code quality.
    * Drafted out all the commands and added extension commands in a Google Document
    after the User Guide consultation with our Teaching Assistant to standardise the commands.
    * Drafted out the Ui design in Google Slides to make the development process clearer.
    * Brainstormed ideas for the different features and the execution of the commands.
    * Contributed to the minutes during weekly team meetings and consultations for future referencing.

* **Enhancements to existing features**:
    * Detected and fixed bugs for some features. ( Pull requests
    [#125](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/125),
    [#185](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/185),
    [#196](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/196),
    [#257](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/257),
    [#282](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/282),
    [#320](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/320)) 
    * Wrote additional tests for existing features to increase test coverage. ( Pull requests
    [#41](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/41),
    [#320](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/320))
    * Improve code quality for certain commands to adhere to coding principles. ( Pull requests
    [#192](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/192),
    [#320](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/320))
    * Made improvements to UI. ( Pull requests
    [#117](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/117),
    [#160](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/160),
    [#175](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/175),
    [#180](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/180),
    [#192](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/192),
    [#195](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/195))
    
* **Documentation**:
    * User Guide:
        * Improved the Introduction section, added the About section, added documentation for the features
        `addgrp`, `delgrp`, `exportatt`, `exportscore`
        edited and standardised the documentation for the other features,
        separated the Command Summary table into 5 tables where each table covers 1 main feature.
        ( Pull request [#121](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/121))
        * Updated the table of contents and fixed a minor UG bug.
        ( Pull request [#129](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/129))
        * Added details to the Layout of the GUI section,
        added documentation for the features `viewatt`, `viewscore`, `viewflag`,
        edited the other features to encapsulate the GUI changes,
        updated the Command Summary table.
        (Pull request [#201](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/201))
    * Developer Guide:
        * Added details for the Architecture, Ui component, Logic component and Student Manager.
        ( Pull request [#281](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/281))

* **Community**:
    * PR reviewed (with review comments): 
    [#291](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/291)
    * Reported bugs and suggestions for other teams in the class (examples:
    [1](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/211),
    [2](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/212), 
    [3](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/213),
    [4](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/214),
    [5](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/215),
    [6](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/216),
    [7](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/217),
    [8](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/218),
    [9](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/219),
    [10](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/220),
    [11](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/221),
    [12](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/222),
    [13](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/223),
    [14](https://github.com/AY2021S1-CS2103T-W13-4/tp/issues/224))

