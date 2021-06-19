    import java.util.*;   
    public class AirplaneSeating  
    {   
    public static void main(String args[])   
    {  
        // Reading Input 
        int noOfPassengers, columns;   
        Scanner sc=new Scanner(System.in); 
        List<int[]> seatingArray = new ArrayList<int[]>(); 
        System.out.print("Enter the number of columns: ");       
        columns = sc.nextInt();
        for(int i=0;i< columns; i++)
        {
            int[] rowCol = new int[2];
            for(int j=1;j>=0;j--) {
                System.out.print("Enter the "+i+"th  column"+j+"th dimension:"); 
                 rowCol[j] =  sc.nextInt(); 
            }
            seatingArray.add(rowCol);
           
        }
        
        System.out.println("Enter the number of passengers :");  
        noOfPassengers = sc.nextInt(); 
        processSeating(columns,seatingArray, noOfPassengers);
    }  
    static void processSeating(int columns,List<int[]> seatingArray, int noOfPassengers) {
       int window = 0, aisle=0, center=0;
       int[] rowCol = new int[2];
       int opcol = 0,oprow = 0;
        
        for(int i=0;i<columns;i++) {
            rowCol = seatingArray.get(i);
            opcol += rowCol[1]; 
            oprow = Math.max(oprow,rowCol[0]);
            if(i == 0) {
                window += rowCol[0];    
                aisle += rowCol[0];  
                center += rowCol[0]*rowCol[1]-2*rowCol[0];
            } else if(i == columns -1 ) {
                window += rowCol[0];    
                aisle += rowCol[0]; 
                center += rowCol[0]*rowCol[1]-2*rowCol[0];  
            } else {
                int aisleSeatsInArray = rowCol[0]*2;
                int totalSeatsInArray = rowCol[0]*rowCol[1];
                if (totalSeatsInArray >=aisleSeatsInArray) {
                    aisle += aisleSeatsInArray;
                    center += totalSeatsInArray-aisleSeatsInArray;
                } 
            }
        }

        // Seating arrangement logic
        int aisleCounter = 0;
        int  windowCounter =  aisle ;
        int centerCounter = aisle + window;  
        
        // Creating output array and setting default value to -1
        int[][] oparr = new int[oprow][opcol];
        oparr = Arrays.stream(oparr)
                      .map(x -> Arrays.stream(x).map(y -> -1).toArray())
                      .toArray(int[][]::new);
                      
        oparr = identifySeatTypes(oparr,columns, seatingArray,noOfPassengers, windowCounter,aisleCounter, centerCounter);
        System.out.println("Identifying seat types");
        printOutput(oparr,oprow,opcol);

         
        oparr = seatingArrangement(oparr,oprow,opcol,noOfPassengers,windowCounter,aisleCounter,centerCounter);
        System.out.println("Seating arrangement for "+ noOfPassengers);
        printOutput(oparr,oprow,opcol);
    }

    // Method to identify the type of seat (window = -2, aisle = -3, center = -4)
    static int[][]  identifySeatTypes(int[][]oparr,int columns,List<int[]> seatingArray,int noOfPassengers,int windowCounter,int aisleCounter,int centerCounter){
         int rowStart = 0;
         int[] rowCol = new int[2];
         for(int i=0;i<columns;i++) {
            rowCol = seatingArray.get(i);
            for(int j = 0; j < rowCol[0];j++)
            {
                for(int k = 0; k < rowCol[1];k++)
                {
                    if(windowCounter < noOfPassengers && ( i == 0 && k == 0 )||(i == columns-1 && k == rowCol[1]-1 ))
                    {
                        oparr[j][rowStart + k] = -2;
                    }
                    else if((k==0||k==rowCol[1]-1)&&(aisleCounter < noOfPassengers))
                    {
                        oparr[j][rowStart +k] = -3;
                    }
                    else if(centerCounter < noOfPassengers)
                    {
                        oparr[j][rowStart +k] = -4;
                    }
                    else
                    {
                        oparr[j][rowStart +k] = 0;
                    }
                }    
            }

            rowStart +=  rowCol[1];
         }
         return oparr;
    }

    // Method to return the seating arrangements for a given no. of passengers
    static int[][] seatingArrangement(int[][] oparr,int oprow,int opcol,int noOfPassengers,int windowCounter,int aisleCounter,int centerCounter) {
        for(int j = 0; j < oprow;j++)
            {
                for(int k = 0; k < opcol;k++)
                {
                    if(oparr[j][k] == -2)
                    {
                        if(windowCounter < noOfPassengers) {
                            oparr[j][k] = ++windowCounter;
                        } else {
                            oparr[j][k] = 0;
                        }
                            
                     }
                    else if(oparr[j][k] == -3)
                    {
                        if(aisleCounter < noOfPassengers) {
                            oparr[j][k] = ++aisleCounter;
                        } else {
                            oparr[j][k] = 0;
                        }
                    }
                    else if(oparr[j][k] == -4)
                    {
                        if(centerCounter < noOfPassengers) {
                            oparr[j][k] = ++centerCounter;
                        } else {
                            oparr[j][k] = 0;
                        }
                    }
                }    
            }
        return oparr;
    }
    static void printOutput(int[][] arr, int row, int col) {
        for(int i=0;i< row;i++) {
            for(int j=0;j<col;j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();

        }
    }


       
       }
