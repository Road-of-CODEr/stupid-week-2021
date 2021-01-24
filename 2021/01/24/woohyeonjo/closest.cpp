#include <iostream>
 
using namespace std;
#define MAX 100002
 
struct Point {
    long long x, y;
};

long long dist(Point &p, Point &q) {
    return (p.x-q.x)*(p.x-q.x)+(p.y-q.y)*(p.y-q.y);
}
 
bool comp(Point a, Point b, int mode) {
  if(mode == 0) {
    if(a.x < b.x) return true;
    return false;
  }
  else {
    if(a.y < b.y) return true;
    return false;
  }
}

int N;
Point points[MAX];
Point MID[MAX];
Point temp[MAX];

void mergeSort(int s, int e, int mode) {
  if(s >= e) return;
  int i = s;
  int k = s;
  int mid = (s+e)/2;
  int j = mid+1;
  mergeSort(s, mid, mode);
  mergeSort(mid+1, e, mode);

  if(mode == 0) {
    while(i <= mid && j <= e) {
      if(comp(points[i], points[j], mode)) temp[k++] = points[i++];
      else temp[k++] = points[j++];
    }
    while(i <= mid) temp[k++] = points[i++];
    while(j <= e) temp[k++] = points[j++];
    for(i = s; i <= e; i ++) {
      points[i] = temp[i];
    }
  }
  else {
    while(i <= mid && j <= e) {
        if(comp(MID[i], MID[j], mode)) temp[k++] = MID[i++];
        else temp[k++] = MID[j++];
      }
      while(i <= mid) temp[k++] = MID[i++];
      while(j <= e) temp[k++] = MID[j++];
      for(i = s; i <= e; i ++) {
        MID[i] = temp[i];
      }
  }
  return;
}
 
long long closest_pair(Point* p, int n) {
    if (n == 2)
        return dist(p[0], p[1]);
    if (n == 3) {
      long long min = 99999999999;
      min = min < dist(p[0], p[1]) ? min : dist(p[0], p[1]);
      min = min < dist(p[1], p[2]) ? min : dist(p[1], p[2]);
      min = min < dist(p[2], p[0]) ? min : dist(p[2], p[0]);
      return min;
    }
    long long line = (p[n/2 - 1].x + p[n/2].x) / 2;
    long long d = 99999999999;
    long long a = closest_pair(p, n/2);
    long long b = closest_pair(p + n/2, n - n/2);
    d = d < a ? d : a;
    d = d < b ? d : b;
     
    int mid_sz = 0;
    for (int i = 0; i < n; i++) {
        int t = line - p[i].x;
        if (t*t < d)
            MID[mid_sz++] = p[i];
    }
 
    mergeSort(0, mid_sz-1, 1);
     
    for (int i = 0; i < mid_sz - 1; i++)
        for (int j = i + 1; j < mid_sz && (MID[j].y - MID[i].y)*(MID[j].y - MID[i].y) < d; j++) {
            d = d < dist(MID[i], MID[j]) ? d : dist(MID[i], MID[j]);
        }
     
    return d;
}
 

int main(void) {
    
    int T;    
    int test_case;

    cin >> T;
    for(test_case = 1; test_case <= T; ++test_case)
	{
    
    cin >> N;
    
    for(int i = 0; i < N; i ++) {
      cin >> points[i].x >> points[i].y;
    }
     mergeSort(0, N-1, 0);
     cout << "#" << test_case << " ";
    cout << closest_pair(points, N) << endl;
  }
  return 0;
}
