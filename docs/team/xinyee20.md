---
layout: page
title: Xin Yee's Project Portfolio Page
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

* **New Feature 1**: Added the ability to mark and flag attendance of students during lessons through commands
  * What it does: 
      * `markpresent` - Allows the users to mark a student present when viewing a lesson
      * `markabsent` - Allows the users to mark a student absent when viewing a lesson
      * `flagatt` - Allows the users to flag attendance of a student when necessary while in lesson view
      * `unflagatt` - Allows the users to un-flag the attendance of a student after settling issue of the flagged 
                      student while in lesson view
  * Justification: This feature improves the product significantly because it is helps a user to keep track of the 
                   attendance of students during tutorial lessons. The ability to mark attendance quickly increases 
                   the efficiency of lessons. The ability to flag attendance of students help users keep track of 
                   the attendance of students in special cases where they have to go for replacement lessons.
  * Highlights: The implementation of this feature was tricky as students were originally deeply nested within a group. A lot of 
                discussion and analysis of the structure of the whole project and the relationship between `Group`s, `Lesson`s,
                `Student`s was done so as to reduce the nesting. Changes had to be made to the implementation of the commands 
                to infuse in the `Manager`s. Another highlight was that the mark attendance commands that are applied to all the
                students in the class did not run very fast so we had to look into how we can optimise the code to make it run
                faster. All these major changes made the implementation of the code very time-consuming and rather challenging.
                

* **New Feature 2**: Added the ability to award and adjust participation scores of students during lessons
  * What it does: 
    * `editscore` - Allows the users to edit the participation score of a student when viewing a lesson
    * `addscore` - Allows the users to increase the participation score of a student when viewing a lesson
    * `subscore` - Allows the users to decrease the participation score of a student when viewing a lesson
  * Justification: This feature improves the product significantly because it is helps a user to 
                       keep track of the score of students during tutorial lessons.
  * Highlights: The implementation of this feature required some thinking through at the start as we try to take into 
                consideration the users' needs without making the command too complicated to use. As this feature has 
                quite a few restrictions in terms of input of score, quite some time was spent testing the commands and 
                adjusting the code, making sure that proper messages are shown when different exceptions are thrown, 
                to guide users who misuse the commands.
    
* **New Feature 3**: Added the ability to use indexing to apply commands to existing students. 
  * What it does: Allows users to apply deleting of students when in group view and the marking, flagging attendance 
                  of students, as well as awarding, adjusting participation score of students using the index of the student 
                  shown on the list when in lesson view.
  * Justification: This feature improves the product significantly because it is helps a user to apply commands to students 
                   without needing to type in the name and matriculation number of students which may be very long and tedious to type, 
                   increasing the efficiency of lessons.
  * Highlights: The implementation of this feature was rather challenging. The data in our application is obtained 
                from excel sheets that are imported into the application. I realised that the usage of `Set` to store the 
                data result in the data list being unordered which is not very desirable for the users if they were to see
                the list of students in different order every time they open the application. I looked into the usage of 
                `Comparator` to sort the list and made use of `LinkedHashSet` so that all the `Lesson`s, `Student`s, 
                `StudentInfo`s are sorted in the list shown in the GUI. Another highlight is that implementing index on top 
                of name and matriculation made me realise that there are many permutations of the usage of the commands 
                so more rigorous testing had to be done and more conditions were added to cover the various scenarios.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=xinyee20&sort=groupTitle&sortWithin=title&since=2020-08-14&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other)

* **Project management**: 
  * Brainstormed ideas for the different features (How we intend our application to behave etc.)
  * Contributed to weekly team meetings regarding design details and implementation constraints
  * Discussed ideas to improve architecture design
  * Used Github Issues Tracker to schedule, track and assign tasks to teammates.
  * Reviewing and merging of PRs before deadlines
  
* **Enhancements to existing features**:
    * Improve consistency of error messages. ( Pull requests 
    [#172](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/172))
    * Detected and fixed bugs for some features. ( Pull request 
    [#87](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/87),
    [#112](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/112),
    [#172](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/172))
    [#270](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/270),
    [#279](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/279),
    [#280](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/280),
    [#291](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/291),
    * Wrote additional tests for existing features to increase test coverage. ( Pull request
    [#138](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/138),
    [#188](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/188),
    [#270](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/270),
    [#280](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/280),
    [#291](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/291),
    * Improve code quality for certain commands to adhere to coding principles. ( Pull request 
    [#188](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/188))
    [#291](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/291),

* **Documentation**:
  * User Guide:
      * Added documentation for the features `markpresent`, `markabsent`, `flagatt`, `unflagatt`, `editscore`,
      `addscore`, `subscore`. ( Pull request 
      [#149](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/149))
      * Addressed documentation issues after initial peer review. ( Pull request 
      [#112](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/112))
      [#120](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/120))
      [#128](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/128))
  * Developer Guide:
      * Added implementation details for `StudentInfoManager`. ( Pull request 
      [#198](https://github.com/AY2021S1-CS2103T-W12-4/tp/pull/198))


* **Community**:
  * Reported bugs and suggestions for other teams in the class (examples: 
    [1](https://github.com/AY2021S1-CS2103T-W13-2/tp/issues/110),
    [2](https://github.com/AY2021S1-CS2103T-W13-2/tp/issues/111), 
    [3](https://github.com/AY2021S1-CS2103T-W13-2/tp/issues/112),
    [4](https://github.com/AY2021S1-CS2103T-W13-2/tp/issues/113),
    [5](https://github.com/AY2021S1-CS2103T-W13-2/tp/issues/114),

