import javafx.scene.control.SelectionModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class zetExample extends JFrame {
    private DefaultListModel<String> model;
    private JList<String> myList;
    private JButton remAllBtn;
    private JButton addBtn;
    private JButton renBtn;
    private JButton delBtn;

    private JButton okBtn;
    private JLabel enabledLbl;
    private JLabel pressedLbl;
    private JLabel armedLbl;
    private JCheckBox checkBox;

    public zetExample() {
        initUI();
    }

    private void createList() {
        model = new DefaultListModel<>();
        model.addElement("Amelie");
        model.addElement("Aguirre, der Zone Gottes");
        model.addElement("Fargo");
        model.addElement("Exorcist");
        model.addElement("Schindler's myList");

        myList = new JList<>(model);
        myList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        myList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = myList.locationToIndex(e.getPoint());
                    String item = model.getElementAt(index);
                    String text = JOptionPane.showInputDialog("Rename item", item);

                    String newItem;

                    if (text != null) {
                        newItem = text.trim();
                    } else {
                        return;
                    }

                    if (!newItem.isEmpty()) {
                        model.remove(index);
                        model.add(index, newItem);

                        ListSelectionModel selModel = myList.getSelectionModel();
                        selModel.setLeadSelectionIndex(index);
                    }
                }
            }
        });
    }

    private void createButtons() {
        remAllBtn = new JButton("Remove All");
        addBtn = new JButton("Add");
        renBtn = new JButton("Rename");
        delBtn = new JButton("Delete");

        addBtn.addActionListener(e -> {
            String text = JOptionPane.showInputDialog("Add a new item");
            String item;

            if (text != null) {
                item = text.trim();
            } else {
                return;
            }
            if (!item.isEmpty()) {
                model.addElement(item);
            }
        });

        delBtn.addActionListener(e -> {
            ListSelectionModel selModel = myList.getSelectionModel();
            int index = selModel.getMinSelectionIndex();

            if (index >= 0) {
                model.remove(index);
            }
        });

        renBtn.addActionListener(e -> {

            ListSelectionModel selModel = myList.getSelectionModel();
            int index = selModel.getMinSelectionIndex();

            if (index == -1) {
                return;
            }

            String  item = model.getElementAt(index);
            String text = JOptionPane.showInputDialog("Rename item", item);
            String newItem;

            if (text != null) {
                newItem = text.trim();
            } else {
                return;
            }

            if (!newItem.isEmpty()) {

                model.remove(index);
                model.add(index, newItem);
            }
        });

        remAllBtn.addActionListener(e -> model.clear());
    }

    private void initUI() {
        okBtn = new JButton("OK");
        okBtn.addChangeListener(new DisabledChangeListener());
        checkBox = new JCheckBox();
        checkBox.setAction(new CheckBoxAction());

        enabledLbl = new JLabel("Endabled: true");
        pressedLbl = new JLabel("Pressed: false");
        armedLbl = new JLabel("Armed: false");

        createLayout(okBtn, checkBox, enabledLbl, pressedLbl, armedLbl);

        setTitle("Button Model");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void createLayout(JComponent... arg) {
        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        gl.setHorizontalGroup(gl.createParallelGroup()
                .addGroup(gl.createSequentialGroup()
                        .addComponent(arg[0])
                        .addGap(80)
                        .addComponent(arg[1]))
                .addGroup(gl.createParallelGroup()
                        .addComponent(arg[2])
                        .addComponent(arg[3])
                        .addComponent(arg[4]))
        );

        gl.setVerticalGroup(gl.createSequentialGroup()
                .addGroup(gl.createParallelGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1]))
                .addGap(40)
                .addGroup(gl.createSequentialGroup()
                        .addComponent(arg[2])
                        .addComponent(arg[3])
                        .addComponent(arg[4]))
        );

        pack();
    }

    private class DisabledChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            DefaultButtonModel model = (DefaultButtonModel) okBtn.getModel();

            if (model.isEnabled()) {
                enabledLbl.setText("Enabled: true");
            } else {
                enabledLbl.setText("Enabled: false");
            }

            if (model.isArmed()) {
                armedLbl.setText("Armed: true");
            } else {
                armedLbl.setText("Armed: false");
            }

            if (model.isPressed()) {
                pressedLbl.setText("Pressed: true");
            } else {
                pressedLbl.setText("Pressed: false");
            }
        }
    }

    private class CheckBoxAction extends AbstractAction {
        public CheckBoxAction() {
            super("Disabled");
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            if (okBtn.isEnabled()) {
                okBtn.setEnabled(false);
            } else {
                okBtn.setEnabled(true);
            }
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            zetExample ex = new zetExample();
            ex.setVisible(true);
        });
    }
}
