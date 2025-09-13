# expense-tracker
An expense tracker application to manage your finances. The application allow users to add, delete, and view their expenses. The application also provide a summary of the expenses.

## Project URL
https://roadmap.sh/projects/expense-tracker

# Documentation

## How to run the app
- to run the app from the command line, move to the expense-tracker/src folder, then run the command `javac -d "../bin" -cp "../lib/picocli-4.7.7.jar" ExpenseTracker.java` to compile the code, then, to run the app, you have to run the command `java -cp "../bin:../lib/picocli-4.7.7.jar" ExpenseTracker add` where the `...` stand for the command you want the app to do, example:

# the following is to fix:

i want to add a task with description "my little house", i will run the following command: `java App add "my little house"`
### Commands
- To add a task: `add "description of the new task"`
- To update a task: `update task_id "description of the updated task"`
- To delete a task: `delete task_id`
- To mark a task as in progress `mark-in-progress task_id`
- To mark a task as done `mark-done task_id`
- To list all tasks `list`
- To list all tasks that are to-do: `list-todo`
- To list all tasks that are in progress: `list-in-progress`
- To list all tasks that are done: `list-done`