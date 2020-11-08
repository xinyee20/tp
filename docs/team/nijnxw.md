---
layout: page
title: Wen Jin's Project Portfolio Page
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

* **New Feature 1**: Implementation of `QuestionManager`
  * What it does: It manages and track all the questions asked during lessons
  * Justification: This enhancement improves the architecture of the model component, and the 
    storing of the question data for each lesson. It abstracts out the implementation of methods 
    related to questions from the `ModelManager` to the `QuestionManager` and lessen deep nesting 
    of data from tutorial group to tutorial lesson to list of questions.
  * Highlights: This enhancement requires an in-depth analysis of design alternatives, the Model,
    Logic and Ui components. The implementation was challenging as we decided to refactor our original 
    model design of storing the list of questions in each lesson to storing the questions in a global 
    list with each question tagged to the tutorial group name and lesson name. Another highlight is 
    the abstraction and integration of the question manager into the model manager to ensure that the 
    GUI can automatically update upon data changes.

* **New Feature 2**: Added the ability to add, delete, edit and find questions through commands
  * What it does: 
    * `addqn` - Allows the users to add a question when viewing a lesson
    * `delqn` - Allows the user to delete a question after it is addressed
    * `editqn` - Allows the user to edit a question's detail in the event that there is a mistake
    * `findqn` - Allows the user to find questions by keywords
  * Justification: This feature improves the product significantly because it is helps a user to 
    keep track of the questions asked during tutorial lessons.
  * Highlights: As questions are related to specific groups and lessons, this enhancement requires an 
    in-depth analysis of the model design alternatives. The implementation too was challenging especially
    for the find command, as I decided that the keywords inputted by users does not need to match the full
    word in a sentence for the question to be displayed. For instance, if user input "work", questions 
    containing words such as "coursework" and "homework" will also be displayed to the user.
    
* **New Feature 3**: Added the ability to view all questions and view a lesson's details 
  * What it does: 
    * `viewqn` - Allows user to view the list of all the questions ask in all tutorial groups and lessons
    * `viewlsn` Allows user to view the details of the specified lesson in a tutorial group.
  * Justification: This enhancement improves the product significantly because a user will want to
    be able to see all the questions that he/she added or see the details of a lesson such as the 
    attendance list, and the class participation scores of each student in that tutorial group. 
  * Highlights: This enhancement required the in-depth analysis of the UI and Logic component. 
    The implementation was also challenging as it involved using JavaFX which is a relatively new 
    framework that I needed to pick up quickly. I was not able to see through the implementation for 
    the Ui component due to time constraint and other features that I had to work on, thus I handed 
    it over to my other team member to work on the Ui aspect of the command.

* **New Feature 4**: Added the ability to load and save the questions into a json file 
  * What it does: Stores the questions in the application as a json file when a user exits the application.
  * Justification: This enhancement improves the product significantly because a user will want to
    be able to see the questions added previously when he/she relaunches the application.
  * Highlights: This enhancement required the in-depth analysis of the Storage and Model component. I had 
    to learn and implement the conversion of Jackson-friendly version of objects to store in json file. I 
    also implemented the loading and saving of the data when the user launches or closes the application.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=nijnxw&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**:
  * Used Github Issues Tracker to schedule, track and assign tasks to teammates.
  * Facilitated weekly team meetings about implementation details, deliverables, and deadlines.
  * Compiled the minutes during team meetings and consultations for future referencing. 
  * Brainstormed ideas for the different features (How we intend our application to behave, design details etc.)

* **Enhancements to existing features**:
  * Refactored code to remove origins of AddressBook3 application.
    (Pull requests [\#111](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/111),
    [\#283](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/283))
  * Repackage and cleaned up code to improve code readability.
    (Pull requests [\#111](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/111),
    [\#133](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/133))
  * Detected and fixed bugs for some features.
    (Pull requests [\#168](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/168),
        [\#176](https://github.com/AY2021S1-CS2103T-W12-4/tp/issues/176),
        [\#278](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/278))
  * Improve code quality for certain commands to adhere to coding principles.
    (Pull requests [\#262](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/262))
  * Standardized all invalid command format error messages to improve user experience.
    (Pull requests [\#284](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/284))
  * Standardized sample data and examples to improve consistency.
    (Pull requests [\#284](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/284))
  * Standardized test case examples to improve consistency and increase readability.
    (Pull requests [\#284](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/284))
  * Wrote additional tests for existing features to increase test coverage.
    (Pull requests [\#144](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/144), 
        [\#296](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/296), 
        [\#306](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/306))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `viewlsn`, `addqn`, `editqn`, `delqn`, `findqn` and `viewqn`: 
        [\#30](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/30),
        [\#106](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/106), 
        [\#107](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/107),
        [\#182](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/182)
  * Developer Guide:
    * Drafted the skeleton of the DG in google docs.
    * Added use cases: [\#32](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/32)
    * Added implementation details for **Question addressing** feature: 
        [\#187](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/187)

* **Community**:
  * PRs reviewed (with non-trivial review comments): 
    [\#184](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/184), 
    [\#291](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/291), 
    [\#293](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/293)
  * Reported bugs and suggestions for other teams in the class (examples: 
    [1](https://github.com/AY2021S1-CS2103T-W13-3/tp/issues/246),
    [2](https://github.com/AY2021S1-CS2103T-W13-3/tp/issues/247), 
    [3](https://github.com/AY2021S1-CS2103T-W13-3/tp/issues/248),
    [4](https://github.com/AY2021S1-CS2103T-W13-3/tp/issues/249),
    [5](https://github.com/AY2021S1-CS2103T-W13-3/tp/issues/250),
    [6](https://github.com/AY2021S1-CS2103T-W13-3/tp/issues/251),
    [7](https://github.com/AY2021S1-CS2103T-W13-3/tp/issues/252)) 
    
