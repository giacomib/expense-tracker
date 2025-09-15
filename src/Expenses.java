import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Year;

public class Expenses {
    ArrayList<Expense> expenses;
    File myFile;

    Expenses() {
        expenses = new ArrayList<Expense>();
        myFile = new File("expenses.json");
        if(!myFile.exists()) {
            createFile();
            setupFile();
        } else {
            importFileData();
        }
    }

    private void createFile() {
        try {
            myFile.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file");
            e.printStackTrace();
        }
    }

    private void setupFile() {
        try {
            FileWriter fileWriter = new FileWriter(myFile);
            fileWriter.write('{' + "\n" + "    \"expenses\":[" + "\n" + "    ]" + "\n" + '}');
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("error occured during file writing");
            e.printStackTrace();
        }
    }

    private void importFileData() {
        boolean preambleFinished = false;
        try {
            Scanner scanner = new Scanner(myFile);
            while(!preambleFinished) {
                String currentLine = scanner.nextLine();
                if(currentLine.contains("expenses")) {
                    preambleFinished = true;
                }
            }
            while(scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if(currentLine.compareTo("    ]") == 0) {
                    break;
                }
                else{
                    int id = Integer.parseInt(currentLine.substring(currentLine.indexOf(':') + 1, currentLine.indexOf(',')));

                    currentLine = currentLine.substring(currentLine.indexOf(',') + 1, currentLine.length());
                    String description = currentLine.substring(currentLine.indexOf(':') + 2, currentLine.indexOf(',') - 1);

                    currentLine = currentLine.substring(currentLine.indexOf(',') + 1, currentLine.length());
                    int amount = Integer.parseInt(currentLine.substring(currentLine.indexOf(':') + 2, currentLine.indexOf(',') - 1));

                    currentLine = currentLine.substring(currentLine.indexOf(',') + 1, currentLine.length());
                    String category = currentLine.substring(currentLine.indexOf(':') + 2, currentLine.indexOf(',') - 1);

                    currentLine = currentLine.substring(currentLine.indexOf(',') + 1, currentLine.length());
                    String date = currentLine.substring(currentLine.indexOf(':') + 2, currentLine.indexOf('}') - 1);

                    expenses.add(new Expense(id, description, amount, category, date));
                }
            }
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private int newID() {
        if (expenses.size() == 0) {
            return 0;
        }
        
        int[] orderedIDs = getIDOrder();
        
        int newId = 0;
        for(int id : orderedIDs) {
            if(newId != id)
                return newId;
            newId++;
        }
        return orderedIDs[expenses.size() - 1] + 1;
    }

    private int[] getIDOrder(){
        int[] orderedIDs = new int[expenses.size()];
        for(int i = 0; i < expenses.size(); i++)
            orderedIDs[i] = expenses.get(i).getid();
        Arrays.sort(orderedIDs);
        return orderedIDs;
    }

    public boolean addExpense(String description, int amount, String category) {
        if(amount < 0)
            return false;
        expenses.add(new Expense(newID(), description, amount, category));
        return updateFile();
    }

    public void list() {
        System.out.println("ID        Description        Amount        Category        Date");
        for(Expense expense:expenses){
            System.out.println(expense.toString());
        }
    }

    public boolean deleteExpense(int id) {
        for (Expense expense : expenses) {
            if(expense.getid() == id) {
                expenses.remove(expense);
                return updateFile();
            }
        }
        System.err.println("no expense found with given id " + id);
        return false;
    }

    public boolean deleteAll() {
        expenses.clear();
        return updateFile();
    }

    private boolean updateFile() {

        try {
            FileWriter fileWriter = new FileWriter(myFile);
            fileWriter.write('{' + "\n" + "    \"expenses\":[" + "\n");

            for(int i = 0; i < expenses.size(); i++) {
                if(i == expenses.size() - 1)
                    fileWriter.write(expenses.get(i).toWriteFile() + "\n");
                else
                    fileWriter.write(expenses.get(i).toWriteFile() + ",\n");
            }

            fileWriter.write("    ]" + "\n" + '}');
            fileWriter.close();
            return true;
        } catch (Exception e) {
            System.out.println("error occured during file writing");
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(int id, String description, int amount, String category){
        Expense expToUpdate = null;

        for(Expense expense : expenses){
            if(expense.getid() == id) {
                expToUpdate = expense;
            }
        }
        
        if(expToUpdate == null)
            return false;
        
        if(description!= null && description.compareTo("no given description") != 0)
            expToUpdate.updateDescription(description);
        
        if(amount > 0 && expToUpdate.getAmount() != amount)
            expToUpdate.updateAmount(amount);

        if(category != null && category.compareTo("no given category") != 0)
            expToUpdate.updateCategory(category);
        
        return updateFile();
    }

    public void summary(){
        if(expenses.size() == 0)
            return;
        int totalExp = 0;
        for(Expense expense : expenses)
            totalExp += expense.getAmount();
        System.out.println("Total amount: " + totalExp);
    }

    public void summary(int month, String category){
        if(expenses.size() == 0)
            return;
        int totalExp = 0;
        for(Expense expense : expenses)
            if(expense.dateMonth() == month && expense.getCategory().compareTo(category) == 0)
                totalExp += expense.getAmount();
        System.out.println("Total amount for the month " + month + " filtered by the following category " + category + " is: " + totalExp);
    }

    public void summaryMonth(int month){
        if(expenses.size() == 0)
            return;
        int totalExp = 0;
        for(Expense expense : expenses)
            if(expense.dateYear() == Year.now().getValue() && expense.dateMonth() == month)
                totalExp += expense.getAmount();
        System.out.println("Total amount for the month of " + month + " is: " + totalExp);
    }

    public void summaryCategory(String category) {
        if(expenses.size() == 0)
            return;
                int totalExp = 0;
        for(Expense expense : expenses)
            if(expense.getCategory().compareTo(category) == 0)
                totalExp += expense.getAmount();
        System.out.println("Total amount filtered by category " + category + " is: " + totalExp);
    }
    
}
