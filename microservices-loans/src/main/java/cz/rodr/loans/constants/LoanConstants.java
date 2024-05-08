package cz.rodr.loans.constants;

public class LoanConstants {
    private LoanConstants() {
        // restrict instantiation
    }
    public static final String HOME_LOAN = "Home Loan";

    public static Integer NEW_LOAN_LIMIT = 100_000;

    public static String STATUS_201 = "201";

    public static String MESSAGE_201 = "Loan successfully created";

    public static String STATUS_200 = "200";

    public static String MESSAGE_200 = "Request processed successfully";

    public static String STATUS_417 = "417";

    public static String MESSAGE_417_UPDATE = "Update failed. Please try again or contact Dev team.";

    public static String MESSAGE_417_DELETE = "Delete failed. Please try again or contact Dev team.";



}
