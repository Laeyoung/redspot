#!/usr/bin/env python

cache = [[0 for x in xrange(101)] for x in xrange(101)] 

def match(W,S,w,s):
    if cache[w][s] != 0:
        return cache[w][s]

    if s < len(S) and w < len(W) and \
            (W[w] == '?' or W[w] == S[s]):
        return match(W,S,w+1,s+1)
    
    if w == len(W):
        cache[w][s] = s == len(S)
        return cache[w][s]

    if W[w] == '*':
        while W[w+1] == '*':
            w+=1

        if match(W,S, w+1, s) or (s < len(S) and match(W,S, w, s+1)):
            cache[w][s] = True
            return True

    cache[w][s] = False
    return False

N = int(raw_input())
for t in range(N):
    W = str(raw_input())
    n = int(raw_input())
    for tt in range(n):
        S = str(raw_input())
        cache = [[0 for x in xrange(101)] for x in xrange(101)] 
        if match(W, S, 0, 0):
            print S
