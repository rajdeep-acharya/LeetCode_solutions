class Solution {
    public int maximumInvitations(int[] favorite) {
        int pairMax = 0;
        int maxCycle = 0;
        List[] graph = new List[favorite.length];
        List<Integer> pairs = new ArrayList<>();
        boolean[] visited = new boolean[favorite.length];
        
        for (int i = 0; i < favorite.length; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < favorite.length; i++) {
            if (visited[i]){
                continue;
            }
            if (i == favorite[favorite[i]]) {
                pairs.add(i);
                visited[favorite[i]] = true;
            } else {
                graph[favorite[i]].add(i);
            }
        }
        
        for (int pair: pairs) {
            pairMax += dfs(graph, pair, visited) + dfs(graph, favorite[pair], visited);
        } 
        
        int[] curCycle  = new int[favorite.length];
        int[] round  = new int[favorite.length];
        int curRound = 1;
        for (int i = 0; i < favorite.length; i++) {
            if (visited[i]) {
                continue;
            }
            if (round[i] != 0) {
                continue;
            }
            int tracker = 1;
            int cur = i;
            while (curCycle[cur] == 0) {
                round[cur] = curRound;
                curCycle[cur] = tracker++;
                cur = favorite[cur];
            }
            if (round[cur] == curRound) {
                maxCycle = Math.max(maxCycle, tracker - curCycle[cur]);
            }
            curRound++;
        }
        return Math.max(maxCycle, pairMax);
    }
    
    private int dfs(List[] graph, int node, boolean[] visited) {
        visited[node] = true;
        int max = 0;
        for (int neighbor: (List<Integer>)graph[node]) {
            max = Math.max(max, dfs(graph, neighbor, visited));
        }
        return max + 1;
    }
}