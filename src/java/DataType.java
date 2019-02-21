/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nishant
 */
public class DataType {
    
}
class newClass
{
    int lakeId;
    int ganeshaId;
    int src;
    boolean isLake;
}

class Ganesha
{
    int ganeshaId;
    double lng;
    double lan;
}
class Lake
{
    int lakeId;
    double lan;
    double lng;
}

class GaneshaLakeId{
    public transient int ganesha=0;
    public int lakeId=0;
}

class GaneshaLakeDist{
    public transient int ganesha=0;
    public int lake=0;
    public int distance = 0;
}

class GaneshaGaneshaDist{
    public transient int ganesha1=0;
    public int ganesha2=0;
    public int distance = 0;
}
