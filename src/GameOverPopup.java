import javax.swing.*;

public class GameOverPopup extends JOptionPane {

    public Object[] options = {"Play Again","Exit"};
    public JDialog dialog;

    public GameOverPopup(String message, String title) {
        super(message);

        setOptions(options);

        dialog = createDialog(new JFrame(), title);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    public Boolean returnAnswer(){
        Object promptAnswer = getValue();
        boolean answer;

        if(promptAnswer.equals(options[0])) {
            answer = true;
        }else{
        	 answer = false;
        }

        dialog.dispose();

        return answer;
    }
}
