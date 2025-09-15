import java.time.LocalDate;

public class Expense {
    private int id;
    private String description;
    private int amount;
    private String category;
    private LocalDate date;

    Expense(int id, String description, int amount, String category) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = LocalDate.now();;
    }

    Expense(int id, String description, int amount, String category, String date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = LocalDate.parse(date);
    }

    public String getDescription() {
        return this.description;
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public int getAmount(){
        return this.amount;
    }

    public void updateAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return this.category;
    }

    public void updateCategory(String category) {
        this.category = category;
    }

    public int getid() {
        return this.id;
    }

    public String date() {
        return this.date.toString();
    }

    public int dateYear() {
        return this.date.getYear();
    }

    public int dateMonth() {
        return this.date.getMonthValue();
    }

    public int dateDay() {
        return this.date.getDayOfMonth();
    }

    @Override
    public String toString(){
        return this.id + "        " +
                this.description + "        " +
                this.amount + "        " +
                this.category + "        " +
                this.date();
    }

    public String toWriteFile() {
        return "        {" +
                "\"ID\"" + ':' + this.id + ", " +
                "\"description\"" + ':' + '"' + this.description + '"' + ", " +
                "\"amount\"" + ':' + '"' + this.amount + '"' + ", " +
                "\"category\"" + ':' + '"' + this.category + '"' + ", " +
                "\"date\"" + ':' + '"' + this.date() + '"'
                + '}';
    }

}
