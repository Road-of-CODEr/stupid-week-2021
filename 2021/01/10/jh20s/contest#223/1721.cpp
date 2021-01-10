

class Solution {
public:

ListNode* swapNodes(ListNode* head, int k) {
	ListNode* l, *r;
	ListNode* start = head;
	int i = 0;
	int len;
	while (start!=NULL) {
		if (i == k-1) {
			l = start;
		}
		i++;
		start = start->next;
	}
	start = head;
	len = i;
	i = 0;
	while (start != NULL) {
		if (i == len -k) {
			r = start;
		}
		i++;
		start = start->next;
	}
	int temp = l->val;
	l->val = r->val;
	r->val = temp;
	return head;
}
};