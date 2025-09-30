package To_Do_App;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TaskManager extends JFrame {
    private DefaultListModel<Task> listModel;
    private JList<Task> list;
    private JTextField input;
    private JButton addBtn, delBtn, doneBtn;

    static class Task {
        String text;
        boolean done;

        Task(String t) {
            text = t;
            done = false;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public TaskManager() {
        setTitle("Task App");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5,5));
        setLocationRelativeTo(null);

        
        listModel = new DefaultListModel<>();
        list = new JList<>(listModel);
        JScrollPane scroll = new JScrollPane(list);
        add(scroll, BorderLayout.CENTER);

        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object val, int idx,
                                                          boolean sel, boolean focus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(l, val, idx, sel, focus);
                Task t = (Task) val;
                if(t.done){
                    label.setForeground(Color.GRAY);
                    label.setFont(label.getFont().deriveFont(Font.ITALIC));
                }
                return label;
            }
        });

       
        input = new JTextField();
        addBtn = new JButton("Add");
        delBtn = new JButton("Delete");
        doneBtn = new JButton("Done");

        JPanel top = new JPanel(new BorderLayout(5,5));
        top.add(input, BorderLayout.CENTER);
        top.add(addBtn, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        JPanel bottom = new JPanel();
        bottom.add(doneBtn);
        bottom.add(delBtn);
        add(bottom, BorderLayout.SOUTH);

        // Add task
        addBtn.addActionListener(e -> addTask());
        input.addActionListener(e -> addTask());

        // Delete task
        delBtn.addActionListener(e -> {
            int i = list.getSelectedIndex();
            if(i != -1) listModel.remove(i);
            else JOptionPane.showMessageDialog(this,"Select a task!","Warning",JOptionPane.WARNING_MESSAGE);
        });

        // Mark done
        doneBtn.addActionListener(e -> {
            int i = list.getSelectedIndex();
            if(i != -1){
                Task t = listModel.getElementAt(i);
                t.done = true;
                list.repaint();
            } else {
                JOptionPane.showMessageDialog(this,"Select a task!","Warning",JOptionPane.WARNING_MESSAGE);
            }
        });

        //edit
        list.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    int i = list.locationToIndex(e.getPoint());
                    if(i!=-1){
                        Task t = listModel.getElementAt(i);
                        String upd = JOptionPane.showInputDialog(TaskManager.this,"Edit Task:",t.text);
                        if(upd!=null && !upd.trim().isEmpty()){
                            t.text = upd.trim();
                            list.repaint();
                        }
                    }
                }
            }
        });
    }

    private void addTask(){
        String txt = input.getText().trim();
        if(txt.isEmpty()){
            JOptionPane.showMessageDialog(this,"Enter a task!","Warning",JOptionPane.WARNING_MESSAGE);
            return;
        }
        listModel.addElement(new Task(txt));
        input.setText(""); 
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new TaskManager().setVisible(true));
    }
}
