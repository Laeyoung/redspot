#!/usr/bin/env python

cache = {}
def match(W,S,w,s):
    if (w,s) in cache:
        return cache[(w,s)]

    if s < len(S) and w < len(W) and \
            (W[w] == '?' or W[w] == S[s]):
        return match(W,S,w+1,s+1)
    
    if w == len(W):
        cache[(w,s)] = s == len(S)
        return cache[(w,s)]

    if W[w] == '*':
        if match(W,S, w+1, s) or \
                (s < len(S) and match(W,S, w, s+1)):
            cache[(w,s)] = True
            return True 

    cache[(w,s)] = False
    return False

N = int(raw_input())
for t in range(N):
    W = str(raw_input())
    n = int(raw_input())
    result = []
    for tt in range(n):
        cache.clear()
        S = str(raw_input())
        if match(W, S, 0, 0):
            result.append(S)
    for x in sorted(result):
        print x
