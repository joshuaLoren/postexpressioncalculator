/**
 *  Write a Java program that allows the user to enter prefix expressions in a text field. The program reads the expression, 
 *  evaluates it, and displays the value in a suitable GUI component. A stack is used to stroe values of sub-expressions as
 *  they are computed. Another stack is used to store operators that have not yet been applied.
 *  @author Small
 */
 
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.*;

public class Project_1_JavaFX_Small extends Application 
{//open class

   public static void main(String[] args) 
   {//open main
      launch(args);
   }//close main
    
   /**
   *   computeExpression() takes in String[] expression as a parameter. Expression is gathered from 
   *   the expressionTextField TextField. computeExpression() It will first validate the input to be
   *   sure that negative numbers, subtraction signs, and division signs are not proccessed. If none 
   *   of these are found, it will add String[] to the Queue initialExpressionQueue and will process
   *   the queue by adding it to multiple stacks.
   *   @param String[] expression - gathered from TextField
   *   @return String result or String errorMessage  
   */
   
   public static String computeExpression(String[] expression) throws EmptyStackException
   {//open computeExpression
      
      int firstNumber = 0;
      int secondNumber = 0;
      int subExpressionResult = 0;
      int result = 0;
      String operator = " ";
      String errorMessage = " ";
      
      //create a stack that stores values of sub-expressions as they are computed
      Stack<Integer> subExpressionStack = new Stack<Integer>();
      //create a stack that will store operators that have not been applied
      Stack<String> operatorStack = new Stack<String>();
      //create a stack that will store operands
      Stack<Integer> operandStack = new Stack<Integer>();
      //create a LinkedList to hold the intitial characters of our expression
      Queue<String> initialExpressionQueue = new LinkedList<String>();
      
      try{
         for(int i=0; i < expression.length; i++)
         {
            
            if(expression[i].equals("-"))
            {
               System.out.println("User entered a subtraction operator");
               errorMessage = "Subtraction is not allowed.";
               return errorMessage;
            }
            else if(expression[i].contains("-"))
            {
               System.out.println("User entered a negative number");
               errorMessage = "Negative numbers are not allowed.";
               return errorMessage;
            }   
            
            else if(expression[i].equals("/"))
            {
               System.out.println("User used a division sign");
               errorMessage = "Division operator is not allowed";
               return errorMessage;
            }
               else
               {
                  initialExpressionQueue.add(expression[i]);
                  System.out.println("Adding to Queue: " + expression[i]);
               }
         }
      
         //first, we will get all of the operators from our queue
         while(!initialExpressionQueue.isEmpty())
         {
            if(initialExpressionQueue.peek().equals("+") || initialExpressionQueue.peek().equals("*"))
            {
               operatorStack.push(initialExpressionQueue.poll());
            }
            else if(Integer.parseInt(initialExpressionQueue.peek()) > 0) //verifies that we are dealing with an integer
            {
               operandStack.push(Integer.parseInt(initialExpressionQueue.poll()));
            }
         }
         
         //for debugging purposes
         System.out.println("These items were added to Operator Stack: " + operatorStack); //print message to console
         System.out.println("These items were added to Operand Stack: " + operandStack); //print message to console
      
         /**
         *   We will remove the top two elements from the operand stack, assigning them to the
         *   variables firstNumber and secondNumber. Next, we will pop the top item from the operator
         *   stack and apply it to the operands. We will repeat this until there is only one operator
         *   left. Subexpressions will be added to the subExpressionStack as they are computed.
         */
      
         while(!operandStack.isEmpty())
         {
            operator = operatorStack.pop();
            firstNumber = operandStack.pop();
            secondNumber = operandStack.pop();
         
            switch(operator)
            {
               case "+": subExpressionResult = firstNumber + secondNumber;
                  subExpressionStack.push(subExpressionResult);
                  break;
               case "*": subExpressionResult = firstNumber * secondNumber;
                  subExpressionStack.push(subExpressionResult);
                  break;
            }
         
         } 
      
         //for debugging purposes only
         System.out.println("These items remain in Operator Stack: " + operatorStack); //print message to console
         System.out.println("These items remain in Operand Stack: " + operandStack); //print message to console
         System.out.println("These values are in the Subexpression Stack: " + subExpressionStack); //print message to console
      
         /*
         *   Next, if there is a remaining operator we will apply it to the items in subExpressionStack
         *   otherwise subExpression stack will equal result
         */
      
         if(!operatorStack.isEmpty())
         {
            operator = operatorStack.pop();
            firstNumber = subExpressionStack.pop();
            secondNumber = subExpressionStack.pop();
         
            switch(operator)
            {
               case "+": result = firstNumber + secondNumber;
                  break;
               case "*": result = firstNumber * secondNumber;
                  break;
            }
            //for dubugging purposes only
            System.out.println("This operator was applied to subExpressionStack elements: " + operator);
         }
         else
         {
            result = subExpressionResult;
         }
      }
      catch(EmptyStackException | NumberFormatException e) //catch exceptions
      {
         System.out.println("EmptyStackException or NumberFormatException");
      }
      return Integer.toString(result);
      
   }//close computeExpression

   @Override
   public void start(Stage primaryStage) //javaFX entry point
   {
      primaryStage.setTitle("Prefix Expression Calculator");
      GridPane grid = new GridPane();
      grid.setAlignment(Pos.CENTER);
      grid.setHgap(10);
      grid.setVgap(10);
      grid.setPadding(new Insets(10, 10, 10, 10));
   
      //create text for scene title, then add to the grid
      Text scenetitle = new Text("Prefix Expression Calculator");
      scenetitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
      grid.add(scenetitle, 0, 1, 2, 1);
   
      //create label call expression label, then add to the grid
      Label expressionLabel = new Label("Prefix Expression:");
      grid.add(expressionLabel, 0, 2);
        
      //create a text field for the expression, then add to grid
      TextField expressionTextField = new TextField();
      grid.add(expressionTextField, 1, 2);
         
      //create a label for result, then add to grid
      Label result = new Label("Result:");
      grid.add(result, 0, 4);
   
      //create text called inputInstructions
      Text inputInstructions = new Text("Ex: * + 16 4 + 3 1");
      grid.add(inputInstructions, 1, 3);
      
      //create a btn called compute, then add it to the grid
      Button btn = new Button("Compute");
      HBox hbBtn = new HBox(10);
      hbBtn.setAlignment(Pos.CENTER);
      hbBtn.getChildren().add(btn);
      grid.add(hbBtn, 1, 5);
   
      //create text called resultDisplay, then add it to the grid
      final Text resultDisplay = new Text();
      resultDisplay.setFill(Color.GREEN);
      grid.add(resultDisplay, 1, 4);
      
      //add text called instructions to grid
      final Text instructions = new Text("Be sure to include spaces between" + 
      " operands and operators");
      grid.add(instructions, 0, 0, 2, 1);
   
      //create an event handler to handle clicking on the compute btn
      btn.setOnAction(
         new EventHandler<ActionEvent>() 
         {
            @Override
            public void handle(ActionEvent e) 
            {  
               String inputString = expressionTextField.getText();
               String[] splitStr = inputString.trim().split("\\s+");
            
               if(splitStr.length == 1)
               {
                  resultDisplay.setText(splitStr[0]);
               } else if((expressionTextField.getText() != null && !expressionTextField.getText().isEmpty()))
               {
                  resultDisplay.setText(computeExpression(splitStr));
               }
            }
         });
   
      //create the scene with the grid pane as root node
      Scene scene = new Scene(grid, 500, 500);
      primaryStage.setScene(scene);
      primaryStage.show();
      
      //for debuggomg puposes only
      grid.setGridLinesVisible(false);
      
   }//close start
   
}//close class