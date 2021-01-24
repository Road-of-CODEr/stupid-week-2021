#include<iostream>

using namespace std;

#define MAX 100002
 
using namespace std;
 
typedef long long ll;
 
struct INFO {
    ll x, y;
    ll p, q;
    INFO(int x1 = 0, int y1 = 0) : x(x1), y(y1), p(1), q(0) {}
};
 
INFO p[MAX];
INFO temp[MAX];

template <typename T>
class Stack {
public:
    Stack();
    Stack(int);
    void pop(); // pop stack
    void push(const T&); // push data to stack
    int size() {
      return top + 1;
    }
    T Top(); // Get the data top of stack
    bool IsEmpty(); // Check the stack is empty or not
    bool IsFull(); // Check the stack is full or not
private:
    T* stack;
    int top = -1; // points nothing
    int capacity;
};
 
/* Stack Constructor */
template <typename T>
Stack<T>::Stack() {
    capacity = MAX;
    stack = new T[capacity];
}
 
template <typename T>
Stack<T>::Stack(int _capacity) {
    capacity = _capacity;
    stack = new T[capacity];
}
 
template <typename T>
void Stack<T>::pop() {
    if (IsEmpty()) {
        return;
    }
    else {
        top--;
    }
}
 
template <typename T>
void Stack<T>::push(const T& data) {
    if (IsFull()) {
        return;
    }
    else {
        stack[++top] = data;
    }
}
 
template <typename T>
T Stack<T>::Top() {
    T result = 0;
    if (!IsEmpty()) {
        result = stack[top];
    }
    return result;
}
 
template <typename T>
bool Stack<T>::IsEmpty() {
    bool isEmpty = false;
    if (top < 0) {
        isEmpty = true;
    }
    return isEmpty;
}
 
template <typename T>
bool Stack<T>::IsFull() {
    bool isFull = false;
    if (top == capacity - 1) {
        isFull = true;
    }
    return isFull;
}
bool comp(const INFO &A, const INFO &B) {
    if (1LL * A.q * B.p != 1LL * A.p*B.q)
        return 1LL * A.q * B.p < 1LL * A.p*B.q;
 
    if (A.y != B.y)
        return A.y < B.y;
 
    return A.x < B.x;
}
 
void mergeSort(int s, int e) {
  if(s >= e) return;
  int i = s;
  int k = s;
  int mid = (s+e)/2;
  int j = mid+1;
  mergeSort(s, mid);
  mergeSort(mid+1, e);

  while(i <= mid && j <= e) {
    if(comp(p[i], p[j])) temp[k++] = p[i++];
    else temp[k++] = p[j++];
  }
  while(i <= mid) temp[k++] = p[i++];
  while(j <= e) temp[k++] = p[j++];
  for(i = s; i <= e; i ++) {
    p[i] = temp[i];
  }
  return;
}

ll ccw(const INFO &A, const INFO &B, const INFO &C) {
    return 1LL * (A.x * B.y + B.x * C.y + C.x * A.y - B.x * A.y - C.x * B.y - A.x * C.y);
}
 

int N;

int main(int argc, char** argv)
{
	int test_case;
	int T, x, y;

	//freopen("18eval_input.txt", "r", stdin);
	cin>>T;

	for(test_case = 1; test_case <= T; ++test_case)
	{
    cin >> N;
   
    for(int i = 0; i < N; i ++) {
      cin >> x >> y;
      p[i] = INFO(x, y);
    }
    mergeSort(0, N-1);
 
    for (int i = 1; i < N; i++) {
        p[i].p = p[i].x - p[0].x;
        p[i].q = p[i].y - p[0].y;
    }
 
    // 반시계 방향으로 정렬(기준점 제외)
    mergeSort(1, N-1);

    Stack<int> s;
 
    // 스택에 first, second를 넣어준다.
    s.push(0);
    s.push(1);
 
    int next = 2;

    while (next < N) {
        while (s.size() >= 2) {
            int first, second;
            second = s.Top();
            s.pop();
            first = s.Top();
            if (ccw(p[first], p[second], p[next]) > 0) {
                s.push(second);
                break;
            }
        }
 
        s.push(next++);
    }
    cout << "#" << test_case << " " << s.size() << endl;
	}
	return 0;
}
