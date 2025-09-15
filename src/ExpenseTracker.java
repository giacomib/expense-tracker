import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;



@Command(name = "expensetracker", subcommands = {Add.class, Update.class, Delete.class, List.class, Summary.class, DeleteAll.class}, mixinStandardHelpOptions = true,
         description = "Expense tracker app")
public class ExpenseTracker {

    Expenses expenses;
    
    public static void main(String[] args){
        ExpenseTracker expenseTracker = new ExpenseTracker();
        expenseTracker.expenses = new Expenses();
        int exitCode = new CommandLine(expenseTracker).execute(args);
        System.exit(exitCode);
    }
}

@Command(name = "add", description="Add an expense")
class Add implements Runnable{

    @ParentCommand
    ExpenseTracker parent;

    @Option(names = {"-d", "--description"}, description = "to describe the expense", required = true)
    boolean descriptionBool;

    @Parameters(paramLabel = "description", defaultValue = "no given description", description = "the description of the expense")
    String description;

    @Option(names = {"-a", "--amount"}, description = "to add the amount", required = true)
    boolean amountBool;
    
    @Parameters(paramLabel = "amount", defaultValue = "0", description = "the amount of the expense")
    int amount;

    @Option(names = {"-c", "--category"}, description = "to describe the category")
    boolean categoryBool;

    @Parameters(paramLabel = "category", defaultValue = "none", description = "the description of the category")
    String category;
    
    @Override
    public void run() {
        parent.expenses.addExpense(description, amount, category);
    }
}

@Command(name = "update", description="Update an expense")
class Update implements Runnable{

    @ParentCommand
    ExpenseTracker parent;

    @Option(names = "-id", description = "ID of the expense to update", required = true)
    boolean idBool;

    @Parameters(paramLabel = "id", defaultValue = "0", description = "ID of the expense to delete")
    int id;

    @Option(names = {"-d", "--description"}, description = "to describe the expense")
    boolean descriptionBool;

    @Parameters(paramLabel = "description", defaultValue = "no given description", description = "the description of the expense")
    String description;

    @Option(names = {"-a", "--amount"}, description = "to add the amount")
    boolean amountBool;
    
    @Parameters(paramLabel = "amount", defaultValue = "0", description = "the amount of the expense")
    int amount;

    @Option(names = {"-c", "--category"}, description = "to describe the category")
    boolean categoryBool;

    @Parameters(paramLabel = "category", defaultValue = "no given category", description = "the description of the category")
    String category;
    
    @Override
    public void run() {
        parent.expenses.update(id, description, amount, category);
    }
}

@Command(name = "delete", description="Delete an expense")
class Delete implements Runnable{

    @Option(names = "-id", description = "ID of the expense to delete", required = true)
    boolean idBool;

    @Parameters(paramLabel = "id", defaultValue = "0", description = "ID of the expense to delete")
    int id;

    @ParentCommand
    ExpenseTracker parent;
    
    @Override
    public void run() {
        parent.expenses.deleteExpense(id);
    }
}

@Command(name = "deleteAll", description="Delete all the expenses")
class DeleteAll implements Runnable{

    @ParentCommand
    ExpenseTracker parent;
    
    @Override
    public void run() {
        parent.expenses.deleteAll();
    }
}

@Command(name = "list", description="View all expenses")
class List implements Runnable{

    @ParentCommand
    ExpenseTracker parent;
    
    @Override
    public void run() {
        parent.expenses.list();
    }
}

@Command(name = "summary", description="View a summary of all expenses")
class Summary implements Runnable{
    
    @ParentCommand
    ExpenseTracker parent;

    @Option(names = "-m", description = "Filter expenses through month")
    boolean monthBool;

    @Parameters(paramLabel = "month", defaultValue = "0", description = "Month of expenses to show")
    int month;

    @Option(names = {"-c", "--category"}, description = "to describe the category")
    boolean categoryBool;

    @Parameters(paramLabel = "category", defaultValue = "no given category", description = "the description of the category")
    String category;

    @Override
    public void run() {
        if(monthBool && categoryBool) {
            if(month != 0 && category.compareTo("no given category") != 0 ){
                parent.expenses.summary(month, category);
                return;
            }
            System.out.println("To summarize by month and category, both a month and a category are needed");
            return;
        }

        if(monthBool) {
            if(month != 0){
                parent.expenses.summaryMonth(month);
                return;
            }
        }

        if(categoryBool) {
            if(category.compareTo("no given category") != 0 ){
                parent.expenses.summaryCategory(category);
                return;
            }
        }

        parent.expenses.summary();
    }
}
