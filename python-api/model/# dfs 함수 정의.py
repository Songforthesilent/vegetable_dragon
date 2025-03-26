import sys
sys.setrecursionlimit(10 ** 4)
input = sys.stdin.readline

# dfs 함수 정의
def dfs(graph, start, visited):
    if visited[start]:
        return 0
    
    visited[start] = True

    count = 1  # 해킹대상에 자기 자신도 포함하기 위해 cnt = 1

    for next_node in graph[start]:
        if not visited[next_node]:
            count += dfs(graph, next_node, visited)

    return count

# 역방향 그래프 생성 ( A <- B )
def find_most_hackable_computers(N, M, trust_relations):
    # N개의 개수만큼 컴퓨터(i) 존재
    graph = {i: [] for i in range(1, N + 1)}
    for A, B in trust_relations:
        graph[B].append(A)  # B를 해킹하면 A도 해킹됨 -> B에 A를 더해줌

    max_hack = 0
    result = []

    for i in range(1, N + 1):
        visited = [False] * (N + 1)# 방문한 노드에 대해 집합을 사용하여 추적
        hacked_count = dfs(graph, i, visited)
        
        if hacked_count > max_hack:
            max_hack = hacked_count

        result.append(hacked_count)

    return result, max_hack


# 입력 처리
N, M = map(int, input().split())
trust_relations = [tuple(map(int, input().split())) for _ in range(M)]

# 결과 출력
re, max_cnt = find_most_hackable_computers(N, M, trust_relations)
for i in range(len(re)):
    if re[i] == max_cnt:
        print(i+1, end=' ')