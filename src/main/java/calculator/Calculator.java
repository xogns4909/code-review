package calculator;

import accumulator.Accumulator;
import accumulator.PostFixAccumulator;
import convertor.InfixToPostfixConverter;
import input.ConsoleInput;
import java.util.List;
import output.ConsoleOutput;
import output.Output;
import repository.Repository;
import util.PatternValidator;


public class Calculator {

  private final int INQUIRY = 1;
  private final int CALCULATE = 2;

  private Output output = new ConsoleOutput();

  private Repository repository = new Repository();

  public void run() {
    ConsoleInput input = new ConsoleInput();
    while (true) {
      try {
        output.displayOptions();
        String selectInput = input.SelectInput();
        PatternValidator.checkSelectValue(selectInput);
        int select = Integer.parseInt(selectInput);
        selectOptions(input, select);
      } catch (IllegalArgumentException e) {
        output.print(e.getMessage());
      }
    }
  }

  private void selectOptions(ConsoleInput input, int select) {
    switch (select) {
      case INQUIRY:
        List<String> result = repository.getResult();
        output.print(result);
        break;

      case CALCULATE:
        String expression = input.ExpressionInput();
        compute(expression);
        break;
    }
  }

  private void compute(String expression) {
    PatternValidator.checkExpressionValue(expression);
    InfixToPostfixConverter infixToPostfixConverter = new InfixToPostfixConverter();
    String postFixExpression = infixToPostfixConverter.changeToPostFix(expression);
    Accumulator calculator = new PostFixAccumulator();
    String result = Integer.toString(calculator.calculate(postFixExpression));
    output.print(result);
    repository.store(expression, result);
  }
}
