import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Student student = Student.getInstance();
        String input;
        student.setState(new NotMotivatedNotStressed());

        Scanner s = new Scanner(System.in);
        System.out.println("What is your name?");
        input = promptForInput(s);
        student.setName(input);
        int semesterCount = promptForSemesterTotal(s);
        displayUseMe();

        boolean over = false;
        while(!over)
        {
            // Wrapped congrats statement in a check that enough tests were completed
            if (student.getCompletedTests() >= semesterCount) {
                System.out.println("Congratulations! You graduated!");
                return;
            }

            input = promptForInput(s);
            switch (input)
            {
                case "exit":
                    over = true;
                    break;
                case "status":
                    System.out.println(student.toString());
                    break;
                case "help":
                    displayUseMe();
                    break;
                case "DS":
                    student.doSchoolwork();
                    break;
                case "IS":
                    student.inspireSelf();
                    break;
                case "DH":
                    student.doHobby();
                    break;
                case "TT":
                    student.takeTest();
                    if(student.getPassedATest())
                    {
                        displaySuccessful();
                        student.setPassedATest(false);
                    }
                    else
                        displayUnsuccessful();
                    break;
                default:
                    System.out.println("Please enter a valid string");
                    displayUseMe();
            }
        }

        // Wrapped congrats statement in a check that enough tests were completed
//        if (student.getCompletedTests() >= semesterCount) {
//            System.out.println("Congratulations! You graduated!");
//        }
    }

    public static void displayUseMe()
    {
        System.out.println("The student program takes string commands that you enter into the command line\n" +
                "DS - Do Schoolwork\nIS - Inspire Self\nDH - Do Hobby\nTT - Take Test\n" +
                "status - shows your current stats as a student\nexit - give up and quit the program" +
                "\nhelp - see these options again");
    }

    public static void displaySuccessful()
    {
        System.out.println("You passed the test, but the stress" +
                " of it all gets to you.\nNext test with new material " +
                "is coming soon,\nyour intelligence has been set to 0.");
    }

    public static void displayUnsuccessful()
    {
        System.out.println("You couldn't pass the test, the stress" +
                " of it all gets to you and you don't feel motivated" +
                " to continue.\nNext test with new material " +
                "is coming soon, your intelligence has been set to 0.");
    }

    public static String promptForInput(Scanner s)
    {
        String input = s.nextLine();
        input = input.trim();
        return input;
    }

    public static int promptForSemesterTotal(Scanner s)
    {
        System.out.println("How many tests will there be during the semester");
        String input = promptForInput(s);
        int number;

        try
        {
            number = Integer.parseInt(input);
        }
        catch (NumberFormatException e)
        {
            number = promptForSemesterTotal(s);
        }

        return number;
    }


}
