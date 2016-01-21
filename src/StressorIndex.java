/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cumimpactsa;

import java.util.ArrayList;

/**
 *
 * @author ast
 */
public class StressorIndex extends SpatialDataLayer
{
    public StressorIndex(String saveFileName)
    {
        super(GlobalResources.getDateTime()+" Stressor index (unweighted)",null,GlobalResources.DATATYPE_SPATIAL,null);
        source = new DataSourceInfo();
        source.sourceFile=saveFileName;
        source.xField="x";
        source.yField="y";
        source.valueField="value";
        
        this.type=GlobalResources.DATATYPE_SPATIAL;
        
        //now create internal data grid
        double[][] data = new double[MappingProject.grid.getDimensions().x][MappingProject.grid.getDimensions().y];
        
        //load all processed grids
        ArrayList<double[][]> stressorDataList = new ArrayList<double[][]>();
        for(int l=0;l<MappingProject.stressors.size();l++)
        {
            stressorDataList.add(MappingProject.stressors.get(l).getProcessedGrid().getData());
        }
        
        //sum them up
        double max=0;
        double min=1;
        for(int l=0; l<stressorDataList.size();l++)
        {
            MappingProject.processingProgressPercent=(int) (100*l/stressorDataList.size());
            double[][] stressorData=stressorDataList.get(l);
            for(int y=0;y<data[0].length;y++)
            {
                for(int x=0;x<data.length;x++)
                {
                    if(stressorData[x][y]==GlobalResources.NODATAVALUE || data[x][y]==GlobalResources.NODATAVALUE)
                    {
                        data[x][y]=GlobalResources.NODATAVALUE;
                    }
                    else
                    {
                        data[x][y]=data[x][y]+stressorData[x][y];
                        if(data[x][y]>max) {max=data[x][y];}
                        if(data[x][y]<min) {min=data[x][y];}
                    }
                }
            }
        }
        
       grid = new DataGrid(data,max,min,GlobalResources.NODATAVALUE);
       
    }
}
