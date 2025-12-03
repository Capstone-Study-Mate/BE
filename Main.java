import java.io.*;
import java.util.*;

public class Main {
    static String [][] map;
    static boolean [][] visited;
    static int result=0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st=new StringTokenizer(br.readLine());

        int R=Integer.parseInt(st.nextToken());
        int C=Integer.parseInt(st.nextToken());
        int startx=0, starty=0;
        map=new String[R][C];
        visited=new boolean[R][C];

        for(int i=0;i<R;i++){
            st=new StringTokenizer(br.readLine());
            for(int j=0;j<C;j++){
                map[i][j]=st.nextToken();
                if(map[i][j].equals("J")){
                    startx=i;
                    starty=j;
                }
            }
        }
        dfs(startx, starty);

        bw.write(result+"\n");

        br.close();
        bw.flush();
        bw.close();
    }

    static void dfs(int x,int y){
        visited[x][y]=true;

    }
}