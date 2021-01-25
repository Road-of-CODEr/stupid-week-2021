/**
 * Definition for singly-linked list.
 * function ListNode(val, next) {
 *     this.val = (val===undefined ? 0 : val)
 *     this.next = (next===undefined ? null : next)
 * }
 */
/**
 * @param {ListNode} head
 * @param {number} k
 * @return {ListNode}
 */
const findLen = (node) => {
  if (!node) return 0;
  return findLen(node.next) + 1;
};
const swap = (node, index, val = 0) => {
  if (index === 1) {
    const cur = node.val;
    node.val = val;
    return cur;
  }
  return swap(node.next, index - 1, val);
};

const swapNodes = (head, k) => {
  const len = findLen(head);
  const lastIndex = len - k + 1;
  if (k === lastIndex || len === 1) return head;

  const prev = swap(head, k);
  const next = swap(head, lastIndex, prev);
  swap(head, k, next);

  return head;
};
