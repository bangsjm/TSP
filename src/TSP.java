import java.awt.*;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;

public class TSP {

    public static void dfsOuLa(int[][] graph,boolean[] hasPath,Stack<Integer> dfs,Stack<Integer> path,int index,int n,int[] du){
        while (!dfs.empty()) {
            if (hasPath[index]) {
                int i;
                dfs.push(index);
                for (i = 0; i < n; i++) {
                    if ((graph[index][i]>0)&&(graph[index][i]< Integer.MAX_VALUE)) {
                        graph[index][i]=Integer.MAX_VALUE;
//                        graph.get(i).set(index,Integer.MAX_VALUE); 在复制边时不用
                        du[index]--;
                        du[i]--;
                        break;
                    }
                }
                hasPath[index]=(du[index]==0)?false:true;
                hasPath[i]=(du[i]==0)?false:true;
                index=i;
            } else {
                int i = dfs.pop();
                path.push(i);
                index=i;
            }
        }
    }

    public static Stack ouLa(int[][] graph,int n,int begin,int []du){
        Stack dfs=new Stack<Integer>();
        Stack path=new Stack<Integer>();
        boolean[] hasPath=new boolean[n];
        for(int i=0;i<n;i++){
            hasPath[i]=true;
        }
        dfs.push(begin);
        dfsOuLa(graph,hasPath,dfs,path,begin,n,du);
        return path;
    }

    public static int minDistance(int[] distance,int n){
        int min=distance[0];
        int index=0;
        for(int i=1;i<n;i++){
            if(min>distance[i]) {
                min=distance[i];
                index=i;
            }
        }
        return index;
    }

    public static void primTree(int[][] graph,int n,int index,int[] path,int[] distance){
        for(int i=0;i<n;i++){
            if(graph[index][i]>0&&graph[index][i] < Integer.MAX_VALUE){
                if(distance[index]+graph[index][i]<distance[i]) {
                    distance[i] =distance[index] + graph[index][i];
                    path[i]=index;
                    graph[i][index]=Integer.MAX_VALUE;
                }
                graph[i][index]=Integer.MAX_VALUE;
            }
        }
        distance[index]=Integer.MAX_VALUE;
        int I=minDistance(distance,n);
        if(distance[I]==Integer.MAX_VALUE)
        {
            return;
        }
        primTree(graph,n,I,path,distance);
    }

    public static int[] prim(int[][] graph,int n,int index){
        int[] path=new int[n];
        int[] distance=new int[n];
        for(int i=0;i<n;i++) {
            distance[i]=(i==index)?0:Integer.MAX_VALUE;
            path[i]=-1;
        }
        primTree(graph,n,index,path,distance);
        return path;
    }

    public  static void printOuLaPath(Stack path,int n){
        path.pop();
        int [] router=new int[n];
        int [] router1=new int[n];
        int size=path.size();
        int j=1,index;
        for(int i=0;i<size;i++){
            index=(int)path.pop();
            if(router[index]==0){
                router[index]=j++;
            }
        }
        for(int i=0;i<n;i++){
            router1[router[i]-1]=i+1;
        }
        for(int i=0;i<n;i++){
            System.out.print(router1[i]+"->");
        }
        System.out.println(router1[0]);
    }
    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        System.out.println("输入城市个数");
        int cityNum=in.nextInt();
        System.out.println("输入起点城市");
        int beg=in.nextInt()-1;
        int[] du=new int[cityNum];
        int graphA[][]=new int[cityNum][cityNum];
        for(int i=0;i<cityNum;i++) {
            graphA[i][i]=0;
            for(int j=i+1;j<cityNum;j++){
                int a=(int)(Math.random()*100);
                graphA[i][j]=a;
                graphA[j][i]=a;
            }
        }
        int[] pathPrim=prim(graphA,cityNum,beg);
        int[][] graphOuLa=new int[cityNum][cityNum];
        for(int i=0;i<cityNum;i++){
            if(pathPrim[i]!=-1) {
                graphOuLa[i][pathPrim[i]] = 1;
                graphOuLa[pathPrim[i]][i] = 1;
                du[i] += 2;
                du[pathPrim[i]] += 2;
            }
        }
        Stack path=ouLa(graphOuLa,cityNum,beg,du);
        printOuLaPath(path,cityNum);
    }
}

