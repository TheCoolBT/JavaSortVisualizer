import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Visualizer extends JPanel implements ActionListener{

    //Initialize Variables
    final int SCREEN_WIDTH = 1280;
    final int SCREEN_HEIGHT = 720;
    final int BAR_HEIGHT = SCREEN_HEIGHT;
    final int BAR_WIDTH = 5;
    final int NUM_BARS = SCREEN_WIDTH/BAR_WIDTH;

    JButton jbtBubbleSort = new JButton();
    JButton jbtInsertSort = new JButton();
    JButton jbtSelectSort = new JButton();
    //JButton jbtQuickSort = new JButton();

    SwingWorker<Void,Void> shuffler, sorter;

    int[] array = new int[NUM_BARS];
    int current;
    int traverse;

    public Visualizer() {
        setBackground(Color.darkGray);
        setPreferredSize(new Dimension());

        //Initialize Bar Height
        for (int i = 0; i < NUM_BARS; i++) {
            array[i] = i * BAR_HEIGHT / NUM_BARS;
        }

        //InsertionSort Button
        jbtInsertSort.setText("Insertion Sort");
        this.add(jbtInsertSort);
        jbtInsertSort.addActionListener(this);


        //BubbleSort Button
        jbtBubbleSort.setText("Bubble Sort");
        this.add(jbtBubbleSort);
        jbtBubbleSort.addActionListener(this);

        //SelectSort Button
        jbtSelectSort.setText("Selection Sort");
        this.add(jbtSelectSort);
        jbtSelectSort.addActionListener(this);

        //QuickSort Button
        //jbtQuickSort.setText("Quick Sort");
        //this.add(jbtQuickSort);
        //jbtQuickSort.addActionListener(this);

    }


    public void shuffleArray() {
        shuffler = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws InterruptedException {
                Random random = new Random();
                for (int i = 0; i < NUM_BARS; i++) {
                    int swap = random.nextInt(NUM_BARS - 1);
                    swap(array, i, swap);

                    Thread.sleep(10);
                    repaint();
                }
                return null;
            }

            @Override
            public void done() {
                super.done();
                sorter.execute();
            }
        };
        shuffler.execute();
    }


    public void insertionSort() {
        sorter = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                for (current = 1; current < NUM_BARS; current++) {
                    traverse = current;
                    while (traverse > 0 && array[traverse] < array[traverse - 1]) {
                        swap(array,traverse, traverse - 1);
                        traverse--;

                        Thread.sleep(1);
                        repaint();
                    }
                }
                current = 0;
                traverse = 0;
                return null;
            }
        };
    }

    public void bubbleSort() {
        sorter = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                for(current = 0; current < NUM_BARS; current++) {
                    for(traverse = 1; traverse < (NUM_BARS - current); traverse++) {
                        if(array[traverse-1] > array[traverse]) {
                            swap(array,traverse, traverse-1);
                            traverse--;

                            Thread.sleep(1);
                            repaint();
                        }
                    }
                }
                current = 0;
                traverse = 0;
                return null;
            }
        };
    }

    public void selectionSort() {
        sorter = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() throws InterruptedException {
                for(int current = 0; current < (NUM_BARS -1); current++) {
                    int min = current;
                    for (int traverse = current+1; traverse < NUM_BARS; traverse++) {
                        if (array[traverse] < array[min]) {
                            min = traverse;
                        }
                    }
                    swap(array,current,min);


                    Thread.sleep(50);
                    repaint();
                }
                current = 0;
                traverse = 0;
                return null;
            }
        };
    }



    public void quickSort(int low, int high) {
        sorter = new SwingWorker<Void, Void>() {

            public boolean arraySorted(int a[], int n)
            {
                if (n == 1 || n == 0)
                    return true;

                return a[n - 1] >= a[n - 2]
                        && arraySorted(a, n - 1);
            }

            public int partition (int[] arr, int low, int high) throws InterruptedException {

                int pivot = arr[high];

                int i = (low - 1);

                for (int j = low; j <= high -1; j++) {
                    if(arr[j] < pivot) {
                        i++;
                        swap(arr,i,j);

                        Thread.sleep(10);
                        repaint();
                    }
                }
                swap(arr,i+1,high);
                return i+1;
            }

            @Override
            public Void doInBackground() throws InterruptedException {
                while (low < high) {
                    int pivot = partition(array,low, high);
                    quickSort(low, pivot-1);
                    quickSort(pivot+1, high);
                }
                return null;
            }
        };
    }

    public void swap(int[] arr, int indexOne, int indexTwo) {
        int temp = arr[indexOne];
        arr[indexOne] = arr[indexTwo];
        arr[indexTwo] = temp;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics = (Graphics2D)g;
        super.paintComponent(graphics);


        g.setColor(Color.white);
        for(int i = 0; i < NUM_BARS; i++) {
            graphics.fillRect(i * BAR_WIDTH,SCREEN_HEIGHT-array[i],BAR_WIDTH,array[i]);
        }
        g.setColor(Color.green);
        graphics.fillRect(current*BAR_WIDTH,SCREEN_HEIGHT-array[current], BAR_WIDTH, array[current]);

        g.setColor(Color.red);
        graphics.fillRect(traverse*BAR_WIDTH,SCREEN_HEIGHT-array[traverse], BAR_WIDTH, array[traverse]);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbtInsertSort) {
            insertionSort();
            shuffleArray();
        }
        else if (e.getSource() == jbtBubbleSort) {
            bubbleSort();
            shuffleArray();
        }
        else if (e.getSource() == jbtSelectSort) {
            selectionSort();
            shuffleArray();
        }
        //else if (e.getSource() == jbtQuickSort) {
          //  quickSort(1,NUM_BARS-1);
          //  shuffleArray();
       // }
    }
}
