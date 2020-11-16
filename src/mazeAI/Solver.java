/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mazeAI;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aya
 */
public class Solver {
	public static final int NORTH = 0;
	public static final int EAST = 1;
	public static final int SOUTH = 2;
	public static final int WEST = 3;
	public static final int WALL = 1;
	public static final int NO_WALL = 0; 
	public static final int NOT_EXIST = -1; 
	
	public static final int CELL_WIDTH = 20;
	public static final int MARGIN = 50;

    
    Cell startCell;
    Cell goalCell;
    Cell bestCell;
    int width;
    int height;
    Cell[] cells;

    List <Cell> openL;
    List <Cell> closedL;
    List <Cell> solution;

    public Solver(Cell start,Cell goal, Cell[] cells, int width, int height) 
    {
        this.startCell = start;
        this.startCell.setParent(null);
        this.goalCell = goal;
        this.cells = cells;
        for(int i=0;i<cells.length;i++)
        	for( int j=0;j<4;j++)
        		System.out.println(cells[i].border[j]);
        openL=new ArrayList<Cell>();
        closedL=new ArrayList<Cell>();
        solution=new ArrayList<Cell>();
       this.width=width;
       this.height=height;
       
       aStar();
        System.out.println("const");
    }

/*this function for calculating manhatance distance which is the heuristic function*/
public static int manhatten_distance(Cell w,Cell g)
{
    int x0=w.getRow();
    int x1=g.getRow();
    int y0=w.getColumn();
    int y1=g.getColumn();
    int distance=Math.abs(x1-x0)+ Math.abs(y1-y0);
    return distance;
}


/*function for tie breaker according to the least h function first
 * if more than one have the same h we put them in another arraylist
 * and return the first  element of it
 */
public static Cell tie_breaker(List <Cell> CellList)
{
    List <Cell> equalCells=new ArrayList<Cell>();
    Cell t=CellList.get(0);
    
    for(int i=1;i<CellList.size();i++)
    {
     if(CellList.get(i).getHn()<t.getHn())
     {t=CellList.get(i);
     }
     
    }
    
    for(int i=0;i<CellList.size();i++)
    {
     if(CellList.get(i).getHn()==t.getHn())
     {equalCells.add(CellList.get(i));
     }
     
    }

    
    return equalCells.get(0);
}

/* a function to check if any two given Cells are the same according to the x and 
y values we use it in the code to check if we reached the goal or not*/
public static boolean isEqualCells(Cell f,Cell g)
{
    if((f.getRow()==g.getRow())&&(f.getColumn()==g.getColumn()))
        return true;
    else 
        return false;
}

/* checking if a Cell is in the open or closed list */
public boolean isInCloseOrOpenLists(Cell n)
{   
    
for(int i=0;i<openL.size();i++)
{
        if((n.getRow()==openL.get(i).getRow())&&(n.getColumn()==openL.get(i).getColumn()))
          return true;
}

for(int i=0;i<closedL.size();i++)
{
        if((n.getRow()==closedL.get(i).getRow())&&(n.getColumn()==closedL.get(i).getColumn()))
          return true;
}

    return false;
}

/* to check if the path of a visited Cell befor better than the new one or not*/
public boolean isBetter(Cell n)
{   boolean b=false;
    
for(int i=0;i<openL.size();i++)
{
        if((n.getRow()==openL.get(i).getRow())&&(n.getColumn()==openL.get(i).getColumn())&&((n.getFn())<openL.get(i).getFn()))
        { 
            openL.removeIf(n1-> ((n1.getRow()==n.getRow())&& (n1.getColumn()==n.getColumn())));
          openL.add(n);
          return true;
        }
}

for(int i=0;i<closedL.size();i++)
{
        if((n.getRow()==closedL.get(i).getRow())&&(n.getColumn()==closedL.get(i).getColumn())&&((n.getFn())<closedL.get(i).getFn()))
        {
            closedL.removeIf(n1-> ((n1.getRow()==n.getRow())&& (n1.getColumn()==n.getColumn())));
          openL.add(n);
          return true;

        }    
}

    return b;
}


public void printt()
{
    for(int i=0;i<openL.size();i++)
        System.out.println(openL.get(i).getRow()+","+openL.get(i).getColumn());
}

/*
 * returns the best Cell in openList
 * a function to choose the smallest f function in the openlist to expand after finding it if we 
 *have two or more Cells have the same f we pass the arraylist contains them to the tie breaker fun
 */
public Cell bestInOpenList()
{                               

    List <Cell> bstL=new ArrayList<Cell>();

    Cell bst=openL.get(0);
    for(int i=1;i<openL.size();i++)
    {
        if(openL.get(i).getFn()<bst.getFn())
            bst=openL.get(i);
    }
    
    for(int i=0;i<openL.size();i++)
    {
        if(openL.get(i).getFn()==bst.getFn())
            bstL.add(openL.get(i));
    }
    
    if(bstL.size()>1)
        bst=tie_breaker(bstL);
                                    

return bst;
}

/*filling the solution arraylist*/
public List<Cell> solution(Cell bst)
{  solution.add(goalCell);

   if(bst.getParent()==null)  
    return solution;
   else 
   {
       solution.add(bst.getParent());
       solution(bst.getParent());
       System.out.println("sol"+bst.getRow());
       return solution;
   }
       
}

/*public void sorting(List <Cell> t)
{   Cell temp=new Cell();
    Cell temp1=new Cell();
    Cell temp2=new Cell();
     for (int i = 0; i < t.size(); i++) 
        {
            for (int j = i + 1; j < t.size(); j++) { 
                if (t.get(i).getFn() > t.get(j).getFn()) 
                {
                    temp = t.get(i);
                    temp1=t.get(i);
                    temp2=t.get(j);
                    temp1=temp2;
                    temp2=temp;
                }
            }
        }
}*/

/*the function of the algorithm*/
public void aStar ()
{   openL.add(startCell);//adding the first state to be expand in the open list which is row 0 in traditional table
    boolean bet;  //checking if the bath is better 
    
    System.out.println("a star");
    do
    {              
        System.out.println("do");
        if(openL.size()==0)//no solution case
        {//sol(0,f);
         System.out.println("empty");
        break;
        }
            
    bestCell=bestInOpenList();
        // System.out.println(bst.getRow()+","+bst.getColumn()+","+bst.getE());

         if ((isEqualCells(bestCell,goalCell)))//reached the goal
        {           System.out.println("found");
                //System.out.println(bst.getRow());
              //  System.out.println(this.g.getColumn());
            solution(bestCell);
            break;
        }
        else
        {         

            /* all the if statments below for checking which children to expand if any exists*/
        if(bestCell.border[WEST] == NO_WALL)
        {                  System.out.println("west");
        	int position=((bestCell.getRow()*width)+bestCell.getColumn())-1;
            System.out.println(position);

        	Cell subCell=cells[position];
           // subCell.setColumn(bestCell.getColumn()-1);
           // subCell.setRow(bestCell.getRow());
            int dist=manhatten_distance(subCell,goalCell);
            int cost=bestCell.getGn()+1;
            if(!isInCloseOrOpenLists(subCell))
            {   subCell.setParent(bestCell);
                openL.add(subCell);
            	subCell.setHn(dist);
            	subCell.setGn(cost);

            }
            else
            {
             bet=isBetter(subCell);
             if(bet)
             {
                subCell.setParent(bestCell);
            	subCell.setHn(dist);
            	subCell.setGn(cost);

             }
            }          
        }
//System.out.println("west");
        if(bestCell.border[EAST] == NO_WALL)
        {System.out.println("east");
    	int position=((bestCell.getRow()*width)+bestCell.getColumn())+1;
        Cell E=cells[position];
            //E.setRow(bestCell.getRow());
            //E.setColumn(bestCell.getColumn()+1);
        int dist=manhatten_distance(E,goalCell);
        int cost=bestCell.getGn()+1;
            if(!isInCloseOrOpenLists(E))
            {   E.setParent(bestCell);
                openL.add(E);
            	E.setHn(dist);
            	E.setGn(cost);
                                //    System.out.println("in east");

                                   // System.out.println(E.getHn());
            }
            else
            {
             bet=isBetter(E);
             if(bet)
             {
                E.setParent(bestCell);
            	E.setHn(dist);
            	E.setGn(cost);

             }
            }          

        }

        if(bestCell.border[SOUTH] == NO_WALL)
        {
        	int position=((bestCell.getRow()*width)+bestCell.getColumn())+width;
            Cell s=cells[position];
           // s.setRow(bestCell.getRow()+1);
           // s.setColumn(bestCell.getColumn());
            int dist=manhatten_distance(s,goalCell);
            int cost=bestCell.getGn()+1;
            System.out.println("south");
            if(!isInCloseOrOpenLists(s))
            {   s.setParent(bestCell);
            	s.setHn(dist);
            	s.setGn(cost);
                openL.add(s);

            }
            else
            {
             bet=isBetter(s);
             if(bet)
             { 
                s.setParent(bestCell);
            	s.setHn(dist);
            	s.setGn(cost);

             }
            }          
        }

        if(bestCell.border[NORTH] == NO_WALL) //Cell.border[NORTH] == NO_WALL
        {
        	int position=((bestCell.getRow()*width)+bestCell.getColumn())-width;
            Cell N=cells[position];
            System.out.println("north");
            int dist=manhatten_distance(N,goalCell);
            int cost=bestCell.getGn()+1;

                        System.out.println(N.getHn());

            if(!isInCloseOrOpenLists(N))
            {   N.setParent(bestCell);
        		N.setHn(dist);
        		N.setGn(cost);

                openL.add(N);
            }
            else
            {
             bet=isBetter(N);
             if(bet)
             {
                N.setParent(bestCell);
        		N.setHn(dist);
        		N.setGn(cost);

             }
            }          

        }
        }
        closedL.add(bestCell);
        openL.removeIf(n-> ((n.getRow()==bestCell.getRow())&& (n.getColumn()==bestCell.getColumn())));
       //sorting(openL);
       printt();
        System.out.println("end");
    }while(true);
}

	public void drawSolution(Graphics g) {
		for(int i = 0; i < solution.size(); i++) {
			int row=solution.get(i).getRow();
			int column=solution.get(i).getColumn();
			System.out.println(row+","+column);
			int positionY=row*(MazeGenerator.CELL_WIDTH)+MazeGenerator.MARGIN+5;
			int positionX=column*MazeGenerator.CELL_WIDTH+MazeGenerator.MARGIN+5;

			g.setColor(Color.GREEN);
			g.fillRect(positionX, positionY, MazeGenerator.CELL_WIDTH-5, MazeGenerator.CELL_WIDTH-5);
		}
	}

}

