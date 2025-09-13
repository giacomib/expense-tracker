import picocli.CommandLine;
import picocli.CommandLine.Command;
//import picocli.CommandLine.Option;
//import picocli.CommandLine.Parameters;

// , Update.class, Delete.class, List.class, Summary.class

@Command(name = "expensetracker", subcommands = {Add.class}, mixinStandardHelpOptions = true,
         description = "Expense tracker app")
public class ExpenseTracker {
    public static void main(String[] args){
        int exitCode = new CommandLine(new ExpenseTracker()).execute(args);
        System.exit(exitCode);
    }
}

@Command(name = "add", description="bla bla bla")
class Add implements Runnable{
    
    @Override
    public void run() {
            System.out.println("funziona tutto ");
    }
}
