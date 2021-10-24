import java.util.Scanner;

class Main {
  public static void main(String args[]) {
    System.out.println("Welcome to Java Calculator App!  Enter a command:");
    Calculator calc = new Calculator();

    Scanner scanner = new Scanner(System.in);

    String command = scanner.next();

    while (!command.equals("quit")) {
      if (command.equals("add")) {
        System.out.println("= " + calc.add(scanner.nextInt(), scanner.nextInt()));
      }

      if (command.equals("subtract")) {
        System.out.println("= " + calc.subtract(scanner.nextInt(), scanner.nextInt()));
      }

      if (command.equals("multiply")) {
        System.out.println("= " + calc.multiply(scanner.nextInt(), scanner.nextInt()));
      }

      if (command.equals("divide")) {
        System.out.println("= " + calc.divide(scanner.nextInt(), scanner.nextInt()));
      }

      if (command.equals("fibonacci")) {
        System.out.println("= " + calc.fibonacciNumberFinder(scanner.nextInt()));
      }

      if (command.equals("binary")) {
        System.out.println("= " + calc.intToBinaryNumber(scanner.nextInt()));
      }

      command = scanner.next();
    }
  }
}