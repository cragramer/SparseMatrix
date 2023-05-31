
import java.util.Scanner;

public class MultiplySparseMatrix {
    //write a class to implement the orthogonal linked list
    public static class linked {
        private int row;
        private int col;
        private int value;
        private linked col_next;
        private linked row_next;

        //private linked col_pointer;
        //private linked row_pointer;
        // constructor
        public linked(int row, int col, int value) {
            this.row = row;
            this.col = col;
            this.value = value;
        }

        public linked() {
            this.row = 0;
            this.col = 0;
            this.value = 0;
        }

        public int getrow() {
            return row;
        }

        public int getcol() {
            return col;
        }

        public int getvalue() {
            return value;
        }

        public linked getcol_next() {
            return col_next;
        }

        public linked getrow_next() {
            return row_next;
        }
    }

    // use class to store the sparse matrix
    public static class SparseMatrix {
        private int row;
        private int col;
        private int number_nonzero;
        private linked[] col_head;
        private linked[] row_head;
        private linked[] col_pointer;
        private linked[] row_pointer;

        public int getrow_number() {
            return row;
        }

        public int getcol_number() {
            return col;
        }

        public void initial(int row_numeral, int col_numeral) {
            //initial the sparse matrix
            row = row_numeral;
            col = col_numeral;
            col_head = new linked[col + 2];
            row_head = new linked[row + 2];
            col_pointer = new linked[col + 2];
            row_pointer = new linked[row + 2];

            //initial the col_head and row_head for every row and column
            for (int i = 1; i < col + 1; i++) {
                col_head[i] = new linked();
                col_pointer[i] = col_head[i];
            }
            for (int i = 1; i < row + 1; i++) {
                //System.out.println(i);
                row_head[i] = new linked();
                row_pointer[i] = row_head[i];
            }
        }

        public void insertnode(int row, int col, int value) {
            //insert a node to the matrix
            linked new_node = new linked(row, col, value);
            //link the new node to the col linked list and row linked list
            while (col_pointer[col].getcol_next() != null) {
                col_pointer[col] = col_pointer[col].getcol_next();
            }
            col_pointer[col].col_next = new_node;
            while (row_pointer[row].getrow_next() != null) {
                row_pointer[row] = row_pointer[row].getrow_next();
            }
            row_pointer[row].row_next = new_node;
        }


    //function to print the sparse matrix
    public void print() {
        //print the row and column number
        System.out.println(row + ", " + col);
        for (int i = 1; i < row+1; i++) {
            linked pointer = row_head[i];
            int j = 1;
            //print the row number
            System.out.print(i + " ");
            if (pointer == null || pointer.getrow_next()==null) {
                System.out.println(":");
                continue;
            }
            while (pointer != null) {
                if (pointer.value != 0) {
                    System.out.print(pointer.col + ":" + pointer.getvalue() + " ");
                }

                pointer = pointer.getrow_next();
                j++;
            }
            System.out.println();
        }
    }
    //function to convert string to the sparse matrix

}


    // Add two sparse matrices
    public static SparseMatrix multiplyMatrix(SparseMatrix a,  SparseMatrix b) {
        if (a.getcol_number() != b.getrow_number()) {
            System.out.println("The two matrix can not be multiplied");
            return null;
        }
        //initial the new matrix
        SparseMatrix new_matrix = new SparseMatrix();
        new_matrix.initial(a.getrow_number(), b.getcol_number());
        for (int i = 1; i < a.getrow_number()+1; i++) {
            //System.out.println(i);
            for (int j = 1; j < b.getcol_number()+1; j++) {
                //System.out.println(j);
                int sum = 0;
                linked linked_col_pointer = b.col_head[i];
                linked linked_row_pointer = a.row_head[j];
                while (linked_col_pointer != null && linked_row_pointer != null) {
                    if (linked_col_pointer.getrow() == linked_row_pointer.getcol()) {
                        sum += linked_col_pointer.getvalue() * linked_row_pointer.getvalue();
                        linked_col_pointer = linked_col_pointer.getcol_next();
                        linked_row_pointer = linked_row_pointer.getrow_next();
                    } else if (linked_col_pointer.getrow() < linked_row_pointer.getcol()) {
                        linked_col_pointer = linked_col_pointer.getcol_next();
                    } else {
                        linked_row_pointer = linked_row_pointer.getrow_next();
                    }
                }
                //System.out.println(sum);
                if (sum != 0) {
                    // add the sum to the new matrix
                    //System.out.println(i + " " + j + " " + sum);
                    new_matrix.insertnode(j, i, sum);
                }
            }
        }
        //new_matrix.print();
        return new_matrix;
        //return null;
    }

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        String line1 = input.nextLine();
        //print the first line1
        //System.out.println(line1);
        //find the ‘,’ in the string and get the number before the ‘,’
        int index1 = line1.indexOf(",");
        int row = Integer.parseInt(line1.substring(0, index1));
        while (line1.charAt(line1.length()-1)==' ') {
            line1 = line1.substring(0, line1.length()-1);
        }
        //get the number after the ‘,’
        int col = Integer.parseInt(line1.substring(index1 + 2, line1.length()));
        //print the row and column number
        String line = input.nextLine();
        SparseMatrix a = new SparseMatrix();
        a.initial(row, col);
        for (int i = 0; i < row; i++) {
            //find the ‘:’ in the string and get the number before the ‘:’
            int index = line.indexOf(":");
            while (index != -1) {
                //get the number before the ‘:’
                //find the ' ' before the ':'
                int index_space = line.substring(0, index).lastIndexOf(" ");
                if (index_space + 1 == index || index_space == 0) {
                    break;
                }
                int col_this = Integer.parseInt(line.substring(index_space + 1, index));
                //find the ' ' after the ':'
                int index_space2 = line.substring(index + 1).indexOf(" ");
                if (index_space2 == 0 || index == line.length() - 1) {
                    break;
                }
                if (index_space2 == -1) {
                    index_space2 = line.substring(index + 1).length();
                }
                int value_this = Integer.parseInt(line.substring(index + 1, index + index_space2 + 1));
               // System.out.println(i + 1 + " " + col_this + " " + value_this);
                a.insertnode(i + 1, col_this, value_this);
                //update the index
                int index_new = line.indexOf(":", index + 1);
                if (index_new > index) {
                    index = index_new;
                } else {
                    index = -1;
                }
            }
            line = input.nextLine();
        }
        //System.out.println("The first matrix is:");
        //a.print();
        //read the next matrix
        int index2 = line.indexOf(",");
        int row2 = Integer.parseInt(line.substring(0, index2));
        //get the number after the ‘,’
        while (line.charAt(line.length()-1)==' ') {
            line = line.substring(0, line.length()-1);
        }
        int col2 = Integer.parseInt(line.substring(index2 + 2, line.length()));
        //print the row and column number
        SparseMatrix b = new SparseMatrix();
        b.initial(row2, col2);
        line=input.nextLine();
        for(int i=0;i<row2;i++)
        {
            //System.out.println(line);
            //find the ‘:’ in the string and get the number before the ‘:’
            int  index = line.indexOf(":");
            while (index != -1) {
                //System.out.println("one_while_loop");
                //get the number before the ‘:’
                //find the ' ' before the ':'
                int index_space = line.substring(0, index).lastIndexOf(" ");
                if (index_space + 1 == index || index_space == 0) {
                    break;
                }
                int col_this = Integer.parseInt(line.substring(index_space + 1, index));
                //find the ' ' after the ':'
                int index_space2 = line.substring(index + 1).indexOf(" ");
                if (index_space2 == 0 || index == line.length() - 1) {
                    break;
                }
                if (index_space2 == -1) {
                    index_space2 = line.substring(index + 1).length();
                }
                int value_this = Integer.parseInt(line.substring(index + 1, index + index_space2 + 1));
                //System.out.println(i + 1 + " " + col_this + " " + value_this);
                b.insertnode(i + 1, col_this, value_this);
                //update the index
                int index_new = line.indexOf(":", index + 1);
                if (index_new > index) {
                    index = index_new;
                } else {
                    index = -1;
                }
            }
            if (i != row2 - 1) {line = input.nextLine();}
            else{ i=row2;}
            //System.out.println(line);
        }
        //System.out.println("The second matrix is:");
        //b.print();
        //multiply the two matrix
        SparseMatrix c = multiplyMatrix(a, b);
        c.print();
    }
}

