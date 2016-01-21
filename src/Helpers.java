/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cumimpactsa;

import java.util.ArrayList;

/**
 *@summary This class provides a collection of helper functions.
 * @author ast
 */
public abstract class Helpers 
{
    

   public final static String OK = "ok"; 
   private static String error = OK;
    
    //low pass filter as moving average
    public static DataGrid lowPassFilter(DataGrid grid, int kernelSize)
    {
        double[][] data = grid.getData();
        double[][] filtered=MappingProject.grid.getEmptyGrid();
        double max = 0;
        for(int x=0; x<data.length;x++)
        {
            for(int y=0; y<data[0].length; y++)
            {
                if(data[x][y]!=grid.getNoDataValue())
                {
                    int startX=Math.max(0, x-kernelSize);
                    int endX = Math.min(x+kernelSize, data.length-1);
                    int startY = Math.max(0, y-kernelSize);
                    int endY = Math.min(y+kernelSize, data[0].length-1);
                    
                    double valueSum=0;
                    int valueCount = 0;
                    
                    for(int rx=startX; rx<=endX; rx++)
                    {
                        for(int ry=startY; ry<=endY; ry++)
                        {
                            if(data[rx][ry]!=grid.getNoDataValue())
                            {
                                valueSum+=data[rx][ry];
                                valueCount++;
                            }
                        }
                    }
                    
                    filtered[x][y]=valueSum/valueCount;
                    if(filtered[x][y]>max) {max=filtered[x][y];}
                }
                
            }
        }
        
        return new DataGrid(filtered,max,0,grid.getNoDataValue());
        
    }
   
    public static double[] stringListToDoubleArray(ArrayList<String> stringList)
    {
        double[] numList = new double[stringList.size()];
        error = OK;
        
        try
        {
            for(int i = 0; i < stringList.size(); i++)
            {
       
                numList[i] = Double.parseDouble(stringList.get(i));
            }
        }
        
        catch(Exception e)
        {
            error = "Error while parsing numbers from text. //// " + e.getMessage() + " //// " + e.getStackTrace();
        }
        
        
        return numList;
    }
    
    public static String[] stringListToStringArray(ArrayList<String> stringList)
    {
        String[] strings = new String[stringList.size()];
        error = OK;
        
        try
        {
            for(int i = 0; i < stringList.size(); i++)
            {
                strings[i] = stringList.get(i);
            }
        }
        
        catch(Exception e)
        {
            error = "Error while extracting strings from list. //// " + e.getMessage() + " //// " + e.getStackTrace();
        }
        
        
        return strings;
    }
    
    /**
     * @summary Returns last function call's error message. If no error, returns Helpers.OK.
     * @return Error string.
     */
    public static String getError()
    {
        return error;
    }
    
    public static double[][] deepArrayCopy(double[][] original)
    {
        double[][] copy = original.clone();
        for (int i = 0; i < copy.length; i++) 
        {
            copy[i] = copy[i].clone();
        }
        return copy;
    }
    
}
