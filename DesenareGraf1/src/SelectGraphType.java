import javax.swing.*;

public class SelectGraphType extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;

    public SelectGraphType() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    public static void main(String[] args) {
        SelectGraphType dialog = new SelectGraphType();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
