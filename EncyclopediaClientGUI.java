import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

public class EncyclopediaClientGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final int NUM_THREADS = 5;
    private ExecutorService executor;
    private Encyclopedia encyclopedia;
    private JLabel countLabel;
    private JLabel repeatedLabel;
    private JLabel longestLabel;
    private JLabel shortestLabel;
    private JLabel repeatLabel;

    public EncyclopediaClientGUI() {
        super("Encyclopedia");

        executor = Executors.newFixedThreadPool(NUM_THREADS);

        try {
            encyclopedia = (Encyclopedia) Naming.lookup("rmi://localhost:1234/Encyclopedia");
        } catch (Exception e) {
            System.err.println("Encyclopedia client exception:");
            e.printStackTrace();
        }

        JPanel panel = new JPanel(new GridLayout(5, 2));

        JButton countButton = new JButton("Count Letters");
        countLabel = new JLabel(" ");
        panel.add(countButton);
        panel.add(countLabel);

        JButton repeatedButton = new JButton("Find Repeated Words");
        repeatedLabel = new JLabel(" ");
        JScrollPane repeatedScrollPane = new JScrollPane(repeatedLabel);
        panel.add(repeatedButton);
        panel.add(repeatedScrollPane);

        JButton longestButton = new JButton("Find Longest Word");
        longestLabel = new JLabel(" ");
        panel.add(longestButton);
        panel.add(longestLabel);

        JButton shortestButton = new JButton("Find Shortest Word");
        shortestLabel = new JLabel(" ");
        panel.add(shortestButton);
        panel.add(shortestLabel);

        JButton repeatButton = new JButton("Find Repeat Counts");
        repeatLabel = new JLabel(" ");
        JScrollPane repeatScrollPane = new JScrollPane(repeatLabel);
        panel.add(repeatButton);
        panel.add(repeatScrollPane);

        countButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Integer, Void> worker = new SwingWorker<Integer, Void>() {
                    @Override
                    protected Integer doInBackground() throws Exception {
                        return encyclopedia.count();
                    }

                    @Override
                    protected void done() {
                        try {
                            int count = get();
                            countLabel.setText(Integer.toString(count));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                };
                executor.execute(worker);
            }
        });

        repeatedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<String[], Void> worker = new SwingWorker<String[], Void>() {
                    @Override
                    protected String[] doInBackground() throws Exception {
                        return encyclopedia.repeatedWords();
                    }

                    @Override
                    protected void done() {
                        try {
                            String[] repeatedWords = get();
                            repeatedLabel.setText(String.join(", ", repeatedWords));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                };
                executor.execute(worker);
            }
        });

        longestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                    @Override
                    protected String doInBackground() throws Exception {
                        return encyclopedia.longest();
                    }

                    @Override
                    protected void done() {
                        try {
                            String longest = get();
                            longestLabel.setText(longest);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                };
                executor.execute(worker);
            }
        });

        shortestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
                    @Override
                    protected String doInBackground() throws Exception {
                        return encyclopedia.shortest();
                    }

                    @Override
                    protected void done() {
                        try {
                            String shortest = get();
                            shortestLabel.setText(shortest);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                };
                executor.execute(worker);
            }
        });

        repeatButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingWorker<Object[], Void> worker = new SwingWorker<Object[], Void>() {
                    @Override
                    protected Object[] doInBackground() throws Exception {
                        return encyclopedia.repeat();
                    }

                    @Override
                    protected void done() {
                        try {
                            Object[] repeatCounts = get();
                            String[] n = (String[]) repeatCounts[0];
                            int[] nn = (int[]) repeatCounts[1];
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < n.length; i++) {
                                if (n[i] != null) {
                                    sb.append(n[i] + ": " + nn[i] + ", ");
                                }
                            }
                            repeatLabel.setText(sb.toString());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                };
                executor.execute(worker);
            }
        });

        add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new EncyclopediaClientGUI());
    }
}
